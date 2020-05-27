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

public class ZeusTurnStrategyTest {
    @Before
    public void setUp(){
        GlobalVariables.game=new Board(new String[]{"Aldo","Giovanni","Giacomo"});
        GlobalVariables.game.getPlayerInGame().get().getWorker(0).moveWorker(GlobalVariables.game.getCellByPosition(new int[]{0,0}));
        GlobalVariables.game.getPlayerInGame().get().getWorker(1).moveWorker(GlobalVariables.game.getCellByPosition(new int[]{3,3}));
        GlobalVariables.game.getPlayerInGame().get().setWorkerMoved(GlobalVariables.game.getPlayerInGame().get().getWorker(0));
    }


    @Test
    public void strategyTest(){
        DivinityStrategy div=new DivinityStrategy(GlobalVariables.DivinityCard.ZEUS);
        TestUtilities test=new TestUtilities();
        List<int[]> current,expected=new ArrayList<>();
        current=div.getTurnStrategyBuilding();
        expected.add(new int[]{0,1});
        expected.add(new int[]{1,1});
        expected.add(new int[]{1,0});
        expected.add(new int[]{0,0});
        assertEquals(current.size(),expected.size());
        for(int i=0;i<current.size();i++){
            assertTrue(test.checkNodes(current,expected.get(i)));
        }
    }
}
