package init.upinmcSE.p2p.tcp;

import init.upinmcSE.p2p.Peer;
import lombok.Getter;
import lombok.Setter;

import java.net.Socket;

/**
 * TCPPeer represents the remote node over a TCP established connection
 * */
@Getter
@Setter
public class TCPPeer implements Peer {

    /**
     * conn is the underlying connection of the peer
     * */
    private Socket conn;

    /**
     * if we dial and retrieve a conn => true
     * if we accept and retrieve a conn => false
     * */
    private boolean outbound;

    public TCPPeer(Socket conn, boolean outbound) {
        this.conn = conn;
        this.outbound = outbound;
    }
}
