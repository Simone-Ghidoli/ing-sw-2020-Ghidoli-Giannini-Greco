package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.serverSide.model.Player;

import static it.polimi.ingsw.ps60.GlobalVariables.game;


public class BaseEffect implements Effect {
    public void move(int[][] move) {
        Player player = game.getPlayerInGame().get();
        player.getWorker(move[0][0]).moveWorker(game.getCellByPosition(move[1]));
        player.setWorkerMoved(player.getWorker(move[0][0]));
    }


    public void build(int[] build){
        Cell cell = game.getCellByPosition(build);
        if (cell.getBuildingLevel() == 3)
            cell.buildDome();
        else
            cell.incrementBuildingLevel();
        game.getPlayerInGame().get().setBuildByWorker(true);
    }

    public void endTurn(){
        Player player = game.getPlayerInGame().get();
        if (player.getWorkerMoved()!= null && player.isBuildByWorker()) {
            winConditions();
            player.getWorkerMoved().setOldCell(null);
            player.setWorkerMoved(null);
            player.setBuildByWorker(false);
        }
        else {
            game.lose(player);
            if (game.getPlayersNumber() == 1) {
                game.changeTurn();
                game.win(game.getPlayerInGame().get());
                return;
            }
        }
        game.changeTurn();
    }

    public void winConditions(){
        Player player =game.getPlayerInGame().get();
        if (player.getWorkerMoved().isLeveledUp() && player.getWorkerMoved().getCellPosition().getBuildingLevel() == 3)
            game.win(player);
    }
}