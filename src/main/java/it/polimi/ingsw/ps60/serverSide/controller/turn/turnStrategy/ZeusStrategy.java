package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Cell;

import java.util.ArrayList;
import java.util.List;

public class ZeusStrategy extends BaseStrategy {
    @Override
    public List<int[]> baseBuilding() {
        Board game = GlobalVariables.game;
        List<int[]> positions = new ArrayList<>();
        int[] positionWorker = game.getPlayerInGame().getNode().getValue().getWorkerMoved().getCellPosition().getPosition();

        Cell cell;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((cell = game.getCellByPosition(new int[]{positionWorker[0] + i, positionWorker[1] + j})) != null && (i == 0 && j == 0)) {
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
}

