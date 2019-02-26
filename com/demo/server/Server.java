package com.demo.server;


import com.demo.msg.TestMessage;
import com.google.protobuf.ByteString;
import org.apache.commons.codec.binary.Base64;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(8080);
        System.out.println("server started...");
        Socket socket = ss.accept();
        System.out.println("a client connected!");
        TestMessage.Words words = TestMessage.Words.parseFrom(ByteString.readFrom(socket.getInputStream()));
        if(words != null) {
            String s = words.toString();
            System.out.println("server received data:\n" + s);
            byte[] res = Base64.encodeBase64(s.getBytes());
            String result = new String(res);
            System.out.println("server sending Base64 data:\n" + result);

            Socket sendSocket = new Socket("localHost",9090);
            TestMessage.Words message = TestMessage.Words.newBuilder().setMessage(result).build();
            OutputStream os = sendSocket.getOutputStream();
            os.write(message.toByteArray());
            os.flush();
            os.close();
        }
    }

}