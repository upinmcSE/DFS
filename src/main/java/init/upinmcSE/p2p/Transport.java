package init.upinmcSE.p2p;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public interface Transport extends Closeable {
    void listenAndAccept() throws IOException;
    BlockingQueue<RPC> consume();
}
