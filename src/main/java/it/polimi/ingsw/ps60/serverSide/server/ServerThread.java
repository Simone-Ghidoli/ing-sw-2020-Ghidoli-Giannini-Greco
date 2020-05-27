package it.polimi.ingsw.ps60.serverSide.server;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.utils.Converters;
import it.polimi.ingsw.ps60.utils.SerializedInteger;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains some methods to communicate between server and client.
 */

public class ServerThread extends Thread {
    private String playerBound;
    protected final Socket socket;
    private final List<ServerThread> serverThreads;
    private InputStream in;
    private OutputStream out;
    private BufferedReader buffer;
    private PrintWriter writer;
    private ObjectInputStream in_obj;
    private ObjectOutputStream out_obj;
    private final Converters converters;

    /**
     * Initialization of socket ecc
     * @param soc is the socket from the Class Server
     * @param serverThreads is the list of "ServerThread" of other sockets (one for each client connected)
     */


    public ServerThread(Socket soc, ArrayList<ServerThread> serverThreads) {
        this.socket = soc;
        this.serverThreads = serverThreads;
        converters = new Converters();
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            buffer = new BufferedReader(new InputStreamReader(in));
            writer = new PrintWriter(out, true);
            out_obj = new ObjectOutputStream(out);
            in_obj = new ObjectInputStream(in);
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e_1) {
                e_1.printStackTrace();
            }
        }
    }

    /**
     * @return the name of the player associate to this ServerThread
     */

    public String getPlayerBound() {
        return playerBound;
    }

    /**
     * Send the loss message
     * @param message Message to send to che client
     */

    public void lossMessage(String message) {
        sendString("loss-" + message);
    }

    /**
     * Send the "move" command to the client to start the "Moving phase", then send the position of the workers and the possible moves.
     * @param possible_choice available cells to move
     * @param positionWorkers positions of 2 workers
     * @return the cell where the player wants to move (calculated automatically) which worker should move
     */

    public int moveMessage(List<int[]>[] possible_choice, int[][] positionWorkers) {
        sendString("move");
        sendPositionsArray(converters.serializeArrayOfListOfInts(possible_choice));
        sendPositionWorkers(converters.serialize2DArrayOfInt(positionWorkers));
        return receiveInteger();
    }

    /**
     * Send the "build" command to che client to start the Building Phase, then send the possible builds.
     * @param possible_choice Possible cells where to build
     * @return Return the cell where the player wants to build
     */

    public int buildMessage(List<int[]> possible_choice) {
        sendString("build");
        sendPositionsList(converters.serializeListOfInts(possible_choice));
        return receiveInteger();
    }

    /**
     * Makes the user choose between a "yes/no" choice
     * @param message is the message that should be print on video
     * @return The choice of the user. (1=true/yes 0=false/no)
     */

    public int specialChoice(String message) {
        sendString("spc-" + message);
        return receiveInteger();
    }

    /**
     * Ask the first player how much players are going to play
     * @return Number of players
     */

    public int numberOfPlayers() {
        int n;
        sendString("nPlayers");
        n = receiveInteger();
        return n;
    }

    /**
     * Ask the player to give his name and his birthday
     * @return nickname and birthday (2 strings)
     */

    public String[] nicknameBirthday() {
        String[] nick_birth = new String[2];
        sendString("nick_birth");
        for (int i = 0; i < 2; i++)
            nick_birth[i] = receiveString();
        return nick_birth;
    }

    /**
     * Ask the player to choose the position of his workers at the start of the game
     * @param takenPositions Positions already taken
     * @return Chosen positions
     */

    public int[][] setWorkers(List<int[]> takenPositions) {
        sendString("workSet");
        sendPositionsList((converters.serializeListOfInts(takenPositions)));
        return converters.deserialize2DArrayOfInts(receivePositions());
    }

    /**
     * Ask the first player which DivinityCards will be played
     * @return Chosen DivinityCards
     */

    public GlobalVariables.DivinityCard[] divinityChoice() {
        sendString("dv_choice");
        sendInt(serverThreads.size());
        return receiveCards();
    }

    /**
     * Ask the player which DivinityCard he wants to play
     * @param divinityCards Available DivinityCards
     * @return Chosen DivinityCard
     */

    public GlobalVariables.DivinityCard divinitySelection(GlobalVariables.DivinityCard[] divinityCards) {
        sendString("div_sel");
        sendCards(divinityCards);
        return receiveCards()[0];
    }

    /**
     * Send the command to make the client print the board on video
     * @param board The current state of the game board
     */

    public void sendBoard(char[] board) {
        sendString("pr-" + new String(board));
    }

    /**
     * @return Return the list of ServerThreads
     */

    public List<ServerThread> getServerThreads() {
        return serverThreads;
    }

    /**
     * Receive an integer
     * @return The received integer
     */

    public int receiveInteger() {
        int n = -1;
        try {
            n = in.read();
        } catch (IOException e) {
            disconnection();
        }
        return n;
    }

    /**
     * Send a String and check that success of the operation
     */

    public void sendString(String message) {
        writer.println(message);
        if (writer.checkError()) {
            disconnection();
        }
    }

    /**
     * Send an array of positions List (SerializedInteger=int[] but implements Serializable). Used for the moving phase
     * @param list the list of Serialized Integer to send
     */

    public void sendPositionsArray(List<SerializedInteger>[] list) {
        try {
            receiveInteger();
            out_obj.writeObject(list);
        } catch (IOException e) {
            disconnection();
        }
    }

    /**
     * send a list of Positions (SerializedInteger). Used for the building phase
     * @param list
     */

    public void sendPositionsList(List<SerializedInteger> list) {
        try {
            receiveInteger();
            out_obj.writeObject(list);
        } catch (IOException e) {
            disconnection();
        }
    }

    /**
     * the positions of the 2 workers of the plyaer
     * @param positionWorkers The 2 positions
     */

    public void sendPositionWorkers(SerializedInteger[] positionWorkers) {
        try {
            receiveInteger();
            out_obj.writeObject(positionWorkers);
        } catch (IOException e) {
            disconnection();
        }
    }

    /**
     * Receive a String
     * @return Received String
     */

    public String receiveString() {
        String n = null;
        try {
            n = buffer.readLine();
        } catch (IOException e) {
            disconnection();
        }
        return n;
    }

    /**
     * Receive the positions of 2 workers
     * @return The positions
     */

    public SerializedInteger[] receivePositions() {
        SerializedInteger[] pos = null;
        try {
            pos = (SerializedInteger[]) in_obj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            disconnection();
        }
        return pos;
    }

    /**
     * Receive a list of DivinityCards
     * @return The list of DivinityCards
     */

    public GlobalVariables.DivinityCard[] receiveCards() {
        GlobalVariables.DivinityCard[] divinityCards = null;
        try {
            divinityCards = (GlobalVariables.DivinityCard[]) in_obj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            disconnection();
        }
        return divinityCards;
    }

    /**
     * Send an integer
     * @param send integer to send
     */

    public void sendInt(int send) {
        try {
            receiveInteger();
            out.write(send);
            out.flush();
        } catch (IOException e) {
            disconnection();
        }
    }

    /**
     * Send an array of DivinityCard
     * @param cards array to send
     */

    public void sendCards(GlobalVariables.DivinityCard[] cards) {
        try {
            receiveInteger();
            out_obj.writeObject(cards);
        } catch (IOException e) {
            disconnection();
        }
    }

    /**
     * This method send an alert to the client
     *
     * @param sting is the alert to send
     */
    public void sendAlert(String sting) {
        sendString("al-" + sting);
        receiveInteger();
    }

    public void sendStatus(int[] divinityCard, int turnNumber){
        StringBuilder stringToSend = new StringBuilder("st-");

        for (int i : divinityCard)
            stringToSend.append(i);

        stringToSend.append(turnNumber);

        sendString(stringToSend.toString());
        receiveInteger();
    }

    /**
     * This method set the nickname of the player of the serverThread
     *
     * @param nickname the nickname of the player
     */
    public void setPlayerBound(String nickname) {
        playerBound = nickname;
    }

    /**
     * This method communicate to all clients that a player has won
     */
    public void win() {
        String message = playerBound + " won the game.";
        for (ServerThread elem : serverThreads) {
            elem.sendString("win-" + message);
        }
    }

    /**
     * This method communicate to all clients that a player has been disconnected
     */
    public void disconnection() {
        for (ServerThread elem : serverThreads) {
            elem.writer.println("disc-User " + playerBound + " left the game. The match is over.");
        }
        System.out.println("Communication error. Exit...");
        System.exit(0);
    }
}