package main;

import main.server.models.ServerRunnable;
import main.server.models.persistents.DBManager;
import main.server.models.persistents.SQLiteConnector;

/**
 * Project Name: Hangman
 */

public class ServerMain {
    public static void main(final String[] args) {
        ServerRunnable serverRunnable = new ServerRunnable(1337);
        serverRunnable.setDBManager(new DBManager(new SQLiteConnector()));
        Thread serverThread = new Thread(serverRunnable);
        serverThread.start();
    }
}
