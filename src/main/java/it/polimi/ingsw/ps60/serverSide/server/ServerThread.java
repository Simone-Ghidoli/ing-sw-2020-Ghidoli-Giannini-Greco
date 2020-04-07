package it.polimi.ingsw.ps60.serverSide.server;

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
    private DataOutputStream out;
    private ObjectOutputStream obj;

    public ServerThread(Socket soc, ArrayList<ServerThread> lista, String s) throws IOException {
        Playerbound = s;
        this.socket = soc;
        this.list = lista;
    }

    public int[] moveMessage(List<SerializedInteger>[] possible_choice) {//Comunica con l'utente per decidere quale muratore muovere e dove
        int[] choice = new int[]{-1,-1};
        sendString("move");
        choice[0]=receiveInteger(); // Quale muratore si intende muovere
        sendPositions(possible_choice[choice[0]]);//Invio solo la parte delle mosse che mi serve(ovvero quelle associate al worker da muovere)
        choice[1]=receiveInteger();// Quale mossa si è scelto di fare
        return choice;//Restituisce il numero inserito dall'utente (quindi la posizione del vettore con la casella in cui costruire)
    }
    public int buildMessage(List<SerializedInteger> possible_choice){//Comunica con l`utente per decidere dove costruire
        int choice=-1;
        sendString("build");
        sendPositions(possible_choice);
        choice=receiveInteger();
        return choice;//Restituisce il numero inserito dall`utente (Quindi la posizione del vettore con la casella in cui costruire)
    }
    public void printBoard(){

    }
    public int[] divinity_card_ingame(){//Farebbe comodo avere una vettore con le corrispondenti carte divinità
        int[] cards=new int[3];
        writer.println("divinity3");//Vengono comuniate le 3 carte scelte come 3 interi. Si recupera la carta corrispondente grazie ad una lista.
        cards[0]=receiveInteger();
        cards[1]=receiveInteger();
        cards[2]=receiveInteger();
        return cards;//ritorna le 3 carte scelte
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

    public void sendPositions(List<SerializedInteger> list){
        try{
            obj=new ObjectOutputStream(socket.getOutputStream());
            obj.writeObject(list);
        }
        catch(IOException e){
            //todo chiama la disconnessione
        }
    }
}