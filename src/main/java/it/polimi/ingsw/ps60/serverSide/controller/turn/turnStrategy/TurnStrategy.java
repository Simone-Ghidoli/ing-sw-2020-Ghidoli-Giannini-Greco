package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import java.util.List;

public interface TurnStrategy {

    /**
     * This method will provide to the player all the possible moves allowed by all his workers
     * @return an array of list of possible moves. In the first list there is the moves allowed for the worker 1,
     * in the second those of the second
     */
    List<int[]>[] baseMovement();

    /**
     * This method will provide to the player all the possible builds allowed by the worker that has moved
     * @return a list of possible builds for the worker that has been moved
     */
    List<int[]> baseBuilding();
}
