package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.DivinityStrategy;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.utils.TestUtilities;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;


public class ApolloTurnStrategyTest {

    /**
     * There are 3 workers. 2 for the 1st player and 1 for the 2nd player
     * There`s a lvl 2 tower in 0,0
     * One worker is in the cell [1,1] and one enemy worker is in the cell[1,2]. Should be possible, for the first one, to move into che second worker cell and swap their position.
     * Another worker is in the cell [2,2]. This worker belongs to the 1st player (like the worker in [1,1]), and should not be possible to move there.
     */
    @Before
    public void createBoard() {
        GlobalVariables.game = new Board(new String[]{"Aldo", "Giovanni", "Giacomo"});
        GlobalVariables.game.getCellByPosition(new int[]{0, 0}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{0, 0}).incrementBuildingLevel();
        GlobalVariables.game.getPlayerInGame().get().getWorker(0).moveWorker(GlobalVariables.game.getCellByPosition(new int[]{1, 1}));
        GlobalVariables.game.getPlayerInGame().get().getWorker(1).moveWorker(GlobalVariables.game.getCellByPosition(new int[]{2, 2}));
        GlobalVariables.game.getPlayerMatrix()[2].getWorker(0).moveWorker(GlobalVariables.game.getCellByPosition(new int[]{1, 2}));
        GlobalVariables.game.getCellByPosition(new int[]{2, 2}).setWorkerIn(GlobalVariables.game.getPlayerInGame().get().getWorker(1));
        GlobalVariables.game.getCellByPosition(new int[]{1, 2}).setWorkerIn(GlobalVariables.game.getPlayerMatrix()[2].getWorker(0));
    }

    /**
     * Should be possible swaps position with opponent worker.
     * The test is only for the worker in [1,1]. He should be able to move in 6 different cells
     */
    @Test
    public void movementTest() {
        DivinityStrategy div = new DivinityStrategy(GlobalVariables.DivinityCard.APOLLO);
        List<int[]> current;
        List<int[]> expected = new ArrayList<>();
        expected.add(new int[]{0, 1});
        expected.add(new int[]{0, 2});
        expected.add(new int[]{1, 0});
        expected.add(new int[]{1, 2});
        expected.add(new int[]{2, 0});
        expected.add(new int[]{2, 1});
        current = div.getTurnStrategyMovement()[0];
        assertEquals(expected.size(), current.size());
        TestUtilities utility = new TestUtilities();
        for (int i = 0; i < current.size(); i++)
            assertTrue(utility.checkNodes(current, expected.get(i)));
    }
}