package it.polimi.ingsw.ps60.serverSide.server;

        import it.polimi.ingsw.ps60.GlobalVariables;
        import it.polimi.ingsw.ps60.serverSide.model.Cell;

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

    public ServerThread(Socket soc, ArrayList<ServerThread> lista) throws IOException {
        this.socket = soc;
        this.list = lista;
        writer = new PrintWriter(socket.getOutputStream(), true);
        in = socket.getInputStream();
        buffer = new BufferedReader(new InputStreamReader(in));
        out= new DataOutputStream(socket.getOutputStream());
    }

    public String getPlayerbound(){return this.Playerbound;}

    public String names(){//Per recuperare gli username dei player
        String name=null;
        try{
            name=buffer.readLine();
        }
        catch(IOException error){
            System.out.println("Communication error");
            error.printStackTrace();
        }
        this.Playerbound=name;
        return name;
    }


    public int numeroGiocatori() {//Viene chiamata solo per il primo giocatore che si connette
        int number=-1;
        try{
            writer.println("2 or 3 players?");
            number=in.read();
        }
        catch(IOException error){
            System.out.println("Communication error");
            error.printStackTrace();
        }
        return number;
    }
    
    public void directMessage(String chiamante){
        writer.println(chiamante);
    }

    public String anything(String chiamante){ //chiamante è una stringa che viene inserita dal chiamante. Contiene il messaggio da mandare sul client
        String message=null;
        try {
            writer.println(chiamante);
            message=buffer.readLine();
        }
        catch(IOException error){
            System.out.println("Communication error");
            error.printStackTrace();
        }
        return message;
    }

    public void printmatrix(){
        //Questo stamperà il matrix a video.
        Cell[][] matrix=GlobalVariables.game.getCellMatrix();
        for(int i=0;i<5;i++){
            for(int k=0;k<5;k++){
                //Varie condizioni logiche per la stampa
            }
        }
    }
    private void disconnect(){
        //disconnesione del client per qualsiasi motivo
    }
}