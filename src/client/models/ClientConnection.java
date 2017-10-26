package client.models;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Project Name: HangmanClient
 */

public class ClientConnection {

    private String ip;
    private int port;
    private Socket clientSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ClientConnection(String ip, int port) {
        this.ip = ip;
        this.port = port;
        createSocket();
    }

    private void createSocket(){
        try (Socket socket = new Socket(ip, port);
             InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()){
            clientSocket = socket;
            inputStream = in;
            outputStream = out;
            clientSocket.setTcpNoDelay(true);
        } catch (IOException e) {
            //e.printStackTrace();
            System.err.println("Cannot find port: " + port + " On this IP: " + ip);
        }
    }
}
