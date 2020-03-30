package it.polimi.ingsw.ps60.serverSide.controller.turnEffects;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Worker;

import java.util.List;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class AthenaEffect extends Turn {

    @Override
    public void move(int[][] move) {
        super.move(move);
        Worker worker = game.getPlayerInGame().getNode().getValue().getWorkerMoved();
        if (worker.getOldCell().getBuildingLevel() < worker.getCellPosition().getBuildingLevel()) {
            GlobalVariables.DivinityCard.ATHENA.setBitException(true);
        } else {
            GlobalVariables.DivinityCard.ATHENA.setBitException(false);
        }
    }
}
