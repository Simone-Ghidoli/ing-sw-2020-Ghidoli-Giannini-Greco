package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.utils.ListContains;
import it.polimi.ingsw.ps60.utils.TestUtilities;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.ps60.GlobalVariables.game;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BaseTurnStrategyTest {

    TestUtilities testUtilities = new TestUtilities();
    BaseTurnStrategy base = new BaseTurnStrategy();

    @Before
    public void createBoard() {
        game = new Board(new String[]{"PlayerToCheck", "PlaceHolder"});


        Cell cell = game.getCellByPosition(new int[]{1, 1});
        Cell cell2 = game.getCellByPosition(new int[]{2, 0});

        game.getPlayerInGame().getNode().getValue().getWorker(0).moveWorker(cell);
        game.getPlayerInGame().getNode().getValue().getWorker(1).moveWorker(cell2);

        testUtilities.buildsNTimes(new int[]{0, 0}, 1);
        testUtilities.buildsNTimes(new int[]{0, 1}, 2);

        testUtilities.buildsNTimes(new int[]{2, 0}, 2);
        testUtilities.buildsNTimes(new int[]{3, 0}, 1);

        testUtilities.buildDome(new int[]{0, 2});
    }

    @Test
    public void baseStrategyTest() {
        ListContains listContains;

        List<int[]>[] truePositions = new ArrayList[2];

        truePositions[0] = new ArrayList<>();
        truePositions[0].add(new int[]{0, 0});
        truePositions[0].add(new int[]{1, 0});
        truePositions[0].add(new int[]{1, 2});
        truePositions[0].add(new int[]{2, 2});
        truePositions[0].add(new int[]{2, 1});

        truePositions[1] = new ArrayList<>();
        truePositions[1].add(new int[]{2, 1});
        truePositions[1].add(new int[]{3, 1});
        truePositions[1].add(new int[]{3, 0});
        truePositions[1].add(new int[]{1, 0});

        List<int[]>[] positionsToTest = base.baseMovement();

        for (int i = 0; i < 2; i++) {
            assertEquals(truePositions[i].size(), positionsToTest[i].size());
            listContains = new ListContains(truePositions[i]);
            for (int k = 0; k < positionsToTest[i].size(); k++)
                assertTrue(listContains.isContained(positionsToTest[i].get(k)));
        }
    }

    @Test
    public void baseStrategyBuild() {
        ListContains listContains;
        List<int[]> positionsToTest;
        List<int[]> truePositions;

        game.getPlayerMatrix()[0].setWorkerMoved(game.getPlayerMatrix()[0].getWorker(0));

        truePositions = new ArrayList<>();
        truePositions.add(new int[]{0, 0});
        truePositions.add(new int[]{0, 1});
        truePositions.add(new int[]{1, 0});
        truePositions.add(new int[]{1, 2});
        truePositions.add(new int[]{2, 1});
        truePositions.add(new int[]{2, 2});

        positionsToTest = base.baseBuilding();

        assertEquals(truePositions.size(), positionsToTest.size());
        listContains = new ListContains(truePositions);
        for (int[] ints : positionsToTest) assertTrue(listContains.isContained(ints));

        game.getPlayerMatrix()[0].setWorkerMoved(game.getPlayerMatrix()[0].getWorker(1));

        truePositions = new ArrayList<>();
        truePositions.add(new int[]{1, 0});
        truePositions.add(new int[]{2, 1});
        truePositions.add(new int[]{3, 0});
        truePositions.add(new int[]{3, 1});

        positionsToTest = base.baseBuilding();

        assertEquals(truePositions.size(), positionsToTest.size());
        listContains = new ListContains(truePositions);
        for (int[] ints : positionsToTest) assertTrue(listContains.isContained(ints));
    }
}
