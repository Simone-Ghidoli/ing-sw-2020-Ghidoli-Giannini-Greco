package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.DivinityStrategy;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Cell;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

/**
 * Atlas Strategy Test
 */

public class AtlasTurnStrategyTest {

    /**
     * Setup of the board
     * 2 Players and 3 workers (2 for the 1st player and 1 for the 2nd player)
     */

    @Before
    public void createBoard(){
        GlobalVariables.game=new Board(new String[]{"Aldo","Giovanni","Giacomo"}){
        };
        GlobalVariables.game.getCellByPosition(new int[]{0,0}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{0,0}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{0,0}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{0,1}).buildDome();
        GlobalVariables.game.getCellByPosition(new int[]{0,2}).buildDome();
        GlobalVariables.game.getCellByPosition(new int[]{1,0}).buildDome();
        GlobalVariables.game.getCellByPosition(new int[]{1,2}).buildDome();
        GlobalVariables.game.getCellByPosition(new int[]{2,2}).buildDome();
        GlobalVariables.game.getCellByPosition(new int[]{2,1}).buildDome();
        GlobalVariables.game.getPlayerInGame().getNode().getValue().getWorker(0).moveWorker(GlobalVariables.game.getCellByPosition(new int[]{1,1}));
        GlobalVariables.game.getCellByPosition(new int[]{1,1}).setWorkerIn(GlobalVariables.game.getPlayerInGame().getNode().getValue().getWorker(0));
        GlobalVariables.game.getPlayerInGame().getNode().getValue().setWorkerMoved(GlobalVariables.game.getPlayerInGame().getNode().getValue().getWorker(0));
    }

    /**
     * Same as baseBuilding. Tested because it returns a different type of List
     */

    @Test
    public void buildingTest(){
        DivinityStrategy div=new DivinityStrategy(GlobalVariables.DivinityCard.ATLAS);
        List<int[]> current,expected;
        expected=new ArrayList<>();
        current=div.getTurnStrategyBuilding();
        expected.add(new int[]{0,0,0});
        expected.add(new int[]{2,0,1});
        assertEquals(expected.size(),current.size());
        assertTrue(current.get(0)[2]==0);
        assertTrue(current.get(1)[2]==1);
    }
}
