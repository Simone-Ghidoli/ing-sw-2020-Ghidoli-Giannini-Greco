package it.polimi.ingsw.ps60.serverSide.controller.turn.turnController;

import java.util.List;

public class HestiaTurnController extends BaseTurnController {
    @Override
    public void buildingSection() {
        super.buildingSection();

        sendBoardToClient();

        List<int[]> buildChoices = player.getDivinityStrategy().getTurnStrategyBuilding();
        int i = 0;
        while (i < buildChoices.size()) {
            if (buildChoices.get(i)[0] == 0 || buildChoices.get(i)[0] == 4 || buildChoices.get(i)[1] == 0 || buildChoices.get(i)[1] == 4)
                buildChoices.remove(i);
            else
                i++;
        }
        if (buildChoices.size() != 0) {
            if (player.getServerThread().specialChoice(player.getDivinityStrategy().getSpecialChoice()) == 1)
                player.getDivinityStrategy().setBuilding(buildChoices.get(player.getServerThread().buildMessage(buildChoices)));
        }
    }
}