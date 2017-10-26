package server.models;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Project Name: HangmanServer
 */

public class ServerConnection {

    private ServerManager serverLog;
    private ServerSocket serverSocket;
    private Socket socket;
    private boolean serverStatus;
    private TextArea logTextArea;
    private int port;

    public ServerConnection(int port) {
        this.port = port;
    }

    public void setServerLog(ServerManager serverLog) {
        this.serverLog = serverLog;
    }

    public void establishConnection() {
        createServer(port);
        serverStatus = true;
        new Thread(new ServerRunnable()).start();
    }

    private void createServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("No free port available");
        }
    }

    public void disconnect() {
        try {
            serverStatus = false;
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveLogControl(TextArea logTextArea) {
        this.logTextArea = logTextArea;
    }

    private class ServerRunnable implements Runnable {

        @Override
        public void run() {
            logTextArea.appendText("Waiting for Connection on port " + serverSocket.getLocalPort() + "...\n");
            try {
                socket = serverSocket.accept();
                logTextArea.appendText("Connection received from: " + socket.getRemoteSocketAddress() + "\n");
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                while (serverStatus) {
                    socket.setTcpNoDelay(true);
                }
                logTextArea.appendText("Close Connection\n");
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Could not create listening socket / No connection from client");
            }
        }
    }
}
