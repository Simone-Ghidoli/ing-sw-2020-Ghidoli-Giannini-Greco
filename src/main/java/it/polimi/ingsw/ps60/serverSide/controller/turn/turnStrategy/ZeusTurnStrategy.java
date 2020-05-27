package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import java.util.List;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class ZeusTurnStrategy extends BaseTurnStrategy {
    /**
     * The player should be able to build even under itself. (Just the moved worker can build like a normal turn)
     * @return A list of possible position where to build
     */
    @Override
    public List<int[]> baseBuilding() {
        List<int[]> positions = super.baseBuilding();

        int[] positionWorker = game.getPlayerInGame().get().getWorkerMoved().getCellPosition().getPosition();
        if(game.getCellByPosition(positionWorker).getBuildingLevel()<3)
            positions.add(positionWorker);

        return positions;
    }
}