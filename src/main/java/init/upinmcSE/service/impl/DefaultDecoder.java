package init.upinmcSE.service.impl;

import init.upinmcSE.p2p.RPC;
import init.upinmcSE.service.Decoder;

import java.io.InputStream;
import java.net.Socket;

/**
 * Minimal decoder: read first byte to detect stream or message.
 * For non-stream, read up to 1028 bytes
 * NOTE: This is a simple codec for demo/testing.
 */
public class DefaultDecoder implements Decoder {
    @Override
    public RPC decode(Socket socket) throws Exception {
        InputStream input = socket.getInputStream();

        int marker = input.read();

        if (marker == -1) throw new IllegalStateException("EOF");

        return null;
    }
}
