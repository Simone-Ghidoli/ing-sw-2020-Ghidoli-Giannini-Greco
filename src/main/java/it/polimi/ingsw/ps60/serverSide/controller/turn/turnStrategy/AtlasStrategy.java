package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import java.util.List;
import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class AtlasStrategy extends BaseStrategy {

    @Override
    public List<int[]> baseBuilding() {
        List<int[]> list = super.baseBuilding();
        int k = 0;
        int[] position;

        for (int i = 0; i < list.size(); i++){
            position = list.remove(k);
            if (game.getCellByPosition(list.get(k)).getBuildingLevel() < 3)
                list.add(new int[]{position[0], position[1], 1});
            else
                list.add(new int[]{position[0], position[1], 0});
        }
        return list;
    }
}