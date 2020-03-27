package it.polimi.ingsw.ps60.serverSide.model;

import it.polimi.ingsw.ps60.GlobalVariables;

public class Player {

    private GlobalVariables.IdPlayer id;
    private String nickname;
    private GlobalVariables.DivinityCard divinityCard;
    private Worker worker1, worker2;
    private GlobalVariables.Colour colour;
    private Worker workerMoved;

    /**
     *
     * @param idPlayer the enumeration of the player
     * @param idWorker1 the enumeration of the first worker of the player
     * @param idWorker2 the enumeration of the second worker of the player
     * @param nickname set the nickname of the player
     */
    public Player(GlobalVariables.IdPlayer idPlayer,
                  GlobalVariables.IdWorker idWorker1, GlobalVariables.IdWorker idWorker2, String nickname){
        this.id = idPlayer;
        this.nickname = nickname;
        worker1 = new Worker(idWorker1, this);
        worker2 = new Worker(idWorker2, this);
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

    /**
     *
     * @return return the first worker of the player
     */
    public Worker getWorker1() {
        return worker1;
    }

    /**
     *
     * @return return the second worker of the player
     */
    public Worker getWorker2() {
        return worker2;
    }

    public Worker getWorkerMoved() {
        return workerMoved;
    }

    public void setWorkerMoved(Worker workerMoved) {
        this.workerMoved = workerMoved;
    }
}
