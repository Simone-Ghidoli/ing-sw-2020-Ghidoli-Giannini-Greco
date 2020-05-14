package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;

import java.util.ArrayList;
import java.util.List;

public class ZeusStrategy extends BaseStrategy {
    @Override
    public List<int[]> baseBuilding() {
        List<int[]> positions = new ArrayList<>();
        int[] positionWorker = GlobalVariables.game.getPlayerInGame().getNode().getValue().getWorkerMoved().getCellPosition().getPosition();
        positions=super.baseBuilding();
        if(GlobalVariables.game.getCellByPosition(new int[]{positionWorker[0],positionWorker[1]}).getBuildingLevel()<3)
            positions.add(new int[]{positionWorker[0],positionWorker[1]});
        return positions;
    }
}

