package init.upinmcSE.p2p;

import java.io.IOException;

@FunctionalInterface
public interface HandshakeFunc {
    boolean handshake(Peer peer) throws IOException;
}

