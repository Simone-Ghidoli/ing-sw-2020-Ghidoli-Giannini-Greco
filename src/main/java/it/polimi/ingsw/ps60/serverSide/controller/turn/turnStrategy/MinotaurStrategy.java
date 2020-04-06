package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import static it.polimi.ingsw.ps60.GlobalVariables.game;
import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.serverSide.model.Player;

import java.util.ArrayList;
import java.util.List;

public class MinotaurStrategy extends TurnStrategy {

    @Override
    public List <int[]>[] baseMovement() {
        Player playerInGame = game.getPlayerInGame().getNode().getValue();
        Cell[] cellWorker = {playerInGame.getWorkers()[0].getCellPosition(), playerInGame.getWorkers()[1].getCellPosition()};
        List<int[]>[] positions = new ArrayList[2];

        int[] positionWorker;

        Cell cell;
        Cell cellNext;

        for (int k = 0; k < 2; k++) {
            positions[k] = new ArrayList<int[]>();
            positionWorker = cellWorker[k].getPosition();
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if ((cell = game.getCellByPosition(new int[]{positionWorker[0] + i, positionWorker[1] + j})) != null && !(i == 0 && j == 0)) {
                        if (!cell.isFree()) {
                            cellNext = game.getCellByPosition(new int[]{cell.getPosition()[0] + i, cell.getPosition()[1] + j});
                            if (cellNext == null || !cellNext.isFree() || cellNext.isDomed()) {
                                continue;
                            }
                            if (!cell.isDomed())
                                positions[k].add(new int[]{positionWorker[0] + i, positionWorker[1] + j});
                        }
                    }
                }
            }
        }

        return disturbMovementByDivinity(positions);
    }
}