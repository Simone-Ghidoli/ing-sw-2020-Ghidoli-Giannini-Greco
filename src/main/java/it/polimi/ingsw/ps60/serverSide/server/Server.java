package it.polimi.ingsw.ps60.serverSide.server;

import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Starts the server and open the connection between clinets and server. Puts Threads in an arraylist trehadsList
 */

public  class Server{
    private String[][] nick_birth;
    private int port;
    private ArrayList<ServerThread> clientlist=new ArrayList<>();
    private ServerSocket Santorini;
    private Socket socket;
    private int numberOfPlayers;

    public Server(int porta) throws IOException {
        port=porta;
        nick_birth=new String[3][2];
        serverStart();
    }

    /**
     * Open connections between clinets and server. Get players' nicknames and the number of players.
     */
    public void serverStart() throws IOException { //todo da riprogrammare sfruttando un po` il multithreading almeno per l`apertura delle connessioni. Per il resto va bene
        String s = null; //stringa di comodo per salvare i nomi dal metodo
        List<String> names = new ArrayList<>();
        while (Santorini.isClosed()) {
            try {
                Santorini = new ServerSocket(port);
            } catch (IOException error) {
                System.out.println("Errore apertura server");
            }
        }
        while (socket.isClosed()) {//finchè non va a buon fine il collegamento del primo giocatore ci riprovo
            try {// Accetto il primo giocatore e chiedo in quanti si gioca
                socket = Santorini.accept();
            } catch (IOException error) {
                if (!socket.isClosed())
                    socket.close();
            }//Socket Chiuso e riparte la connessione del primo giocatore
            if (!socket.isClosed()) {
                ServerThread nuovo = new ServerThread(socket, clientlist);
                clientlist.add(nuovo); //primo thread aggiunto alla lista
                numberOfPlayers = nuovo.numberOfPlayers();
                nick_birth[0] = nuovo.nickname_birthday();
                nuovo.setPlayerBound(nick_birth[0][0]);
            }
        }
        while (clientlist.size() < numberOfPlayers) {//Collega i socket fino a quando si arriva al numero corretto di giocatori
            try {
                socket = Santorini.accept();
            } catch (IOException errore) {
                if (!socket.isClosed())
                    socket.close();
            }    //viene chiuso il socket e si riprova la connessione con il client che ha fallito
            if (!socket.isClosed()) { //Se il socket è aperto crea un nuovo Thread e lo aggiunge alla lista di quelli in esecuzione
                ServerThread nuovoThread = new ServerThread(socket, clientlist);
                nick_birth[clientlist.size()] = nuovoThread.nickname_birthday();
                nuovoThread.setPlayerBound(nick_birth[clientlist.size()][0]);
                clientlist.add(nuovoThread);
            }
        }
    }

    public String[][] getNick_birth() {
        return nick_birth;
    }

    /**
     * @return the list of the sockets
     */
    public ArrayList<ServerThread> getsocketlist(){return clientlist;}//recupera la lista dei Thread
}