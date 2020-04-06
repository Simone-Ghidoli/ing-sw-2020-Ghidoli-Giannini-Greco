package it.polimi.ingsw.ps60.clientSide.view.client;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import it.polimi.ingsw.ps60.serverSide.controller.turn.SerializedInteger;

public class ClientParser extends Thread{
    private List<String> messagesFromServer;
    private Socket socket;
    BufferedReader br;
    PrintWriter pr;
    ObjectInputStream obj;
    InputStream input;

    public ClientParser(Socket sock,List<String> messages){
        socket=sock;
        messagesFromServer=messages;
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
    public void movement(){
        flushInput();//flush per evitare che ci siano stati inserimenti prima della chiamata del metodo
        List<SerializedInteger> stalin;
        System.out.println("Which worker do you want to move?\n");
        int worker;
        worker=integerInput(2,1);
        sendInt(worker);//Invio worker da muovere al server
        stalin=recieveList();//recupero le posizioni
        //todo chiamare la stampa delle posizioni a video.
        int pos;
        pos=integerInput(stalin.size(),1);//todo chiedere a vinc il valore della prima stampa
        sendInt(pos);
    }

    public void building(){//Molto simile a build ma viene saltata la fase di scelta del worker
        flushInput();
        List<SerializedInteger> stalin;
        stalin=recieveList();
        //todo chiama la stampa delle posizioni a video SOLO DI STALIN[0]!!!
        int pos;
        pos=integerInput(stalin.size(),1);
        sendInt(pos);
    }

    public void specialChoice(String message){//Viene fatta una scelta speciale. Viene inviato a server il responso: 1=sì 2=no
        int n;//Di solito la scelta speciale è segnata da un intero.
        n=integerInput(1,0);
        sendInt(n);
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


    public List<SerializedInteger> recieveList(){
        try {
            List<SerializedInteger> stalin;
            input = socket.getInputStream();//preparo lo stream per ricevere la lista di posizioni
            obj = new ObjectInputStream(input);
            stalin = (List<SerializedInteger>) obj.readObject();
            return stalin;
        }
        catch(IOException | ClassNotFoundException e){
            //todo chiama la disconnessione
        }
        return null;
    }

    public void flushInput(){
        try {
            System.in.read(new byte[System.in.available()]);
        }
        catch(IOException e_0){}
    }

    public int integerInput(int upperLimit,int lowerLimit){//I limiti sono compresi nei valori accettabili
        Scanner scanner;
        int n=-1;
        flushInput();
        do{
            scanner=new Scanner(System.in);
            n=scanner.nextInt();
            if(n<lowerLimit||n>upperLimit)
                System.out.println("Input non valido");
        }while(n<lowerLimit||n>upperLimit);
        return n;
    }

    //stampaBoard() non so se esiste già da qualche parte. Nel caso non serve riscriverla basta chiamarla dal parser.
}
