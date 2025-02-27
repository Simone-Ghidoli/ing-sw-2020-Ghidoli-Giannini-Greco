package it.polimi.ingsw.ps60.serverSide.controller.turn.turnController;

import java.util.List;

public class DemeterTurnController extends BaseTurnController {
    @Override
    public void buildingSection() {
        List<int[]> buildChoices = player.getDivinityStrategy().getTurnStrategyBuilding();
        if (buildChoices.size() != 0) {

            player.getServerThread().sendAlert("Select where to build");

            int choice = player.getServerThread().buildMessage(buildChoices);
            player.getDivinityStrategy().setBuilding(buildChoices.remove(choice));

            if (buildChoices.size() > 0)
                if (player.getServerThread().specialChoice(player.getDivinityStrategy().getSpecialChoice()) == 1)
                    player.getDivinityStrategy().setBuilding(buildChoices.get(player.getServerThread().buildMessage(buildChoices)));
        } else {
            player.getServerThread().lossMessage("Unable to move in any position");
            lost = true;
        }
    }
}