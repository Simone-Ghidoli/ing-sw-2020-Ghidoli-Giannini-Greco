package it.polimi.ingsw.ps60.clientSide.view.client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.*;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.ViewMethodSelection;
import it.polimi.ingsw.ps60.utils.SerializedInteger;


public class ClientParser extends Thread{
    private List<String> messagesFromServer;
    private Socket socket;
    BufferedReader br;
    PrintWriter pr;
    ObjectInputStream in_obj;
    ObjectOutputStream out_obj;
    InputStream input;
    ViewMethodSelection methodSelection;

    public ClientParser(Socket sock,List<String> messages,ViewMethodSelection viewmethod){
        socket=sock;
        messagesFromServer=messages;
        methodSelection=viewmethod;
    }

    public void run() {//processa i messaggi
        while (true) {
            synchronized (messagesFromServer) {
                while (messagesFromServer.size() != 0) {
                    //todo parsare le stringe con serie di if/else
                }
            }
        }
    }
    public void movement(){//Interagisce con l'utente per fargli decidere la giocata
        List<SerializedInteger>[] stalin;
        stalin=recieveListArray();//recupero le posizioni
        SerializedInteger[] workers=receiveWorkers();//Riceve la posizione dei due signori
        int play=methodSelection.moveChoice(convertTypePosition(stalin),convertSerialized_to_integer(workers));//output della giocata fatta dall'utente
        sendInt(play);
    }

    public void building(){//Molto simile a build ma viene saltata la fase di scelta del worker
        List<SerializedInteger> stalin;
        stalin=recieveList();//ricevo la lista stalin
        int play=methodSelection.buildChoice(convertTypePositionBuild(stalin));
        sendInt(play);
    }

    public void specialChoice(String s){//Viene fatta una scelta speciale. Viene inviato a server il responso: 1=sì 2=no todo Aggiungere la stringa in input
        boolean n=methodSelection.specialChoices(s);
        int choice;
        if(n)
            choice=1;
        else
            choice=0;
        sendInt(choice);
    }

    public void number_of_players(){//da fare ancora in methods
        int number=methodSelection.numberOfPlayers();
        sendInt(number);
    }

    public void nickname_birthday(){//invia nick e altro nel server
        String[] inputs=new String[2];
        inputs=methodSelection.nicknameBirthdayChoice();
        sendString(inputs[0]);//NickName
        sendString(inputs[1]);//Birthday
    }

    public void printBoard(String board){
        methodSelection.printBoard(board);
    }

    public void setworkers(){
        List<SerializedInteger> takenPositions=recieveList();
        int[][] answer=methodSelection.firstSetWorkers(convertTypePositionBuild(takenPositions));

    }

    public void divinityChoice(){
        int numberOfPlayers=receiveInt();
        GlobalVariables.DivinityCard[] inGameCards=methodSelection.cardChoices(numberOfPlayers);
        sendCards(inGameCards);
    }

    public void divinitySelection(){
        GlobalVariables.DivinityCard[] cards;
        cards=receiveCards();
        GlobalVariables.DivinityCard choice[]=new GlobalVariables.DivinityCard[1];
        choice[0] = methodSelection.divinitySelection(cards);
        sendCards(choice);
    }



    public void sendPositions(SerializedInteger positions){
        try{
            out_obj=new ObjectOutputStream(socket.getOutputStream());
            out_obj.writeObject(positions);
        }
        catch(IOException e_0){
            //todo chiama la disonnessione
        }
    }

    public void sendInt(int send){
        try {
            socket.getOutputStream().write(send);
            socket.getOutputStream().flush();
        }
        catch(IOException e){
            //todo chiama la disconnessione
        }
    }

    public List<SerializedInteger>[] recieveListArray(){
        try {
            List<SerializedInteger>[] stalin;
            input = socket.getInputStream();//preparo lo stream per ricevere la lista di posizioni
            in_obj = new ObjectInputStream(input);
            stalin = (List<SerializedInteger>[]) in_obj.readObject();
            return stalin;
        }
        catch(IOException | ClassNotFoundException e){
            //todo chiama la disconnessione
        }
        return null;
    }//Per  il movement.

    public List<SerializedInteger> recieveList(){   //Per il building
        try {
            List<SerializedInteger> stalin;
            input = socket.getInputStream();//preparo lo stream per ricevere la lista di posizioni
            in_obj = new ObjectInputStream(input);
            stalin = (List<SerializedInteger>) in_obj.readObject();
            return stalin;
        }
        catch(IOException | ClassNotFoundException e){
            //todo chiama la disconnessione
        }
        return null;
    }

    public SerializedInteger[] convertInteger_to_Serialized(int[][] inte){
        SerializedInteger[] appoggio=null;
        for(int i=0;i<inte.length;i++){
            appoggio[i].serialized=inte[i];
        }
        return appoggio;
    }

    public int[][] convertSerialized_to_integer(SerializedInteger[] seria){
        int[][] appoggio=new int[2][2];
        for(int i=0;i<seria.length;i++){
            appoggio[i]=seria[i].serialized;
        }
        return appoggio;
    }

    public List<int[]>[] convertTypePosition(List<SerializedInteger>[] positions){//Restituisce position come Lista di interi e non SerializedInteger
        List<int[]>[] appoggio= new ArrayList[2];
        for(int i=0;i<2;i++){
            for(int k=0;k<positions[i].size();k++){
                appoggio[i].add(positions[k].get(k).serialized);
            }
        }
        return appoggio;
    }

    public List<int[]> convertTypePositionBuild(List<SerializedInteger> positions){//Restituisce position come Lista di interi e non SerializedInteger
        List<int[]> appoggio= new ArrayList();
            for(int k=0;k<positions.size();k++){
                appoggio.add(positions.get(k).serialized);
            }
        return appoggio;
    }

    public SerializedInteger[] receiveWorkers(){
        SerializedInteger[] positionworkers=null;
        try {
            input = socket.getInputStream();
            in_obj=new ObjectInputStream(input);
            positionworkers=(SerializedInteger[]) in_obj.readObject();
        }
        catch(IOException | ClassNotFoundException e){
            //todo chiama la disconnessione
        }
        return positionworkers;
    }

    public void sendString(String toServer){
        try {
            pr = new PrintWriter(socket.getOutputStream(), true);
            pr.println(toServer);
        }
        catch(IOException e_0){
            //todo chiama la disconnessione
        }
    }

    public int receiveInt(){
        int n=-1;
        try {
            n=socket.getInputStream().read();
        }
        catch(IOException e){
            //todo chiama la disconnessione
        }
        return n;
    }

    public void sendCards(GlobalVariables.DivinityCard[] cards){
        try {
            out_obj = new ObjectOutputStream(socket.getOutputStream());
            out_obj.writeObject(cards);
        }
        catch(IOException e){
            //todo chiama la disconnessione
        }
    }

    public GlobalVariables.DivinityCard[] receiveCards(){
        GlobalVariables.DivinityCard[] appoggio=null;
        try {
            in_obj = new ObjectInputStream(socket.getInputStream());
            appoggio=(GlobalVariables.DivinityCard[]) in_obj.readObject();
        }
        catch(IOException | ClassNotFoundException e){
            //todo chiama la disconnessione
        }
        return appoggio;
    }

}
