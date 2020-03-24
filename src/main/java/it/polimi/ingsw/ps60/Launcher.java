package it.polimi.ingsw.ps60;

import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter 0 for server, 1 for client");

        if (input.nextInt() == 0){
            //avviere il server
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
