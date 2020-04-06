package it.polimi.ingsw.ps60.clientSide.view.client;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClientStarter{
    private List<String> messagesFromServer;
    private static ExecutorService pool = Executors.newFixedThreadPool(2);
    private int port;
    private String ipAddress;
    private ClientReader reader;
    private ClientParser parser;
    Socket socket;

    public ClientStarter(int porta,String ip){
        port=porta;
        ipAddress=ip;
    }
    public void avvioClient() throws InterruptedException {
        while (socket.isClosed()) {
            try {
                socket = new Socket(ipAddress, port);
            } catch (IOException e1) {
                System.out.println("Failed to connect to the server. I`m trying again");
                TimeUnit.SECONDS.sleep(5);
            }
            if(!socket.isClosed()) {
                reader = new ClientReader(socket,messagesFromServer);
                parser= new ClientParser(socket,messagesFromServer);
                pool.execute(reader);
                pool.execute(parser);
            }
        }
    }
}
