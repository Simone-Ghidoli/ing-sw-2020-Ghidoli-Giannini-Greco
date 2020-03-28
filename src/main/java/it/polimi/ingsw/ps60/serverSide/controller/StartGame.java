package it.polimi.ingsw.ps60.serverSide.controller;


import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class StartGame {

    public void startBoard(String[] nickname) {
        game = new Board(nickname);
    }

    public GlobalVariables.DivinityCard[] getDivinityCard() {
        return GlobalVariables.DivinityCard.values();
    }

    public void selectDivinityCard(GlobalVariables.DivinityCard[] divinityCards) {
        for (int j = 0; j < divinityCards.length; j++)
            game.getPlayerById(GlobalVariables.IdPlayer.getPlayerByInt(j)).setDivinityCard(divinityCards[j]);
    }

    public void posizionamentoPedine(int[][][] positions) {
        for (int j = 0; j < positions.length; j++) {
            for (int i = 0; i < 2; i++) {
                game.getPlayerById(GlobalVariables.IdPlayer.getPlayerByInt(j)).getWorker(i).moveWorker(game.getCellByPosition(positions[j][i]));
            }
        }
    }
}
