package com.demo.client;


import com.demo.msg.TestMessage;
import com.google.protobuf.ByteString;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Client {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8080);
        String testMessage = "this is a test";
        TestMessage.Words message = TestMessage.Words.newBuilder().setMessage(testMessage).build();
        OutputStream os = socket.getOutputStream();
        os.write(message.toByteArray());
        os.flush();
        os.close();
        System.out.println("client message sent");
        System.out.println("the message is " + testMessage);
        ServerSocket ss = new ServerSocket(9090);
        Socket receiveSocket = ss.accept();
        TestMessage.Words received = TestMessage.Words.parseFrom(ByteString.readFrom(receiveSocket.getInputStream()));
        if(received != null) {
            String s = received.toString();
            System.out.println("client received data:\n" + s);
        }
    }

}