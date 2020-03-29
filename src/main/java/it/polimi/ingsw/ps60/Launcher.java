package it.polimi.ingsw.ps60;

import java.io.IOException;
import java.util.Scanner;
import it.polimi.ingsw.ps60.serverSide.server.serverSideStarter;

public class Launcher {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter 0 for server, 1 for client");

        if (input.nextInt() == 0){
            serverSideStarter.startServer(args);
        }
        else{
            System.out.println("Enter 0 for CLI, 1 for GUI");
            if (input.nextInt() == 0){
                //avviare CLI
            }
            else{
                //avviare GUI
            }
        }
    }
}