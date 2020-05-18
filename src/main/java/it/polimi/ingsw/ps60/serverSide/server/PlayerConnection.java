package it.polimi.ingsw.ps60.serverSide.server;

import java.net.Socket;
import java.util.List;
import java.util.concurrent.Callable;

public class PlayerConnection implements Callable<String[]> {
    List<ServerThread> list;
    ServerThread thread;
    Socket socket;

    public PlayerConnection(Socket sock,List<ServerThread> list_in,ServerThread thh){
        list = list_in;
        socket = sock;
        thread = thh;
    }

    @Override
    public String[] call(){
        return thread.nicknameBirthday();
    }
}
