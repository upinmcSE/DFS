package init.upinmcSE.p2p.tcp;

import init.upinmcSE.p2p.Peer;
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
import java.util.concurrent.*;

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
    private BlockingQueue<RPC> rpcQueue = new LinkedBlockingQueue<>(1024);
    private ExecutorService executor = Executors.newCachedThreadPool();
    private ConcurrentMap<String, Peer> peers = new ConcurrentHashMap<>();

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

    /**
     * Consume implements the Transport interface, which will return read-only channel
     * for reading the incoming messages received from another peer in the network.
     */
    @Override
    public BlockingQueue<RPC> consume() {
        return this.rpcQueue;
    }

    private void handleConn(Socket socket, boolean outbound) {
        try{
            TCPPeer peer = new TCPPeer(socket, outbound);
            log.info("new incoming connection from {} , {}", peer.getConn(), peer.isOutbound());

            // handshake
            if(this.opts.getHandshakeFunc() != null){
                this.opts.getHandshakeFunc().handshake(peer);
            }

            if (opts.getOnPeer() != null) {
                opts.getOnPeer().handle(peer);
            }

            peers.put(socket.getRemoteSocketAddress().toString(), peer);

            // read and decode
            while (!socket.isClosed()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                RPC rpc = this.opts.getDecoder().decode(socket);
                rpc.setFrom(socket.getRemoteSocketAddress().toString());
                log.info("message: {} {} {}", rpc.getFrom(), rpc.getPayload(), rpc.isStream());

                // push into queue (blocks if full)
                rpcQueue.put(rpc);
            }
        }catch(Exception e){
            log.error("dropping peer connection: {}", e.getMessage());
        } finally {
            try { socket.close(); } catch (Exception ignored) {}
        }
    }

    @Override
    public void close() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
        executor.shutdownNow();
    }
}
