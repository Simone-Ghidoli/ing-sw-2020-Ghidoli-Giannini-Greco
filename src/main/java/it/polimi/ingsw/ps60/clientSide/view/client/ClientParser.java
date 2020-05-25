package it.polimi.ingsw.ps60.clientSide.view.client;

import java.io.*;
import java.net.Socket;
import java.util.*;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.ViewMethodSelection;
import it.polimi.ingsw.ps60.utils.SerializedInteger;

/**
 * This class is the parser. Its function is to process server's commands and call the correct method to proceed in the game
 */

    public class ClientParser implements Runnable{
    private final List<String> messagesFromServer;
    private final Socket socket;
    InputStream input;
    OutputStream output;
    PrintWriter pr;
    ObjectInputStream in_obj;
    ObjectOutputStream out_obj;
    ViewMethodSelection methodSelection;

    public ClientParser(Socket sock, List<String> messages, ViewMethodSelection viewMethodSelection) {
        socket = sock;
        messagesFromServer = messages;
        methodSelection = viewMethodSelection;
        try {
            input = socket.getInputStream();
            output = socket.getOutputStream();
            out_obj = new ObjectOutputStream(output);
            in_obj = new ObjectInputStream(input);
            pr = new PrintWriter(output, true);

        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e_0) {
                //e.printStackTrace();
            }
            disconnection("Communication error, logging out");
        }
    }

    /**
     * List of comparisons between server's messages and known commands
     */
    public void run() {
        String message;
        while (true) {
            synchronized (messagesFromServer) {
                while (messagesFromServer.size() != 0) {
                    synchronized (socket) {
                        if (socket.isClosed())
                            return;

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
                        else if (message.equals("workset"))
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

    public void socketClose(){
        try {
            socket.close();
        }
        catch(IOException e){
            //non fa nulla
        }
    }

    public void alert(String s){
        methodSelection.alert(s);
        sendInt(0);
    }

    public void movement() {
//        List<SerializedInteger>[] movementStrategy = receiveListArray();
//        SerializedInteger[] workers = receiveWorkers();
//        int play = methodSelection.moveChoice(convertTypePosition(movementStrategy), convertSerialized_to_integer(workers));
//        sendInt(play);
        sendInt(methodSelection.moveChoice(convertTypePosition(receiveListArray()), convertSerialized_to_integer(receiveWorkers())));
    }

    /**
     * method used for building
     */
    public void building() {
//        List<SerializedInteger> buildingStrategy = receiveList();
//        int play = methodSelection.buildChoice(convertTypePositionBuild(buildingStrategy));
//        sendInt(play);
        sendInt(methodSelection.buildChoice(convertTypePositionBuild(receiveList())));
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
//        int number = methodSelection.numberOfPlayers();
//        sendInt(number);
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
     * @param board contains a stream of characters that will be used to build the correct board to print
     */
    public void printBoard(String board) {
        methodSelection.printBoard(board);
    }

    /**
     * Set the workers' position at the start of the game
     */
    public void setWorkers() {
//        List<SerializedInteger> takenPositions = receiveList();
//        int[][] answer = methodSelection.firstSetWorkers(convertTypePositionBuild(takenPositions));
//        sendPositions(convertInteger_to_Serialized(answer));
        sendPositions(convertInteger_to_Serialized(methodSelection.firstSetWorkers(convertTypePositionBuild(receiveList()))));
    }

    /**
     * The first player needs to choose in-game cards
     */
    public void divinityChoice() {
//        int numberOfPlayers = receiveInt();
//        GlobalVariables.DivinityCard[] inGameCards = methodSelection.cardChoices(numberOfPlayers);
//        sendCards(inGameCards);
        sendCards(methodSelection.cardChoices(receiveInt()));
    }

    /**
     * Card pick between available cards
     */
    public void divinitySelection() {
//        GlobalVariables.DivinityCard[] cards = receiveCards();
//        GlobalVariables.DivinityCard[] choice = new GlobalVariables.DivinityCard[1];
//        choice[0] = methodSelection.divinitySelection(cards);
//        sendCards(choice);
        sendCards(new GlobalVariables.DivinityCard[]{methodSelection.divinitySelection(receiveCards())});
    }


    /**
     * Some methods for Client-Server communication. Methods can send/receive string, integers or special objects
     * Some methods are used to convert normal types to serializable types and the other way around
     */
    public void sendPositions(SerializedInteger[] positions) {
        try {
            out_obj.writeObject(positions);
        } catch (IOException e_0) {
            disconnection("Communication error, logging out");
        }
    }

    public void sendInt(int send) {
        try {
            output.write(send);
            output.flush();
        } catch (IOException e) {
            disconnection("Communication error, logging out");
        }
    }

    public List<SerializedInteger>[] receiveListArray() {
//        sendInt(0);
        try {
            sendInt(0);
//            List<SerializedInteger>[] stalin;
//            stalin = (List<SerializedInteger>[]) in_obj.readObject();
//            return stalin;
            return (List<SerializedInteger>[]) in_obj.readObject();
        } catch (IOException e) {
            disconnection("Communication error, logging out");
            System.out.println("Communication error, logging out");
        } catch(ClassNotFoundException e_1){
            System.out.println("Class not found");
        }
        return null;
    }

    public List<SerializedInteger> receiveList() {

//        List<SerializedInteger> serializedIntegers;
            try {
//                serializedIntegers = (List<SerializedInteger>) in_obj.readObject();
//                return serializedIntegers;
                sendInt(0);
                return (List<SerializedInteger>) in_obj.readObject();
            } catch (IOException e) {
                e.printStackTrace();
                disconnection("Communication error, logging out");
            } catch (ClassNotFoundException e_1) {
                System.out.println("fbuaiwbfu");
            }
        return null;
    }

    public SerializedInteger[] convertInteger_to_Serialized(int[][] ints) {
        SerializedInteger[] serializedIntegers=new SerializedInteger[ints.length];
        for (int i = 0; i < ints.length; i++) {
            serializedIntegers[i]=new SerializedInteger(ints[i]);
        }
        return serializedIntegers;
    }

    public int[][] convertSerialized_to_integer(SerializedInteger[] serialized) {
//        int[][] appoggio = new int[2][2];
//        for (int i = 0; i < serialized.length; i++) {
//            appoggio[i] = serialized[i].serialized;
//        }
        return new int[][]{serialized[0].serialized, serialized[1].serialized};
    }

    public List<int[]>[] convertTypePosition(List<SerializedInteger>[] positions) {//Restituisce position come Lista di interi e non SerializedInteger
        List<int[]>[] lists = new ArrayList[2];
//        lists[0]=new ArrayList<>();
//        lists[1]=new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            lists[i] = new ArrayList<>();
            for (int k = 0; k < positions[i].size(); k++) {
                lists[i].add(positions[i].get(k).serialized);
            }
        }
        return lists;
    }

    public List<int[]> convertTypePositionBuild(List<SerializedInteger> positions) {//Restituisce position come Lista di interi e non SerializedInteger
        List<int[]> list = new ArrayList<>();
        for (SerializedInteger position : positions) {
            list.add(position.serialized);
        }
        return list;
    }

    public SerializedInteger[] receiveWorkers() {
        SerializedInteger[] positionworkers = null;
        try {
            sendInt(0);
            positionworkers = (SerializedInteger[]) in_obj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            disconnection("Communication error, logging out");
        }
        return positionworkers;
    }

    public void sendString(String toServer) {
        pr.println(toServer);
        if (pr.checkError())
            disconnection("Communication error, logging out");
    }

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

    public void sendCards(GlobalVariables.DivinityCard[] cards) {
        try {
            out_obj.writeObject(cards);
        } catch (IOException e) {
            disconnection("Communication error, logging out");
        }
    }

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
        try {
            socket.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        Thread.currentThread().interrupt();
    }
}
