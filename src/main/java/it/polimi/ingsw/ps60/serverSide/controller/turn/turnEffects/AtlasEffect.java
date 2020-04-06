package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.serverSide.model.Cell;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class AtlasEffect extends Turn {

    @Override
    public void build(int[] build){ //Ci devono essere 3 elementi nel vettore (da 0 a 2)
        Cell cell=game.getCellByPosition(build);
        if(cell.getBuildingLevel()==3 || build[2] == 1) // 0 non dome
            cell.buildDome();
        else
            cell.incrementBuildingLevel();
        game.getPlayerInGame().getNode().getValue().setBuildByWorker(true);
    }
}
