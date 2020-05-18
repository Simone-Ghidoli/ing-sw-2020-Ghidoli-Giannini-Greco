package it.polimi.ingsw.ps60;

import java.util.Scanner;

import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.CLIMethods;
import it.polimi.ingsw.ps60.clientSide.view.client.ClientStarter;
import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.GUIMethods;
import it.polimi.ingsw.ps60.serverSide.controller.ServerStarter;

public class Launcher {

    public static void main(String[] args){
        firstSelection();
    }

    private static void firstSelection(){

        System.out.println("Enter 0 for server, 1 for client");

        switch (new Scanner(System.in).nextLine()) {
            case "0":
                new ServerStarter();
                break;
            case "1":
                clientSelection();
                break;
            default:
                System.out.println("Wrong input");
                firstSelection();
        }
    }

    private static void clientSelection(){
        System.out.println("Enter 0 for GUI, 1 for CLI");

        switch (new Scanner(System.in).nextLine()){
            case "0":
                new ClientStarter(new GUIMethods());
                break;
            case "1":
                new ClientStarter(new CLIMethods());
                break;
            default:
                System.out.println("Wrong input");
                clientSelection();
        }
    }
}