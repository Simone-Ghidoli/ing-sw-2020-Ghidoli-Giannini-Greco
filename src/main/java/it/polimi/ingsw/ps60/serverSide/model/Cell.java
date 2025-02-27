package it.polimi.ingsw.ps60.serverSide.model;

import java.io.Serializable;

/**
 * This class is a single cell of the board
 */
public class Cell implements Serializable {

    private final int[] position;
    private int buildingLevel;
    private Worker workerIn;
    private boolean dome;
    private final Board board;

    /**
     * This will be a single cell of the 5 x 5 board of the game.
     * This cell will be created with nothing built on it (building level = 0)
     * and it will not be with a dome on it (domed = false)
     *
     * @param position associate to the cell a position in the board
     * @param board is the board associated to the cell
     */
    public Cell(int[] position, Board board) {
        this.position = position;
        buildingLevel = 0;
        dome = false;
        this.board = board;
    }

    /**
     * @return return if the cell have a player on it
     */
    public boolean isFree() {
        return workerIn == null;
    }

    /**
     * This method will increase the building level.
     * Can be build up to 3 levels
     */
    public void incrementBuildingLevel() {
        buildingLevel++;
        updateCellToSend();
    }

    /**
     * @return return the number of buildings on the cell
     */
    public int getBuildingLevel() {
        return buildingLevel;
    }

    /**
     * @return return if there is a dome built on the cell
     */
    public boolean isDomed() {
        return dome;
    }

    /**
     * This method will build a dome on the cell
     */
    public void buildDome() {
        dome = true;
        if (buildingLevel == 3)
            board.increaseCompleteTower();
        updateCellToSend();
    }

    /**
     * @return return the position of the cell in the 5 x 5 board
     */
    public int[] getPosition() {
        return position;
    }

    /**
     * @param workerIn set the worker present on the cell, null if there is no one
     */
    public void setWorkerIn(Worker workerIn) {
        this.workerIn = workerIn;
        updateCellToSend();
    }

    /**
     * @return return the worker present on the cell, null if there is no one
     */
    public Worker getWorkerIn() {
        return workerIn;
    }

    /**
     * This method update the char associated to the cell based on what there are on the cell itself
     */
    private void updateCellToSend() {
        int i = 48 + getBuildingLevel();

        if (isDomed())
            i = i + 4;
        else if (!isFree()) {
            i = i + 8;
            if (workerIn.getOwner() != board.getPlayerMatrix()[0]) {
                i = i + 4;
                if (workerIn.getOwner() != board.getPlayerMatrix()[1]) {
                    i = i + 4;
                }
            }
        }
        board.getCellToSend()[position[0] * 5 + position[1]] = (char) i;
    }
}