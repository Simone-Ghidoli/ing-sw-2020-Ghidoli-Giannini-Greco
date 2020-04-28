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
    private List<ServerThread> list;
    private InputStream in;
    private OutputStream out;
    private BufferedReader buffer;
    private PrintWriter writer;
    private ObjectInputStream in_obj;
    private ObjectOutputStream out_obj;

    public ServerThread(Socket soc, ArrayList<ServerThread> lista){
        this.socket = soc;
        this.list = lista;
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

    public int moveMessage(List<int[]>[] possible_choice,int[][] positionworkers) {//Comunica con l'utente per decidere quale muratore muovere e dove
        int choice=-1;
        sendString("move");
        sendPositionsArray(convertIntegerToSerialized_move(possible_choice));//Invio solo la parte delle mosse che mi serve(ovvero quelle associate al worker da muovere)
        sendPositionWorkers(convertIntegerToSerialized_workers(positionworkers));//PositionWorkers viene inserito in input quando viene chiamato il metodo.
        choice=receiveInteger();
        return choice;//Restituisce il numero inserito dall'utente (quindi la posizione del vettore con la casella in cui costruire)
    }
    public int buildMessage(List<int[]> possible_choice){//Comunica con l`utente per decidere dove costruire
        int choice=-1;
        sendString("build");
        sendPositionsList(convertPositionListToSerializedInteger(possible_choice));
        choice=receiveInteger();
        return choice;//Restituisce il numero inserito dall`utente (Quindi la posizione del vettore con la casella in cui costruire)
    }
    public int specialchoice(String message){
        int choice=-1;
        sendString("spc-"+message);
        choice=receiveInteger();
        return choice;
    }
    public int numberOfPlayers(){//ask how much players gonna play
        int n=-1;
        sendString("nPlayers");
        n=receiveInteger();
        return n;
    }
    public String[] nickname_birthday(){
        String[] nick_birth=new String[2];
        sendString("nick_birth");
        nick_birth[0]=receiveString();//Nickname
        nick_birth[1]=receiveString();//Birth
        return nick_birth;
    }
    public int[][] setWorkers(List<int[]> takenPos){//Restituisce un vettore con le posizioni dei 2 workers
        sendPositionsList(convertPositionListToSerializedInteger(takenPos));
        sendString("workset");
        SerializedInteger[] appoggio=receivePositions();
        return convertSerializedToInteger_workers(appoggio);
    }
    public GlobalVariables.DivinityCard[] divinity_Choice(){
        sendString("dv_choice");
        sendInt(list.size());
        GlobalVariables.DivinityCard[] appoggio=receiveCards();
        return appoggio;
    }
    public GlobalVariables.DivinityCard[] divinity_Selection(GlobalVariables.DivinityCard[] ingame){
        sendString("div_sel");
        sendCards(ingame);
        GlobalVariables.DivinityCard[] choice=receiveCards();
        return choice;
    }
    public void sendBoard(char[] board){
        String result=board.toString();
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
            //todo chiama la disconnessione
        }
        return n;
    }

    public void sendString(String message){
        writer.println(message);
        if(writer.checkError()){
            //todo chiama la disconnessione
        }
    }

    public void sendPositionsArray(List<SerializedInteger>[] list){
        try{
            out_obj.writeObject(list);
        }
        catch(IOException e){
            //todo chiama la disconnessione
        }
    }

    public void sendPositionsList(List<SerializedInteger> list){
        try{
            out_obj.writeObject(list);
        }
        catch(IOException e){
            //todo chiama la disconnessione
        }
    }//A differenza del primo manda una sola lista e non un vettore di liste

    public void sendPositionWorkers(SerializedInteger[] positionworkers){
        try{
            out_obj.writeObject(positionworkers);
        }
        catch(IOException e){
            //todo chiama la disconnessione
        }
    }

    public String receiveString(){
        String n=null;
        try{
            n=buffer.readLine();
        }
        catch(IOException e){
            //todo chiama la disconnessione
        }
        return n;
    }

    public SerializedInteger[] receivePositions(){
        SerializedInteger[] pos=null;
        try{
            pos=(SerializedInteger[]) in_obj.readObject();
        }
        catch(IOException | ClassNotFoundException e) {
            //todo chiama la disconnessione
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
            temp[i].serialized=positions[i];
        }
        return temp;
    }

    public GlobalVariables.DivinityCard[] receiveCards(){
        GlobalVariables.DivinityCard[] appoggio=null;
        try {
            appoggio=(GlobalVariables.DivinityCard[]) in_obj.readObject();
        }
        catch(IOException | ClassNotFoundException e){
            //todo chiama la disconnessione
        }
        return appoggio;
    }

    public void sendInt(int send){
        try {
            out.write(send);
            out.flush();
        }
        catch(IOException e){
            //todo chiama la disconnessione
        }
    }

    public void sendCards(GlobalVariables.DivinityCard[] cards){
        try {
            out_obj.writeObject(cards);
        }
        catch(IOException e){
            //todo chiama la disconnessione
        }
    }

    public List<SerializedInteger> convertPositionListToSerializedInteger(List<int[]> lista){ //Converte il tipo da List<int> a Serialized Integer
        List<SerializedInteger> appoggio= new ArrayList<>();
        for(int[] elem: lista){
            appoggio.add(new SerializedInteger(elem));
        }
        return appoggio;
    }

    //public void printDisconnection()

    public void setPlayerBound(String s){
        playerBound =s;
    }

}