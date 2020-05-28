package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.utils.TestUtilities;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class AtlasEffectTest {
    TestUtilities testUtilities = new TestUtilities();
    @Before
    public void setUp() {
        game = new Board(new String[]{"PlayerToCheck", "PlaceHolder"});
        game.getPlayerMatrix()[0].setDivinityCard(GlobalVariables.DivinityCard.ATLAS);
        Cell cell1 = game.getCellByPosition(new int[]{1, 1});
        Cell cell2 = game.getCellByPosition(new int[]{2, 0});
        Cell cell3 = game.getCellByPosition(new int[]{2, 2});
        Cell cell4 = game.getCellByPosition(new int[]{3, 3});
        game.getPlayerMatrix()[0].getWorker(0).moveWorker(cell1);
        game.getPlayerMatrix()[0].getWorker(1).moveWorker(cell2);
        game.getPlayerMatrix()[1].getWorker(0).moveWorker(cell3);
        game.getPlayerMatrix()[1].getWorker(1).moveWorker(cell4);
        testUtilities.buildsNTimes(new int[]{1, 0}, 2);
        testUtilities.buildsNTimes(new int[]{2, 1}, 2);
        testUtilities.buildsNTimes(new int[]{0, 0}, 3);
    }
    @Test
    public void checkAtlasEffect(){
        game.getPlayerMatrix()[0].getDivinityStrategy().setBuilding(new int[]{1,0,1});
        game.getPlayerMatrix()[1].getDivinityStrategy().setBuilding(new int[]{2,1,0});
        game.getPlayerMatrix()[0].getDivinityStrategy().setBuilding(new int[]{0,0,0});
        assertTrue(game.getCellByPosition(new int[]{1,0}).isDomed());
        assertFalse(game.getCellByPosition(new int[]{2,1}).isDomed());
        assertTrue(game.getCellByPosition(new int[]{0,0}).isDomed());
    }
}
