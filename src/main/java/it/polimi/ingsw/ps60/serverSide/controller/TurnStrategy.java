package it.polimi.ingsw.ps60.serverSide.controller;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Cell;
import it.polimi.ingsw.ps60.serverSide.model.Player;

import java.util.ArrayList;
import java.util.List;

public class TurnStrategy implements DivinityCard {

    public List<int[]>[] baseMovement() {
        Board game = GlobalVariables.game;
        Cell[][] cellMatrix = GlobalVariables.game.getCellMatrix();
        Player playerInGame = GlobalVariables.game.getPlayerInGame();
        Cell[] cellWorker = {playerInGame.getWorker1().getCellPosition(), playerInGame.getWorker2().getCellPosition()};

//        int[][][] positions = new int[2][8][8];
        List<int[]>[] positions = new ArrayList[2];

        int[] positionWorker;

        for (int k = 0; k < 2; k++){
            positionWorker = cellWorker[k].getPosition();
            for (int i = -1; i < 2; i++){
                for (int j = -1; j < 2; j++){
                    if (positionWorker[0] + i >= 0 && positionWorker[0] + i <= 4)//controllo sulla x{
                        if (positionWorker[1] + j >= 0 && positionWorker[1] + j <= 4)//controllo sulla y{
                            if (game.getCellByPosition(new int[]{positionWorker[0] + i, positionWorker[1] + j}).isFree())//controllo posizione libera{
                                if (game.getCellByPosition(new int[]{positionWorker[0] + i, positionWorker[1] + j}).getBuildingLevel() <= cellWorker[k].getBuildingLevel()+1){
                                    if (!game.getCellByPosition(new int[]{positionWorker[0] + i, positionWorker[1] + j}).isDomed()){
                                        positions[k].add(new int[]{positionWorker[0] + i, positionWorker[1] + j});
                        }
                    }
                }
            }
        }

        return positions;
    }

    public List<int[]>[] baseBuilding() {
        List<int[]>[] positions = new ArrayList[2];
        return  positions;
    }
}