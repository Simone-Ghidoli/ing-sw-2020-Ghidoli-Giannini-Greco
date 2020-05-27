package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.serverSide.model.Player;

import java.util.List;
import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class PrometheusTurnStrategy extends BaseTurnStrategy {

    /**
     * The player should be able to build with a worker one additional time before the moving phase, but the worker won`t be able to move up.
     * @return A list of possible position to move. If the worker built in a cell yet, the upper level cells will be removed.
     */
    @Override
    public List<int[]>[] baseMovement() {
        Player player = game.getPlayerInGame().get();
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