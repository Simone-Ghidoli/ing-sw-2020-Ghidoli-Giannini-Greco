package it.polimi.ingsw.ps60.serverSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class ServerStarter {
    public static void main(String[] args) throws IOException {
        System.out.println("Enter port number");
        Scanner input = new Scanner(System.in);
        try(ServerSocket socketListener = new ServerSocket(input.nextInt())){
            //ServerThread(socketListener);
        }

        catch(IOException error){
            error.printStackTrace();
        }
    }
}
