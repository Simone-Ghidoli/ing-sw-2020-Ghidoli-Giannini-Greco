package it.polimi.ingsw.ps60.serverSide.controller.turn.turnController;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class PoseidonTurnController  extends BaseTurnController{
    @Override
    public void turn() {
        player = game.getPlayerInGame().get();

        sendBoardToClient();
        movementSection();
        sendBoardToClient();
        buildingSection();
        sendBoardToClient();
        specialChoice();
        sendBoardToClient();
        endTurnSection();
    }

    /**
     * This method asks to to the player if with his not moved player he wants to build up to 3 times
     */
    public void specialChoice() {

        int i = 0;

        if (player.getWorkers()[0] == player.getWorkerMoved())
            i = 1;

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
