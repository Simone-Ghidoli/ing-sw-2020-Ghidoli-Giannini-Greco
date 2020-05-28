package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.utils.TestUtilities;
import org.junit.Before;
import org.junit.Test;

import static it.polimi.ingsw.ps60.GlobalVariables.game;
import static org.junit.Assert.*;

public class ChronusEffectTest {
    TestUtilities testUtilities = new TestUtilities();
    @Before
    public void setUp() {
        game = new Board(new String[]{"PlayerToCheck", "PlaceHolder"});
        game.getPlayerMatrix()[0].setDivinityCard(GlobalVariables.DivinityCard.CHRONUS);
        Cell cell1 = game.getCellByPosition(new int[]{1, 1});
        Cell cell2 = game.getCellByPosition(new int[]{2, 0});
        Cell cell3 = game.getCellByPosition(new int[]{2, 2});
        Cell cell4 = game.getCellByPosition(new int[]{3, 3});
        game.getPlayerMatrix()[0].getWorker(0).moveWorker(cell1);
        game.getPlayerMatrix()[0].getWorker(1).moveWorker(cell2);
        game.getPlayerMatrix()[1].getWorker(0).moveWorker(cell3);
        game.getPlayerMatrix()[1].getWorker(1).moveWorker(cell4);
        testUtilities.buildsNTimes(new int[]{1, 0}, 3);
        testUtilities.buildsNTimes(new int[]{2, 1}, 3);
        testUtilities.buildDome(new int[]{2,1});
        testUtilities.buildsNTimes(new int[]{0, 0}, 3);
        testUtilities.buildDome(new int[]{0,0});
        testUtilities.buildsNTimes(new int[]{3, 1}, 3);
        testUtilities.buildDome(new int[]{3,1});
        testUtilities.buildsNTimes(new int[]{4, 0}, 3);
        testUtilities.buildDome(new int[]{4,0});
        testUtilities.buildsNTimes(new int[]{2, 3}, 3);
    }
    @Test
    public void checkChronusEffect(){
        int[][] mossa1 = new int[2][2];
        mossa1[0][0] = 0;
        mossa1[0][1] = 0;
        mossa1[1] = new int[]{0,1};
        game.getPlayerMatrix()[1].getDivinityStrategy().setBuilding(new int[]{2,3});
        game.getPlayerMatrix()[1].getDivinityStrategy().setEndTurn();
        assertTrue(game.getCellByPosition(new int[]{2,3}).isDomed());
        assertSame(5,game.getCompleteTower());

        assertEquals(null,game.getPlayerWinner());

        game.getPlayerMatrix()[0].getDivinityStrategy().setMovement(mossa1);
        game.getPlayerMatrix()[0].getDivinityStrategy().setBuilding(new int[]{1,0});
        game.getPlayerMatrix()[0].getDivinityStrategy().setEndTurn();
        assertEquals(game.getPlayerMatrix()[0],game.getPlayerWinner());
    }
}
