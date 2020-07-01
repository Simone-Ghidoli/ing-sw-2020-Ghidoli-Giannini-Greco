package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;
import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.utils.TestUtilities;
import org.junit.Before;
import org.junit.Test;

import static it.polimi.ingsw.ps60.GlobalVariables.game;
import static org.junit.Assert.*;
public class HephaestusEffectTest {
    final TestUtilities testUtilities = new TestUtilities();

    @Before
    public void setUp() {
        game = new Board(new String[]{"PlayerToCheck"});
        game.getPlayerMatrix()[0].setDivinityCard(GlobalVariables.DivinityCard.HEPHAESTUS);

        Cell cell1 = game.getCellByPosition(new int[]{1, 1});
        Cell cell2 = game.getCellByPosition(new int[]{2, 0});
        game.getPlayerMatrix()[0].getWorker(0).moveWorker(cell1);
        game.getPlayerMatrix()[0].getWorker(1).moveWorker(cell2);
        testUtilities.buildsNTimes(new int[]{1, 0}, 2);
        testUtilities.buildsNTimes(new int[]{2, 1}, 3);
        testUtilities.buildDome(new int[]{2, 1});
        testUtilities.buildsNTimes(new int[]{0, 0}, 1);
        testUtilities.buildDome(new int[]{0, 0});
        testUtilities.buildsNTimes(new int[]{3, 1}, 3);
        testUtilities.buildDome(new int[]{3, 1});
        testUtilities.buildsNTimes(new int[]{4, 0}, 3);
        testUtilities.buildDome(new int[]{4, 0});
        testUtilities.buildsNTimes(new int[]{2, 3}, 3);
    }

    /**
     * Check that Hephaestus can build two times in the same cell but not a dome
     */
    @Test
    public void checkHephaestusEffect() {
        game.getPlayerMatrix()[0].getDivinityStrategy().setBuilding(new int[]{1, 0, 1});
        assertFalse(game.getCellByPosition(new int[]{1, 0}).isDomed());

        game.getPlayerMatrix()[0].getDivinityStrategy().setBuilding(new int[]{0, 0, 1});
        assertSame(3, game.getCellByPosition(new int[]{0, 0}).getBuildingLevel());

        game.getPlayerMatrix()[0].getDivinityStrategy().setBuilding(new int[]{0, 1, 0});
        assertSame(1, game.getCellByPosition(new int[]{0, 1}).getBuildingLevel());
    }
}