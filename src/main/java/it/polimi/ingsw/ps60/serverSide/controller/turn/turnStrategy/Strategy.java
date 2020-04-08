package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import java.util.List;

public interface Strategy {
    List<int[]>[] baseMovement();

    List<int[]> baseBuilding();
}
