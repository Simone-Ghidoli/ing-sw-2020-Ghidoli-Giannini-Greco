package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.serverSide.model.Cell;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class AtlasTurnEffect extends BaseTurnEffect {

    @Override
    public void build(int[] build) {
        Cell cell = game.getCellByPosition(build);
        if (cell.getBuildingLevel() == 3 || build[2] == 1)
            cell.buildDome();
        else
            cell.incrementBuildingLevel();
        game.getPlayerInGame().get().setBuildByWorker(true);
    }
}