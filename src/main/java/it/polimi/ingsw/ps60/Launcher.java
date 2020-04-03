package it.polimi.ingsw.ps60;

import java.util.Scanner;

import it.polimi.ingsw.ps60.clientSide.view.cli.CLIStarter;
import it.polimi.ingsw.ps60.clientSide.view.gui.GUIStarter;
import it.polimi.ingsw.ps60.serverSide.ServerStarter;

public class Launcher {

    public static void main(String[] args) {
        firstSelection(args);
    }

    private static void firstSelection(String[] args){

        System.out.println("Enter 0 for server, 1 for client");

        switch (new Scanner(System.in).nextInt()) {
            case 0:
                ServerStarter.main(args);
                break;
            case 1:
                clientSelection(args);
                break;
            default:
                System.out.println("Wrong input");
                firstSelection(args);
        }
    }

    private static void clientSelection(String[] args){
        System.out.println("Enter 0 for GUI, 1 for CLI");

        switch (new Scanner(System.in).nextInt()){
            case 0:
                GUIStarter.main(args);
                break;
            case 1:
                CLIStarter.main(args);
                break;
            default:
                System.out.println("Wrong input");
                clientSelection(args);
        }
    }
}