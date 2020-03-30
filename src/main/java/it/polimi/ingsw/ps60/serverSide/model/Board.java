package it.polimi.ingsw.ps60.serverSide.model;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.utils.circularList.CircularLinkedList;
import it.polimi.ingsw.ps60.utils.circularList.CircularListIterator;

public class Board {
    CircularListIterator<Player> playerInGame;
    int playersNumber;
    Cell[][] cellMatrix;
    Player[] playerMatrix;
    CircularLinkedList<Player> playerList;
    int completeTower;

    /**
     * The board is the memory of the game.
     * It will contains all the cells of the board, all the players and all the workers
     * It has to be initialized only when the order of player will be established by the birthday.
     * playerInGame, that represents the player that is now playing, will be initialized as Player1
     * @param nicknames the nickname of the players
     */
    public Board(String[] nicknames){
        this.playersNumber = nicknames.length;
        cellMatrix = new Cell[5][5];
        playerMatrix = new Player[playersNumber];
        playerList = new CircularLinkedList<>();

        //This will create a 5 x 5 array of cells
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                cellMatrix[i][j] = new Cell(new int[]{i, j}, this);
            }
        }

        for (int i = 0; i< playersNumber; i++) {
            playerMatrix[i] = new Player(GlobalVariables.IdPlayer.getPlayerByInt(i), nicknames[i]);
            playerList.addNode(playerMatrix[i]);
        }

        playerInGame = new CircularListIterator<>(playerList);
        completeTower = 0;
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
    public Cell getCellByPosition(int[] position) {
        if ((position[0] >= 0) && (position[0] <= 4) && position[1] >= 0 && position[1] <= 4)
            return null;
        return cellMatrix[position[0]][position[1]];
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
        playersNumber = playerList.getSize();

        for (int i = 0; i < 2; i++)
            player.getWorkers()[i].moveWorker(null);
    }

    public void win(Player player){
        /**
         * Manda un messaggio in output a tutti i client informandoli di quale giocatore ha vinto e che la partita è finita.
         * Questo metodo viene chiamato anche nel momento in cui un giocatore fa Last Man Standing
         */
    }

    //method not sure it will be used
    /**
     *
     * @param idPlayer the id of the player that will be returned
     * @return return a player based on his id
     */
    public Player getPlayerById(@org.jetbrains.annotations.NotNull GlobalVariables.IdPlayer idPlayer){
        switch (idPlayer){
            case PLAYER1: return playerMatrix[0];
            case PLAYER2: return playerMatrix[1];
            case PLAYER3: return playerMatrix[2];
            default: return null;
        }
    }

    public int getCompleteTower() {
        return completeTower;
    }

    public void increaseCompleteTower(){
        completeTower++;
    }
}