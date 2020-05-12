package it.polimi.ingsw.ps60.serverSide.controller.turn.turnController;

import it.polimi.ingsw.ps60.serverSide.model.Player;
import it.polimi.ingsw.ps60.serverSide.server.ServerThread;

import java.util.List;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class BaseTurnController implements TurnController {
    Player player;

    public void turn(){
        player = game.getPlayerInGame().getNode().getValue();

        sendBoardToClient();
        movementSection();
        sendBoardToClient();
        buildingSection();
        sendBoardToClient();
        endTurnSection();
    }

    public void movementSection() {
        List<int[]>[] moveChoices = player.getDivinityStrategy().getTurnStrategyMovement();
        if (moveChoices[0].size() != 0 || moveChoices[1].size() != 0) {
            int choice = player.getServerThread().moveMessage(moveChoices,
                    new int[][]{player.getWorker(0).getCellPosition().getPosition(), player.getWorker(1).getCellPosition().getPosition()});
            if (moveChoices[0].size() >= choice + 1)
                player.getDivinityStrategy().setMovement(new int[][]{new int[]{0, 0}, moveChoices[0].get(choice)});
            else {
                choice = choice - moveChoices[0].size() + 1;
                player.getDivinityStrategy().setMovement(new int[][]{new int[]{1, 0}, moveChoices[1].get(choice)});
            }
        } else {
            player.getServerThread().lossMessage("Unable to move in any position");
            endTurnSection();
        }
    }

    public void buildingSection() {
        List<int[]> buildChoices = player.getDivinityStrategy().getTurnStrategyBuilding();
        if (buildChoices.size() != 0) {
            int choice = player.getServerThread().buildMessage(buildChoices);
            player.getDivinityStrategy().setBuilding(buildChoices.get(choice));
        } else {
            player.getServerThread().lossMessage("Unable to build in any position");
            endTurnSection();
        }
    }

    public void endTurnSection(){
        player.getDivinityStrategy().setEndTurn();
    }

    public void sendBoardToClient(){
        for (ServerThread serverThread : game.getPlayerInGame().getNode().getValue().getServerThread().getList()){
            serverThread.sendBoard(game.getCellToSend());
        }
    }
}