package init.upinmcSE.service;

import init.upinmcSE.p2p.RPC;

import java.net.Socket;

public interface Decoder {
    /**
     * Decode an RPC object from Socket.
     * Return an RPC or throw exception on serious error.
     * If stream frame is detected, return RPC with stream=true and payload null.
     */
    RPC decode(Socket socket) throws Exception;
}
