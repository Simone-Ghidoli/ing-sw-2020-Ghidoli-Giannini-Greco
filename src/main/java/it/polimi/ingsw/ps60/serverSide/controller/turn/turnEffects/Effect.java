package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

public interface Effect {

    void move(int[][] move);

    void build(int[] build);

    void endTurn();
}
