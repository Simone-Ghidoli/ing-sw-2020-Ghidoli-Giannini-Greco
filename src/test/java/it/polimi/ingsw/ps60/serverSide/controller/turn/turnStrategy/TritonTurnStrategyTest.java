package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.utils.TestUtilities;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class TritonTurnStrategyTest extends TestCase {

    TestUtilities testUtilities = new TestUtilities();
    BaseTurnStrategy base = new BaseTurnStrategy();

    @Before
    public void setUp() {
        game = new Board(new String[]{"PlayerToCheck", "PlaceHolder"});

        game.getPlayerMatrix()[0].setDivinityCard(GlobalVariables.DivinityCard.TRITON);

        Cell cell = game.getCellByPosition(new int[]{1, 1});
        Cell cell2 = game.getCellByPosition(new int[]{2, 0});

        game.getPlayerInGame().get().getWorker(0).moveWorker(cell);
        game.getPlayerInGame().get().getWorker(1).moveWorker(cell2);

        testUtilities.buildsNTimes(new int[]{0, 0}, 1);
        testUtilities.buildsNTimes(new int[]{0, 1}, 2);

        testUtilities.buildsNTimes(new int[]{2, 0}, 2);
        testUtilities.buildsNTimes(new int[]{3, 0}, 1);

        testUtilities.buildDome(new int[]{0, 2});
    }

    @Test
    public void testBaseMovement() {
        List<int[]>[] positionsToTest = game.getPlayerMatrix()[0].getDivinityStrategy().getTurnStrategyMovement();
    }
}