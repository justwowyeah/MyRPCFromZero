package com.justwowyeah.MyRPCVersion1.client;

import com.justwowyeah.MyRPCVersion1.common.RPCRequest;
import com.justwowyeah.MyRPCVersion1.common.RPCResponse;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.IOException;

public class IOClient {
    public static RPCResponse sendRequest(String host, int port, RPCRequest request) {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream objectoutputstream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream objectinputstream = new ObjectInputStream(socket.getInputStream())) {
            objectoutputstream.writeObject(request);
            objectoutputstream.flush();
            RPCResponse response = (RPCResponse)objectinputstream.readObject();
            return response;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
