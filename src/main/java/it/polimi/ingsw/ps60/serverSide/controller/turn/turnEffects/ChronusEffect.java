package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class ChronusEffect extends TurnEffect {

    @Override
    public void winConditions () {
        super.winConditions();
        if (game.getCompleteTower() >= 5){
            game.win(game.getPlayerInGame().getNode().getValue());
        }
    }
}
