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
     * @param cellPosition set the cell where the worker is
     */
    public void setCellPosition(Cell cellPosition) {
        this.cellPosition = cellPosition;
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
}
