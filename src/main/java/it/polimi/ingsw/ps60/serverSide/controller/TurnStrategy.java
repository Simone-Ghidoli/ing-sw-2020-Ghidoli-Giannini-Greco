package it.polimi.ingsw.ps60.serverSide.controller;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.serverSide.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard way to make player's worker move and build. These methods will be implemented differently in other classes
 * for every possible god.
 */

public class TurnStrategy implements DivinityCard {

    /**
     * This method look for all the possible move that both player's worker can do
     *
     * @return returns a list containing the coordinates of all possible reachable cell.
     */
    public List<int[]>[] baseMovement() {
        Board game = GlobalVariables.game;
        Player playerInGame = game.getPlayerInGame().getNode().getValue();
        Cell[] cellWorker = {playerInGame.getWorker1().getCellPosition(), playerInGame.getWorker2().getCellPosition()};
        List<int[]>[] positions = new ArrayList[2];

        int[] positionWorker;

        Cell cell;

        for (int k = 0; k < 2; k++) {
            positionWorker = cellWorker[k].getPosition();
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if ((cell = game.getCellByPosition(new int[]{positionWorker[0] + i, positionWorker[1] + j})) != null && (i == 0 && j == 0)) {
                        if (cell.isFree()) {//controllo posizione libera
                            //Controllo Livello della costruzione
                            if (cell.getBuildingLevel() <= cellWorker[k].getBuildingLevel() + 1) {
                                //Controllo presenza cupola
                                if (!cell.isDomed()) {
                                    positions[k].add(new int[]{positionWorker[0] + i, positionWorker[1] + j});
                                }
                            }
                        }
                    }
                }
            }
        }

        return positions;
    }

    /**
     * the same method but for building. Unlike the previous one, this method doesn't check the high of the cell
     *
     * @return returns a list of all possible available cell to build
     */
    public List<int[]> baseBuilding() {

        Board game = GlobalVariables.game;
        List<int[]> positions = new ArrayList<>();
        int[] positionWorker = game.getPlayerInGame().getNode().getValue().getWorkerMoved().getCellPosition().getPosition();;

        Cell cell;

        for (int i = -1; i < 2; i++) {//x
            for (int j = -1; j < 2; j++) {//y
                if ((cell = game.getCellByPosition(new int[]{positionWorker[0] + i, positionWorker[1] + j})) != null && (i == 0 && j == 0)) {
                    if (cell.isFree()) {
                        //check cell status
                        if (!cell.isDomed()) {//check cupola
                            positions.add(new int[]{positionWorker[0] + i, positionWorker[1] + j});
                        }
                    }
                }
            }
        }
        return positions;
    }
}