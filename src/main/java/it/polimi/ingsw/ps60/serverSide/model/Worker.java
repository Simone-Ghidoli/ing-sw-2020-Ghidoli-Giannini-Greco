package it.polimi.ingsw.ps60.serverSide.model;

import java.io.Serializable;

/**
 * This class is the worker with his status
 */
public class Worker implements Serializable {

    private Cell cellPosition;
    private final Player owner;
    private Cell OldCell;

    /**
     * @param owner associate to the worker his player
     */
    public Worker(Player owner) {
        this.owner = owner;
        cellPosition = null;
    }

    /**
     * @return return the cell where the worker is
     */
    public Cell getCellPosition() {
        return cellPosition;
    }

    /**
     * @return return the owner of the worker
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * This method will update the cell making it aware that the player is now on it and
     * it will also make the previous cell aware that the player is'not on it,
     * but it will conserve his pointer in OldCell
     *
     * @param cellNewPosition set the cell where the worker is
     */
    public void moveWorker(Cell cellNewPosition) {
        if (cellPosition != null) {
            cellPosition.setWorkerIn(null);
            OldCell = cellPosition;
        }
        if (cellNewPosition != null)
            cellNewPosition.setWorkerIn(this);
        cellPosition = cellNewPosition;
    }

    /**
     * Method to verify if a worker level up last turn
     *
     * @return True if it happened, false otherwise
     */
    public boolean isLeveledUp() {
        return OldCell.getBuildingLevel() < cellPosition.getBuildingLevel();
    }

    /**
     * This method return the cell where the worker was during this turn before moving
     *
     * @return the previous cell where the worker was during this turn
     */
    public Cell getOldCell() {
        return OldCell;
    }

    /**
     * This method sets the cell where the worker was before moving
     *
     * @param oldCell the cell where the worker was before moving
     */
    public void setOldCell(Cell oldCell) {
        OldCell = oldCell;
    }
}