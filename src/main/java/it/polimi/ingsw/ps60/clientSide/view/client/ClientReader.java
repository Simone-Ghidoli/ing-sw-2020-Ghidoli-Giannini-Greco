package it.polimi.ingsw.ps60.clientSide.view.client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientReader extends Thread {
    List<String> messagesFromServer;
    Socket socket;
    String serverSays;
    BufferedReader br;
    InputStream in;
    PrintWriter pr;

    public ClientReader(Socket sock,List<String> messages){
        messagesFromServer=messages;
        socket=sock;
        messagesFromServer=new ArrayList<>();
    }

    public void run(){
        try {
            while(true) {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                serverSays = br.readLine();
                synchronized (messagesFromServer){
                    messagesFromServer.add(serverSays);
                }
            }
        }
        catch(IOException e){
            //todo chiamo la disconnessione e la chiusura della partita
        }
    }
    public List<String> getMessagesFromServer(){
        return messagesFromServer;
    }
}
