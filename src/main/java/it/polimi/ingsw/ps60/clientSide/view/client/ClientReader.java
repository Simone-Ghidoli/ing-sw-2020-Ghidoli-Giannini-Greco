package it.polimi.ingsw.ps60.clientSide.view.client;

import java.net.Socket;

public class ClientReader extends Thread {
    Socket socket;

    public ClientReader(Socket sock){
        socket=sock;
    }
}
