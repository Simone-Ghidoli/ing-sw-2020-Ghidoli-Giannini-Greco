package it.polimi.ingsw.ps60.serverSide.controller.turn.turnController;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Player;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class PrometheusController extends TurnController {
    @Override
    public void turn() {
        Player player = game.getPlayerInGame().getNode().getValue();
        int specialChoice = 2;
        List<int[]>[] moveChoices;
        List<int[]> buildChoices;
        int choice;


        if (player.getServerThread().specialchoice(player.getDivinityStrategy().getSpecialChoice().split("\n")[0]) == 1) {
            specialChoice = player.getServerThread().specialchoice(player.getDivinityStrategy().getSpecialChoice().split("\n")[1]);
            player.setWorkerMoved(player.getWorker(specialChoice));
            buildChoices = player.getDivinityStrategy().getTurnStrategyBuilding();
            choice = player.getServerThread().buildMessage(buildChoices);
            player.getDivinityStrategy().setBuilding(buildChoices.get(choice));
            player.setWorkerMoved(null);
        }

        moveChoices = player.getDivinityStrategy().getTurnStrategyMovement();
        if (player.isBuildByWorker()) {
            if (specialChoice == 0) {
                moveChoices[1] = new ArrayList<>();
            } else if (specialChoice == 1) {
                moveChoices[0] = new ArrayList<>();
            }
        }
        player.setBuildByWorker(false);

        choice = player.getServerThread().moveMessage(moveChoices,
                new int[][]{player.getWorker(0).getCellPosition().getPosition(), player.getWorker(1).getCellPosition().getPosition()});

        if (moveChoices[0].size() - 1 >= choice)
            player.getDivinityStrategy().setMovement(new int[][]{new int[]{0, 0}, moveChoices[0].get(choice)});
        else {
            choice = choice - moveChoices[0].size() + 1;
            player.getDivinityStrategy().setMovement(new int[][]{new int[]{1, 0}, moveChoices[1].get(choice)});
        }

        buildChoices = player.getDivinityStrategy().getTurnStrategyBuilding();
        choice = player.getServerThread().buildMessage(buildChoices);

        player.getDivinityStrategy().setBuilding(buildChoices.get(choice));

        player.getDivinityStrategy().setEndTurn();
    }
}

