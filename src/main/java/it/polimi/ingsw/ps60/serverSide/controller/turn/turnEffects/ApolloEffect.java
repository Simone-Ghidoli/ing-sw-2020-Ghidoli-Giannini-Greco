package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.serverSide.model.Player;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class ApolloEffect extends TurnEffect {
    @Override
    public void move(int[][] move) {
        Player player = game.getPlayerInGame().getNode().getValue();
        if(!(game.getCellByPosition(move[1]).isFree())){
            game.getCellByPosition(move[1]).getWorkerIn().moveWorker(player.getWorker(move[0][0]).getCellPosition());
        }
        player.getWorker(move[0][0]).moveWorker(game.getCellByPosition(move[1]));
        player.setWorkerMoved(player.getWorker(move[0][0]));
    }
}
