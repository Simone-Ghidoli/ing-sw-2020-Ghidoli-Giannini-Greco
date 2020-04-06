package it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.StartGame;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.serverSide.model.Player;
import org.junit.Before;

public class ApolloAthenaAtlasTest {
    private Board board=null;
    private StartGame newgame;
    private String[] nicknames;
    private Player player1,player2,player3;
    private int[] coord1 = new int[2];
    private int[] coord2 = new int[2];
    private int[] coord3 = new int[2];
    private int[] coord4 = new int[2];
    private int[] coord5 = new int[2];
    private int[] coord6 = new int[2];
    private int[][] posPlayer1,posPlayer2,posPlayer3;
    @Before
    public void setupGame(){
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
        coord4[1] = 3;
        coord5[0] = 1;
        coord5[1] = 1;
        coord6[0] = 3;
        coord6[1] = 3;
        posPlayer1=new int[][]{coord1,coord2};
        posPlayer2=new int[][]{coord3,coord4};
        posPlayer3=new int[][]{coord5,coord6};
        player1=new Player(GlobalVariables.IdPlayer.PLAYER1, nicknames[0]);
        player2=new Player(GlobalVariables.IdPlayer.PLAYER2, nicknames[1]);
        player3=new Player(GlobalVariables.IdPlayer.PLAYER3, nicknames[2]);
        player1.setDivinityCard(GlobalVariables.DivinityCard.APOLLO);
        player2.setDivinityCard(GlobalVariables.DivinityCard.ATHENA);
        player3.setDivinityCard(GlobalVariables.DivinityCard.ATLAS);
        newgame=new StartGame();
        newgame.startBoard(nicknames);
        newgame.setWorkersPositions(new int[][][]{posPlayer1,posPlayer2,posPlayer3});
 



    }
}
