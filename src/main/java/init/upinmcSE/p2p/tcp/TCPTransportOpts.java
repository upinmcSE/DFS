package init.upinmcSE.p2p.tcp;

import init.upinmcSE.p2p.HandshakeFunc;
import init.upinmcSE.p2p.OnPeerHandler;
import init.upinmcSE.service.Decoder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TCPTransportOpts {
    private String listenAddr; // e.g. ":3000" or "localhost:3000" or "0.0.0.0:3000"
    private HandshakeFunc handshakeFunc;
    private Decoder decoder;
    private OnPeerHandler onPeer;

    public TCPTransportOpts(
            String listenAddr,
            HandshakeFunc handshakeFunc,
            Decoder decoder,
            OnPeerHandler onPeer) {
        this.listenAddr = listenAddr;
        this.handshakeFunc = handshakeFunc;
        this.decoder = decoder;
        this.onPeer = onPeer;
    }
}
