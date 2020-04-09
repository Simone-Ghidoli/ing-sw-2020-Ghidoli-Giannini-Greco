package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.serverSide.model.Player;

import java.util.List;
import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class PrometheusStrategy extends TurnStrategy{
    @Override
    public List<int[]>[] baseMovement() {
        Player player = game.getPlayerInGame().getNode().getValue();
        if (!player.isBuildByWorker())
            return super.baseMovement();
        else {
            List<int[]>[] positions = super.baseMovement();
            int c;
            Cell cell;

            for (int i = 0; i < 2; i++){
                c = 0;

                while (c < positions[i].size()){
                    cell = game.getCellByPosition(positions[i].get(c));
                    if (cell.getBuildingLevel() > player.getWorker(i).getCellPosition().getBuildingLevel())
                        positions[i].remove(c);
                    else
                        c++;
                }
            }
            return positions;
        }
    }
}