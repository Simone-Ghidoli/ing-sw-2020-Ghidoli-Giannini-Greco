package it.polimi.ingsw.ps60.serverSide.controller.turnStrategy;

import it.polimi.ingsw.ps60.GlobalVariables;


public class AthenaStrategy extends TurnStrategy {
    public void ChangeBit(){
        GlobalVariables.DivinityCard.ATHENA.setBitException(false);
    }

    /**
     * Verify if the moved worker was in a lower level before the move.
     */
    public void LevelUpVerify(){
        if(GlobalVariables.game.getPlayerInGame().getNode().getValue().getWorkerMoved().isLeveledUp())
            GlobalVariables.DivinityCard.ATHENA.setBitException(true);
    }
}