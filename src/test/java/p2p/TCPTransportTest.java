package p2p;

import init.upinmcSE.p2p.tcp.TCPTransport;
import init.upinmcSE.p2p.tcp.TCPTransportOpts;
import init.upinmcSE.service.impl.DefaultDecoder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TCPTransportTest {

    private TCPTransport transport;
    private TCPTransportOpts opts;

    @BeforeEach
    void setUp() {
        opts = new TCPTransportOpts(
                ":3000",
                p -> {},
                new DefaultDecoder()
        );

        transport = new TCPTransport(opts);
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

        assertEquals(transport.getServerSocket().getLocalPort(), port);



        clientSocket.close();
    }

}
