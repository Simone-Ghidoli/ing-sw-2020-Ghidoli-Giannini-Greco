package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

public interface Turn {

    public void move(int[][] move);

    public void build(int[] build);

    public void endTurn();
}
