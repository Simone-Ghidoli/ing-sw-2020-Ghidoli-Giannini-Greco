package it.polimi.ingsw.ps60.serverSide.controller;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.serverSide.model.Player;

import java.util.ArrayList;
import java.util.List;

public class MinotaurStrategy extends TurnStrategy {
    @Override
    public List<int[]>[] baseMovement() {
        Board game = GlobalVariables.game;
        Player playerInGame = game.getPlayerInGame().getNode().getValue();
        Cell[] cellWorker = {playerInGame.getWorkers()[0].getCellPosition(), playerInGame.getWorkers()[1].getCellPosition()};
        List<int[]>[] positions = new ArrayList[2];
        int[] positionWorker;
        Cell cell;
        Cell cellNext;
        for (int k = 0; k < 2; k++) {
            positionWorker = cellWorker[k].getPosition();
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if ((cell = game.getCellByPosition(new int[]{positionWorker[0] + i, positionWorker[1] + j})) != null && !(i == 0 && j == 0)) {
                        cellNext = getcellNext(positionWorker, cell);
                        if (cellNext.isFree() && cellNext != null) {
                            if (cell.getBuildingLevel() <= cellWorker[k].getBuildingLevel() + 1) {
                                if (!cell.isDomed()) {
                                    positions[k].add(new int[]{positionWorker[0] + i, positionWorker[1] + j});
                                }
                            }
                        }
                    }
                }
            }
        }

        return disturbByDivinity(positions);
    }


    public Cell getcellNext(int posInit[], Cell newCell) {
        if()


    }
}