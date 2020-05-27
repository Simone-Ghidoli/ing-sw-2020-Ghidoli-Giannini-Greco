package it.polimi.ingsw.ps60.clientSide.view.client;

import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.ViewMethodSelection;

import java.io.*;
import java.net.Socket;
import java.util.List;


/**
 * This class is used to read and store server's commands
 */
public class ClientReader implements Runnable {
    private final List<String> messagesFromServer;
    final Socket socket;
    String serverSays;
    BufferedReader br;
    final ViewMethodSelection methodSelection;


    /**
     * Opens the buffered reader
     * @param sock is the socket
     * @param messages is the list where the commands will be saved
     * @param method is the viewMethodSelection (CLI/GUI)
     */


    public ClientReader(Socket sock, List<String> messages,ViewMethodSelection method){
        messagesFromServer=messages;
        socket=sock;
        methodSelection=method;
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(IOException e_0){
            synchronized (socket){
                try{
                    socket.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
            methodSelection.alert("communication error, logging out");
        }
    }

    /**
     * Just receive all text messages(Strings) from the server and stores them in the list
     */

    @Override
    public void run() {
        while (true) {
                synchronized(socket) {
                    if (socket.isClosed()) {
                        return;
                    }
                }
            try {
                synchronized (socket) {
                    if (br.ready())
                        serverSays = br.readLine();
                }
                if(serverSays!=null) {
                    synchronized (messagesFromServer) {
                        messagesFromServer.add(serverSays);
                        serverSays=null;
                    }
                }
            } catch (IOException e) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    return;
                }
            }
        }
    }
}
