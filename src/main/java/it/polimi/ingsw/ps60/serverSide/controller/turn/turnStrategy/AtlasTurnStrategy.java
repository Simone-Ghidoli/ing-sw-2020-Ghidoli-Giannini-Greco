package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import java.util.List;
import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class AtlasTurnStrategy extends BaseTurnStrategy {

    /**
     * The player should be able to build a dome at any level of the cells
     * @return List of possible cells where to build. In any cell a 3rd int in the array is set to 1 if is possible use god's power in that cell
     */

    @Override
    public List<int[]> baseBuilding() {
        List<int[]> list = super.baseBuilding();
        int k = 0;
        int[] position;

        for (int i = 0; i < list.size(); i++){
            position = list.remove(k);
            if (game.getCellByPosition(position).getBuildingLevel() < 3)
                list.add(new int[]{position[0], position[1], 1});
            else
                list.add(new int[]{position[0], position[1], 0});
        }
        return list;
    }
}