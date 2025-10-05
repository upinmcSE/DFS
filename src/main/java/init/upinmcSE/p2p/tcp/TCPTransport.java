package init.upinmcSE.p2p.tcp;

import init.upinmcSE.p2p.RPC;
import init.upinmcSE.p2p.Transport;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TCPTransport implements Transport interface using ServerSocket/Socket.
 * It manages an internal BlockingQueue<RPC>
 */

@Getter
@Setter
@Slf4j(topic = "TCPTransport")
public class TCPTransport implements Transport {
    private TCPTransportOpts opts;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newCachedThreadPool();

    public TCPTransport(TCPTransportOpts opts) {
        this.opts = opts;
    }

    @Override
    public void listenAndAccept() throws IOException {
        int port;
        if (opts.getListenAddr().startsWith(":")) {
            port = Integer.parseInt(opts.getListenAddr().substring(1));
        } else {
            String[] parts = opts.getListenAddr().split(":");
            port = Integer.parseInt(parts[1]);
        }
        serverSocket = new ServerSocket(port);
        executor.submit(this::startAcceptLoop);
        System.out.printf("TCP transport listening on %s%n", opts.getListenAddr());
    }

    private void startAcceptLoop() {
        while (!serverSocket.isClosed()) {
            try {
                Socket conn = serverSocket.accept();
                executor.submit(() -> handleConn(conn, false));
            } catch (IOException e) {
                if (serverSocket.isClosed()) return;
                e.printStackTrace();
            }
        }
    }

    private void handleConn(Socket socket, boolean outbound) {
        try{
            TCPPeer peer = new TCPPeer(socket, outbound);
            log.info("new incoming connection from {} , {}", peer.getConn(), peer.isOutbound());

            // handshake
            if(this.opts.getHandshakeFunc() != null){
                this.opts.getHandshakeFunc().handshake(peer);
            }

            // read and decode
            while (!socket.isClosed()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String message;
                while ((message = reader.readLine()) != null) {
                    log.info("Received message {}", message);
                }
            }
        }catch(Exception e){
            log.error("dropping peer connection: {}", e.getMessage());
        } finally {
            try { socket.close(); } catch (Exception ignored) {}
        }

    }
}
