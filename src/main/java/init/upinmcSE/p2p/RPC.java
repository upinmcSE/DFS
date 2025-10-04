package init.upinmcSE.p2p;

import lombok.Getter;
import lombok.Setter;

/**
 * RPC holds any arbitrary data that is being sent over
 * each transport between two nodes in the network.
 * */

@Getter
@Setter
public class RPC {
    private String from;
    private String payload;
    private boolean stream;
}
