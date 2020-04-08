package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

public interface Turn {

    void move(int[][] move);

    void build(int[] build);

    void endTurn();
}
