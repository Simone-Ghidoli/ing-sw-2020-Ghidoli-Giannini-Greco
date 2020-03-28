package it.polimi.ingsw.ps60.serverSide.controller;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Cell;

import java.util.List;
import java.util.ListIterator;

public class ArtemisStrategy extends TurnStrategy {

    @Override
    public List<int[]>[] baseMovement() {

        Board game = GlobalVariables.game;
        List<int[]>[] positions = super.baseMovement();
        int[] positionWorker;
        Cell cell;
        Cell[] cellWorker = {game.getPlayerInGame().getNode().getValue().getWorkers()[0].getCellPosition(),
                game.getPlayerInGame().getNode().getValue().getWorkers()[1].getCellPosition()};

        for (int k = 0; k < 2; k++) {
            for (int l = 0; l < positions.length; l++) {
                positionWorker = positions[k].get(l);
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if ((cell = game.getCellByPosition(new int[]{positionWorker[0] + i, positionWorker[1] + j})) != null && (i == 0 && j == 0)) {
                            if (cell.isFree()) {
                                if (cell.getBuildingLevel() <= cellWorker[k].getBuildingLevel() + 1) {
                                    if (!cell.isDomed()) {
                                        if (!positions[k].contains(new int[]{positionWorker[0] + i, positionWorker[1] + j}))
                                        positions[k].add(new int[]{positionWorker[0] + i, positionWorker[1] + j});
                                    }
                                }
                            }
                        }
                    }
                }
            }
            positions[k].remove(cellWorker[k].getPosition());
        }
        return disturbByDivinity(positions);
    }
}
