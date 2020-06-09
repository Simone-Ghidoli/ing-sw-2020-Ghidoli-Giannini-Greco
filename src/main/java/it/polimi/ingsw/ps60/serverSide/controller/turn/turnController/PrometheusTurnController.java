package it.polimi.ingsw.ps60.serverSide.controller.turn.turnController;

import java.util.ArrayList;
import java.util.List;

public class PrometheusTurnController extends BaseTurnController {

    @Override
    public void movementSection() {

        int choice = -1;

        List<int[]>[] buildChoices = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            player.setWorkerMoved(player.getWorker(i));
            buildChoices[i] = player.getDivinityStrategy().getTurnStrategyBuilding();
        }

        if (buildChoices[0].size() != 0 || buildChoices[1].size() != 0) {
            if (player.getServerThread().specialChoice(player.getDivinityStrategy().getSpecialChoice().split("\n")[0]) == 1) {

                player.getServerThread().sendAlert(player.getDivinityStrategy().getSpecialChoice().split("\n")[1]);
                choice = player.getServerThread().moveMessage(buildChoices,
                        new int[][]{player.getWorker(0).getCellPosition().getPosition(), player.getWorker(1).getCellPosition().getPosition()});
                if (buildChoices[0].size() > choice) {
                    player.getDivinityStrategy().setBuilding(buildChoices[0].get(choice));
                    choice = 0;
                } else {
                    choice = choice - buildChoices[0].size();
                    player.getDivinityStrategy().setBuilding(buildChoices[1].get(choice));
                    choice = 1;
                }
                sendBoardToClient();
            }
        }
        player.setWorkerMoved(null);


        List<int[]>[] moveChoices = player.getDivinityStrategy().getTurnStrategyMovement();
        if (player.isBuildByWorker()) {
            if (choice == 0) {
                moveChoices[1] = new ArrayList<>();
            } else if (choice == 1) {
                moveChoices[0] = new ArrayList<>();
            }
        }
        player.setBuildByWorker(false);

        if (moveChoices[0].size() > 0 || moveChoices[1].size() > 0) {

            player.getServerThread().sendAlert("Select where to move");

            choice = player.getServerThread().moveMessage(moveChoices,
                    new int[][]{player.getWorker(0).getCellPosition().getPosition(), player.getWorker(1).getCellPosition().getPosition()});

            if (moveChoices[0].size() >= (choice + 1))
                player.getDivinityStrategy().setMovement(new int[][]{new int[]{0, 0}, moveChoices[0].get(choice)});
            else {
                choice = choice - moveChoices[0].size();
                player.getDivinityStrategy().setMovement(new int[][]{new int[]{1, 0}, moveChoices[1].get(choice)});
            }
        } else {
            player.getServerThread().lossMessage("Unable to move in any position");
            lost = true;
        }
    }
}