package it.polimi.ingsw.ps60.clientSide.view.client;

import java.io.*;
import java.net.Socket;
import java.util.*;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.ViewMethodSelection;
import it.polimi.ingsw.ps60.utils.Converters;
import it.polimi.ingsw.ps60.utils.SerializedInteger;

/**
 * This class is the parser. Its function is to process server's commands and call the correct method to proceed in the game
 */
public class ClientParser implements Runnable {
    private final List<String> messagesFromServer;
    private boolean exit;
    private final Socket socket;
    InputStream input;
    OutputStream output;
    ObjectInputStream in_obj;
    ObjectOutputStream out_obj;
    final ViewMethodSelection methodSelection;
    private final Converters converters;

    /**
     * Constructor of the Parser
     *
     * @param sock                is the socket
     * @param messages            List of string received from the server
     * @param viewMethodSelection Starts method from Gui methods/ Cli methods
     * @param in_obj              is the objectInputStream shared between parser and reader
     */
    public ClientParser(Socket sock, List<String> messages, ViewMethodSelection viewMethodSelection, ObjectInputStream in_obj) {
        socket = sock;
        exit=false;
        messagesFromServer = messages;
        methodSelection = viewMethodSelection;
        converters = new Converters();
        synchronized (socket) {
            try {
                input = socket.getInputStream();
                output = socket.getOutputStream();
                out_obj = new ObjectOutputStream(output);
                this.in_obj = in_obj;
            } catch (IOException e) {
                try {
                    socket.close();
                } catch (IOException e_0) {
                    //e.printStackTrace();
                }
                disconnection("Communication error, logging out");
            }
        }
    }

    /**
     * List of comparisons between server's messages and known commands
     */
    public void run() {
        String message;
        while (!exit) {
            synchronized (messagesFromServer) {
                synchronized (socket) {
                    if (socket.isClosed()) {
                        socket.notify();
                        exit = true;
                    }
                while (messagesFromServer.size() != 0) {

                        socket.notify();

                        message = messagesFromServer.get(0);

                        if (message.equals("move"))
                            movement();
                        else if (message.equals("build"))
                            building();
                        else if (message.contains("spc-"))
                            specialChoice(message.split("spc-", 2)[1]);
                        else if (message.equals("nPlayers"))
                            number_of_players();
                        else if (message.equals("nick_birth"))
                            nickname_birthday();
                        else if (message.equals("workSet"))
                            setWorkers();
                        else if (message.contains("pr-"))
                            printBoard(message.split("pr-", 2)[1]);
                        else if (message.equals("dv_choice"))
                            divinityChoice();
                        else if (message.equals("div_sel"))
                            divinitySelection();
                        else if (message.contains("al-"))
                            alert(message.split("al-", 2)[1]);
                        else if (message.contains("loss-"))
                            alert(message.split("loss-", 2)[1]);
                        else if (message.contains("st-"))
                            status(message.split("st-", 2)[1]);
                        else if (message.contains("win-")) {
                            methodSelection.alert(message.split("win-", 2)[1]);
                            socketClose();
                            return;
                        } else if (message.contains("disc-")) {
                            disconnection(message.split("disc-", 2)[1]);
                            return;
                        }

                        messagesFromServer.remove(0);
                    }
                }
            }
        }
    }

    /**
     * This method receive the status of the players from the server
     * @param status contains nicknames, divinity cards and the player turn number
     */
    public void status(String status) {
        String[] statusToParse = status.split(" ");

        int[] divinityNumbers = new int[(statusToParse.length - 1)/2];
        String[] nicknames = new String[divinityNumbers.length];

        for (int i = 0; i < divinityNumbers.length; i++) {
            divinityNumbers[i] = Integer.parseInt(statusToParse[i*2]);
        }

        for(int i=0;i < divinityNumbers.length;i++){
            nicknames[i] = statusToParse[(i*2)+1];
        }

        methodSelection.status(divinityNumbers, Integer.parseInt(statusToParse[statusToParse.length - 1]), nicknames);

        sendInt(0);
    }

    /**
     * Close the socket after the game is stopped (for any reason)
     */
    public void socketClose() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show a video message to the player
     *
     * @param alert is the message to print
     */

    public void alert(String alert) {
        methodSelection.alert(alert);
        sendInt(0);
    }

    /**
     * Starts the move phase
     * Receive the Possible moves and the positions of the workers
     */

    public void movement() {
        sendInt(methodSelection.moveChoice(converters.deserializeArrayOfListOfInts(receiveListArray()), converters.deserialize2DArrayOfInts(receiveWorkers())));
    }

    /**
     * method used for building
     */
    public void building() {
        sendInt(methodSelection.buildChoice(converters.deserializeListOfInts(receiveList())));
    }

    /**
     * method used for special choices
     *
     * @param s contains the message that users will see to know what are they choosing
     */
    public void specialChoice(String s) {
        boolean n = methodSelection.specialChoices(s);
        int choice;
        if (n)
            choice = 1;
        else
            choice = 0;
        sendInt(choice);
    }

