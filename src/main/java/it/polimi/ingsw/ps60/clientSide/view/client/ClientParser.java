package it.polimi.ingsw.ps60.clientSide.view.client;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

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

    public void run() {//processa i messaggi
        while (true) {
            synchronized (messagesFromServer) {
                while (messagesFromServer.size() != 0) {
                    //synchronized (socket) {
                    GlobalVariables.frassino.lock();
                    String message = messagesFromServer.get(0);
                    if (message.equals("move")) {
                        movement();
                    } else if (message.equals("build")) {
                        building();
                    } else if (message.contains("spc-")) {
                        String s = message.replace("spc-", "");
                        specialChoice(s);
                    } else if (message.equals("nPlayers")) {
                        number_of_players();
                    } else if (message.equals("nick_birth")) {
                        nickname_birthday();
                    } else if (message.equals("workset")) {
                        setworkers();
                    } else if (message.contains("pr-")) {
                        String s = message.replace("pr-", "");
                        printBoard(s);
                    } else if (message.equals("dv_choice")) {
                        divinityChoice();
                    } else if (message.equals("div_sel")) {
                        divinitySelection();
                    } else if (message.contains("disc-")) {
                        String s = message.replace("disc-", "");
                        disconnection(s);//chiama la disconnessione segnalando quale giocatore si è disconnesso
                    } else if (message.contains("loss-")) {
                        String s = message.replace("loss-", "");
                        loss(s);
                    }
                    messagesFromServer.remove(0);
                    GlobalVariables.frassino.unlock();
                }
            }
        }
    }


    /**
     * method used for movement
     */
    public void loss(String s){
        methodSelection.alert(s);
    }

    public void movement() {//Interagisce con l'utente per fargli decidere la giocata
        List<SerializedInteger>[] stalin;
        stalin = recieveListArray();//recupero le posizioni
        SerializedInteger[] workers = receiveWorkers();//Riceve la posizione dei due signori
        int play = methodSelection.moveChoice(convertTypePosition(stalin), convertSerialized_to_integer(workers));//output della giocata fatta dall'utente
        sendInt(play);
    }

    /**
     * method used for building
     */
    public void building() {//Molto simile a build ma viene saltata la fase di scelta del worker
        List<SerializedInteger> stalin;
        stalin = recieveList();//ricevo la lista stalin
        int play = methodSelection.buildChoice(convertTypePositionBuild(stalin));
        sendInt(play);
    }

    /**
     * method used for special choices
     *
     * @param s contains the message that users will see to know what are they choosing
     */
    public void specialChoice(String s) {//Viene fatta una scelta speciale. Viene inviato a server il responso: 1=sì 2=no
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
    public void number_of_players() {//da fare ancora in methods
        int number = methodSelection.numberOfPlayers();
        sendInt(number);
    }

    /**
     * Players' names and birthdays
     */
    public void nickname_birthday() {//invia nick e altro nel server
        String[] inputs = new String[2];
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
    public void setworkers() {
        List<SerializedInteger> takenPositions = recieveList();
        int[][] answer = methodSelection.firstSetWorkers(convertTypePositionBuild(takenPositions));
        sendPositions(convertInteger_to_Serialized(answer));
    }

    /**
     * The first player needs to choose in-game cards
     */
    public void divinityChoice() {
        int numberOfPlayers = receiveInt();
        GlobalVariables.DivinityCard[] inGameCards = methodSelection.cardChoices(numberOfPlayers);
        sendCards(inGameCards);
    }

    /**
     * Card pick between available cards
     */
    public void divinitySelection() {
        GlobalVariables.DivinityCard[] cards;
        cards = receiveCards();
        GlobalVariables.DivinityCard[] choice = new GlobalVariables.DivinityCard[1];
        choice[0] = methodSelection.divinitySelection(cards);
        sendCards(choice);
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

    public List<SerializedInteger>[] recieveListArray() {
        try {
            List<SerializedInteger>[] stalin;
            stalin = (List<SerializedInteger>[]) in_obj.readObject();
            return stalin;
        } catch (IOException e) {
            disconnection("Communication error, logging out");
        } catch(ClassNotFoundException e_1){
            System.out.println("non trovo la classe");
        }
        return null;
    }//Per  il movement.

    public List<SerializedInteger> recieveList() {   //Per il building
        List<SerializedInteger> stalin;
            try {
                stalin = (List<SerializedInteger>) in_obj.readObject();
                return stalin;
            } catch (IOException e) {
                e.printStackTrace();
                disconnection("Communication error, logging out");
            } catch (ClassNotFoundException e_1) {
                System.out.println("fbuaiwbfu");
            }
        return null;
    }

    public SerializedInteger[] convertInteger_to_Serialized(int[][] inte) {
        SerializedInteger[] appoggio=new SerializedInteger[inte.length];
        for (int i = 0; i < inte.length; i++) {
            appoggio[i]=new SerializedInteger(inte[i]);
        }
        return appoggio;
    }

    public int[][] convertSerialized_to_integer(SerializedInteger[] seria) {
        int[][] appoggio = new int[2][2];
        for (int i = 0; i < seria.length; i++) {
            appoggio[i] = seria[i].serialized;
        }
        return appoggio;
    }

    public List<int[]>[] convertTypePosition(List<SerializedInteger>[] positions) {//Restituisce position come Lista di interi e non SerializedInteger
        List<int[]>[] appoggio = new ArrayList[2];
        appoggio[0]=new ArrayList<>();
        appoggio[1]=new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < positions[i].size(); k++) {
                appoggio[i].add(positions[i].get(k).serialized);
            }
        }
        return appoggio;
    }

    public List<int[]> convertTypePositionBuild(List<SerializedInteger> positions) {//Restituisce position come Lista di interi e non SerializedInteger
        List<int[]> appoggio = new ArrayList<>();
        for (SerializedInteger position : positions) {
            appoggio.add(position.serialized);
        }
        return appoggio;
    }

    public SerializedInteger[] receiveWorkers() {
        SerializedInteger[] positionworkers = null;
        try {
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
        GlobalVariables.DivinityCard[] appoggio = null;
        try {
            appoggio = (GlobalVariables.DivinityCard[]) in_obj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            disconnection("Communication error, logging out");
        }
        return appoggio;
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
