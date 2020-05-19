package it.polimi.ingsw.ps60.serverSide.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Starts the server and open the connection between clients and server. Puts Threads in an arraylist threadsList
 */

public  class Server{
    private List<String[]> nickBirth;
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
    private void serverStart() { //todo da riprogrammare sfruttando un po` il multithreading almeno per l`apertura delle connessioni. Per il resto va bene

        while (serverSocket == null || serverSocket.isClosed()) {
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException error) {
                serverSocket = null;
                System.exit(3);
            }
        }

        ServerThread newThread;

        while (socket == null || socket.isClosed()) {//finchè non va a buon fine il collegamento del primo giocatore ci riprovo
            try {// Accetto il primo giocatore e chiedo in quanti si gioca
                socket = serverSocket.accept();
                System.out.println("client accepted");
            } catch (IOException error) {
                if (!socket.isClosed())
                    try {
                        socket.close();
                    } catch (IOException e_0) {
                        e_0.printStackTrace();
                    }
                socket = null;
            }//Socket Chiuso e riparte la connessione del primo giocatore

            if (socket != null && !socket.isClosed()) {
                newThread = new ServerThread(socket, clientList);
                clientList.add(newThread); //primo thread aggiunto alla lista
                numberOfPlayers = newThread.numberOfPlayers();
                nickBirth = new ArrayList<>();
                nickBirth.add(newThread.nicknameBirthday());
                newThread.setPlayerBound(nickBirth.get(0)[0]);
            }
        }

        while (clientList.size() < numberOfPlayers) {//Collega i socket fino a quando si arriva al numero corretto di giocatori
            try {
                socket = serverSocket.accept();
                System.out.println("client accepted");
            } catch (IOException e) {
                if (!socket.isClosed())
                    try {
                        socket.close();
                    } catch (IOException e_1) {
                        e_1.printStackTrace();
                    }
                socket = null;
            }    //viene chiuso il socket e si riprova la connessione con il client che ha fallito
            if (socket != null && !socket.isClosed()) { //Se il socket è aperto crea un nuovo Thread e lo aggiunge alla lista di quelli in esecuzione
                newThread = new ServerThread(socket, clientList);
                clientList.add(newThread);
                do {
                    nickBirth.add(newThread.nicknameBirthday());
                } while (name_problem(nickBirth.get(clientList.size() - 1)[0]));
                newThread.setPlayerBound(nickBirth.get(clientList.size() - 1)[0]);
            }
        }
    }

    /**
     * Check the uniqueness of the username
     */
    public boolean name_problem(String current){
        int i=0;
        for(String[] elem:nickBirth) {
            if (elem[0].equals(current)) {
                i++;
            }
        }
        return i > 1;
    }

    public String[][] getNickBirth() {
        String[][] nicksValue=new String[nickBirth.size()][];
        for(int i=0;i<nickBirth.size();i++){
            nicksValue[i]=nickBirth.get(i);
        }
        return nicksValue;
    }

    /**
     * @return the list of the sockets
     */
    public ArrayList<ServerThread> getSocketList(){return clientList;}//recupera la lista dei Thread
}