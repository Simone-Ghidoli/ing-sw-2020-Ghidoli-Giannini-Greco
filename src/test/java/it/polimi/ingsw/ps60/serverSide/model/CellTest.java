package it.polimi.ingsw.ps60.serverSide.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {
    private Board board = null;
    private String[] nicknames;
    private int[] coord1 = new int[2];
    private int[] coord2 = new int[2];
    private int[] coord3 = new int[2];
    private int[] coord4 = new int[2];
    private int[] coord5 = new int[2];
    private Cell cell1, cell2, cell3, cell4, cell5;

    @Before
    public void setupCell() {
        nicknames = new String[3];
        nicknames[0] = "Nico";
        nicknames[1] = "Vinz";
        nicknames[2] = "Simo";
        coord1[0] = 3;
        coord1[1] = 4;
        coord2[0] = 1;
        coord2[1] = 2;
        coord3[0] = 3;
        coord3[1] = 2;
        coord4[0] = 4;
        coord4[1] = 2;
        coord5[0] = 1;
        coord5[1] = 1;
        board = new Board(nicknames);
        cell1 = board.getCellByPosition(coord1);
        cell2 = board.getCellByPosition(coord2);
        cell3 = board.getCellByPosition(coord3);
        cell4 = board.getCellByPosition(coord4);
        cell5 = board.getCellByPosition(coord5);
        cell2.incrementBuildingLevel();
        cell2.incrementBuildingLevel();
        cell2.incrementBuildingLevel();
        cell2.buildDome();
        cell3.buildDome();
        cell4.incrementBuildingLevel();
        cell4.setWorkerIn(board.playerMatrix[0].getWorker(0));
        cell5.incrementBuildingLevel();
        cell5.buildDome();
        cell5.removeDome();
        cell5.incrementBuildingLevel();
    }

    @After
    public void tearDown() {
        board = null;
    }

    @Test
    public void isFree_correctOutput() {
        assertTrue(cell1.isFree());
        assertTrue(cell2.isFree());
        assertTrue(cell3.isFree());
        assertFalse(cell4.isFree());
        assertTrue(cell5.isFree());

    }
    @Test
    public void getBuildingLevel_correctOutput(){
        assertEquals(cell1.getBuildingLevel(),0);
        assertEquals(cell2.getBuildingLevel(),3);
        assertEquals(cell3.getBuildingLevel(),0);
        assertEquals(cell4.getBuildingLevel(),1);
        assertEquals(cell5.getBuildingLevel(),2);
    }
    @Test
    public void isDomed_correctOutput(){
        assertFalse(cell1.isDomed());
        assertTrue(cell2.isDomed());
        assertTrue(cell3.isDomed());
        assertFalse(cell4.isDomed());
        assertFalse(cell5.isDomed());
    }
    @Test
    public void getPosition_correctOutput(){
        assertSame(cell1.getPosition(),coord1);
    }
    @Test
    public void getWorkerIn_correctOutput(){
        assertSame(cell4.getWorkerIn(),board.playerMatrix[0].getWorker(0));
    }
    @Test
    public void numberCompleteBuild(){
        assertEquals(board.completeTower,1);
    }
}