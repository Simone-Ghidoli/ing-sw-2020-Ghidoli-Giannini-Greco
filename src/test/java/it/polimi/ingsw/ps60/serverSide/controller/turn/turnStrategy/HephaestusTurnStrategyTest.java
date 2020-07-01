package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.DivinityStrategy;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import org.junit.Before;
import org.junit.Test;

import static it.polimi.ingsw.ps60.GlobalVariables.game;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class HephaestusTurnStrategyTest {

    /**
     * Setup of the board. 2 Workers on the board, 1 in the cell [1,1] (worker to check) and 1 in the cell [4,4];
     * There are 5 domes around the worker's cell (in [0,0][0,1][0,2][1,0][1,2]. One level 1 tower in [2,0] and one
     * level 2 tower in [2,1].
     **/

    @Before
    public void setUp() {
        game = new Board(new String[]{"Aldo", "Giovanni", "Giacomo"});
        game.getPlayerInGame().get().getWorker(0).moveWorker(game.getCellByPosition(new int[]{1, 1}));
        game.getCellByPosition(new int[]{1, 1}).setWorkerIn(game.getPlayerInGame().get().getWorker(0));
        game.getPlayerInGame().get().setWorkerMoved(game.getPlayerInGame().get().getWorker(0));
        game.getPlayerInGame().get().getWorker(1).moveWorker(game.getCellByPosition(new int[]{4, 4}));
        game.getCellByPosition(new int[]{0, 0}).buildDome();
        game.getCellByPosition(new int[]{0, 1}).buildDome();
        game.getCellByPosition(new int[]{0, 2}).buildDome();
        game.getCellByPosition(new int[]{1, 0}).buildDome();
        game.getCellByPosition(new int[]{1, 2}).buildDome();
        game.getCellByPosition(new int[]{2, 0}).incrementBuildingLevel();
        game.getCellByPosition(new int[]{2, 0}).incrementBuildingLevel();
        game.getCellByPosition(new int[]{2, 1}).incrementBuildingLevel();
    }

    /**
     * The worker in [1,1] may build one additional block (not a dome) on the top of the first block.
     */

    @Test
    public void strategyTest() {
        DivinityStrategy div = new DivinityStrategy(GlobalVariables.DivinityCard.HEPHAESTUS);
        List<int[]> current, expected;
        expected = new ArrayList<>();
        current = div.getTurnStrategyBuilding();
        expected.add(new int[]{2, 0});
        expected.add(new int[]{2, 1});
        expected.add(new int[]{2, 2});
        assertEquals(current.size(), expected.size());
        assertEquals(0, current.get(0)[2]);
        assertEquals(1, current.get(1)[2]);
        assertEquals(1, current.get(2)[2]);
    }
}