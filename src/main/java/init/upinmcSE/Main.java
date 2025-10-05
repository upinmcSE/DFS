package init.upinmcSE;

import init.upinmcSE.p2p.tcp.TCPTransport;
import init.upinmcSE.p2p.tcp.TCPTransportOpts;
import init.upinmcSE.service.impl.GOBDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {
        TCPTransportOpts tcpOpts = new TCPTransportOpts(
                ":3000",
                h -> {}, // NOP
                new GOBDecoder());

        TCPTransport tr = new TCPTransport(tcpOpts);

        tr.listenAndAccept();
    }
}