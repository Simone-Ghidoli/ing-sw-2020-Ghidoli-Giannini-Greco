package it.polimi.ingsw.ps60.serverSide.model;

import it.polimi.ingsw.ps60.utils.circularList.CircularLinkedList;
import it.polimi.ingsw.ps60.utils.circularList.CircularListIterator;

import java.io.Serializable;

public class Board implements Serializable {
    private final CircularListIterator<Player> playerInGame;
    private int playersNumber;
    private final Cell[][] cellMatrix;
    private final Player[] playerMatrix;
    private final CircularLinkedList<Player> playerList;
    private int completeTower;
    private final char[] cellToSend;
    private Player playerWinner;

    /**
     * The board is the memory of the game.
     * It will contains all the cells of the board, all the players and all the workers
     * It has to be initialized only when the order of player will be established by the birthday.
     * playerInGame, that represents the player that is now playing, will be initialized as Player1
     *
     * @param nicknames the nickname of the players
     */
    public Board(String[] nicknames) {
        this.playersNumber = nicknames.length;
        cellMatrix = new Cell[5][5];
        playerMatrix = new Player[playersNumber];
        playerList = new CircularLinkedList<>();
        playerWinner = null;
        cellToSend = new char[25];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                cellMatrix[i][j] = new Cell(new int[]{i, j}, this);
                cellToSend[i * 5 + j] = '0';
            }
        }

        for (int i = 0; i < playersNumber; i++) {
            playerMatrix[i] = new Player(nicknames[i]);
            playerList.addNode(playerMatrix[i]);
        }

        playerInGame = new CircularListIterator<>(playerList);
        completeTower = 0;
    }

    /**
     * @return return the number of player in game
     */
    public int getPlayersNumber() {
        return playersNumber;
    }

    /**
     * @param position represents the position of the cell in the 5 x 5 board
     * @return the cell in the position
     */
    public Cell getCellByPosition(int[] position) {
        if ((position[0] >= 0) && (position[0] <= 4) && position[1] >= 0 && position[1] <= 4)
            return cellMatrix[position[0]][position[1]];

        return null;
    }

    /**
     * @return return only the player in game
     */
    public CircularListIterator<Player> getPlayerInGame() {

        return playerInGame;
    }

    /**
     * This method will change the player that is currently playing
     */
    public void changeTurn() {
        playerInGame.nextNode();
    }

    /**
     * This method will remove the worker of a player from the board and
     * the player himself from the list of player but not from the playerMatrix
     *
     * @param player the player that lose
     */
    public void lose(Player player) {
        playerList.removeNode(player);
        playersNumber = playerList.getSize();

        for (int i = 0; i < 2; i++)
            player.getWorkers()[i].moveWorker(null);
    }

    /**
     * @param player is the winner of the game
     */
    public void win(Player player) {
        playerWinner = player;
    }

    /**
     * This method return the winner of the game
     *
     * @return the winner, if none has won return null
     */
    public Player getPlayerWinner() {
        return playerWinner;
    }

    /**
     * This method returns all the chars associated to the cells to send to clients
     *
     * @return all the chars associated to the cells to send to clients based on what there are on them
     */
    public char[] getCellToSend() {
        return cellToSend;
    }

    /**
     * This method returns the amount of complete towers on the board
     *
     * @return the number of complete tower. A complete tower is a building with high = 3 and a dome
     */
    public int getCompleteTower() {
        return completeTower;
    }

    /**
     * This method increase the amount of complete tower
     */
    public void increaseCompleteTower() {
        completeTower++;
    }

    /**
     * This method returns if a player has wan the game
     *
     * @return true if none has won the game, false otherwise
     */
    public boolean isNotWon() {
        return playerWinner == null;
    }

    /**
     * This method returns the matrix of players in the game
     *
     * @return the matrix of players in game also if one of them have already lost
     */
    public Player[] getPlayerMatrix() {
        return playerMatrix;
    }
}