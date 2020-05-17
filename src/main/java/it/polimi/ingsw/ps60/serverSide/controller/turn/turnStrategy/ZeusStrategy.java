package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import java.util.List;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class ZeusStrategy extends BaseStrategy {
    @Override
    public List<int[]> baseBuilding() {
        List<int[]> positions = super.baseBuilding();

        int[] positionWorker = game.getPlayerInGame().get().getWorkerMoved().getCellPosition().getPosition();
        if(game.getCellByPosition(positionWorker).getBuildingLevel()<3)
            positions.add(positionWorker);

        return positions;
    }
}