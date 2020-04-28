package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.DivinityStrategy;
import it.polimi.ingsw.ps60.utils.ListContains;
import it.polimi.ingsw.ps60.utils.SetupForTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PanArtemisTritonEffectsTest {
    private SetupForTest test;

    @Before

    public void setupGame(){

        test = new SetupForTest();
        test.player1.setDivinityCard(GlobalVariables.DivinityCard.PAN);
        test.player2.setDivinityCard(GlobalVariables.DivinityCard.ARTEMIS);
        test.player3.setDivinityCard(GlobalVariables.DivinityCard.TRITON);
        GlobalVariables.game.getCellByPosition(new int[]{3, 4}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{2, 4}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{2, 4}).incrementBuildingLevel();
        DivinityStrategy divinityStrategy1 = GlobalVariables.game.getPlayerInGame().getNode().getValue().getDivinityStrategy();
        test.listContains = new ListContains(divinityStrategy1.getTurnStrategyMovement()[0]);
        int[][] mossa1 = new int[2][2];

        mossa1[0][0] = 0;
        mossa1[0][1] = 0;
        mossa1[1] = new int[]{2, 4};
        if (test.listContains.isContained(mossa1[1])) {
            divinityStrategy1.setMovement(mossa1);
        }

        test.listContains = new ListContains((divinityStrategy1.getTurnStrategyBuilding()));

        if (test.listContains.isContained(new int[]{2, 3}))
            divinityStrategy1.setBuilding(new int[]{2, 3});
        divinityStrategy1.setEndTurn();

        DivinityStrategy divinityStrategy2 = GlobalVariables.game.getPlayerInGame().getNode().getValue().getDivinityStrategy();
        test.listContains=new ListContains(divinityStrategy2.getTurnStrategyMovement()[0]);
        int[][] mossa2 = new int[2][2];

        mossa2[0][0] = 0;
        mossa2[0][1] = 0;
        mossa2[1] = new int[]{3, 4};
        if (test.listContains.isContained(mossa2[1])) {
            divinityStrategy2.setMovement(mossa2);
        }
        test.listContains=new ListContains(divinityStrategy2.getTurnStrategyBuilding());
        if(test.listContains.isContained(new int[]{2,3}))
            divinityStrategy2.setBuilding(new int[]{2,3});
        divinityStrategy2.setEndTurn();

        DivinityStrategy divinityStrategy3 =GlobalVariables.game.getPlayerInGame().getNode().getValue().getDivinityStrategy();
        test.listContains=new ListContains(divinityStrategy3.getTurnStrategyMovement()[1]);
        int[][] mossa3 = new int[2][2];
        mossa3[0][0]=1;
        mossa3[0][1]=0;
        mossa3[1]=new int[]{4,1};
        if (test.listContains.isContained(mossa3[1]))
            divinityStrategy3.setMovement(mossa3);
        else {
            test.listContains=new ListContains(divinityStrategy3.getTurnStrategyMovement()[0]);
            mossa3[0][0]=0;
            mossa3[0][1]=0;
            
            mossa3[1] = new int[]{4, 1};
            if (test.listContains.isContained(mossa3[1]))
                divinityStrategy3.setMovement(mossa3);
        }
        test.listContains=new ListContains(divinityStrategy3.getTurnStrategyBuilding());
        if(test.listContains.isContained(new int[]{3,1}))
            divinityStrategy3.setBuilding(new int[]{3,1});
        divinityStrategy3.setEndTurn();
        test.listContains = new ListContains(divinityStrategy1.getTurnStrategyMovement()[0]);

        mossa1[0][0] = 0;
        mossa1[0][1] = 0;
        mossa1[1] = new int[]{1, 4};
        if (test.listContains.isContained(mossa1[1])) {
            divinityStrategy1.setMovement(mossa1);
        }

        test.listContains = new ListContains((divinityStrategy1.getTurnStrategyBuilding()));

        if (test.listContains.isContained(new int[]{0, 4}))
            divinityStrategy1.setBuilding(new int[]{0, 4});
        divinityStrategy1.setEndTurn();



    }
    @After
    public void tearDown(){
        test.board=null;

    }

    @Test
    public void checkPanPower(){

        assertEquals(GlobalVariables.game.getBitWinner(),1);
    }
    @Test
    public void checkArtemisPower(){
        assertEquals(GlobalVariables.game.getCellByPosition(new int[]{3,4}),test.player2.getWorker(0).getCellPosition());
    }
    @Test
    public void checkTritonPower(){
        assertEquals(GlobalVariables.game.getCellByPosition(new int[]{3,3}),test.player3.getWorker(1).getCellPosition());
        assertEquals(GlobalVariables.game.getCellByPosition(new int[]{4,1}),test.player3.getWorker(0).getCellPosition());
    }
}
