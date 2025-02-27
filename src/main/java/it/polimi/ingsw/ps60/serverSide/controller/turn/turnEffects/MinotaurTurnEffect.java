package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.serverSide.model.Worker;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class MinotaurTurnEffect extends BaseTurnEffect {

    @Override
    public void move(int[][] move) {
        Worker worker = game.getPlayerInGame().get().getWorker(move[0][0]);
        int[] delta = {move[1][0] - worker.getCellPosition().getPosition()[0],
                move[1][1] - worker.getCellPosition().getPosition()[1]};

        if (!game.getCellByPosition(move[1]).isFree())
            game.getCellByPosition(move[1]).getWorkerIn().moveWorker
                    (game.getCellByPosition(new int[]{move[1][0] + delta[0], move[1][1] + delta[1]}));
        super.move(move);
    }
}