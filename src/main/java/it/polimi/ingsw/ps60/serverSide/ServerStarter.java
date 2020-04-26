package it.polimi.ingsw.ps60.serverSide;

import it.polimi.ingsw.ps60.serverSide.controller.StartGame;
import it.polimi.ingsw.ps60.serverSide.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.Scanner;

public class ServerStarter {
    public static void start() throws IOException {
        System.out.println("Enter port number");
        Scanner input = new Scanner(System.in);
        int port=input.nextInt();
        Server server = new Server(port);
        StartGame startGame = new StartGame(server.getNick_birth());
    }
}
