package init.upinmcSE;

import init.upinmcSE.p2p.tcp.TCPTransport;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {
        TCPTransport tr = new TCPTransport(3000, null);

        tr.listenAndAccept();
    }
}