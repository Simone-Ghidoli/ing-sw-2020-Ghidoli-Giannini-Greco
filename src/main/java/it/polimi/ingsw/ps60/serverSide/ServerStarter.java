package it.polimi.ingsw.ps60.serverSide;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.StartGame;
import it.polimi.ingsw.ps60.serverSide.server.Server;
import it.polimi.ingsw.ps60.utils.StringRegexValidation;

import java.io.IOException;
import java.util.Scanner;

public class ServerStarter {
    public static void start() throws IOException {
        new StartGame(new Server(portSelection()).getNick_birth());
    }

    private static int portSelection(){
        Scanner input = new Scanner(System.in);
        String port = null;

        System.out.println("Enter the port number");

        while (port == null){
            port = input.nextLine();
            if (!new StringRegexValidation(GlobalVariables.StringPatterns.PortNumber.getPattern()).isValid(port)) {
                System.out.println("Wrong input");
                port = null;
            }
        }

        return Integer.parseInt(port);
    }
}
