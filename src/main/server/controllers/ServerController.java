package main.server.controllers;

import main.server.models.ServerRunnable;

/**
 * Project Name: HangmanClient
 */

public class ServerController {

    private ServerRunnable serverRunnable;
    public void setUpContent() {
    }

    public void setConnection(ServerRunnable serverRunnable) {
        this.serverRunnable = serverRunnable;
    }
}
