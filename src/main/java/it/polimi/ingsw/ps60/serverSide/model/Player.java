package it.polimi.ingsw.ps60.serverSide.model;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.DivinityStrategy;
import it.polimi.ingsw.ps60.serverSide.server.ServerThread;

import java.io.Serializable;

public class Player implements Serializable {

    private final String nickname;
    private GlobalVariables.DivinityCard divinityCard;
    private final Worker[] workers;
    private Worker workerMoved;
    private boolean buildByWorker;
    private DivinityStrategy divinityStrategy;
    private ServerThread serverThread;

    /**
     *
     * @param nickname the nickname of the player
     */
    public Player(String nickname){
        this.nickname = nickname;
        workers = new Worker[2];
        for (int x = 0; x < 2; x++) {
            workers[x] = new Worker(this);
        }
        buildByWorker = false;
        serverThread = null;

    }

    public void setServerThread(ServerThread serverThread) {
        this.serverThread = serverThread;
    }

    /**
     *
     * @return return the nickname of the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     *
     * @param divinityCard set the divinity card identified by his enumeration
     */
    public void setDivinityCard(GlobalVariables.DivinityCard divinityCard) {
        this.divinityCard = divinityCard;
        divinityStrategy = new DivinityStrategy(divinityCard);
    }

    /**
     *
     * @return return the enumeration of the divinity card
     */
    public GlobalVariables.DivinityCard getDivinityCard() {
        return divinityCard;
    }

    public Worker[] getWorkers() {
        return workers;
    }

    public Worker getWorker(int workerNumber){
        return workers[workerNumber];
    }

    public Worker getWorkerMoved() {
        return workerMoved;
    }

    public void setWorkerMoved(Worker workerMoved) {
        this.workerMoved = workerMoved;
    }

    public boolean isBuildByWorker() {
        return buildByWorker;
    }

    public void setBuildByWorker(boolean i){
        buildByWorker = i;
    }

    public DivinityStrategy getDivinityStrategy() {
        return divinityStrategy;
    }

    public ServerThread getServerThread() {
        return serverThread;
    }
}
