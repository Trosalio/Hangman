package main.server.controllers;

import main.server.models.ServerManager;

/**
 * Project Name: HangmanClient
 */

public class MainController {

    private  ServerManager manager;

    public void setUpContent() {
    }

    public void setManager(ServerManager manager) {
        this.manager = manager;
    }
}
