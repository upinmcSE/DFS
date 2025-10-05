package init.upinmcSE.p2p.tcp;

import init.upinmcSE.p2p.HandshakeFunc;
import init.upinmcSE.service.Decoder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TCPTransportOpts {
    private String listenAddr; // e.g. ":3000" or "localhost:3000" or "0.0.0.0:3000"
    private HandshakeFunc handshakeFunc;
    private Decoder decoder;

    public TCPTransportOpts(String listenAddr, HandshakeFunc handshakeFunc, Decoder decoder) {
        this.listenAddr = listenAddr;
        this.handshakeFunc = handshakeFunc;
        this.decoder = decoder;
    }
}
