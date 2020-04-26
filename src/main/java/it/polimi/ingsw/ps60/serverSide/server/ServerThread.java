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
    private String Playerbound;
    protected Socket socket;
    private List<ServerThread> list;
    private InputStream in;
    private BufferedReader buffer;
    private PrintWriter writer;
    private ObjectInputStream in_obj;
    private ObjectOutputStream obj;

    public ServerThread(Socket soc, ArrayList<ServerThread> lista, String s) throws IOException {
        Playerbound = s;
        this.socket = soc;
        this.list = lista;
    }

    public int moveMessage(List<SerializedInteger>[] possible_choice,SerializedInteger[] positionworkers) {//Comunica con l'utente per decidere quale muratore muovere e dove
        int choice=-1;
        sendString("move");
        sendPositionsArray(possible_choice);//Invio solo la parte delle mosse che mi serve(ovvero quelle associate al worker da muovere)
        sendPositionWorkers(positionworkers);//PositionWorkers viene inserito in input quando viene chiamato il metodo.
        choice=receiveInteger();
        return choice;//Restituisce il numero inserito dall'utente (quindi la posizione del vettore con la casella in cui costruire)
    }
    public int buildMessage(List<SerializedInteger> possible_choice){//Comunica con l`utente per decidere dove costruire
        int choice=-1;
        sendString("build");
        sendPositionsList(possible_choice);
        choice=receiveInteger();
        return choice;//Restituisce il numero inserito dall`utente (Quindi la posizione del vettore con la casella in cui costruire)
    }
    public int specialchoice(String message){
        int choice=-1;
        sendString("spc-"+message);
        choice=receiveInteger();
        return choice;
    }
    public int numberOfPlayers(){
        int n=-1;
        sendString("nPlayers");
        n=receiveInteger();
        return n;
    }
    public String[] nickname_birthday(){
        String[] nick_birth=new String[2];
        sendString("nick_birth");
        nick_birth[0]=receiveString();
        nick_birth[1]=receiveString();
        return nick_birth;
    }
    public int[][] setWorkers(){//Restituisce un vettore con le posizioni dei 2 workers
        sendString("workset");
        SerializedInteger[] appoggio=receivePositions();
        return convertSerializedToInteger(appoggio);
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

    public int receiveInteger(){
        int n=-1;
        try {
            n = socket.getInputStream().read();
        }
        catch(IOException e){
            //todo chiama la disconnessione
        }
        return n;
    }

    public void sendString(String message){
        try {
            writer=new PrintWriter(socket.getOutputStream(),true);
            writer.println(message);
        }
        catch(IOException e){
            //todo chiama la disconnessione
        }
    }

    public void sendPositionsArray(List<SerializedInteger>[] list){
        try{
            obj=new ObjectOutputStream(socket.getOutputStream());
            obj.writeObject(list);
        }
        catch(IOException e){
            //todo chiama la disconnessione
        }
    }

    public void sendPositionsList(List<SerializedInteger> list){
        try{
            obj=new ObjectOutputStream(socket.getOutputStream());
            obj.writeObject(list);
        }
        catch(IOException e){
            //todo chiama la disconnessione
        }
    }//A differenza del primo manda una sola lista e non un vettore di liste

    public void sendPositionWorkers(SerializedInteger[] positionworkers){
        try{
            obj=new ObjectOutputStream(socket.getOutputStream());
            obj.writeObject(positionworkers);
        }
        catch(IOException e){
            //todo chiama la disconnessione
        }
    }

    public String receiveString(){
        String n=null;
        try{
            buffer=new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
            in_obj=new ObjectInputStream(socket.getInputStream());
            pos=(SerializedInteger[]) in_obj.readObject();
        }
        catch(IOException | ClassNotFoundException e) {
            //todo chiama la disconnessione
        }
        return pos;
    }

    public int[][] convertSerializedToInteger(SerializedInteger[] seria){
        int[][] appoggio=new int[2][2];
        for(int i=0;i<seria.length;i++){
            appoggio[i]=seria[i].serialized;
        }
        return appoggio;
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

    public void sendInt(int send){
        try {
            socket.getOutputStream().write(send);
            socket.getOutputStream().flush();
        }
        catch(IOException e){
            //todo chiama la disconnessione
        }
    }

    public void sendCards(GlobalVariables.DivinityCard[] cards){
        try {
            obj = new ObjectOutputStream(socket.getOutputStream());
            obj.writeObject(cards);
        }
        catch(IOException e){
            //todo chiama la disconnessione
        }
    }

}