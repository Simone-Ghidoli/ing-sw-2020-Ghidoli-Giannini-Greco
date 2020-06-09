package it.polimi.ingsw.ps60.serverSide.controller.turn.turnController;

import javax.swing.*;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class PoseidonTurnController  extends BaseTurnController{

    @Override
    public void buildingSection() {
        super.buildingSection();

        sendBoardToClient();

        int i;

        if (player.getWorkers()[0] == player.getWorkerMoved())
            i = 1;
        else
            i = 0;

        if (player.getWorker(i).getCellPosition().getBuildingLevel() != 0)
            return;

        player.setWorkerMoved(player.getWorker(i));

        for (int j = 0; j < 3; j++){
            if (player.getServerThread().specialChoice(player.getDivinityStrategy().getSpecialChoice()) == 0)
                break;
            if (player.getDivinityStrategy().getTurnStrategyBuilding().size() > 0)
                buildingSection();
        }

        if (i == 0)
            player.setWorkerMoved(player.getWorker(1));
        else
            player.setWorkerMoved(player.getWorker(0));
    }
}
