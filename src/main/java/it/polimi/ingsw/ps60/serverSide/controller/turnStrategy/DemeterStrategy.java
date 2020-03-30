package it.polimi.ingsw.ps60.serverSide.controller.turnStrategy;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Cell;
import java.util.ArrayList;
import java.util.List;
public class DemeterStrategy extends TurnStrategy {

    /**
     *in the same turn you can build again but not in the same position
     * @param oldBuild  contains the position of the cell where it was previously build
     * @return A list containing all the possible cell where can build
     */

    public List <int[]> buildingAgain(int[] oldBuild) {
        Board game = GlobalVariables.game;
        List<int[]> positions = new ArrayList<>();
        int[] positionWorker = game.getPlayerInGame().getNode().getValue().getWorkerMoved().getCellPosition().getPosition();
        ;
        Cell cell;
        for (int i = -1; i < 2; i++) {//x
            for (int j = -1; j < 2; j++) {//y
                if ((cell = game.getCellByPosition(new int[]{positionWorker[0] + i, positionWorker[1] + j})) != null && !(i == 0 && j == 0)) {
                    if (cell.isFree()) {
                        //check cell status
                        if (!cell.isDomed() && !(cell.getPosition().equals(game.getCellByPosition(oldBuild)))) {//check cupola and not build in the same position
                            positions.add(new int[]{positionWorker[0] + i, positionWorker[1] + j});
                        }
                    }
                }
            }
        }

        return positions;
    }
}
