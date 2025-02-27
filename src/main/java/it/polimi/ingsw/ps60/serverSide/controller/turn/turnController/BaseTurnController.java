package it.polimi.ingsw.ps60.serverSide.controller.turn.turnController;

import it.polimi.ingsw.ps60.serverSide.model.Player;
import it.polimi.ingsw.ps60.serverSide.server.ServerThread;

import java.util.List;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class BaseTurnController implements TurnController {
    Player player;
    boolean lost;

    public void turn() {
        player = game.getPlayerInGame().get();
        lost = false;

        sendBoardToClient();
        movementSection();
        sendBoardToClient();
        if (!lost)
            buildingSection();
        if (!lost)
            sendBoardToClient();
        endTurnSection();
    }

    /**
     * This method is the sequence of actions in the movement section
     */
    public void movementSection() {
        List<int[]>[] moveChoices = player.getDivinityStrategy().getTurnStrategyMovement();
        if (moveChoices[0].size() != 0 || moveChoices[1].size() != 0) {

            player.getServerThread().sendAlert("Select where to move");

            int choice = player.getServerThread().moveMessage(moveChoices,
                    new int[][]{player.getWorker(0).getCellPosition().getPosition(), player.getWorker(1).getCellPosition().getPosition()});
            if (moveChoices[0].size() > choice)
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

    /**
     * This method is the sequence of actions in the building section
     */
    public void buildingSection() {
        List<int[]> buildChoices = player.getDivinityStrategy().getTurnStrategyBuilding();
        if (buildChoices.size() != 0) {

            player.getServerThread().sendAlert("Select where to build");

            int choice = player.getServerThread().buildMessage(buildChoices);
            player.getDivinityStrategy().setBuilding(buildChoices.get(choice));
        } else {
            player.getServerThread().lossMessage("Unable to build in any position");
            lost = true;
        }
    }

    /**
     * This method is the sequence of actions in the end turn section
     */
    public void endTurnSection() {
        player.getDivinityStrategy().setEndTurn();
    }

    /**
     * This method will send the board to all the clients
     */
    public void sendBoardToClient() {
        for (ServerThread serverThread : game.getPlayerInGame().get().getServerThread().getServerThreads()) {
            serverThread.sendBoard(game.getCellToSend());
        }
    }
}