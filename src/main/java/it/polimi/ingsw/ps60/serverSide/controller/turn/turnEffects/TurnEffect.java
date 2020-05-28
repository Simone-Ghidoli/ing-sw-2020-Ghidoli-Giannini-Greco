package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

public interface TurnEffect {

    /**
     * This method will change the model for a given move
     *
     * @param move is the move selected by the player between all the moves provided by the strategy
     */
    void move(int[][] move);

    /**
     * This method will change the model for a given build
     *
     * @param build is the build selected by the player between all the builds provided by the strategy
     */
    void build(int[] build);

    /**
     * This method, at the end of the turn, will check if a player has lost or win and will change the player in game
     */
    void endTurn();
}