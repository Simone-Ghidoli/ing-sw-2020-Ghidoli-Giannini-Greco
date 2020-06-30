package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.DivinityStrategy;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.utils.TestUtilities;
import org.junit.Before;
import org.junit.Test;

import static it.polimi.ingsw.ps60.GlobalVariables.game;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class MinotaurTurnStrategyTest {

    /**
     * There are 2 workers for the player 1: one in the cell[0,0] and one in the cell [4,4].
     * There are 2 workers for the player 2: one in the cell[0,1] and one in the cell [1,0].
     * There are 2 domes: one in the cell [1,1] and one in the cell[0,2].
     */

    @Before
    public void setUp() {
        game = new Board(new String[]{"Aldo", "Giovanni", "Giacomo"});
        game.getPlayerInGame().get().getWorker(0).moveWorker(game.getCellByPosition(new int[]{0, 0}));
        game.getPlayerInGame().get().getWorker(1).moveWorker(game.getCellByPosition(new int[]{4, 4}));
        game.getPlayerMatrix()[2].getWorker(0).moveWorker(game.getCellByPosition(new int[]{0, 1}));
        game.getPlayerMatrix()[2].getWorker(1).moveWorker(game.getCellByPosition(new int[]{1, 0}));
        game.getCellByPosition(new int[]{0, 1}).setWorkerIn(game.getPlayerMatrix()[2].getWorker(0));
        game.getCellByPosition(new int[]{1, 0}).setWorkerIn(game.getPlayerMatrix()[2].getWorker(1));
        game.getCellByPosition(new int[]{0, 0}).setWorkerIn(game.getPlayerInGame().get().getWorker(0));
        game.getCellByPosition(new int[]{1, 1}).buildDome();
        game.getCellByPosition(new int[]{0, 2}).buildDome();
    }

    /**
     * The worker in [1,1] may move into an opponent Worker's space, if that worker can be forced one space straight
     * backwards to an unoccupied space at any level.
     */

    @Test
    public void strategyTest() {
        TestUtilities test = new TestUtilities();
        DivinityStrategy div = new DivinityStrategy(GlobalVariables.DivinityCard.MINOTAUR);
        List<int[]>[] current;
        List<int[]> expected = new ArrayList<>();
        expected.add(new int[]{1, 0});
        current = div.getTurnStrategyMovement();
        assertEquals(current[0].size(), expected.size());
        for (int i = 0; i < current[0].size(); i++) {
            test.checkNodes(current[0], expected.get(i));
        }
    }
}