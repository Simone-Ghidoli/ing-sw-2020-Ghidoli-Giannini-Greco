package it.polimi.ingsw.ps60.serverSide.controller.turn.turnController;

import java.util.List;

public class AtlasHephaestusController extends TurnController{
    @Override
    public void buildingSection() {
        List<int[]> buildChoices = player.getDivinityStrategy().getTurnStrategyBuilding();
        int choice = player.getServerThread().buildMessage(buildChoices);

        if (buildChoices.get(choice)[2] == 1) {
            int specialChoice = player.getServerThread().specialchoice(player.getDivinityStrategy().getSpecialChoice());
            if (specialChoice == 0)
                player.getDivinityStrategy().setBuilding(new int[]{buildChoices.get(choice)[0], buildChoices.get(choice)[1], 0});
            else
                player.getDivinityStrategy().setBuilding(buildChoices.get(choice));
        }

        player.getDivinityStrategy().setBuilding(buildChoices.get(choice));
    }
}
