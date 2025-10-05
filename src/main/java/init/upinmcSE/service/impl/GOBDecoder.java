package init.upinmcSE.service.impl;

import init.upinmcSE.p2p.RPC;
import init.upinmcSE.service.Decoder;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class GOBDecoder implements Decoder {
    @Override
    public RPC decode(Socket socket) throws Exception {
        InputStream input = socket.getInputStream();

        ObjectInputStream ois = new ObjectInputStream(input);
        Object obj = ois.readObject();
        if (obj instanceof RPC) {
            return (RPC) obj;
        } else {
            throw new IllegalStateException("Expected RPC object");
        }
    }
}
