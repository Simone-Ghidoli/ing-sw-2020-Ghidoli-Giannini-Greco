package it.polimi.ingsw.ps60.serverSide.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.polimi.ingsw.ps60.serverSide.server.ServerThread;

/**
 * Starts the server and open the connection between clinets and server. Puts clients in an arraylist
 */

public  class ServerStarter{
    static final int port=5090;//todo Poi la faccio scegliere
    private ArrayList<ServerThread> clientlist=new ArrayList();
    private static ExecutorService pool = Executors.newFixedThreadPool(3);

    public void AvvioServer() throws IOException {
        ServerSocket Santorini=null;
        Socket soc=null;

        try{
            Santorini=new ServerSocket(port);
        }
        catch(IOException error){
            error.printStackTrace();
        }
        while(true){
            try{
                soc= Santorini.accept();
                ServerThread nuovo=new ServerThread(soc,clientlist);
                clientlist.add(nuovo);
                pool.execute(nuovo);
            }
            catch(IOException errore){
                System.out.println("IO error"+errore);
                soc.close();
            }
        }
    }

    /**
     * @return the list of the sockets
     */
    public ArrayList<ServerThread> getsocketlist(){return clientlist;}
    public void ModifyList(){
        //modifiche alla lista
    }
}