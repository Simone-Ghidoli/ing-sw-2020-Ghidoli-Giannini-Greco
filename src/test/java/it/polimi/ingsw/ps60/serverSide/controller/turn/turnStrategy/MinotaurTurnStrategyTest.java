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

public class MinotaurTurnStrategyTest {
    @Before
    public void setUp(){
        GlobalVariables.game=new Board(new String[]{"Aldo","Giovanni","Giacomo"});
        GlobalVariables.game.getPlayerInGame().get().getWorker(0).moveWorker(GlobalVariables.game.getCellByPosition(new int[]{0,0}));
        GlobalVariables.game.getPlayerInGame().get().getWorker(1).moveWorker(GlobalVariables.game.getCellByPosition(new int[]{4,4}));
        GlobalVariables.game.getPlayerMatrix()[2].getWorker(0).moveWorker(GlobalVariables.game.getCellByPosition(new int[]{0,1}));
        GlobalVariables.game.getPlayerMatrix()[2].getWorker(1).moveWorker(GlobalVariables.game.getCellByPosition(new int[]{1,0}));
        GlobalVariables.game.getCellByPosition(new int[]{0,1}).setWorkerIn(GlobalVariables.game.getPlayerMatrix()[2].getWorker(0));
        GlobalVariables.game.getCellByPosition(new int[]{1,0}).setWorkerIn(GlobalVariables.game.getPlayerMatrix()[2].getWorker(1));
        GlobalVariables.game.getCellByPosition(new int[]{0,0}).setWorkerIn(GlobalVariables.game.getPlayerInGame().get().getWorker(0));
        GlobalVariables.game.getCellByPosition(new int[]{1,1}).buildDome();
        GlobalVariables.game.getCellByPosition(new int[]{0,2}).buildDome();
    }
    @Test
    public void strategyTest(){
        TestUtilities test=new TestUtilities();
        DivinityStrategy div=new DivinityStrategy(GlobalVariables.DivinityCard.MINOTAUR);
        List<int[]>[] current;
        List<int[]> expected=new ArrayList<>();
        expected.add(new int[]{1,0});
        current=div.getTurnStrategyMovement();
        assertEquals(current[0].size(),expected.size());
        for(int i=0;i<current[0].size();i++){
            test.checkNodes(current[0],expected.get(i));
        }
    }

}
