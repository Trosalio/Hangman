package main.server.controllers;

import main.server.models.ServerConnection;

/**
 * Project Name: HangmanClient
 */

public class MainController {

    private ServerConnection serverConnection;
    public void setUpContent() {
    }

    public void setConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }
}
