package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.GlobalVariables;

import it.polimi.ingsw.ps60.serverSide.controller.turn.DivinityStrategy;

import it.polimi.ingsw.ps60.utils.ListContains;
import it.polimi.ingsw.ps60.utils.SetupForTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class ApolloEffectAthenaEffectAtlasTurnEffectTest {
    private SetupForTest test;

    @Before

    public void setupGame() {

        test= new SetupForTest();

        test.player1.setDivinityCard(GlobalVariables.DivinityCard.APOLLO);
        test.player2.setDivinityCard(GlobalVariables.DivinityCard.ATHENA);
        test.player3.setDivinityCard(GlobalVariables.DivinityCard.ATLAS);

        DivinityStrategy divinityStrategy1 = GlobalVariables.game.getPlayerInGame().get().getDivinityStrategy();
        test.listContains=new ListContains(divinityStrategy1.getTurnStrategyMovement()[0]);
        int[][] mossa1 = new int[2][2];

        mossa1[0][0] = 0; //muovo il worker 1
        mossa1[0][1] = 0; //default 0 poi cambia per ogni carta divinità
        mossa1[1] = test.coord6;
        if(test.listContains.isContained(mossa1[1])) {
            divinityStrategy1.setMovement(mossa1);
        }

        test.listContains=new ListContains((divinityStrategy1.getTurnStrategyBuilding()));

        if (test.listContains.isContained(new int[]{2, 2}))
            divinityStrategy1.setBuilding(new int[]{2, 2});


        divinityStrategy1.setEndTurn();
        DivinityStrategy divinityStrategy2 = GlobalVariables.game.getPlayerInGame().get().getDivinityStrategy();
        test.listContains=new ListContains(divinityStrategy2.getTurnStrategyMovement()[0]);
        int[][] mossa2 = new int[2][2];


        mossa2[0][0] = 0; //muovo il worker 1
        mossa2[0][1] = 0;
        mossa2[1] = new int[]{2, 2};
        if(test.listContains.isContained(mossa2[1]))
            divinityStrategy2.setMovement(mossa2);

        test.listContains=new ListContains((divinityStrategy2.getTurnStrategyBuilding()));
        if (test.listContains.isContained(new int[]{2, 3}))
            divinityStrategy2.setBuilding(new int[]{2, 3});
        divinityStrategy2.setEndTurn();


        DivinityStrategy divinityStrategy3 = GlobalVariables.game.getPlayerInGame().get().getDivinityStrategy();

        int[][] mossa3 = new int[2][2];
        test.listContains= new ListContains(divinityStrategy3.getTurnStrategyMovement()[1]);
        mossa3[0][0] = 1;
        mossa3[0][1] = 0;
        mossa3[1] = new int[]{2, 3};
        if(test.listContains.isContained(mossa3[1]))
            divinityStrategy3.setMovement(mossa3);
        else {
            test.listContains = new ListContains(divinityStrategy3.getTurnStrategyMovement()[0]);
            mossa3[0][0] = 0;
            mossa3[0][1] = 0;
            mossa3[1] = new int[]{2, 4};
            if (test.listContains.isContained(mossa3[1]))
                divinityStrategy3.setMovement(mossa3);
        }
        test.listContains=new ListContains((divinityStrategy3.getTurnStrategyBuilding()));
        if(test.listContains.isContained(new int[]{1, 4}))
            divinityStrategy3.setBuilding(new int[]{1, 4, 1});
        divinityStrategy3.setEndTurn();


        test.listContains=new ListContains(divinityStrategy1.getTurnStrategyMovement()[1]);
        mossa1[0][0]=1;
        mossa1[0][1]=0;
        mossa1[1]=new int[]{2,3};
        if(test.listContains.isContained(mossa1[1]))
            divinityStrategy1.setMovement(mossa1);
        else{
            mossa1[1]=new int[]{2,1};
            if (test.listContains.isContained(mossa1[1]))
                divinityStrategy1.setMovement(mossa1);
        }
        test.listContains=new ListContains(divinityStrategy1.getTurnStrategyBuilding());
        if(test.listContains.isContained(new int[]{2,2}))
            divinityStrategy1.setBuilding(new int[]{2,2});
        else if(test.listContains.isContained(new int[]{3,1}))
            divinityStrategy1.setBuilding(new int[]{3,1});
        divinityStrategy1.setEndTurn();

        test.listContains= new ListContains(divinityStrategy2.getTurnStrategyMovement()[1]);
        mossa2[0][0]=1;
        mossa2[0][1]=0;
        mossa2[1]=new int[]{4,1};
        if(test.listContains.isContained(mossa2[1]))
            divinityStrategy2.setMovement(mossa2);
        test.listContains=new ListContains(divinityStrategy2.getTurnStrategyBuilding());
        if(test.listContains.isContained(new int[]{4,2}))
            divinityStrategy2.setBuilding(new int[]{4,2});
        divinityStrategy2.setEndTurn();

        test.listContains= new ListContains(divinityStrategy3.getTurnStrategyMovement()[0]);
        mossa3[0][0]=0;
        mossa3[0][1]=0;
        mossa3[1]=new int[]{2,3};
        if(test.listContains.isContained(mossa3[1])) {
            divinityStrategy3.setMovement(mossa3);
        }
        test.listContains=new ListContains((divinityStrategy3.getTurnStrategyBuilding()));
        if(test.listContains.isContained(new int[]{1,3}))
            divinityStrategy3.setBuilding(new int[]{1,3,0});

    }

    @After
    public void tearDown(){
        GlobalVariables.game=null;

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
        assertEquals(GlobalVariables.game.getCellByPosition(new int[]{2, 3}),test.player3.getWorker(0).getCellPosition());
        assertEquals(GlobalVariables.game.getCellByPosition(new int[]{3, 4}),test.player3.getWorker(1).getCellPosition());
    }
    @Test
    public void checkApolloPower(){
        assertEquals(GlobalVariables.game.getCellByPosition(test.coord1),test.player3.getWorker(1).getCellPosition());
    }
    @Test
    public void checkAtlasPower(){
        assertTrue(GlobalVariables.game.getCellByPosition(new int[]{1, 4}).isDomed());
        assertFalse(GlobalVariables.game.getCellByPosition(new int[]{1,3}).isDomed());
    }
}
