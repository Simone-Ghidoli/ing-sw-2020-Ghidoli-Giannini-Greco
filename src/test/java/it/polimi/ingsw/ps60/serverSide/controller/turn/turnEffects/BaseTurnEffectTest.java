package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.utils.TestUtilities;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class BaseTurnEffectTest {
    TestUtilities testUtilities = new TestUtilities();

    @Before
    public void setUp() {
        game = new Board(new String[]{"PlayerToCheck1", "PlayerToCheck2"});
        Cell cell1 = game.getCellByPosition(new int[]{1, 1});
        Cell cell2 = game.getCellByPosition(new int[]{2, 0});
        Cell cell3 = game.getCellByPosition(new int[]{2, 3});
        Cell cell4 = game.getCellByPosition(new int[]{3, 4});
        game.getPlayerMatrix()[0].getWorker(0).moveWorker(cell1);
        game.getPlayerMatrix()[0].getWorker(1).moveWorker(cell2);
        game.getPlayerMatrix()[1].getWorker(0).moveWorker(cell3);
        game.getPlayerMatrix()[1].getWorker(1).moveWorker(cell4);
        testUtilities.buildsNTimes(new int[]{2, 3}, 2);
        testUtilities.buildsNTimes(new int[]{3, 3}, 2);
        testUtilities.buildDome(new int[]{0, 0});
    }
    @Test
    public void checkBaseTest(){
        int[][] movement1 = new int[2][2];
        movement1[0][0] = 0;
        movement1[0][1] = 0;
        movement1[1] = new int[]{2, 2};
        game.getPlayerMatrix()[0].getDivinityStrategy().setMovement(movement1);
        game.getPlayerMatrix()[0].getDivinityStrategy().setBuilding(new int[]{3, 3});
        game.getPlayerMatrix()[0].getDivinityStrategy().setEndTurn();
        assertEquals(game.getPlayerInGame().get(),game.getPlayerMatrix()[1]);
        int[][] movement2 = new int[2][2];
        movement2[0][0] = 0;
        movement2[0][1] = 0;
        movement2[1] = new int[]{3, 3};
        game.getPlayerInGame().get().getDivinityStrategy().setMovement(movement2);
        game.getPlayerInGame().get().getDivinityStrategy().setBuilding(new int[]{4, 4});
        game.getPlayerInGame().get().getDivinityStrategy().setEndTurn();
        assertEquals(game.getPlayerMatrix()[1],game.getPlayerWinner());

    }
}
