package init.upinmcSE.service;

import init.upinmcSE.p2p.RPC;

import java.io.IOException;
import java.net.Socket;

public interface Decoder {
    RPC decode(Socket socket) throws IOException;
}
