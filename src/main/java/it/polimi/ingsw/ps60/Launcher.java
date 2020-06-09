package it.polimi.ingsw.ps60;

import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.CLIMethods;
import it.polimi.ingsw.ps60.clientSide.view.client.ClientStarter;
import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.GUIMethods;
import it.polimi.ingsw.ps60.serverSide.controller.ServerStarter;

import static it.polimi.ingsw.ps60.GlobalVariables.input;

/**
 * This class is the main. From here you can launch both server and client
 */
public class Launcher {

    public static void main(String[] args) {
        firstSelections();
    }

    /**
     * This method will makes able to select to run a client or a server
     */
    private static void firstSelections() {

        System.out.println("Enter 0 for server, 1 for client");

        switch (input.nextLine()) {
            case "0":
                new ServerStarter().start();
                break;
            case "1":
                clientSelection();
                break;
            default:
                System.out.println("Wrong input");
                firstSelections();
        }
    }

    /**
     * This method makes the player able to choice if he wants to play in CLI or GUI
     */
    private static void clientSelection() {
        System.out.println("Enter 0 for GUI, 1 for CLI");

        switch (input.nextLine()) {
            case "0":
                new ClientStarter(new GUIMethods()).start();
                break;
            case "1":
                new ClientStarter(new CLIMethods()).start();
                break;
            default:
                System.out.println("Wrong input");
                clientSelection();
        }
    }
}