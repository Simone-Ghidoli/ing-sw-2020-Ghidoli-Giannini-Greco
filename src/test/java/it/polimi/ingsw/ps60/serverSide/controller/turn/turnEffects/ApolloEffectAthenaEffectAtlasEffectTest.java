package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.StartGame;
import it.polimi.ingsw.ps60.serverSide.controller.turn.DivinityController;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Player;
import it.polimi.ingsw.ps60.utils.ListContains;
import it.polimi.ingsw.ps60.utils.SetupForTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;

public class ApolloEffectAthenaEffectAtlasEffectTest {
    private SetupForTest test;

    @Before

    public void setupGame() {

        test= new SetupForTest();


        test.player1.setDivinityCard(GlobalVariables.DivinityCard.APOLLO);
        test.player2.setDivinityCard(GlobalVariables.DivinityCard.ATHENA);
        test.player3.setDivinityCard(GlobalVariables.DivinityCard.ATLAS);

        DivinityController divinityController1 = GlobalVariables.game.getPlayerInGame().getNode().getValue().getDivinityController();
        test.listContains=new ListContains(divinityController1.getTurnStrategyMovement()[0]);
        int[][] mossa1 = new int[2][2];

        mossa1[0][0] = 0; //muovo il worker 1
        mossa1[0][1] = 0; //default 0 poi cambia per ogni carta divinità
        mossa1[1] = test.coord6;
        if(test.listContains.isContained(mossa1[1])) {
            divinityController1.setMovemet(mossa1);
        }

        test.listContains=new ListContains((divinityController1.getTurnStrategyBuilding()));

        if (test.listContains.isContained(new int[]{2, 2}))
            divinityController1.setBuilding(new int[]{2, 2});


        divinityController1.setEndTurn();
        DivinityController divinityController2 = GlobalVariables.game.getPlayerInGame().getNode().getValue().getDivinityController();
        test.listContains=new ListContains(divinityController2.getTurnStrategyMovement()[0]);
        int[][] mossa2 = new int[2][2];


        mossa2[0][0] = 0; //muovo il worker 1
        mossa2[0][1] = 0;
        mossa2[1] = new int[]{2, 2};
        if(test.listContains.isContained(mossa2[1]))
            divinityController2.setMovemet(mossa2);

        test.listContains=new ListContains((divinityController2.getTurnStrategyBuilding()));
        if (test.listContains.isContained(new int[]{2, 3}))
            divinityController2.setBuilding(new int[]{2, 3});
        divinityController2.setEndTurn();

        DivinityController divinityController3 = GlobalVariables.game.getPlayerInGame().getNode().getValue().getDivinityController();
        test.listContains=new ListContains(divinityController3.getTurnStrategyMovement()[0]);
        int[][] mossa3 = new int[2][2];

        mossa3[0][0] = 0;
        mossa3[0][1] = 0;
        mossa3[1] = new int[]{2, 4};
        if(test.listContains.isContained(mossa3[1]))
            divinityController3.setMovemet(mossa3);
        test.listContains=new ListContains((divinityController3.getTurnStrategyBuilding()));
        if(test.listContains.isContained(new int[]{1, 4}))
            divinityController3.setBuilding(new int[]{1, 4, 1});
        divinityController3.setEndTurn();
    }

    @After
    public void tearDown(){

    }
    @Test
    public void checkPosition_worker0Player1(){
        assertEquals(GlobalVariables.game.getCellByPosition(test.coord6), test.player1.getWorker(0).getCellPosition());
    }
    @Test
    public void checkPosition_worker0Player2(){
        assertEquals(GlobalVariables.game.getCellByPosition(new int[]{2,2}),test.player2.getWorker(0).getCellPosition());
    }

    @Test
    public void checkAthenaPower(){
        assertEquals(GlobalVariables.game.getCellByPosition(new int[]{2, 4}),test.player3.getWorker(0).getCellPosition());
    }
    @Test
    public void checkApolloPower(){
        assertEquals(GlobalVariables.game.getCellByPosition(test.coord1),test.player3.getWorker(1).getCellPosition());
    }
    @Test
    public void checkAtlasPower(){
        assertTrue(GlobalVariables.game.getCellByPosition(new int[]{1, 4}).isDomed());
    }
}
