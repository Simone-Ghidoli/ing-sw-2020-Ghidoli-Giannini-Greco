package it.polimi.ingsw.ps60.serverSide.model;

import it.polimi.ingsw.ps60.GlobalVariables;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class BoardTest {
    private Board board=null;
    private String[] nicknames;
    private int[] coord1=new int[2];
    private Cell cell;

    @Before
    public void setupBoard() {
        nicknames= new String[3];
            nicknames[0]="Nico";
            nicknames[1]="Vinz";
            nicknames[2]="Simo";
            coord1[0]=3;
            coord1[1]=4;
            board = new Board(nicknames);
            cell=new Cell(coord1,board);
    }


    @After
    public void tearDown(){


    }

    @Test
    public void playersNumber_correctOutput(){
        assertEquals(board.getPlayersNumber(), 3);
    }

    @Test
    public void getCellByPosition_correctInput_correctOutput(){
        assertSame(board.getCellByPosition(coord1),board.cellMatrix[3][4]);
    }
    @Test
    public void getPlayerInGame_correctOutput() {
        assertSame(board.getPlayerInGame(), board.playerInGame);
    }
    @Test
    public void getPlayerById_correctInput_correctOutput(){
        assertSame(board.getPlayerById(GlobalVariables.IdPlayer.PLAYER1),board.playerMatrix[0]);
    }
}