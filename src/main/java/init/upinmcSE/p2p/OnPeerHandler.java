package init.upinmcSE.p2p;

@FunctionalInterface
public interface OnPeerHandler {
    void handle(Peer peer);
}
