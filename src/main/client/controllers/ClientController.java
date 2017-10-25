package main.client.controllers;

import main.client.models.ClientConnection;

/**
 * Project Name: HangmanClient
 */

public class ClientController {

    private ClientConnection clientConnection;

    public void setUpContent() {
    }

    public void setConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }
}
