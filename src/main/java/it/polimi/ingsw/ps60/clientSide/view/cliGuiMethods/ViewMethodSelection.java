package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods;

import it.polimi.ingsw.ps60.GlobalVariables;

import java.util.List;

public interface ViewMethodSelection {

    void printBoard(String board);

    int moveChoice(List<int[]>[] moves, int[][] positionsWorkers);

    int buildChoice(List<int[]> moves);

    String[] ipPortChoices();

    String[] nicknameBirthdayChoice();

    GlobalVariables.DivinityCard[] cardChoices(int playerNumber);

    GlobalVariables.DivinityCard divinitySelection(GlobalVariables.DivinityCard[] cards);

    int[][] firstSetWorkers(List<int[]> impossiblePositions);

    boolean specialChoices(String string);

    int numberOfPlayers();

    void alert(String string);
}