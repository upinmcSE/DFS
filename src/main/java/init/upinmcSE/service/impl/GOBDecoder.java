package init.upinmcSE.service.impl;

import init.upinmcSE.p2p.RPC;
import init.upinmcSE.service.Decoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Socket;

@Service
public class GOBDecoder implements Decoder {
    @Override
    public RPC decode(Socket socket) throws IOException {
        return null;
    }
}
