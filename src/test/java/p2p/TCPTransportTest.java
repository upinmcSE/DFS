package p2p;

import init.upinmcSE.p2p.tcp.TCPTransport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TCPTransportTest {

    private TCPTransport transport;

    @BeforeEach
    void setUp() {
        transport = new TCPTransport(3333);
    }

    @AfterEach
    void tearDown() throws IOException {
        if (transport != null && transport.getServerSocket() != null && !transport.getServerSocket().isClosed()) {
            transport.getServerSocket().close();
        }
    }

    @Test
    void testServerAcceptsConnection() throws Exception {
        transport.listenAndAccept();

        int port = transport.getServerSocket().getLocalPort();

        Socket clientSocket = new Socket("localhost", port);

        TimeUnit.MILLISECONDS.sleep(200);

        assertTrue(clientSocket.isConnected(), "Client should be connected to server");

        clientSocket.close();
    }

    @Test
    void testServerClosesGracefully() throws Exception {
        transport.listenAndAccept();

        int port = transport.getServerSocket().getLocalPort();

        transport.getServerSocket().close();

        TimeUnit.MILLISECONDS.sleep(100);

        assertTrue(transport.getServerSocket().isClosed(), "Server socket should be closed");
    }
}
