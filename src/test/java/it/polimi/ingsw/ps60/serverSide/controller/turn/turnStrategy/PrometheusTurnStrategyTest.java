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

public class PrometheusTurnStrategyTest {
    @Before
    public void setUp(){
        game=new Board(new String[]{"Aldo","Giovanni","Giacomo"});
        game.getCellByPosition(new int[]{1,1}).incrementBuildingLevel();
        game.getPlayerInGame().get().getWorker(0).moveWorker(game.getCellByPosition(new int[]{0,0}));
        game.getPlayerInGame().get().setBuildByWorker(true);
        game.getPlayerInGame().get().getWorker(1).moveWorker(game.getCellByPosition(new int[]{4,4}));
    }

    @Test
    public void strategyTest(){
        List<int[]>[] current;
        List<int[]> expected=new ArrayList<>();
        DivinityStrategy div=new DivinityStrategy(GlobalVariables.DivinityCard.PROMETHEUS);
        expected.add(new int[]{0,1});
        expected.add(new int[]{1,0});
        current=div.getTurnStrategyMovement();
        assertEquals(expected.size(),current[0].size());
        TestUtilities test=new TestUtilities();
        for(int i=0;i<expected.size();i++){
            assertTrue(test.checkNodes(current[0],expected.get(i)));
        }
    }
}
