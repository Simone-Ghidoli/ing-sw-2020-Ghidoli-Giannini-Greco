package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;
import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.DivinityStrategy;
import it.polimi.ingsw.ps60.utils.ListContains;
import it.polimi.ingsw.ps60.utils.SetupForTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChronusHephaestusMinotaurEffectsTest {
    private SetupForTest test;

    @Before

    public void setupGame() {

        test = new SetupForTest();
        test.player1.setDivinityCard(GlobalVariables.DivinityCard.CHRONUS);
        test.player2.setDivinityCard(GlobalVariables.DivinityCard.HEPHAESTUS);
        test.player3.setDivinityCard(GlobalVariables.DivinityCard.MINOTAUR);
        //inizio la partita con 4 torri complete per testare il potere di crono
        GlobalVariables.game.getCellByPosition(new int[]{0, 0}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{0, 0}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{0, 0}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{0, 0}).buildDome();
        GlobalVariables.game.getCellByPosition(new int[]{1, 0}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{1, 0}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{1, 0}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{1, 0}).buildDome();
        GlobalVariables.game.getCellByPosition(new int[]{2, 0}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{2, 0}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{2, 0}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{2, 0}).buildDome();
        GlobalVariables.game.getCellByPosition(new int[]{3, 0}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{3, 0}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{3, 0}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{3, 0}).buildDome();

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
        test.listContains = new ListContains(divinityStrategy2.getTurnStrategyMovement()[0]);
        int[][] mossa2 = new int[2][2];
        mossa2[0][0] = 0; //muovo il worker 1
        mossa2[0][1] = 0;
        mossa2[1] = new int[]{2, 2};
        if (test.listContains.isContained(mossa2[1]))
            divinityStrategy2.setMovement(mossa2);

        test.listContains = new ListContains((divinityStrategy2.getTurnStrategyBuilding()));
        if (test.listContains.isContained(new int[]{2, 3}))
            divinityStrategy2.setBuilding(new int[]{2, 3, 1});
        divinityStrategy2.setEndTurn();


        DivinityStrategy divinityStrategy3 = GlobalVariables.game.getPlayerInGame().getNode().getValue().getDivinityStrategy();
        test.listContains = new ListContains(divinityStrategy3.getTurnStrategyMovement()[0]);
        int[][] mossa3 = new int[2][2];
        mossa3[0][0] = 0; //muovo il worker 1
        mossa3[0][1] = 0;
        mossa3[1] = new int[]{1, 2};
        if (test.listContains.isContained(mossa3[1]))
            divinityStrategy3.setMovement(mossa3);

        test.listContains = new ListContains((divinityStrategy3.getTurnStrategyBuilding()));
        if (test.listContains.isContained(new int[]{2, 3}))
            divinityStrategy3.setBuilding(new int[]{2, 3});
        divinityStrategy3.setEndTurn();

        test.listContains= new ListContains(divinityStrategy1.getTurnStrategyMovement()[0]);
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
    public void checkChronusPower(){
        assertEquals(GlobalVariables.game.getBitWinner(),1);
    }
    @Test
    public void checkHephaestusPower(){
        assertEquals(GlobalVariables.game.getCellByPosition(new int[]{2,3}).getBuildingLevel(),3);
        assertTrue(GlobalVariables.game.getCellByPosition(new int[]{2,3}).isDomed());
        assertEquals(GlobalVariables.game.getCompleteTower(),5);
    }
    @Test
    public void checkMinotaurPower(){
        assertEquals(GlobalVariables.game.getCellByPosition(new int[]{1,1}),test.player1.getWorker(1).getCellPosition());
        assertEquals(GlobalVariables.game.getCellByPosition(new int[]{1,2}),test.player3.getWorker(0).getCellPosition());
    }
}
