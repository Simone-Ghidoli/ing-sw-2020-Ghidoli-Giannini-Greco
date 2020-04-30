package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.serverSide.model.Player;
import static it.polimi.ingsw.ps60.GlobalVariables.game;

import java.util.ArrayList;
import java.util.List;

public class ApolloStrategy extends BaseStrategy {

    /**
     * A player can move his worker into an opponent's space forcing opponent's worker to the space the first one
     * just vacated.
     * Standard build phase
     * @return A list containing all the possible moves
     */
    @Override
    public List<int[]>[] baseMovement() {
        Player playerInGame = game.getPlayerInGame().getNode().getValue();
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
                        if (cell.getBuildingLevel() <= cellWorker[k].getBuildingLevel() + 1) {
                            if (!cell.isDomed()) {
                                if (!isDisturbedByDivinity(positionWorker, new int[]{positionWorker[0] + i, positionWorker[1] + j}))
                                    positions[k].add(new int[]{positionWorker[0] + i, positionWorker[1] + j});
                            }
                        }
                    }
                }
            }
        }
        return positions;
    }
}
