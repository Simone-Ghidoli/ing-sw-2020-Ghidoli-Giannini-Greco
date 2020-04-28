package it.polimi.ingsw.ps60.serverSide.server;

import java.util.concurrent.Callable;

public class FirstPlayerConnected implements Callable<Integer>{
    ServerThread server;
    FirstPlayerConnected(ServerThread user) {
        server=user;
    }
    @Override
    public Integer call(){
        int n=server.numberOfPlayers();
        if(n!=2&&n!=3){
            
        }
        return n;
    }
}
