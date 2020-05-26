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

public class ClientStarter {
    private final List<String> messagesFromServer;
    private final ExecutorService pool = Executors.newFixedThreadPool(2);
    private Socket socket;
    private final int port;
    private final String ipAddress;
    private final ViewMethodSelection viewMethodSelection;

    public ClientStarter(ViewMethodSelection viewMethodSelection) {
        String[] strings = viewMethodSelection.ipPortChoices();
        messagesFromServer = new ArrayList<>();
        ipAddress = strings[0];
        port = Integer.parseInt(strings[1]);
        this.viewMethodSelection = viewMethodSelection;
    }

    /**
     * Start tries to connect to the server, if the connection fails, this method will wait for 5 seconds before try again.
     * When the connection is established pippo create the reader and the parser.
     * When the game ends(for any reason) pippo will close the parser/reader tasks
     */
    public void start() {
        while (socket == null) {
            try {
                socket = new Socket(ipAddress, port);
            } catch (IOException e1) {
                viewMethodSelection.alert("Failed to connect to the server. I`m trying again");
                socket = null;
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        ClientReader reader = new ClientReader(socket, messagesFromServer, viewMethodSelection);
        ClientParser parser = new ClientParser(socket, messagesFromServer, viewMethodSelection);
        pool.execute(reader);
        pool.execute(parser);
        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}