package it.polimi.ingsw.ps60.serverSide.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Starts the server and open the connection between clinets and server. Puts Threads in an arraylist trehadsList
 */

public  class Server{
    private String[][] nickBirth;
    private final int port;
    private final ArrayList<ServerThread> clientList;
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
    private void serverStart(){

        while (serverSocket==null||serverSocket.isClosed()) {
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException error) {
                serverSocket=null;
                System.exit(3);
            }
        }

        ServerThread newThread;

        while (socket==null||socket.isClosed()) {

            try {
                socket = serverSocket.accept();
                System.out.println("client accepted");
            } catch (IOException error) {
                if (!socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException e_0) {
                        e_0.printStackTrace();
                    }
                }
                socket = null;
            }

            if (socket != null && !socket.isClosed()) {
                newThread = new ServerThread(socket, clientList);
                clientList.add(newThread);
                numberOfPlayers = newThread.numberOfPlayers();
                nickBirth = new String[numberOfPlayers][2];
                nickBirth[0] = newThread.nicknameBirthday();
                newThread.setPlayerBound(nickBirth[0][0]);
            }
        }

        while (clientList.size() < numberOfPlayers) {
            try {
                socket = serverSocket.accept();
                System.out.println("client accepted");
            } catch (IOException e) {
                if (!socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException e_1) {
                        e_1.printStackTrace();
                    }
                    socket = null;
                }
            }
            if (socket != null && !socket.isClosed()) {
                newThread = new ServerThread(socket, clientList);
                clientList.add(newThread);

                do
                    nickBirth[clientList.size() - 1] = newThread.nicknameBirthday();
                while(name_problem(nickBirth[(clientList.size()-1)][0]));

                newThread.setPlayerBound(nickBirth[clientList.size() -1][0]);
            }
        }
    }

    /**
     * Check the uniqueness of the username
     */
    public boolean name_problem(String current){
        int i=0;
        for(String[] elem:nickBirth){
            if(elem[0].equals(current))
                i++;
        }
        return i > 1;
    }

    public String[][] getNickBirth() {
        return nickBirth;
    }

    /**
     * @return the list of the sockets
     */
    public ArrayList<ServerThread> getSocketList(){return clientList;}//recupera la lista dei Thread
}