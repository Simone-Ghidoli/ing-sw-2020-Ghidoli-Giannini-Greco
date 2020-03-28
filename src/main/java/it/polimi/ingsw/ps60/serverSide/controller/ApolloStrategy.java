package it.polimi.ingsw.ps60.serverSide.controller;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Player;
import it.polimi.ingsw.ps60.utils.circularList.CircularListIterator;

import java.util.ArrayList;
import java.util.List;

public class ApolloStrategy extends TurnStrategy {

    @Override
    public List<int[]>[] baseMovement() {
        List<int[]>[] positions = super.baseMovement();
        Board game = GlobalVariables.game;

        CircularListIterator<Player> players = game.getPlayerInGame();
        players.nextNode();

        for (int i = 0; i < players.getList().getSize()-1; i++){
            for (int j = 0; j < 2; j++){
                positions[j].add(players.getNode().getValue().getWorker1().getCellPosition().getPosition());
                positions[j].add(players.getNode().getValue().getWorker2().getCellPosition().getPosition());
            }
        }
        return positions;
    }
}
