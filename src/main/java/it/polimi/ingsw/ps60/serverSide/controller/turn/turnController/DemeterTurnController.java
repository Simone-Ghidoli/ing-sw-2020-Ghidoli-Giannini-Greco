package it.polimi.ingsw.ps60.serverSide.controller.turn.turnController;

import java.util.List;

public class DemeterTurnController extends BaseTurnController {
    @Override
    public void buildingSection() {
        List<int[]> buildChoices = player.getDivinityStrategy().getTurnStrategyBuilding();
        if (buildChoices.size() != 0) {
            int choice = player.getServerThread().buildMessage(buildChoices);
            player.getDivinityStrategy().setBuilding(buildChoices.get(choice));

            if (player.getServerThread().specialChoice(player.getDivinityStrategy().getSpecialChoice()) == 1) {
                buildChoices.remove(choice);
                choice = player.getServerThread().buildMessage(buildChoices);
                player.getDivinityStrategy().setBuilding(buildChoices.get(choice));
            }
        }
        else {
            player.getServerThread().lossMessage("Unable to move in any position");
            lost = true;
        }
    }
}
