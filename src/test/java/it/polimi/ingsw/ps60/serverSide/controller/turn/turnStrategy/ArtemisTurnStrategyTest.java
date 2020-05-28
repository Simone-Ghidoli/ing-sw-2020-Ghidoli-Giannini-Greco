package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.DivinityStrategy;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Cell;

import it.polimi.ingsw.ps60.utils.TestUtilities;
import org.junit.Before;
import org.junit.Test;

import static it.polimi.ingsw.ps60.GlobalVariables.game;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class ArtemisTurnStrategyTest {

    /**
     * Just 1 worker in the cell [1,1]. This test should be able to test the possibility to move 2 times in the turn, but not in the initial cell.
     * The second worker is in [4,4] but is just to avoid null pointers.
     */

    @Before
    public void createBoard() {
        game = new Board(new String[]{"Aldo", "Giovanni", "Giacomo"}) {
        };
        game.getCellByPosition(new int[]{0, 0}).incrementBuildingLevel();
        game.getCellByPosition(new int[]{0, 0}).incrementBuildingLevel();
        game.getCellByPosition(new int[]{0, 1}).incrementBuildingLevel();
        game.getCellByPosition(new int[]{0, 1}).incrementBuildingLevel();
        game.getCellByPosition(new int[]{0, 2}).incrementBuildingLevel();
        game.getCellByPosition(new int[]{0, 2}).incrementBuildingLevel();
        game.getCellByPosition(new int[]{1, 0}).incrementBuildingLevel();
        game.getCellByPosition(new int[]{1, 0}).incrementBuildingLevel();
        game.getCellByPosition(new int[]{1, 2}).incrementBuildingLevel();
        game.getCellByPosition(new int[]{1, 2}).incrementBuildingLevel();
        game.getCellByPosition(new int[]{2, 0}).incrementBuildingLevel();
        game.getCellByPosition(new int[]{2, 0}).incrementBuildingLevel();
        game.getCellByPosition(new int[]{2, 2}).incrementBuildingLevel();
        game.getCellByPosition(new int[]{2, 2}).incrementBuildingLevel();
        game.getPlayerInGame().get().getWorker(0).moveWorker(new Cell(new int[]{1,1}, game));
        game.getPlayerInGame().get().getWorker(1).moveWorker(new Cell(new int[]{4,4}, game));
        game.getCellByPosition(new int[]{1,1}).setWorkerIn(game.getPlayerInGame().get().getWorker(0));
    }

    @Test
    public void moveTest(){
        TestUtilities utility=new TestUtilities();
        DivinityStrategy div=new DivinityStrategy(GlobalVariables.DivinityCard.ARTEMIS);
        List<int[]>[] current;
        List<int[]> expected=new ArrayList<>();
        expected.add(new int[]{2,1});
        expected.add(new int[]{3,0});
        expected.add(new int[]{3,1});
        expected.add(new int[]{3,2});
        current=div.getTurnStrategyMovement();
        assertEquals(current[0].size(),expected.size());
        for(int i=0;i<current[0].size();i++){
            assertTrue(utility.checkNodes(current[0],expected.get(i)));
        }
    }
}