package it.polimi.ingsw.ps60.serverSide.controller;

import it.polimi.ingsw.ps60.GlobalVariables;

/**
 * Athena's class just switch the control bit of the god. (true->false or false->true).
 * Always set to false at the start of the turn and eventually set to true if one of the workers level up.
 * Standard move and build.
 */
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