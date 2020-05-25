package it.polimi.ingsw.ps60.serverSide.server;

import it.polimi.ingsw.ps60.GlobalVariables;
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
    private final List<ServerThread> list;
    private InputStream in;
    private OutputStream out;
    private BufferedReader buffer;
    private PrintWriter writer;
    private ObjectInputStream in_obj;
    private ObjectOutputStream out_obj;

    public ServerThread(Socket soc, ArrayList<ServerThread> list) {
        this.socket = soc;
        this.list = list;
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

    public int moveMessage(List<int[]>[] possible_choice, int[][] positionworkers) {//Comunica con l'utente per decidere quale muratore muovere e dove
        sendString("move");
        sendPositionsArray(convertIntegerToSerialized_move(possible_choice));//Invio solo la parte delle mosse che mi serve(ovvero quelle associate al worker da muovere)
        sendPositionWorkers(convertIntegerToSerialized_workers(positionworkers));//PositionWorkers viene inserito in input quando viene chiamato il metodo.
        return receiveInteger();//Restituisce il numero inserito dall'utente (quindi la posizione del vettore con la casella in cui costruire)
    }

    public int buildMessage(List<int[]> possible_choice) {//Comunica con l`utente per decidere dove costruire
        sendString("build");
        sendPositionsList(convertPositionListToSerializedInteger(possible_choice));
        return receiveInteger();//Restituisce il numero inserito dall`utente (Quindi la posizione del vettore con la casella in cui costruire)
    }

    public int specialChoice(String message) {
        sendString("spc-" + message);
        return receiveInteger();
    }

    public int numberOfPlayers() {//ask how much players gonna play
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

    public int[][] setWorkers(List<int[]> takenPositions) {//Restituisce un vettore con le posizioni dei 2 workers
        sendString("workset");
        sendPositionsList((convertPositionListToSerializedInteger(takenPositions)));
        SerializedInteger[] newTakenPositions = receivePositions();
        return convertSerializedToInteger_workers(newTakenPositions);
    }

    public GlobalVariables.DivinityCard[] divinityChoice() {
        sendString("dv_choice");
        sendInt(list.size());
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

    public List<ServerThread> getList() {
        return list;
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
    }//A differenza del primo manda una sola lista e non un vettore di liste

    public void sendPositionWorkers(SerializedInteger[] positionworkers) {
        try {
            receiveInteger();
            out_obj.writeObject(positionworkers);
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

    public int[][] convertSerializedToInteger_workers(SerializedInteger[] serializedInteger) {
        int[][] ints = new int[2][2];
        for (int i = 0; i < serializedInteger.length; i++) {
            ints[i] = serializedInteger[i].serialized;
        }
        return ints;
    }

    public List<SerializedInteger>[] convertIntegerToSerialized_move(List<int[]>[] possible_choice) {
        List<SerializedInteger>[] list = new ArrayList[possible_choice.length];
        for (int i = 0; i < 2; i++) {
            list[i] = new ArrayList<>();
            for (int[] elem : possible_choice[i]) {
                list[i].add(new SerializedInteger(elem));
            }
        }
        return list;
    }

    public SerializedInteger[] convertIntegerToSerialized_workers(int[][] positions) { //Riceve sempre un vettore con le posizioni di 2 workers.
//        SerializedInteger[] temp=new SerializedInteger[2];
//        for(int i=0;i<2;i++){
//            temp[i]=new SerializedInteger(positions[i]);
//        }
        return new SerializedInteger[]{new SerializedInteger(positions[0]), new SerializedInteger(positions[1])};
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


    public List<SerializedInteger> convertPositionListToSerializedInteger(List<int[]> list) { //Converte il tipo da List<int> a Serialized Integer
        List<SerializedInteger> serializedIntegerList = new ArrayList<>();
        for (int[] elem : list) {
            serializedIntegerList.add(new SerializedInteger(elem));
        }
        return serializedIntegerList;
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
        for (ServerThread elem : list) {
            elem.sendString("win-" + message);
        }
    }

    /**
     * This method communicate to all clients that a player has been disconnected
     */
    public void disconnection() {
        for (ServerThread elem : list) {
            elem.writer.println("disc-User " + playerBound + " left the game. The match is over.");
        }
        System.out.println("Communication error. Exit...");
        System.exit(0);
    }
}