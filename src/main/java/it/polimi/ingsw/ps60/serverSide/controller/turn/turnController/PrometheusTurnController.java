package it.polimi.ingsw.ps60.serverSide.controller.turn.turnController;

import java.util.ArrayList;
import java.util.List;

public class PrometheusTurnController extends BaseTurnController {
    @Override
    public void movementSection() {

        int specialChoice = 2;
        int choice;

        if (player.getServerThread().specialChoice(player.getDivinityStrategy().getSpecialChoice().split("\n")[0]) == 1) {
            specialChoice = player.getServerThread().specialChoice(player.getDivinityStrategy().getSpecialChoice().split("\n")[1]);
            player.setWorkerMoved(player.getWorker(specialChoice));
            List<int[]> buildChoices = player.getDivinityStrategy().getTurnStrategyBuilding();
            if (buildChoices.size() != 0) {
                choice = player.getServerThread().buildMessage(buildChoices);
                player.getDivinityStrategy().setBuilding(buildChoices.get(choice));
            }
            player.setWorkerMoved(null);
        }

        List<int[]>[] moveChoices = player.getDivinityStrategy().getTurnStrategyMovement();
        if (player.isBuildByWorker()) {
            if (specialChoice == 0) {
                moveChoices[1] = new ArrayList<>();
            } else if (specialChoice == 1) {
                moveChoices[0] = new ArrayList<>();
            }
        }
        player.setBuildByWorker(false);

        if (moveChoices[specialChoice].size() != 0) {

            choice = player.getServerThread().moveMessage(moveChoices,
                    new int[][]{player.getWorker(0).getCellPosition().getPosition(), player.getWorker(1).getCellPosition().getPosition()});

            if (moveChoices[0].size() >= (choice + 1))
                player.getDivinityStrategy().setMovement(new int[][]{new int[]{0, 0}, moveChoices[0].get(choice)});
            else {
                choice = choice - moveChoices[0].size();
                player.getDivinityStrategy().setMovement(new int[][]{new int[]{1, 0}, moveChoices[1].get(choice)});
            }
        }
        else {
            player.getServerThread().lossMessage("Unable to move in any position");
            endTurnSection();
        }
    }
}

