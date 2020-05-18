package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.serverSide.model.Worker;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class AthenaTurnEffect extends BaseTurnEffect {

    @Override
    public void move(int[][] move) {
        super.move(move);
        Worker worker = game.getPlayerInGame().get().getWorkerMoved();
        game.getPlayerInGame().get().getDivinityStrategy().setBitException(worker.getOldCell().getBuildingLevel() < worker.getCellPosition().getBuildingLevel());
    }
}
