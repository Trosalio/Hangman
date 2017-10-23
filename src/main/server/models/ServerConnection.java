package main.server.models;

import main.models.NetworkConnection;
import main.server.models.persistents.DBManager;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Project Name: HangmanServer
 */

public class ServerConnection extends NetworkConnection{

    private DBManager dbManager;
    private int port;

    public ServerConnection(int port, Consumer<Serializable> onReceivedCallback) {
        super(onReceivedCallback);
        this.port = port;
    }

    // Is a server, returns true
    @Override
    protected boolean isServer() {
        return true;
    }

    // Server does not need to fetch the port, so server
    @Override
    protected String getIP() {
        return null;
    }

    @Override
    protected int getPort() {
        return port;
    }

    public void setDBManager(DBManager dbManager){
        this.dbManager = dbManager;
    }
}
