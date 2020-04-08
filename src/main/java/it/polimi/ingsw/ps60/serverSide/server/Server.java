        package it.polimi.ingsw.ps60.serverSide.server;

        import java.io.*;
        import java.net.*;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;

        /**
         * Starts the server and open the connection between clinets and server. Puts Threads in an arraylist trehadsList
         */

        public  class Server{
            private int port;
            List<ServerThread> threadslist;
            private ArrayList<ServerThread> clientlist=new ArrayList<>();
            private ServerSocket Santorini;
            private Socket socket;
            private int numberOfPlayers;

            public Server(int porta){
                port=porta;
                threadslist=new ArrayList<>();
            }

            /**
             * Open connections between clinets and server. Get players' nicknames and the number of players.
             */
            public List<String> AvvioServer() throws IOException { //todo da riprogrammare sfruttando un po` il multithreading almeno per l`apertura delle connessioni. Per il resto va bene
                String s=null; //stringa di comodo per salvare i nomi dal metodo
                List<String> names=new ArrayList<>();
                while(Santorini.isClosed()) {
                    try {
                        Santorini = new ServerSocket(port);
                    } catch (IOException error) {
                        System.out.println("Errore apertura server");
                    }
                }
                while(socket.isClosed()) {//finchè non va a buon fine il collegamento del primo giocatore ci riprovo
                    try {// Accetto il primo giocatore e chiedo in quanti si gioca
                        socket = Santorini.accept();
                        numberOfPlayers = firstClientConnected();  //numero di giocatori in partita secondo il primo giocatore
                        s=nicknameRequest();
                    } catch (IOException error) {
                        if(!socket.isClosed())
                            socket.close();
                    }//Socket Chiuso e riparte la connessione del primo giocatore
                    if(!socket.isClosed()){
                        names.add(s);
                        ServerThread nuovo = new ServerThread(socket, clientlist,s);
                        clientlist.add(nuovo); //primo thread aggiunto alla lista
                    }
                }
                    while (clientlist.size() < numberOfPlayers) {//Collega i socket fino a quando si arriva al numero corretto di giocatori
                        try {
                            socket = Santorini.accept();
                            s=nicknameRequest();
                        } catch (IOException errore) {
                            if(!socket.isClosed())
                            socket.close();
                        }    //viene chiuso il socket e si riprova la connessione con il client che ha fallito
                        if (!socket.isClosed()) { //Se il socket è aperto crea un nuovo Thread e lo aggiunge alla lista di quelli in esecuzione
                            ServerThread nuovoThread = new ServerThread(socket, clientlist,s);
                            names.add(s);//aggiungo il nome alla lista dei nomi
                            clientlist.add(nuovoThread);
                        }
                    }
                    return names;
                }

            /**
             * @return the list of the sockets
             */
            public ArrayList<ServerThread> getsocketlist(){return clientlist;}//recupera la lista dei Thread

            /**
             * Decide il numero dei giocatori
             */
            public int firstClientConnected(){
                try {
                    PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
                    pr.println("numberofplayers");
                    return socket.getInputStream().read();//Aspetta la risposta del Client. Numero intero.

                }
                catch(IOException e){
                    return -1;
                }
            }

            /**
             * Chiede al giocatore di inserire il nome
             * La partita inizia con la stampa della board
             */
            public String nicknameRequest(){ //todo oltre al nickname inserire anche la data di nascita
                String name;
                try {
                    PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
                    pr.println("nameRequest");
                    BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));//Aspetta la risposta del client. Stringa
                    name=br.readLine();//Legge la risposta del client.
                    return name;
                }
                catch(IOException e){
                    return null;
                }
            }
        }