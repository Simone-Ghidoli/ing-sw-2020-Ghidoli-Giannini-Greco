package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.DivinityStrategy;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import org.junit.Before;
import org.junit.Test;

import static it.polimi.ingsw.ps60.GlobalVariables.game;
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
        game=new Board(new String[]{"Player1","Player2","Player3"});
        game.getCellByPosition(new int[]{0,0}).incrementBuildingLevel();
        game.getCellByPosition(new int[]{0,0}).incrementBuildingLevel();
        game.getCellByPosition(new int[]{0,0}).incrementBuildingLevel();
        game.getCellByPosition(new int[]{0,1}).buildDome();
        game.getCellByPosition(new int[]{0,2}).buildDome();
        game.getCellByPosition(new int[]{1,0}).buildDome();
        game.getCellByPosition(new int[]{1,2}).buildDome();
        game.getCellByPosition(new int[]{2,2}).buildDome();
        game.getCellByPosition(new int[]{2,1}).buildDome();
        game.getPlayerInGame().get().getWorker(0).moveWorker(game.getCellByPosition(new int[]{1,1}));
        game.getCellByPosition(new int[]{1,1}).setWorkerIn(game.getPlayerInGame().get().getWorker(0));
        game.getPlayerInGame().get().setWorkerMoved(game.getPlayerInGame().get().getWorker(0));
    }

    /**
     *  in according to 3rd int in the array the player should be able to build a dome
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
        assertEquals(0, current.get(0)[2]);
        assertEquals(1, current.get(1)[2]);
    }
}
