package it.polimi.ingsw.ps60.clientSide.view.client;

import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.ViewMethodSelection;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * This class is used to read and store server's commands
 */
public class ClientReader extends Thread {
    private final List<String> messagesFromServer;
    final Socket socket;
    String serverSays;
    BufferedReader br;
    ViewMethodSelection methodSelection;


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
    public void run() {
            while(true) {
                synchronized(socket) {
                    if (socket.isClosed())
                        return;
                }
                try{
                    serverSays = br.readLine();
                    synchronized (messagesFromServer){
                        messagesFromServer.add(serverSays);
                    }
                }
                catch (IOException e) {
                    methodSelection.alert("Communication error, logging out");
                    try {
                        synchronized (socket) {
                            socket.close();
                        }
                    } catch (IOException ex) {
                        return;
                    }
                }
            }
        }
    public List<String> getMessagesFromServer(){
        return messagesFromServer;
    }
}
