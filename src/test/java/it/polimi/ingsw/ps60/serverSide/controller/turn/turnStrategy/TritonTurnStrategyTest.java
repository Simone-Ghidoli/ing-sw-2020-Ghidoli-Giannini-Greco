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

public class TritonTurnStrategyTest {
    /**
     * Setup of the board
     * 2 Players and 3 workers (2 for the 1st player and 1 for the 2nd player)
     */

    @Before
    public void createBoard(){
        GlobalVariables.game=new Board(new String[]{"poldo","franco","giacomo"}){
        };
        GlobalVariables.game.getPlayerInGame().getNode().getValue().getWorker(0).moveWorker(GlobalVariables.game.getCellByPosition(new int[]{0,0}));
        GlobalVariables.game.getPlayerInGame().getNode().getValue().getWorker(1).moveWorker(GlobalVariables.game.getCellByPosition(new int[]{4,4}));
        GlobalVariables.game.getCellByPosition(new int[]{0,0}).setWorkerIn(GlobalVariables.game.getPlayerInGame().get().getWorker(0));
        GlobalVariables.game.getCellByPosition(new int[]{2,1}).buildDome();
        GlobalVariables.game.getCellByPosition(new int[]{3,1}).buildDome();
        GlobalVariables.game.getCellByPosition(new int[]{1,1}).buildDome();
        GlobalVariables.game.getCellByPosition(new int[]{1,2}).buildDome();
        GlobalVariables.game.getCellByPosition(new int[]{1,3}).buildDome();
        GlobalVariables.game.getCellByPosition(new int[]{1,4}).buildDome();
        GlobalVariables.game.getCellByPosition(new int[]{4,1}).buildDome();
    }

    /**
     * Should be able to move until he moves on a not-perimetral cell.
     */

    @Test
    public void movementTest(){
        DivinityStrategy div=new DivinityStrategy(GlobalVariables.DivinityCard.TRITON);
        List<int[]>[] current=new ArrayList[2];
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
        for(int i=0;i<expected.size();i++){
            assertTrue(test.checkNodes(current[0],expected.get(i)));
        }
    }
}
