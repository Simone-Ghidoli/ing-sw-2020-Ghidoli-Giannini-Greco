package it.polimi.ingsw.ps60.serverSide.controller;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Worker;
import it.polimi.ingsw.ps60.serverSide.model.Board;

/**
 * Pan has a special win condition. If one of the player's worker drops by 2 level the player immediately wins.
 * Standard move and build.
 */
public class PanStrategy extends TurnStrategy {
    public void PanWinCondition(){
        Worker temp=GlobalVariables.game.getPlayerInGame().getNode().getValue().getWorkerMoved();
        if(temp.getOldCell().getBuildingLevel()<=temp.getCellPosition().getBuildingLevel()+2)
            GlobalVariables.game.win();  //Questo metodo è ancora da implementare.
    }
}
