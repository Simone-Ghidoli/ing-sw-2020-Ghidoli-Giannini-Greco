package it.polimi.ingsw.ps60.serverSide.model;

import it.polimi.ingsw.ps60.GlobalVariables;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    private String[] nicknames;
    private Player player1,player2,player3;
    @Before
    public void setup_Player(){
        nicknames = new String[3];
        nicknames[0] = "Nico";
        nicknames[1] = "Vinz";
        nicknames[2] = "Simo";
        player1=new Player(GlobalVariables.IdPlayer.PLAYER1, nicknames[0]);
        player2=new Player(GlobalVariables.IdPlayer.PLAYER2, nicknames[1]);
        player3=new Player(GlobalVariables.IdPlayer.PLAYER3, nicknames[2]);
        player1.setDivinityCard(GlobalVariables.DivinityCard.APOLLO);
        player2.setColour(GlobalVariables.Colour.RED);
        player1.setWorkerMoved(player1.getWorkers()[0]);
        player2.setBuildByWorker(true);
        player3.setBuildByWorker(false);


    }
    @After
    public void tearDown(){

    }

    @Test
    public void getid_correctOutput(){
        assertSame(GlobalVariables.IdPlayer.PLAYER1, player1.getId());
    }
    @Test
    public void getNickname_correctOutput(){
        assertEquals(nicknames[0],player1.getNickname());
    }
    @Test
    public void getDivinityCard_correctOutput(){
        assertSame(GlobalVariables.DivinityCard.APOLLO, player1.getDivinityCard());
    }
    @Test
    public void getColour_correctOutput(){
        assertSame(GlobalVariables.Colour.RED, player2.getColour());
    }
    @Test
    public void getWorkers_correctOutput(){
        assertSame(player1.getWorker(0), player1.getWorkers()[0]);
    }
    @Test
    public void getWorker_corrrectOutput(){
        assertSame(player1.getWorkers()[0] ,player1.getWorker(0));
    }
    @Test
    public void getWorkerMoved_correctOutput(){
        assertSame(player1.getWorker(0),player1.getWorkerMoved());
    }
    @Test
    public void isBuildWorker(){
        assertTrue(player2.isBuildByWorker());
        assertFalse(player3.isBuildByWorker());
    }


}
