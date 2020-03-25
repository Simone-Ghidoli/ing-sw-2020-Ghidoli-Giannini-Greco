package it.polimi.ingsw.ps60.serverSide.model;

import it.polimi.ingsw.ps60.GlobalVariables;

public class Worker {

    private GlobalVariables.IdWorker id;
    private Cell cellPosition;
    private Player owner;

    /**
     *
     * @param id associate an univocal id to the worker
     * @param owner associate to the worker his player
     */
    public Worker(GlobalVariables.IdWorker id, Player owner){
        this.id = id;
        this.owner = owner;
        cellPosition = null;
    }

    /**
     *
     * @return return the id associated to the worker
     */
    public GlobalVariables.IdWorker getId() {
        return id;
    }

    /**
     *
     * @return return the cell where the worker is
     */
    public Cell getCellPosition() {
        return cellPosition;
    }

    /**
     *
     * @return return the owner of the worker
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * This method will update the cell making it aware that the player is now on it and
     * it will also make the previous cell allowed that the player is'not on it.
     * @param cellNewPosition set the cell where the worker is
     */
    public void moveWorker(Cell cellNewPosition) {
        if (cellPosition != null)
            cellPosition.setWorkerIn(null);
        if (cellNewPosition != null)
            cellNewPosition.setWorkerIn(this);
        cellPosition = cellNewPosition;
    }
}
