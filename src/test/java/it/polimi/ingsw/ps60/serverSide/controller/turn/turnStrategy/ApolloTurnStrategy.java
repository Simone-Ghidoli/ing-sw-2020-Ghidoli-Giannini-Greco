package it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Cell;
import org.junit.Before;
import org.junit.Test;

public class ApolloTurnStrategy {
    @Before
    public void createBoard(){
        GlobalVariables.game=new Board(new String[]{"poldo","franco","giacomo"}){
        };
        Cell cell=new Cell(new int[]{1,1},GlobalVariables.game);
        Cell cell2=new Cell(new int[]{2,2},GlobalVariables.game);
        GlobalVariables.game.getPlayerInGame().getNode().getValue().getWorker(0).moveWorker(cell);
        GlobalVariables.game.getPlayerInGame().getNode().getValue().getWorker(1).moveWorker(cell2);
    }

    @Test
    public void movementTest(){

    }
}
