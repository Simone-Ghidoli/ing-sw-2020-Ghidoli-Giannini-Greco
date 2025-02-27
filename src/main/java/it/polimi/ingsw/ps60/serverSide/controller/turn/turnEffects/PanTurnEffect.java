package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.serverSide.model.Player;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class PanTurnEffect extends BaseTurnEffect {

    @Override
    public void winConditions() {
        super.winConditions();
        Player player = game.getPlayerInGame().get();

        if (player.getWorkerMoved().getOldCell().getBuildingLevel() - player.getWorkerMoved().getCellPosition().getBuildingLevel() >= 2)
            game.win(player);
    }
}