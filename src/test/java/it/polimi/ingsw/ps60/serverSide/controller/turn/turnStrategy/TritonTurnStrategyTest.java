package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;


import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.DivinityStrategy;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.utils.TestUtilities;
import org.junit.Before;
import org.junit.Test;

import static it.polimi.ingsw.ps60.GlobalVariables.game;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class TritonTurnStrategyTest {
    /**
     * There are 2 workers for the Player1: one in the cell[0,0], one in the cell [4,4].
     * There are 7 domes on the board. (the domes are in [2,1][3,1][1,1][1,2][1,3][1,4][4,1]).
     */

    @Before
    public void createBoard(){
        game=new Board(new String[]{"poldo","franco","giacomo"});
        game.getPlayerInGame().get().getWorker(0).moveWorker(game.getCellByPosition(new int[]{0,0}));
        game.getPlayerInGame().get().getWorker(1).moveWorker(game.getCellByPosition(new int[]{4,4}));
        game.getCellByPosition(new int[]{0,0}).setWorkerIn(game.getPlayerInGame().get().getWorker(0));
        game.getCellByPosition(new int[]{2,1}).buildDome();
        game.getCellByPosition(new int[]{3,1}).buildDome();
        game.getCellByPosition(new int[]{1,1}).buildDome();
        game.getCellByPosition(new int[]{1,2}).buildDome();
        game.getCellByPosition(new int[]{1,3}).buildDome();
        game.getCellByPosition(new int[]{1,4}).buildDome();
        game.getCellByPosition(new int[]{4,1}).buildDome();
    }

    /**
     * Each time a worker moves into a perimeter space he can immediately move again.
     */
    @Test
    public void movementTest(){
        DivinityStrategy div=new DivinityStrategy(GlobalVariables.DivinityCard.TRITON);
        List<int[]>[] current;
        List<int[]> expected=new ArrayList<>();
        TestUtilities test=new TestUtilities();
        expected.add(new int[]{0,1});
        expected.add(new int[]{1,0});
        expected.add(new int[]{0,2});
        expected.add(new int[]{0,3});
        expected.add(new int[]{0,4});
        expected.add(new int[]{2,0});
        expected.add(new int[]{3,0});
        expected.add(new int[]{4,0});
        current=div.getTurnStrategyMovement();
        assertEquals(current[0].size(),expected.size());
        for (int[] ints : expected)
            assertTrue(test.checkNodes(current[0], ints));
    }
}
