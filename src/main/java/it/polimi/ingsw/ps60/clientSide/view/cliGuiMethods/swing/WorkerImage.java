package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.swing;

import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.GUIMethods;
import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.ViewMethodSelection;

public class WorkerImage {

    public static void main(String[] args) {
        ViewMethodSelection v = new GUIMethods();
        //System.out.println(Arrays.toString(v.cardChoices(2)));
        //System.out.println(v.divinitySelection(GlobalVariables.DivinityCard.values()));
//        System.out.println(Arrays.deepToString(v.firstSetWorkers(new ArrayList<int[]>())));
        v.printBoard("0100000000000000000000000");
    }
}
