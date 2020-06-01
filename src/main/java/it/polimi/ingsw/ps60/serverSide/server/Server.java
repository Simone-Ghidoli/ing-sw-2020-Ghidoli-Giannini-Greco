package it.polimi.ingsw.ps60.serverSide.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Starts the server and open the connection between clients and server. Puts Threads in an arraylist threadsList
 */
public  class Server {
    private List<String[]> nickBirth;
    private final int port;
    private final ArrayList<ServerThread> clientList;
    private ServerSocket serverSocket;
    private Socket socket;
    private int numberOfPlayers;

    public Server(int port) {
        this.port = port;
        clientList = new ArrayList<>();
        serverStart();
    }

    /**
     * Open connections between clients and server. Get players' nicknames and the number of players.
     */
    private void serverStart() {

        while (serverSocket == null || serverSocket.isClosed()) {
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException error) {
                serverSocket = null;
                System.exit(3);
            }
        }

        ServerThread newThread;

        while (socket == null || socket.isClosed()) {
            acceptClientConnection();

            if (socket != null && !socket.isClosed()) {
                newThread = new ServerThread(socket, clientList);
                clientList.add(newThread);
                numberOfPlayers = newThread.numberOfPlayers();
                nickBirth = new ArrayList<>();
                nickBirth.add(newThread.nicknameBirthday());
                newThread.setPlayerBound(nickBirth.get(0)[0]);
            }
        }

        while (clientList.size() < numberOfPlayers) {
            acceptClientConnection();
            if (socket != null && !socket.isClosed()) {
                newThread = new ServerThread(socket, clientList);
                clientList.add(newThread);
                String[] newNickBirth = newThread.nicknameBirthday();

                while (!nameProblem(newNickBirth[0]))
                    newNickBirth = newThread.nicknameBirthday();

                nickBirth.add(newNickBirth);

                newThread.setPlayerBound(newNickBirth[0]);
            }
        }
    }

    /**
     * This method accept a client connection
     */
    private void acceptClientConnection() {
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
        }
    }

    /**
     * Check the uniqueness of the username
     *
     * @param current is the name that has to be tested
     * @return true if there are no problems, false otherwise
     */
    public boolean nameProblem(String current) {
        for (String[] elem : nickBirth) {
            if (elem[0].equals(current)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method return an array of nicknames and birthday of all players
     *
     * @return array of nicknames (position [n][0]) and birthday (position [n][1]) of all players
     */
    public String[][] getNicknameAndBirthday() {
        String[][] nicksValue = new String[nickBirth.size()][];
        for (int i = 0; i < nickBirth.size(); i++) {
            nicksValue[i] = nickBirth.get(i);
        }
        return nicksValue;
    }

    /**
     * This method return the list of all sockets
     *
     * @return the list of the sockets
     */
    public ArrayList<ServerThread> getSocketList() {
        return clientList;
    }
}