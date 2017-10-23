package main.models;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * Project Name: Hangman
 */
public abstract class NetworkConnection {

    private Consumer<Serializable> onReceivedCallback;
    private ConnectionRunnable connectionRunnable;
    private Thread connectionThread;

    public NetworkConnection(Consumer<Serializable> onReceivedCallback) {
        this.onReceivedCallback = onReceivedCallback;
        connectionRunnable = new ConnectionRunnable();
        connectionThread = new Thread(connectionRunnable);

    }

    public void openConnection() throws Exception {
        connectionThread.setDaemon(true);
        connectionThread.start();
        System.out.println("Open Socket: " + (connectionRunnable.socket == null));
    }

    public void closeConnection() throws Exception {
        System.out.println("Close Socket: " + (connectionRunnable.socket == null));
        connectionRunnable.socket.close();
    }

    public void sendData(Serializable data) throws Exception {
        connectionRunnable.outputStream.writeObject(data);
    }

    protected abstract boolean isServer();
    protected abstract String getIP();
    protected abstract int getPort();

    private class ConnectionRunnable implements Runnable {

        private ServerSocket serverSocket;
        private Socket socket;
        private ObjectOutputStream outputStream;

        @Override
        public void run() {
            try (ServerSocket server = isServer() ? new ServerSocket(getPort()) : null;
                 Socket socket = isServer() ? server.accept() : new Socket(getIP(), getPort());
                 ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
                this.socket = socket;
                this.outputStream = outputStream;
                socket.setTcpNoDelay(true);

                while (true) {
                    Serializable data = (Serializable) inputStream.readObject();
                    onReceivedCallback.accept(data);
                }
            } catch (Exception e) {
                onReceivedCallback.accept("Connection Closed");
            }
        }
    }
}
