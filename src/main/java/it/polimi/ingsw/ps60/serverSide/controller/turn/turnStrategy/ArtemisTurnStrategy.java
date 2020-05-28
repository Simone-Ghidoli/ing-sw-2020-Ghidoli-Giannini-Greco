package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.utils.ListContains;

import java.util.List;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class ArtemisTurnStrategy extends BaseTurnStrategy {

    /**
     * A player should be able to move 2 times in a row, but not back in the initial space
     *
     * @return the list of possible positions to move
     */

    @Override
    public List<int[]>[] baseMovement() {

        List<int[]>[] positions = super.baseMovement();
        int numberOfIterations;
        int[] positionWorker;
        Cell cell;
        Cell[] cellWorker = {game.getPlayerInGame().get().getWorkers()[0].getCellPosition(),
                game.getPlayerInGame().get().getWorkers()[1].getCellPosition()};

        for (int k = 0; k < 2; k++) {
            numberOfIterations = positions[k].size();
            for (int l = 0; l < numberOfIterations; l++) {
                positionWorker = positions[k].get(l);
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if ((cell = game.getCellByPosition(new int[]{positionWorker[0] + i, positionWorker[1] + j})) != null
                                && cell != cellWorker[k] && !(i == 0 && j == 0)) {
                            if (cell.isFree()) {
                                if (cell.getBuildingLevel() <= game.getCellByPosition(positionWorker).getBuildingLevel() + 1) {
                                    if (!cell.isDomed()) {
                                        if (isNotDisturbedByDivinity(cell.getPosition(), new int[]{positionWorker[0] + i, positionWorker[1] + j}))
                                            if (!new ListContains(positions[k]).isContained(cell.getPosition()))
                                                positions[k].add(cell.getPosition());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return positions;
    }
}