package it.polimi.ingsw.ps60.serverSide.model;

import it.polimi.ingsw.ps60.GlobalVariables;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class WorkerTest {

    private GlobalVariables.IdWorker idWorker;
    private Player owner;
    private Worker worker;
    private Cell cell;

    @Before
    public void setupWorker(){
        idWorker = GlobalVariables.IdWorker.WORKER1;
        owner = new Player(GlobalVariables.IdPlayer.PLAYER1, "vincent");

        worker = new Worker(idWorker, owner);

        cell = new Cell(new int[]{1, 2},null);
    }

    @After
    public void tearDown(){
        worker = null;
    }

    @Test
    public void getId_correctOutput(){
        assertSame(worker.getId(), idWorker);
    }

    @Test
    public void getOwner_correctOutput(){
        assertSame(worker.getOwner(), owner);
    }

    @Test
    public void getCellPosition_correctOutput(){
        assertSame(worker.getCellPosition(), null);
    }

}
