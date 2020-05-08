package it.polimi.ingsw.ps60.utils;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Player;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class SetupForTest {
    public String[] nicknames;
    public Player player1, player2, player3;
    public int[] coord1;
    public int[] coord2;
    public int[] coord3;
    public int[] coord4;
    public int[] coord5;
    public int[] coord6;
    public int[][] posPlayer1, posPlayer2, posPlayer3;
    public int[][][] posPlayers;
    public ListContains listContains;

    public SetupForTest(){
        nicknames = new String[]{"Nico", "Vinz", "Simo"};
        coord1 = new int[]{3, 4};
        coord2 = new int[]{1, 2};
        coord3 = new int[]{3, 2};
        coord4 = new int[]{4, 2};
        coord5 = new int[]{1, 3};
        coord6 = new int[]{3, 3};
        posPlayer1 = new int[][]{coord1, coord2};
        posPlayer2 = new int[][]{coord3, coord4};
        posPlayer3 = new int[][]{coord5, coord6};

        posPlayers = new int[][][]{posPlayer1, posPlayer2, posPlayer3};

        game = new Board(nicknames);

        for (int i = 0; i < game.getPlayersNumber(); i++) {
            for (int j = 0; j < 2; j++) {
                game.getPlayerInGame().getNode().getValue().getWorker(j).moveWorker(game.getCellByPosition(posPlayers[i][j]));
            }
        }
        player1 = GlobalVariables.game.getPlayerById(GlobalVariables.IdPlayer.PLAYER1);
        player2 = GlobalVariables.game.getPlayerById(GlobalVariables.IdPlayer.PLAYER2);
        player3 = GlobalVariables.game.getPlayerById(GlobalVariables.IdPlayer.PLAYER3);
    }
}
