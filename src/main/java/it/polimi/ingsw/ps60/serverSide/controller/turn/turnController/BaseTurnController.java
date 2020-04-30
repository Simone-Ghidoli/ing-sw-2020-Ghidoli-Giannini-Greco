package it.polimi.ingsw.ps60.serverSide.controller.turn.turnController;

import it.polimi.ingsw.ps60.serverSide.model.Player;
import java.util.List;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class BaseTurnController implements TurnController {
    Player player;

    public void turn(){
        player = game.getPlayerInGame().getNode().getValue();

        movementSection();
        buildingSection();
        endTurnSection();
    }

    public void movementSection(){
        List<int[]>[] moveChoices = player.getDivinityStrategy().getTurnStrategyMovement();
        int choice = player.getServerThread().moveMessage(moveChoices,
                new int[][]{player.getWorker(0).getCellPosition().getPosition(), player.getWorker(1).getCellPosition().getPosition()});
        if (moveChoices[0].size() >= choice + 1)
            player.getDivinityStrategy().setMovement(new int[][]{new int[]{0, 0}, moveChoices[0].get(choice)});
        else {
            choice = choice - moveChoices[0].size() + 1;
            player.getDivinityStrategy().setMovement(new int[][]{new int[]{1, 0}, moveChoices[1].get(choice)});
        }
    }

    public void buildingSection(){
        List<int[]> buildChoices = player.getDivinityStrategy().getTurnStrategyBuilding();
        int choice = player.getServerThread().buildMessage(buildChoices);
        player.getDivinityStrategy().setBuilding(buildChoices.get(choice));
    }

    public void endTurnSection(){
        player.getDivinityStrategy().setEndTurn();
    }

}