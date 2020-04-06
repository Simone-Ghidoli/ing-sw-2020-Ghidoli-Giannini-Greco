package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.serverSide.model.Player;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class PanEffect extends TurnEffect {
    @Override
    public void winConditions() {
        super.winConditions();
        Player player = game.getPlayerInGame().getNode().getValue();

        if (player.getWorkerMoved().getOldCell().getBuildingLevel() - player.getWorkerMoved().getCellPosition().getBuildingLevel() >= 2)
            game.win(player);
    }
}