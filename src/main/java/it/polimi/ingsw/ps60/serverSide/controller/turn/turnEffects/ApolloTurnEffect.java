package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.serverSide.model.Player;
import it.polimi.ingsw.ps60.serverSide.model.Worker;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class ApolloTurnEffect extends BaseTurnEffect {
    @Override
    public void move(int[][] move) {
        Worker worker;
        Player player = game.getPlayerInGame().get();
        if(!(game.getCellByPosition(move[1]).isFree())){
            worker=game.getCellByPosition(move[1]).getWorkerIn();
            game.getCellByPosition(move[1]).getWorkerIn().moveWorker(player.getWorker(move[0][0]).getCellPosition());
            player.getWorker(move[0][0]).moveWorker(game.getCellByPosition(move[1]));
            player.getWorker(move[0][0]).getOldCell().setWorkerIn(worker);
        }
        else
            player.getWorker(move[0][0]).moveWorker(game.getCellByPosition(move[1]));
        player.setWorkerMoved(player.getWorker(move[0][0]));
    }
}
