package init.upinmcSE.service.impl;

import init.upinmcSE.p2p.Constants;
import init.upinmcSE.p2p.RPC;
import init.upinmcSE.service.Decoder;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class GOBDecoder implements Decoder {
    @Override
    public RPC decode(Socket socket) throws Exception {
        InputStream input = socket.getInputStream();
        RPC msg = new RPC();

        int peek = input.read();
        if (peek == -1) return null;

        boolean isStream = (byte) peek == Constants.INCOMING_STREAM;
        if(isStream){
            msg.setStream(true);
            return msg;
        }

        byte[] buf = new byte[1028];
        int n = input.read(buf);
        if (n == -1) {
            throw new IOException("End of stream reached unexpectedly");
        }

        byte[] payload = new byte[n];
        System.arraycopy(buf, 0, payload, 0, n);
        msg.setPayload(payload);

        return msg;
    }
}
