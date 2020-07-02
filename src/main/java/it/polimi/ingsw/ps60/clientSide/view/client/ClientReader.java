package it.polimi.ingsw.ps60.clientSide.view.client;

import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.ViewMethodSelection;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * This class is used to read and store server's commands
 */
public class ClientReader implements Runnable {

    private final List<String> messagesFromServer;
    final Socket socket;
    String serverSays;
    ObjectInputStream in_obj;
    InputStream in;
    final ViewMethodSelection methodSelection;

    /**
     * Opens the buffered reader
     *
     * @param sock     is the socket
     * @param messages is the list where the commands will be saved
     * @param method   is the viewMethodSelection (CLI/GUI)
     */
    public ClientReader(Socket sock, List<String> messages, ViewMethodSelection method, ObjectInputStream inob) {
        messagesFromServer = messages;
        socket = sock;
        methodSelection = method;
        synchronized (socket) {
            in_obj = inob;
        }
    }

    /**
     * Just receive all text messages(Strings) from the server and stores them in the list
     */
    @Override
    public void run() {
        while (true) {
            synchronized (socket) {
                if (socket.isClosed()) {
                    return;
                }
                try {
                    serverSays = (String) in_obj.readObject();
                    if (serverSays != null) {
                        messagesFromServer.add(serverSays);
                        serverSays = null;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    methodSelection.alert("Server has disconnected");
                    System.exit(0);
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        return;
                    }
                }
                try {
                    socket.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}