package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods;

import it.polimi.ingsw.ps60.GlobalVariables;

import java.util.List;

public interface ViewMethodSelection {
    void printBoard(String board);

    int moveChoice(List<int[]>[] moves, int[][] positionsWorkers);

    int buildChoice(List<int[]> moves);

    String[] ipPortChoices();

    String[] nicknameBirthdayChoice();

    GlobalVariables.DivinityCard[] cardChoices(int playerNumber); //qui verranno date le carte divinità al 1 giocatore e ne sceglierà n = numero giocatori

    GlobalVariables.DivinityCard divinitySelection(GlobalVariables.DivinityCard[] card); //scelta carta divinità singolo giocatore

    int[][] firstSetWorkers(List<int[]> impossiblePositions); //posizionamento pedine prima di iniziare la partita

    boolean specialChoices(String string);

    public int numberOfPlayers();
}