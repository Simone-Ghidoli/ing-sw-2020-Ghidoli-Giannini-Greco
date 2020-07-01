package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.serverSide.model.Player;
import it.polimi.ingsw.ps60.utils.circularList.CircularListIterator;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard way to make player's worker move and build. These methods will be implemented differently in other classes
 * for every possible god.
 */

public class BaseTurnStrategy implements TurnStrategy {

    /**
     * This method look for all the possible move that both player's worker can do
     *
     * @return returns a list containing the coordinates of all possible reachable cell.
     */
    public List<int[]>[] baseMovement() {
        Player playerInGame = game.getPlayerInGame().get();
        Cell[] cellWorker = {playerInGame.getWorkers()[0].getCellPosition(), playerInGame.getWorkers()[1].getCellPosition()};
        List<int[]>[] positions = new ArrayList[2];

        int[] positionWorker;

        Cell cell;

        for (int k = 0; k < 2; k++) {
            positions[k] = new ArrayList<>();
            positionWorker = cellWorker[k].getPosition();
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if ((cell = game.getCellByPosition(new int[]{positionWorker[0] + i, positionWorker[1] + j})) != null && !(i == 0 && j == 0)) {
                        if (cell.isFree()) {
                            if (cell.getBuildingLevel() <= cellWorker[k].getBuildingLevel() + 1) {
                                if (!cell.isDomed()) {
                                    if (isNotDisturbedByDivinity(positionWorker, new int[]{positionWorker[0] + i, positionWorker[1] + j}))
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
     * This method provide all the possible cells in witch the previously moved player can build
     *
     * @return returns a list of all possible available cell to build
     */
    public List<int[]> baseBuilding() {

        List<int[]> positions = new ArrayList<>();
        int[] positionWorker = game.getPlayerInGame().get().getWorkerMoved().getCellPosition().getPosition();
        Cell cell;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((cell = game.getCellByPosition(new int[]{positionWorker[0] + i, positionWorker[1] + j})) != null && !(i == 0 && j == 0)) {
                    if (cell.isFree()) {
                        if (!cell.isDomed()) {
                            positions.add(new int[]{positionWorker[0] + i, positionWorker[1] + j});
                        }
                    }
                }
            }
        }
        return positions;
    }

    /**
     * @param workerPosition the position where the worker actually is
     * @param targetPosition the position where the worker wants to move
     * @return true if no divinity disturbs the worker to move in the target position, false otherwise
     */
    public boolean isNotDisturbedByDivinity(int[] workerPosition, int[] targetPosition) {

        if (game.getPlayerInGame().get().getDivinityCard() != GlobalVariables.DivinityCard.ATHENA) {
            Player playerAthena;
            CircularListIterator<Player> circularListIterator = new CircularListIterator<>(game.getPlayerInGame().getList());

            for (int i = 0; i < game.getPlayerInGame().getList().getSize(); i++) {
                playerAthena = circularListIterator.get();
                if (playerAthena.getDivinityCard() == GlobalVariables.DivinityCard.ATHENA) {
                    if (playerAthena.isBitException()) {
                        return game.getCellByPosition(targetPosition).getBuildingLevel() <= game.getCellByPosition(workerPosition).getBuildingLevel();
                    } else return true;
                }
                circularListIterator.nextNode();
            }
        }
        return true;
    }
}