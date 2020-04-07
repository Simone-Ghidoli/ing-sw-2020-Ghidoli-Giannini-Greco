package it.polimi.ingsw.ps60;

import java.io.IOException;
import java.util.Scanner;

import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.CLIMethods;
import it.polimi.ingsw.ps60.clientSide.view.client.Starter;
import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.GUIMethods;
import it.polimi.ingsw.ps60.serverSide.ServerStarter;

public class Launcher {

    public static void main(String[] args) throws IOException, InterruptedException {
        firstSelection(args);
    }

    private static void firstSelection(String[] args) throws IOException, InterruptedException {

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

    private static void clientSelection(String[] args) throws InterruptedException {
        System.out.println("Enter 0 for GUI, 1 for CLI");

        switch (new Scanner(System.in).nextInt()){
            case 0:
                Starter.main(args, new GUIMethods());
                break;
            case 1:
                Starter.main(args, new CLIMethods());
                break;
            default:
                System.out.println("Wrong input");
                clientSelection(args);
        }
    }
}