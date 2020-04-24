package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.serverSide.model.Cell;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class HephaestusEffect extends TurnEffect {
    @Override
    public void build(int[] build) {
        super.build(build);

        Cell cell = game.getCellByPosition(build);
        if (build[2] == 1 && game.getCellByPosition(build).getBuildingLevel()!=3)
            cell.incrementBuildingLevel();
    }
}
