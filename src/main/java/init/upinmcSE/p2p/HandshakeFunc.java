package init.upinmcSE.p2p;

import java.io.IOException;

@FunctionalInterface
public interface HandshakeFunc {
    void handshake(Peer peer) throws IOException;
}

