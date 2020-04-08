package it.polimi.ingsw.ps60.serverSide;

import it.polimi.ingsw.ps60.serverSide.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class ServerStarter {
    public static void start() throws IOException {
        System.out.println("Enter port number");
        Scanner input = new Scanner(System.in);
        int port=input.nextInt();
        Server avvio=new Server(port);
        avvio.AvvioServer();//todo Questo restituisce la lista dei nomi dei giocatori come ArrayList<String>
    }
}
