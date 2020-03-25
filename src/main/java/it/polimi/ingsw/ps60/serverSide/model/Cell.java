package it.polimi.ingsw.ps60.serverSide.model;

public class Cell {

    private int[] position;
    private int buildingLevel;
    private Worker workerIn;
    private boolean dome;

    /**
     * This will be a single cell of the 5 x 5 board of the game.
     * This cell will be created with nothing built on it (building level = 0)
     * and it will not be with a dome on it (domed = false)
     * @param position associate to the cell a position in the board
     */
    public Cell(int[] position){
        this.position = position;
        buildingLevel = 0;
        dome = false;
    }

    /**
     *
     * @return return if the cell have a player on it
     */
    public boolean isFree(){
        return workerIn == null;
    }

    /**
     * This method will increase the building level.
     * Can be build up to 3 levels
     */
    public void incrementBuildingLevel(){
        buildingLevel++;
    }

    /**
     *
     * @return return the number of buildings on the cell
     */
    public int getBuildingLevel() {
        return buildingLevel;
    }

    /**
     *
     * @return return if there is a dome built on the cell
     */
    public boolean isDomed(){
        return dome;
    }

    /**
     * This method will build a dome on the cell
     */
    public void buildDome(){
        dome = true;
    }

    /**
     * This method will remove the dome of the cell
     */
    public void removeDome(){
        dome = false;
    }

    /**
     *
     * @return return the position of the cell in the 5 x 5 board
     */
    public int[] getPosition() {
        return position;
    }

    /**
     *
     * @param workerIn set the worker present on the cell, null if there is no one
     */
    public void setWorkerIn(Worker workerIn) {
        this.workerIn = workerIn;
    }

    /**
     *
     * @return return the worker present on the cell, null if there is no one
     */
    public Worker getWorkerIn() {
        return workerIn;
    }
}