    /**
     * How much players gonna to play?
     */
    public void number_of_players() {
        sendInt(methodSelection.numberOfPlayers());
    }

    /**
     * Players' names and birthdays
     */
    public void nickname_birthday() {
        String[] inputs;
        inputs = methodSelection.nicknameBirthdayChoice();
        sendString(inputs[0]);//NickName
        sendString(inputs[1]);//Birthday
    }

    /**
     * Shot the actual state of the board to the player
     *
     * @param board contains a stream of characters that will be used to build the correct board to print
     */
    public void printBoard(String board) {
        methodSelection.printBoard(board);
    }

    /**
     * Set the workers' position at the start of the game
     */
    public void setWorkers() {
        sendPositions(converters.serialize2DArrayOfInt(methodSelection.firstSetWorkers(converters.deserializeListOfInts(receiveList()))));
    }

    /**
     * The first player needs to choose in-game cards
     */
    public void divinityChoice() {
        sendCards(methodSelection.cardChoices(receiveInt()));
    }

    /**
     * Card pick between available cards
     */
    public void divinitySelection() {
        sendCards(new GlobalVariables.DivinityCard[]{methodSelection.divinitySelection(receiveCards())});
    }


    /**
     * Some methods for Client-Server communication. Methods can send/receive string, integers or special objects
     * Some methods are used to convert normal types to serializable types and the other way around
     *
     * @param positions are the positions selected by the client
     */
    public void sendPositions(SerializedInteger[] positions) {
        try {
            out_obj.writeObject(positions);
        } catch (IOException e_0) {
            disconnection("Communication error, logging out");
        }
    }

    /**
     * Send and integer
     *
     * @param send integer to send
     */

    public void sendInt(int send) {
        try {
            output.write(send);
            output.flush();
        } catch (IOException e) {
            disconnection("Communication error, logging out");
        }
    }

    /**
     * Receive an array of lists of Serialized Integer=(int[]). Method for the movement phase
     *
     * @return an array of lists of Serialized Integer=(int[])
     */

    public List<SerializedInteger>[] receiveListArray() {
        try {
            sendInt(0);
            return (List<SerializedInteger>[]) in_obj.readObject();
        } catch (IOException e) {
            disconnection("Communication error, logging out");
            System.out.println("Communication error, logging out");
        } catch (ClassNotFoundException e_1) {
            System.out.println("Class not found");
        }
        return null;
    }

    /**
     * Receive a list of Serialized Integer=(int[]). Method for building phase
     *
     * @return a list of Serialized Integer=(int[])
     */

    public List<SerializedInteger> receiveList() {
        try {
            sendInt(0);
            return (List<SerializedInteger>) in_obj.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            disconnection("Communication error, logging out");
        } catch (ClassNotFoundException e_1) {
            e_1.printStackTrace();
        }
        return null;
    }

    /**
     * Receive an array of Serialized Integer. Just for receive the positions of 2 workers
     *
     * @return an array of Serialized Integer
     */

    public SerializedInteger[] receiveWorkers() {
        SerializedInteger[] positionWorkers = null;
        try {
            sendInt(0);
            positionWorkers = (SerializedInteger[]) in_obj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            disconnection("Communication error, logging out");
        }
        return positionWorkers;
    }

    /**
     * Send a String to the server
     *
     * @param toServer String to send
     */
    public void sendString(String toServer) {
        try {
            out_obj.writeObject(toServer);
        } catch (IOException e) {
            e.printStackTrace();
            disconnection("Communication error, logging out");
        }
        /*pr.println(toServer);
        if (pr.checkError())
            disconnection("Communication error, logging out");*/
    }

    /**
     * Receive an integer
     *
     * @return integer received
     */
    public int receiveInt() {
        int n = -1;
        try {
            sendInt(0);
            n = input.read();
        } catch (IOException e) {
            disconnection("Communication error, logging out");
        }
        return n;
    }

    /**
     * Send an array of DivinityCards
     *
     * @param cards Array to send
     */
    public void sendCards(GlobalVariables.DivinityCard[] cards) {
        try {
            out_obj.writeObject(cards);
        } catch (IOException e) {
            disconnection("Communication error, logging out");
        }
    }

    /**
     * Receive an array of cards.
     *
     * @return Received array
     */
    public GlobalVariables.DivinityCard[] receiveCards() {
        GlobalVariables.DivinityCard[] divinityCards = null;
        try {
            sendInt(0);
            divinityCards = (GlobalVariables.DivinityCard[]) in_obj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            disconnection("Communication error, logging out");
        }
        return divinityCards;
    }

    /**
     * disconnection
     *
     * @param s contains the message to print before disconnection
     */
    public void disconnection(String s) {
        methodSelection.alert(s);
        exit=true;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}