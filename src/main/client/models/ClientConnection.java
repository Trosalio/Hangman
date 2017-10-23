package main.client.models;

import main.models.NetworkConnection;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Project Name: HangmanClient
 */

public class ClientConnection extends NetworkConnection {

    private String ip;
    private int port;

    public ClientConnection(String ip, int port, Consumer<Serializable> onReceivedCallback) {
        super(onReceivedCallback);
        this.ip = ip;
        this.port = port;
    }

    // Is not a server, returns false
    @Override
        protected boolean isServer() {
            return false;
        }

    // Client need to fetch the port, so client returns this.ip
        @Override
        protected String getIP() {
            return ip;
        }

        @Override
        protected int getPort() {
            return port;
        }
    }
