package it.polimi.ingsw.ps60.serverSide.controller.turn.turnController;

import java.util.List;

public class DemeterController extends TurnController {
    @Override
    public void buildingSection() {
        List<int[]> buildChoices = player.getDivinityStrategy().getTurnStrategyBuilding();
        int choice = player.getServerThread().buildMessage(buildChoices);
        player.getDivinityStrategy().setBuilding(buildChoices.get(choice));

        if (player.getServerThread().specialchoice(player.getDivinityStrategy().getSpecialChoice()) == 1) {
            buildChoices.remove(choice);
            choice = player.getServerThread().buildMessage(buildChoices);
            player.getDivinityStrategy().setBuilding(buildChoices.get(choice));
        }
    }
}
