package it.polimi.ingsw.ps60.serverSide.model;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.DivinityController;
import it.polimi.ingsw.ps60.serverSide.server.ServerThread;

public class Player {

    private GlobalVariables.IdPlayer id;
    private String nickname;
    private GlobalVariables.DivinityCard divinityCard;
    private Worker[] workers;
    private GlobalVariables.Colour colour;
    private Worker workerMoved;
    private boolean buildByWorker;
    private DivinityController divinityController;
    private ServerThread serverThread;


    /**
     *
     * @param idPlayer the enumeration of the id of the player
     * @param nickname the nickname of the player
     */
    public Player(GlobalVariables.IdPlayer idPlayer, String nickname){
        this.id = idPlayer;
        this.nickname = nickname;
        workers = new Worker[2];
        for (int x = 0; x < 2; x++) {
            workers[x] = new Worker(idPlayer.getIdWorkers()[x], this);
        }
        buildByWorker = false;
        serverThread = null;

    }

    public void setServerThread(ServerThread serverThread) {
        this.serverThread = serverThread;
    }

    /**
     *
     * @return the enumeration of the player
     */
    public GlobalVariables.IdPlayer getId() {
        return id;
    }

    /**
     *
     * @return return rhe nickname of the player
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
        divinityController = new DivinityController(divinityCard);
    }

    /**
     *
     * @return return the enumeration of the divinity card
     */
    public GlobalVariables.DivinityCard getDivinityCard() {
        return divinityCard;
    }


    /**
     *
     * @param colour set the colour of the workers, colour is identified by his enumeration
     */
    public void setColour(GlobalVariables.Colour colour) {
        this.colour = colour;
    }

    /**
     *
     * @return return the colour of the workers, colour is identified by is enumeration
     */
    public GlobalVariables.Colour getColour() {
        return colour;
    }

    public Worker[] getWorkers() {
        return workers;
    }

    public Worker getWorker(int i){
        return workers[i];
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

    public DivinityController getDivinityController() {
        return divinityController;
    }
}
