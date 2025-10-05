package init.upinmcSE.p2p;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * RPC holds any arbitrary data that is being sent over
 * each transport between two nodes in the network.
 * */

@Getter
@Setter
public class RPC implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String from;
    private String payload;
    private boolean stream;
}
