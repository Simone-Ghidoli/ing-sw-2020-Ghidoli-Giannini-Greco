package it.polimi.ingsw.ps60.serverSide.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerOpen {
    private int port;
    private ServerSocket serverSocket;
    public void StartServer(int port) {
        ServerSocket Santorini;
        Socket socket;
        try {
            Santorini = new ServerSocket();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
