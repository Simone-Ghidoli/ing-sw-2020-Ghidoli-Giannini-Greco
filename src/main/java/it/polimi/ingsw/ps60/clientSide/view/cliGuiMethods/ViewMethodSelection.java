package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods;

import it.polimi.ingsw.ps60.GlobalVariables;

import java.util.List;

public interface ViewMethodSelection {
    public void printBoard(char[] board);

    public int moveChoice(List<int[]>[] moves, int[][]positionsWorkers);

    public int buildChoice(List<int[]> moves);

    public String[] ipPortChoices();

    public String[] nicknameBirthdayChoice();

    public GlobalVariables.DivinityCard[] cardChoices(GlobalVariables.DivinityCard[] allCards); //qui verranno date le carte divinità al 1 giocatore e ne sceglierà n = numero giocatori

    public GlobalVariables.DivinityCard[] divinitySelection(GlobalVariables.DivinityCard card); //scelta carta divinità singolo giocatore

    public int[][] fistSetWorkers(List<int[]> posiiblePositions); //posizionamento pedine prima di iniziare la partita

    public int specialChoices();
}
