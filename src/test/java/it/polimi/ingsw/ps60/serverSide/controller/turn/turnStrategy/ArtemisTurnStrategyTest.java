package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.DivinityStrategy;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.serverSide.model.Player;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ArtemisTurnStrategyTest {
    @Before
    public void createBoard() {
        GlobalVariables.game = new Board(new String[]{"poldo", "franco", "giacomo"}) {
        };
        Cell cell = new Cell(new int[]{0, 0}, GlobalVariables.game);
        Cell cell2 = new Cell(new int[]{2, 2}, GlobalVariables.game);
        Cell cell3 = new Cell(new int[]{1, 2}, GlobalVariables.game);
        GlobalVariables.game.getCellByPosition(new int[]{0, 0}).incrementBuildingLevel();
        GlobalVariables.game.getCellByPosition(new int[]{0, 0}).incrementBuildingLevel();
        GlobalVariables.game.getPlayerInGame().get().getWorker(0).moveWorker(cell);
        GlobalVariables.game.getPlayerInGame().get().getWorker(1).moveWorker(cell2);
        GlobalVariables.game.getPlayerMatrix()[2].getWorker(0).moveWorker(cell3);
        GlobalVariables.game.getCellByPosition(new int[]{2,2}).setWorkerIn(GlobalVariables.game.getPlayerInGame().get().getWorker(0));
        GlobalVariables.game.getCellByPosition(new int[]{1,2}).setWorkerIn(GlobalVariables.game.getPlayerMatrix()[2].getWorker(0));
    }

    @Test
    public void moveTest(){
        Board board=GlobalVariables.game;
        DivinityStrategy div=new DivinityStrategy(GlobalVariables.DivinityCard.ARTEMIS);
        List<int[]>[] current=new ArrayList[2];
        current=div.getTurnStrategyMovement();
        System.out.println("done");
    }
}