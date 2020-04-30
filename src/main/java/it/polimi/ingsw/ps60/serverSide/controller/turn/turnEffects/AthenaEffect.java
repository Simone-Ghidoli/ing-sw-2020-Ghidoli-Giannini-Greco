package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Worker;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class AthenaEffect extends BaseEffect {

    @Override
    public void move(int[][] move) {
        super.move(move);
        Worker worker = game.getPlayerInGame().getNode().getValue().getWorkerMoved();
        GlobalVariables.DivinityCard.ATHENA.setBitException(worker.getOldCell().getBuildingLevel() < worker.getCellPosition().getBuildingLevel());
    }
}
