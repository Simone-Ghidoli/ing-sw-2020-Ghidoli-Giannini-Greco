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
     * @param nickname the nickname of the player
     */
    public Player(String nickname) {
        this.nickname = nickname;
        workers = new Worker[2];
        for (int x = 0; x < 2; x++) {
            workers[x] = new Worker(this);
        }
        buildByWorker = false;
        serverThread = null;

    }

    /**
     * This method set the serverThread of the player
     *
     * @param serverThread the serverThread of the player
     */
    public void setServerThread(ServerThread serverThread) {
        this.serverThread = serverThread;
    }

    /**
     * @return return the nickname of the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param divinityCard set the divinity card identified by his enumeration
     */
    public void setDivinityCard(GlobalVariables.DivinityCard divinityCard) {
        for (int i = 0; i < GlobalVariables.DivinityCard.values().length; i++) {
            if (divinityCard.equals(GlobalVariables.DivinityCard.values()[i])) {
                this.divinityCard = i;
                break;
            }
        }
    }

    /**
     * @return return the enumeration of the divinity card
     */
    public GlobalVariables.DivinityCard getDivinityCard() {
        return GlobalVariables.DivinityCard.values()[divinityCard];
    }

    public Worker[] getWorkers() {
        return workers;
    }

    /**
     * This method returns a specific player
     *
     * @param workerNumber 0 for worker 1 and 1 for worker 2
     * @return The worker specified by parameter
     */
    public Worker getWorker(int workerNumber) {
        return workers[workerNumber];
    }

    /**
     * This method return the worker that has been moved in the turn
     *
     * @return the worker that has been moved
     */
    public Worker getWorkerMoved() {
        return workerMoved;
    }

    /**
     * This method set the worker that has been moved in the turn
     *
     * @param workerMoved the worker that has been moved in the turn
     */
    public void setWorkerMoved(Worker workerMoved) {
        this.workerMoved = workerMoved;
    }

    /**
     * This method returns if worker has built in a turn
     *
     * @return tre if has built, false otherwise
     */
    public boolean isBuildByWorker() {
        return buildByWorker;
    }

    /**
     * This method set if a worker has built in a turn
     *
     * @param i true if has built, false otherwise
     */
    public void setBuildByWorker(boolean i) {
        buildByWorker = i;
    }

    /**
     * This methods return a divinity strategy
     *
     * @return the divinity strategy associated at the player's divinity card
     */
    public DivinityStrategy getDivinityStrategy() {
        return new DivinityStrategy(getDivinityCard());
    }

    /**
     * This method return the serverThread associated to the player
     *
     * @return serverThread associated to the player
     */
    public ServerThread getServerThread() {
        return serverThread;
    }
}