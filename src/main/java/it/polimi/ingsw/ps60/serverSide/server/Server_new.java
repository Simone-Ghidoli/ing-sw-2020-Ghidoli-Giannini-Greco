package it.polimi.ingsw.ps60.serverSide.server;

import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server_new {
    List<ServerThread> lista;
    int port;
    String address;
    Socket socket;
    public static ExecutorService pool= Executors.newFixedThreadPool(3);
    public Server_new(int p,String add){
        port=p;
        address=add;
    }
    public void openThread(){

    }
}
