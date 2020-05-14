package it.polimi.ingsw.ps60.serverSide.server;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.utils.SerializedInteger;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * This class contains some methods to communicate between server and client.
 */

public class ServerThread extends Thread {
    private String playerBound;
    protected Socket socket;
    private List<ServerThread> list;
    private InputStream in;
    private OutputStream out;
    private BufferedReader buffer;
    private PrintWriter writer;
    private ObjectInputStream in_obj;
    private ObjectOutputStream out_obj;

    public ServerThread(Socket soc, ArrayList<ServerThread> list){
        this.socket = soc;
        this.list = list;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            buffer = new BufferedReader(new InputStreamReader(in));
            writer = new PrintWriter(out, true);
            out_obj = new ObjectOutputStream(out);
            in_obj = new ObjectInputStream(in);
        }
        catch(IOException e){
            try{
                socket.close();
            }
            catch(IOException e_1){e_1.printStackTrace();}
        }
    }

    public String getPlayerBound() {
        return playerBound;
    }

    public void lossMessage(String message){
        sendString("loss-"+message);
    }

    public int moveMessage(List<int[]>[] possible_choice,int[][] positionworkers) {//Comunica con l'utente per decidere quale muratore muovere e dove
        int choice;
        sendString("move");
        sendPositionsArray(convertIntegerToSerialized_move(possible_choice));//Invio solo la parte delle mosse che mi serve(ovvero quelle associate al worker da muovere)
        sendPositionWorkers(convertIntegerToSerialized_workers(positionworkers));//PositionWorkers viene inserito in input quando viene chiamato il metodo.
        choice=receiveInteger();
        return choice;//Restituisce il numero inserito dall'utente (quindi la posizione del vettore con la casella in cui costruire)
    }
    public int buildMessage(List<int[]> possible_choice){//Comunica con l`utente per decidere dove costruire
        int choice;
        sendString("build");
        sendPositionsList(convertPositionListToSerializedInteger(possible_choice));
        choice=receiveInteger();
        return choice;//Restituisce il numero inserito dall`utente (Quindi la posizione del vettore con la casella in cui costruire)
    }
    public int specialChoice(String message){
        int choice;
        sendString("spc-"+message);
        choice=receiveInteger();
        return choice;
    }
    public int numberOfPlayers(){//ask how much players gonna play
        int n;
        sendString("nPlayers");
        n=receiveInteger();
        return n;
    }
    public String[] nicknameBirthday(){
        String[] nick_birth=new String[2];
        sendString("nick_birth");
        nick_birth[0]=receiveString();//Nickname
        nick_birth[1]=receiveString();//Birth
        return nick_birth;
    }
    public int[][] setWorkers(List<int[]> takenPos) {//Restituisce un vettore con le posizioni dei 2 workers
        sendString("workset");
        List<SerializedInteger> temp;
        temp=(convertPositionListToSerializedInteger(takenPos));
        sendPositionsList(temp);
        SerializedInteger[] appoggio=receivePositions();
        return convertSerializedToInteger_workers(appoggio);
    }
    public GlobalVariables.DivinityCard[] divinityChoice(){
        sendString("dv_choice");
        sendInt(list.size());
        return receiveCards();
    }
    public GlobalVariables.DivinityCard divinitySelection(GlobalVariables.DivinityCard[] divinityCards){
        sendString("div_sel");
        sendCards(divinityCards);
        return receiveCards()[0];
    }
    public void sendBoard(char[] board){
        try{
            TimeUnit.MILLISECONDS.sleep(100);
        }
        catch(InterruptedException e) {
            disconnection();
        }
        String result= new String(board);
        sendString("pr-"+result);
    }

    public List<ServerThread> getList(){
        return list;
    }

    public int receiveInteger(){
        int n=-1;
        try {
            n = in.read();
        }
        catch(IOException e){
            disconnection();
        }
        return n;
    }

    public void sendString(String message){
        writer.println(message);
        if(writer.checkError()){
            disconnection();
        }
    }

    public void sendPositionsArray(List<SerializedInteger>[] list){
        try{
            TimeUnit.MILLISECONDS.sleep(100);
            out_obj.writeObject(list);
        }
        catch(IOException | InterruptedException e){
            disconnection();
        }
    }

    public void sendPositionsList(List<SerializedInteger> list){
        try{
            TimeUnit.MILLISECONDS.sleep(100);
            out_obj.writeObject(list);
        }
        catch(IOException | InterruptedException e){
            disconnection();
        }
    }//A differenza del primo manda una sola lista e non un vettore di liste

    public void sendPositionWorkers(SerializedInteger[] positionworkers){
        try{
            TimeUnit.MILLISECONDS.sleep(100);
            out_obj.writeObject(positionworkers);
        }
        catch(IOException | InterruptedException e){
            disconnection();
        }
    }

    public String receiveString(){
        String n=null;
        try{
            n=buffer.readLine();
        }
        catch(IOException e){
            disconnection();
        }
        return n;
    }

    public SerializedInteger[] receivePositions(){
        SerializedInteger[] pos=null;
        try{
            pos=(SerializedInteger[]) in_obj.readObject();
        }
        catch(IOException | ClassNotFoundException e) {
            disconnection();
        }
        return pos;
    }

    public int[][] convertSerializedToInteger_workers(SerializedInteger[] seria){
        int[][] appoggio=new int[2][2];
        for(int i=0;i<seria.length;i++){
            appoggio[i]=seria[i].serialized;
        }
        return appoggio;
    }

    public List<SerializedInteger>[] convertIntegerToSerialized_move(List<int[]>[] possible_choice){
        List<SerializedInteger>[] list=new ArrayList[possible_choice.length];
        list[0]=new ArrayList<>();
        list[1]=new ArrayList<>();
        for(int[] elem:possible_choice[0]){
            list[0].add(new SerializedInteger(elem));
        }
        for(int[] elem:possible_choice[1]){
            list[1].add(new SerializedInteger(elem));
        }
        return list;
    }

    public SerializedInteger[] convertIntegerToSerialized_workers(int[][] positions){ //Riceve sempre un vettore con le posizioni di 2 workers.
        SerializedInteger[] temp=new SerializedInteger[2];
        for(int i=0;i<2;i++){
            temp[i]=new SerializedInteger(positions[i]);
        }
        return temp;
    }

    public GlobalVariables.DivinityCard[] receiveCards(){
        GlobalVariables.DivinityCard[] appoggio=null;
        try {
            appoggio=(GlobalVariables.DivinityCard[]) in_obj.readObject();
        }
        catch(IOException | ClassNotFoundException e){
            disconnection();
        }
        return appoggio;
    }

    public void sendInt(int send){
        try {
            TimeUnit.MILLISECONDS.sleep(100);
            out.write(send);
            out.flush();
        }
        catch(IOException | InterruptedException e){
            disconnection();
        }
    }

    public void sendCards(GlobalVariables.DivinityCard[] cards){
        try {
            TimeUnit.MILLISECONDS.sleep(100);
            out_obj.writeObject(cards);
        }
        catch(IOException | InterruptedException e){
            disconnection();
        }
    }

    public List<SerializedInteger> convertPositionListToSerializedInteger(List<int[]> lista){ //Converte il tipo da List<int> a Serialized Integer
        List<SerializedInteger> appoggio= new ArrayList<>();
        for(int[] elem: lista){
            appoggio.add(new SerializedInteger(elem));
        }
        return appoggio;
    }

    public void setPlayerBound(String s){
        playerBound =s;
    }

    public void win(){
        String message=playerBound+" won the game. 30L pliz";
        for(ServerThread elem:list){
            elem.sendString("win-"+message);
        }
    }

    public void disconnection(){
        for(ServerThread elem:list){
            elem.writer.println("disc-User "+playerBound+" left the game. The match is over.");
        }
        System.out.println("Communication error. Exit...");
        System.exit(0);
    }
}