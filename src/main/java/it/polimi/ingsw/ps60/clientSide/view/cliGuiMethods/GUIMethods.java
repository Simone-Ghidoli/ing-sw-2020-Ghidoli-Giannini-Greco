package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods;

import it.polimi.ingsw.ps60.GlobalVariables;

import java.util.List;

public class GUIMethods implements ViewMethodSelection {
    @Override
    public void printBoard(String board) {

    }

    @Override
    public int moveChoice(List<int[]>[] moves, int[][] positionsWorkers) {
        return 0;
    }

    @Override
    public int buildChoice(List<int[]> moves) {
        return 0;
    }

    @Override
    public String[] ipPortChoices() {
        return new String[0];
    }

    @Override
    public String[] nicknameBirthdayChoice() {
        return new String[0];
    }

    @Override
    public GlobalVariables.DivinityCard[] cardChoices(int playerNumber) {
        return new GlobalVariables.DivinityCard[0];
    }

    @Override
    public GlobalVariables.DivinityCard divinitySelection(GlobalVariables.DivinityCard[] card) {
        return card[0];
    }

    @Override
    public int[][] firstSetWorkers(List<int[]> posiiblePositions) {
        return new int[0][];
    }

    @Override
    public boolean specialChoices(String string) {
        return true;
    }

    @Override
    public int numberOfPlayers(){return 0;}
}
