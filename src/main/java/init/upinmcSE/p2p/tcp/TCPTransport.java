package init.upinmcSE.p2p.tcp;

import init.upinmcSE.p2p.HandshakeFunc;
import init.upinmcSE.p2p.Transport;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
@Setter
@Slf4j(topic = "TCPTransport")
public class TCPTransport implements Transport {
    private int listenAddress;
    private ServerSocket serverSocket;
    private HandshakeFunc handshakeFunc;
    private ExecutorService executor = Executors.newCachedThreadPool();

    public TCPTransport(int listenAddress, HandshakeFunc handshakeFunc) {
        this.listenAddress = listenAddress;
        this.handshakeFunc = handshakeFunc;
    }

    @Override
    public void listenAndAccept() throws IOException {
        this.serverSocket = new ServerSocket(this.listenAddress);

        executor.submit(() -> {
            while (!serverSocket.isClosed()) {
                try {
                    Socket socket = serverSocket.accept();
                    executor.submit(() -> handleConn(socket, false));
                } catch (IOException e) {
                    if (serverSocket.isClosed()) break;
                    e.printStackTrace();
                }
            }
        });
    }

    private void handleConn(Socket socket, boolean outbound) {
        TCPPeer peer = new TCPPeer(socket, outbound);
        log.info("new incoming connection from {} , {}", peer.getConn(), peer.isOutbound());

        // handshake

        // read and decode

    }
}
