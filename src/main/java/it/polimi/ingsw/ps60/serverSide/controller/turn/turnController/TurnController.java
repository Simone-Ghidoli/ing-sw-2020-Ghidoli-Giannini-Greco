package it.polimi.ingsw.ps60.serverSide.controller.turn.turnController;

public interface TurnController {

    /**
     * This method is the sequence of the sections in a turn
     */
    void turn();

    /**
     * This method is the sequence of actions in the movement section
     */
    void movementSection();

    /**
     * This method is the sequence of actions in the building section
     */
    void buildingSection();

    /**
     * This method is the sequence of actions in the end turn section
     */
    void endTurnSection();

    /**
     * This method will send the board to all the clients
     */
    void sendBoardToClient();
}
