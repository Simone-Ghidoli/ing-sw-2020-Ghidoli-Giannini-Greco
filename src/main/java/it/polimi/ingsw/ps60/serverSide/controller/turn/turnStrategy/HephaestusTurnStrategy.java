package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import java.util.List;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

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