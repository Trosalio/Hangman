package main.server.models;

import main.server.models.persistents.DBManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Project Name: HangmanServer
 */

public class ServerRunnable implements Runnable {

    private DBManager dbManager;
    private ServerSocket server;
    private Socket serverSocket;

    public ServerRunnable(int port) {
        createServer(port);
    }

    private void createServer(int port) {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("No free port available");
        }
    }

    private void createSocket() {
        System.out.println("Waiting for Connection...");
        try (Socket socket = server.accept();
             InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()) {
            serverSocket = socket;
            System.out.println("Connection received from: " + serverSocket.getInetAddress().getCanonicalHostName());
            serverSocket.setTcpNoDelay(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not create listening socket / No connection from client");
        }
    }

    public void setDBManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void run() {
        while(true){
            createSocket();
        }
    }
}
