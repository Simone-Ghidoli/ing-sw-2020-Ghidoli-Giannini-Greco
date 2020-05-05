package it.polimi.ingsw.ps60.serverSide.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server_new {
    private ArrayList<ServerThread> lista;
    private ServerThread nnew;
    int port;
    private String address;
    private static Socket socket;
    ServerSocket Santorini;
    int numberOfPlayers;
    public static ExecutorService pool= Executors.newFixedThreadPool(3);

    public Server_new(int p,String add){
        port=p;
        address=add;
        numberOfPlayers=3;
    }

    public void openThread(){
        try{
            Santorini=new ServerSocket(port);
        }
        catch(IOException e){
            //todo disconnessione
        }
        while(lista.size()<numberOfPlayers){
            try {
                socket = Santorini.accept();
            }
            catch(IOException e_0){
                //todo chiamerà la disconnessione
            }
            nnew=new ServerThread(socket, lista);
            if(lista.size()==0){
                //new FirstPlayerConnected() todo creare la cosa del primo giocatore connesso
            }
            new PlayerConnection(socket,lista,nnew);
        }
    }
}
