package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.StartGame;
import it.polimi.ingsw.ps60.serverSide.controller.turn.DivinityController;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;

public class ApolloEffectAthenaEffectAtlasEffectTest {
    private Board board = null;
    private StartGame newgame;
    private String[] nicknames;
    private Player player1, player2, player3;
    private int[] coord1 = new int[2];
    private int[] coord2 = new int[2];
    private int[] coord3 = new int[2];
    private int[] coord4 = new int[2];
    private int[] coord5 = new int[2];
    private int[] coord6 = new int[2];
    private int[][] posPlayer1, posPlayer2, posPlayer3;

    @Before
    public void setupGame() {

        nicknames = new String[]{"Nico", "Vinz", "Simo"};
        coord1 = new int[]{3, 4};
        coord2[0] = 1;
        coord2[1] = 2;
        coord3[0] = 3;
        coord3[1] = 2;
        coord4[0] = 4;
        coord4[1] = 2;
        coord5[0] = 1;
        coord5[1] = 3;
        coord6[0] = 3;
        coord6[1] = 3;
        posPlayer1 = new int[][]{coord1, coord2};
        posPlayer2 = new int[][]{coord3, coord4};
        posPlayer3 = new int[][]{coord5, coord6};
        newgame = new StartGame();
        newgame.startBoard(nicknames);
        newgame.setWorkersPositions(new int[][][]{posPlayer1, posPlayer2, posPlayer3});

        player1 = GlobalVariables.game.getPlayerById(GlobalVariables.IdPlayer.PLAYER1);
        player2 = GlobalVariables.game.getPlayerById(GlobalVariables.IdPlayer.PLAYER2);
        player3 =  GlobalVariables.game.getPlayerById(GlobalVariables.IdPlayer.PLAYER3);

        player1.setDivinityCard(GlobalVariables.DivinityCard.APOLLO);
        player2.setDivinityCard(GlobalVariables.DivinityCard.ATHENA);
        player3.setDivinityCard(GlobalVariables.DivinityCard.ATLAS);

        DivinityController divinityController1 = GlobalVariables.game.getPlayerInGame().getNode().getValue().getDivinityController();
        List<int[]>[] possibeMoves1 = divinityController1.getTurnStrategyMovement();


        int[][] mossa1 = new int[2][2];

        if (possibeMoves1[0].equals(coord6)) {
            mossa1[0][0] = 0; //muovo il worker 1
            mossa1[0][1] = 0; //default 0 poi cambia per ogni carta divinità
            mossa1[1] = coord6;
            divinityController1.setMovemet(mossa1);

        }

        List<int[]> possibleBuild1 = divinityController1.getTurnStrategyBuilding();
        if (possibleBuild1.contains(new int[]{2, 2}))
            divinityController1.setBuilding(new int[]{2, 2});


        divinityController1.setEndTurn();
        DivinityController divinityController2 = GlobalVariables.game.getPlayerInGame().getNode().getValue().getDivinityController();
        List<int[]>[] possibeMoves2 = divinityController2.getTurnStrategyMovement();

        int[][] mossa2 = new int[2][2];
        if (possibeMoves2[0].contains(new int[]{2, 2})) {
            mossa2[0][0] = 0; //muovo il worker 1
            mossa2[0][1] = 0; //default 0 poi cambia per ogni carta divinità
            mossa2[1] = new int[]{2, 2};
            divinityController1.setMovemet(mossa2);

        }

        List<int[]> possibleBuild2 = divinityController2.getTurnStrategyBuilding();
        if (possibleBuild2.contains(new int[]{2, 3}))
            divinityController2.setBuilding(new int[]{2, 3});
        divinityController2.setEndTurn();

        DivinityController divinityController3 = GlobalVariables.game.getPlayerInGame().getNode().getValue().getDivinityController();
        List<int[]>[] possibeMoves3 = divinityController3.getTurnStrategyMovement();
        int[][] mossa3 = new int[2][2];
        if(possibeMoves3[0].contains(new int[]{2,3})){
            mossa3[0][0]=0;
            mossa3[0][1]=0;
            mossa3[1]=new int[]{2,3};
            divinityController3.setMovemet(mossa3);
        }
        if (possibeMoves3[0].contains(new int[]{2,4})){
            mossa3[0][0]=0;
            mossa3[0][1]=0;
            mossa3[1]=new int[]{2,4};
            divinityController3.setMovemet(mossa3);
        }

        List<int[]> possibleBuild3 = divinityController3.getTurnStrategyBuilding();

        if (possibleBuild3.contains(new int[]{1,4})){
            divinityController3.setBuilding(new int[]{1,4,1});
        }
        divinityController3.setEndTurn();





    }
    @After
    public void tearDown(){

    }
    @Test
    public void checkPosition_worker0Player1(){
        assertEquals(GlobalVariables.game.getCellByPosition(coord6), player1.getWorker(0).getCellPosition());
    }
    @Test
    public void checkPosition_worker0Player2(){
        assertEquals(GlobalVariables.game.getCellByPosition(new int[]{2,2}),player2.getWorker(0).getCellPosition());
    }

    @Test
    public void checkAthenaPower(){
        assertEquals(GlobalVariables.game.getCellByPosition(coord5),player3.getWorker(0).getCellPosition());
    }
    @Test
    public void checkApolloPower(){
        assertEquals(GlobalVariables.game.getCellByPosition(coord1),player3.getWorker(1).getCellPosition());
    }
}
