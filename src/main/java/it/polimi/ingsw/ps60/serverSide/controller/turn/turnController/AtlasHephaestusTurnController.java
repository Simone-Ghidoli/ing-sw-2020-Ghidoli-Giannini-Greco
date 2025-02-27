package it.polimi.ingsw.ps60.serverSide.controller.turn.turnController;

import java.util.List;

public class AtlasHephaestusTurnController extends BaseTurnController {
    @Override
    public void buildingSection() {
        List<int[]> buildChoices = player.getDivinityStrategy().getTurnStrategyBuilding();
        if (buildChoices.size() != 0) {

            player.getServerThread().sendAlert("Select where to build");

            int choice = player.getServerThread().buildMessage(buildChoices);

            if (buildChoices.get(choice)[2] == 1) {
                if (player.getServerThread().specialChoice(player.getDivinityStrategy().getSpecialChoice()) == 0)
                    player.getDivinityStrategy().setBuilding(new int[]{buildChoices.get(choice)[0], buildChoices.get(choice)[1], 0});
                else
                    player.getDivinityStrategy().setBuilding(buildChoices.get(choice));
            } else
                player.getDivinityStrategy().setBuilding(buildChoices.get(choice));
        } else {
            player.getServerThread().lossMessage("Unable to move in any position");
            lost = true;
        }
    }
}
