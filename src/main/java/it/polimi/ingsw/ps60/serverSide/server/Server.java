package it.polimi.ingsw.ps60.serverSide.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Starts the server and open the connection between clinets and server. Puts Threads in an arraylist trehadsList
 */

public  class Server{
    private String[][] nickBirth;
    private final int port;
    private ArrayList<ServerThread> clientList;
    private ServerSocket serverSocket;
    private Socket socket;
    private int numberOfPlayers;

    public Server(int port) {
        this.port=port;
        clientList = new ArrayList<>();
        serverStart();
    }

    /**
     * Open connections between clients and server. Get players' nicknames and the number of players.
     */
    private void serverStart(){ //todo da riprogrammare sfruttando un po` il multithreading almeno per l`apertura delle connessioni. Per il resto va bene

        while (serverSocket==null||serverSocket.isClosed()) {
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException error) {
                serverSocket=null;
                System.out.println("Error opening server");
            }
        }

        ServerThread newThread;

        while (socket==null||socket.isClosed()) {//finchè non va a buon fine il collegamento del primo giocatore ci riprovo
            try {// Accetto il primo giocatore e chiedo in quanti si gioca
                socket = serverSocket.accept();
                System.out.println("client accepted");
            } catch (IOException error) {
                if (!socket.isClosed())
                    try {
                        socket.close();
                    }
                catch(IOException e_0){e_0.printStackTrace();}
                socket=null;
            }//Socket Chiuso e riparte la connessione del primo giocatore
            if (!socket.isClosed()) {
                newThread = new ServerThread(socket, clientList);
                clientList.add(newThread); //primo thread aggiunto alla lista
                numberOfPlayers = newThread.numberOfPlayers();
                nickBirth = new String[numberOfPlayers][2];
                nickBirth[0] = newThread.nicknameBirthday();
                newThread.setPlayerBound(nickBirth[0][0]);
            }
        }
        while (clientList.size() < numberOfPlayers) {//Collega i socket fino a quando si arriva al numero corretto di giocatori
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                if (!socket.isClosed())
                    try {
                        socket.close();
                    }
                catch(IOException e_1){e_1.printStackTrace();}
            }    //viene chiuso il socket e si riprova la connessione con il client che ha fallito
            if (!socket.isClosed()) { //Se il socket è aperto crea un nuovo Thread e lo aggiunge alla lista di quelli in esecuzione
                newThread = new ServerThread(socket, clientList);
                clientList.add(newThread);
                nickBirth[clientList.size() - 1] = newThread.nicknameBirthday();
                newThread.setPlayerBound(nickBirth[clientList.size() -1][0]);
            }
        }
    }

    public String[][] getNickBirth() {
        return nickBirth;
    }

    /**
     * @return the list of the sockets
     */
    public ArrayList<ServerThread> getSocketList(){return clientList;}//recupera la lista dei Thread
}