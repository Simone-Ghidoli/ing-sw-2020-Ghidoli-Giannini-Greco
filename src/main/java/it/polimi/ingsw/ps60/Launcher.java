package it.polimi.ingsw.ps60;

import java.io.IOException;
import java.util.Scanner;

import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.CLIMethods;
import it.polimi.ingsw.ps60.clientSide.view.client.Starter;
import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.GUIMethods;
import it.polimi.ingsw.ps60.serverSide.ServerStarter;

public class Launcher {

    public static void main(String[] args) throws InterruptedException {
        firstSelection();
    }

    private static void firstSelection() throws InterruptedException {

        System.out.println("Enter 0 for server, 1 for client");

        switch (new Scanner(System.in).nextLine()) {
            case "0":
                ServerStarter.start();
                break;
            case "1":
                clientSelection();
                break;
            default:
                System.out.println("Wrong input");
                firstSelection();
        }
    }

    private static void clientSelection() throws InterruptedException {
        System.out.println("Enter 0 for GUI, 1 for CLI");

        switch (new Scanner(System.in).nextLine()){
            case "0":
                Starter.start(new GUIMethods());
                break;
            case "1":
                Starter.start(new CLIMethods());
                break;
            default:
                System.out.println("Wrong input");
                clientSelection();
        }
    }
}