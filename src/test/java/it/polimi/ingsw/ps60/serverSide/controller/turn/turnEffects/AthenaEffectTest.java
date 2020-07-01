package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.utils.ListContains;
import it.polimi.ingsw.ps60.utils.TestUtilities;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.ps60.GlobalVariables.game;
import static org.junit.Assert.*;

public class AthenaEffectTest {
    TestUtilities testUtilities = new TestUtilities();
    @Before
    public void setUp() {
        game = new Board(new String[]{"PlayerToCheck", "PlaceHolder"});
        game.getPlayerMatrix()[0].setDivinityCard(GlobalVariables.DivinityCard.ATHENA);
        Cell cell1 = game.getCellByPosition(new int[]{1, 1});
        Cell cell2 = game.getCellByPosition(new int[]{2, 0});
        Cell cell3 = game.getCellByPosition(new int[]{2, 2});
        Cell cell4 = game.getCellByPosition(new int[]{3, 3});
        game.getPlayerMatrix()[0].getWorker(0).moveWorker(cell1);
        game.getPlayerMatrix()[0].getWorker(1).moveWorker(cell2);
        game.getPlayerMatrix()[1].getWorker(0).moveWorker(cell3);
        game.getPlayerMatrix()[1].getWorker(1).moveWorker(cell4);
        testUtilities.buildsNTimes(new int[]{1, 0}, 1);
        testUtilities.buildsNTimes(new int[]{2, 3}, 1);
    }

    /**
     * test athena power, her worker goes up one level, the opposing workers can't level up for one turn
     */
    @Test
    public void checkAthenaEffect(){
        int[][] movement1 = new int[2][2];
        movement1[0][0] = 0;
        movement1[0][1] = 0;
        movement1[1] = new int[]{1,0};
        game.getPlayerMatrix()[0].getDivinityStrategy().setMovement(movement1);
        ListContains listContains=new ListContains(game.getPlayerMatrix()[1].getDivinityStrategy().getTurnStrategyMovement()[0]);
        assertTrue(game.getPlayerInGame().get().isBitException());
        assertFalse(listContains.isContained(new int[]{2,3}));
    }
}