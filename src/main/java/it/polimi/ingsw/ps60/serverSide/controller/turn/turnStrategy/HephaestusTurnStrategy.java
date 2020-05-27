package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import java.util.List;

import static it.polimi.ingsw.ps60.GlobalVariables.game;


/**
 * A player should be able to build 2 times in a row on the same cell.
 * A third int in the array is set to 1 if is possible to use the power on that cell
 * @return a list of possible position to build
 */
public class HephaestusTurnStrategy extends BaseTurnStrategy {
    @Override
    public List<int[]> baseBuilding() {
        List<int[]> positions = super.baseBuilding();
        int[] cellPosition;
        int c = 0;

        while (c < positions.size()){
            cellPosition = positions.remove(0);
            if (game.getCellByPosition(cellPosition).getBuildingLevel() < 2)
                positions.add(new int[]{cellPosition[0], cellPosition[1], 1});
            else
                positions.add(new int[]{cellPosition[0], cellPosition[1], 0});
            c++;
        }
        return positions;
    }
}