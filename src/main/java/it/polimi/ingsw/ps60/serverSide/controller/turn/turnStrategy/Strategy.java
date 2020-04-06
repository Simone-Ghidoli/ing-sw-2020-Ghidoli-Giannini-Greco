package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import java.util.List;

public interface Strategy {
    public List<int[]>[] baseMovement();

    public List<int[]> baseBuilding();
}
