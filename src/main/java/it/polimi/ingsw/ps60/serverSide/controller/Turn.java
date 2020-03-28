package it.polimi.ingsw.ps60.serverSide.controller;

import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.serverSide.model.Player;

import static it.polimi.ingsw.ps60.GlobalVariables.game;


public class Turn {
    public void move(int[][] move) {
        Player player = game.getPlayerInGame().getNode().getValue();
        player.getWorker(move[0][0]).moveWorker(game.getCellByPosition(move[1]));
        player.setWorkerMoved(player.getWorker(move[0][0]));
    }


    public void build(int[][] build){
        Cell cell = game.getCellByPosition(build[1]);
        if (cell.getBuildingLevel() == 3)
            cell.buildDome();
        else
            cell.incrementBuildingLevel();
    }

    public void endTurn(){
        Player player = game.getPlayerInGame().getNode().getValue();
        if (player.getWorkerMoved()!= null)
            player.setWorkerMoved(null);
        else
            game.lose(player);
        game.changeTurn();
    }
}
