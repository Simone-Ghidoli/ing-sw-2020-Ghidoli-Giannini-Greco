package it.polimi.ingsw.ps60.clientSide.view.client;

import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.ViewMethodSelection;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClientStarter{
    private List<String> messagesFromServer;
    private static ExecutorService pool = Executors.newFixedThreadPool(2);
    private ClientReader reader;
    private ClientParser parser;
    Socket socket;

    public ClientStarter(int port, String ipAddress, ViewMethodSelection viewMethodSelection) throws InterruptedException {
        while (socket.isClosed()) {
            try {
                socket = new Socket(ipAddress, port);
            } catch (IOException e1) {
                System.out.println("Failed to connect to the server. I`m trying again");
                TimeUnit.SECONDS.sleep(5);
            }
            if(!socket.isClosed()) {
                reader = new ClientReader(socket,messagesFromServer);
                parser= new ClientParser(socket,messagesFromServer,viewMethodSelection);
                pool.execute(reader);
                pool.execute(parser);
            }
        }
    }
}
