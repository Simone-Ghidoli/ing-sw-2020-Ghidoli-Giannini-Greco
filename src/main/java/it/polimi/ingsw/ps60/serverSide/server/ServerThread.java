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
    protected Socket socket;
    private final List<ServerThread> serverThreads;
    private InputStream in;
    private OutputStream out;
    private BufferedReader buffer;
    private PrintWriter writer;
    private ObjectInputStream in_obj;
    private ObjectOutputStream out_obj;
    private final Converters converters;

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

    public String getPlayerBound() {
        return playerBound;
    }

    public void lossMessage(String message) {
        sendString("loss-" + message);
    }

    public int moveMessage(List<int[]>[] possible_choice, int[][] positionWorkers) {
        sendString("move");
        sendPositionsArray(converters.serializeArrayOfListOfInts(possible_choice));
        sendPositionWorkers(converters.serialize2DArrayOfInt(positionWorkers));
        return receiveInteger();
    }

    public int buildMessage(List<int[]> possible_choice) {
        sendString("build");
        sendPositionsList(converters.serializeListOfInts(possible_choice));
        return receiveInteger();
    }

    public int specialChoice(String message) {
        sendString("spc-" + message);
        return receiveInteger();
    }

    public int numberOfPlayers() {
        int n;
        sendString("nPlayers");
        n = receiveInteger();
        return n;
    }

    public String[] nicknameBirthday() {
        String[] nick_birth = new String[2];
        sendString("nick_birth");
        for (int i = 0; i < 2; i++)
            nick_birth[i] = receiveString();
        return nick_birth;
    }

    public int[][] setWorkers(List<int[]> takenPositions) {
        sendString("workSet");
        sendPositionsList((converters.serializeListOfInts(takenPositions)));
        return converters.deserialize2DArrayOfInts(receivePositions());
    }

    public GlobalVariables.DivinityCard[] divinityChoice() {
        sendString("dv_choice");
        sendInt(serverThreads.size());
        return receiveCards();
    }

    public GlobalVariables.DivinityCard divinitySelection(GlobalVariables.DivinityCard[] divinityCards) {
        sendString("div_sel");
        sendCards(divinityCards);
        return receiveCards()[0];
    }

    public void sendBoard(char[] board) {
        sendString("pr-" + new String(board));
    }

    public List<ServerThread> getServerThreads() {
        return serverThreads;
    }

    public int receiveInteger() {
        int n = -1;
        try {
            n = in.read();
        } catch (IOException e) {
            disconnection();
        }
        return n;
    }

    public void sendString(String message) {
        writer.println(message);
        if (writer.checkError()) {
            disconnection();
        }
    }

    public void sendPositionsArray(List<SerializedInteger>[] list) {
        try {
            receiveInteger();
            out_obj.writeObject(list);
        } catch (IOException e) {
            disconnection();
        }
    }

    public void sendPositionsList(List<SerializedInteger> list) {
        try {
            receiveInteger();
            out_obj.writeObject(list);
        } catch (IOException e) {
            disconnection();
        }
    }

    public void sendPositionWorkers(SerializedInteger[] positionWorkers) {
        try {
            receiveInteger();
            out_obj.writeObject(positionWorkers);
        } catch (IOException e) {
            disconnection();
        }
    }

    public String receiveString() {
        String n = null;
        try {
            n = buffer.readLine();
        } catch (IOException e) {
            disconnection();
        }
        return n;
    }

    public SerializedInteger[] receivePositions() {
        SerializedInteger[] pos = null;
        try {
            pos = (SerializedInteger[]) in_obj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            disconnection();
        }
        return pos;
    }

    public GlobalVariables.DivinityCard[] receiveCards() {
        GlobalVariables.DivinityCard[] divinityCards = null;
        try {
            divinityCards = (GlobalVariables.DivinityCard[]) in_obj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            disconnection();
        }
        return divinityCards;
    }

    public void sendInt(int send) {
        try {
            receiveInteger();
            out.write(send);
            out.flush();
        } catch (IOException e) {
            disconnection();
        }
    }

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
        String message = playerBound + " won the game. 30L pliz";
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