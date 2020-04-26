package it.polimi.ingsw.ps60.clientSide.view.client;

import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.ViewMethodSelection;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class is used to open the connection with the server and starts reader and parser tasks
 */

public class ClientStarter{
    private List<String> messagesFromServer;
    private static ExecutorService pool = Executors.newFixedThreadPool(2);
    private ClientReader reader;
    private ClientParser parser;
    Socket socket;
    private static int porta;
    private static String address;
    private static ViewMethodSelection methodSelection;

    public ClientStarter(int port, String ipAddress, ViewMethodSelection viewMethodSelection){
        messagesFromServer= new ArrayList<>();
        porta=port;
        address=ipAddress;
        methodSelection=viewMethodSelection;
    }

    /**
     * Pippo tries to connect to the server, if the connection fails, this method will wait for 5 seconds before try again.
     * When the connection is established pippo create the reader and the parser.
     * When the game ends(for any reason) pippo will close the parser/reader tasks
     * @throws InterruptedException
     */

    public void pippo() throws InterruptedException {
        while (socket.isClosed()){
            try {
                socket = new Socket(address, porta);
            } catch (IOException e1) {
                methodSelection.alert("Failed to connect to the server. I`m trying again");
                try {
                    TimeUnit.SECONDS.sleep(5);
                }
                catch(InterruptedException e){
                    System.out.println("\nBro, do you ever hear philopendolo's tale?");
                }
            }
            if(!socket.isClosed()) {
                reader = new ClientReader(socket,messagesFromServer,methodSelection);
                parser= new ClientParser(socket,messagesFromServer,methodSelection);
                pool.execute(reader);
                pool.execute(parser);
                pool.shutdown();
                pool.awaitTermination(Long.MAX_VALUE,TimeUnit.SECONDS);
            }
        }
    }
}