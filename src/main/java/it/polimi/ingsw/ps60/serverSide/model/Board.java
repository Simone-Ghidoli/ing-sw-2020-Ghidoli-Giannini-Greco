package it.polimi.ingsw.ps60.serverSide.model;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.utils.CircularLinkedList;
import it.polimi.ingsw.ps60.utils.CircularListIterator;
import org.jetbrains.annotations.NotNull;

public class Board {
    CircularListIterator<Player> playerInGame;
    int playersNumber;
    Cell[][] cellMatrix;
    Player[] playerMatrix;
    CircularLinkedList<Player> playerList;
//    CSV for undo

    /**
     * The board is the memory of the game.
     * It will contains all the cells of the board, all the players and all the workers
     * It has to be initialized only when the order of player will be established by the birthday.
     * playerInGame, that represents the player that is now playing, will be initialized as Player1
     * @param playersNumber set the number of players in game (2 or 3 allowed)
     * @param nicknameP1 set the nickname of the first player
     * @param nicknameP2 set the nickname of the second player
     * @param nicknameP3 set the nickname of the third player
     */
    public Board(int playersNumber, String nicknameP1, String nicknameP2, String nicknameP3){
        this.playersNumber = playersNumber;
        cellMatrix = new Cell[25][25];
        playerMatrix = new Player[playersNumber];

        //This will create a 5 x 5 array of cells
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                cellMatrix[i][j] = new Cell(new int[]{i, j});
            }
        }

        //This will create 2 o 3 player depending on how many player there is
        playerMatrix[0] = new Player(GlobalVariables.IdPlayer.PLAYER1, GlobalVariables.IdWorker.WORKER1,
                GlobalVariables.IdWorker.WORKER2, nicknameP1);
        playerMatrix[1] = new Player(GlobalVariables.IdPlayer.PLAYER2, GlobalVariables.IdWorker.WORKER3,
                GlobalVariables.IdWorker.WORKER4, nicknameP2);
        if (playersNumber == 3)
            playerMatrix[2] = new Player(GlobalVariables.IdPlayer.PLAYER3, GlobalVariables.IdWorker.WORKER5,
                    GlobalVariables.IdWorker.WORKER6, nicknameP3);

        playerList = new CircularLinkedList<>();
        playerList.addNode(playerMatrix[0]);
        playerList.addNode(playerMatrix[1]);
        playerList.addNode(playerMatrix[2]);

        playerInGame = new CircularListIterator<>(playerList);
    }

    /**
     *
     * @return return the number of player in game
     */
    public int getPlayersNumber() {
        return playersNumber;
    }

    /**
     *
     * @return return all the cells of the board
     */
    public Cell[][] getCellMatrix() {
        return cellMatrix;
    }

    /**
     *
     * @param position represents the position of the cell in the 5 x 5 board
     * @return the cell in the position
     */
    public Cell getCellByPosition(@NotNull int[] position) {
        return cellMatrix[position[0]][position[1]];
    }

    /**
     *
     * @param idPlayer the id of the player that will be returned
     * @return return a player based on his id
     */
    public Player getPlayerById(@org.jetbrains.annotations.NotNull GlobalVariables.IdPlayer idPlayer){
        switch (idPlayer){
            case PLAYER1: return playerMatrix[0];
            case PLAYER2: return playerMatrix[1];
            case PLAYER3: return playerMatrix[3];
            default: return null;
        }
    }

    /**
     *
     * @return return only the player in game
     */
    public CircularListIterator<Player> getPlayerInGame() {
        return playerInGame;
    }

    /**
     * This method will change the player that is currently playing
     */
    public void changeTurn(){
        playerInGame.nextNode();
    }

    /**
     * This method will remove the worker of a player from the board and
     * the player himself from the list of player but not from the playerMatrix
     * @param player the player that lose
     */
    public void lose(Player player){
        playerList.removeNode(player);

        //Now it will remove the workers
        player.getWorker1().moveWorker(null);
        player.getWorker2().moveWorker(null);
    }

    public void win(){
        //Not implemented yet
    }

}
