package it.polimi.ingsw.ps60.serverSide.model;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.DivinityStrategy;
import it.polimi.ingsw.ps60.serverSide.server.ServerThread;

import java.io.Serializable;

public class Player implements Serializable {

    private final String nickname;
    private int divinityCard;
    private final Worker[] workers;
    private Worker workerMoved;
    private boolean buildByWorker;
    private transient ServerThread serverThread;

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
        for (int i = 0; i < GlobalVariables.DivinityCard.values().length; i++){
            if (divinityCard.equals(GlobalVariables.DivinityCard.values()[i])) {
                this.divinityCard = i;
                break;
            }
        }
    }

    /**
     *
     * @return return the enumeration of the divinity card
     */
    public GlobalVariables.DivinityCard getDivinityCard() {
        return GlobalVariables.DivinityCard.values()[divinityCard];
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
        return new DivinityStrategy(getDivinityCard());
    }

    public ServerThread getServerThread() {
        return serverThread;
    }
}
