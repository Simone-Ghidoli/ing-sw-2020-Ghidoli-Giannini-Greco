package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.utils.ListContains;

import java.util.List;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class TritonTurnStrategy extends BaseTurnStrategy {
    @Override
    public List<int[]>[] baseMovement() {
        List<int[]>[] position = super.baseMovement();
        ListContains listContains;
        Cell cell;

        Cell[] cellWorker = {game.getPlayerInGame().get().getWorkers()[0].getCellPosition(),
                game.getPlayerInGame().get().getWorkers()[1].getCellPosition()};

        for (int d = 0; d < 2; d++) {
            listContains = new ListContains(position[d]);
            for (int c = 0; c < position[d].size(); c++) {
                if (position[d].get(c)[0] == 0 || position[d].get(c)[1] == 0 || position[d].get(c)[0] == 4 || position[d].get(c)[1] == 4) {
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            if ((cell = game.getCellByPosition(new int[]{position[d].get(c)[0] + i, position[d].get(c)[1] + j})) != null
                                    && !listContains.isContained(cell.getPosition()) && !(i == 0 && j == 0)) {
                                if (cell.isFree()) {
                                    if (cell.getBuildingLevel() <= game.getCellByPosition(position[d].get(c)).getBuildingLevel() + 1) {
                                        if (!cell.isDomed()) {
                                            if (!isDisturbedByDivinity(position[d].get(c), cell.getPosition())) {
                                                if (!(cellWorker[d].getPosition()[0] == cell.getPosition()[0] && cellWorker[d].getPosition()[1] == cell.getPosition()[1]))
                                                    position[d].add(cell.getPosition());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return position;
    }
}