package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.swing;

import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.GUIMethods;
import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.ViewMethodSelection;

import java.util.ArrayList;
import java.util.List;

public class WorkerImage {

    public static void main(String[] args) {
        ViewMethodSelection v = new GUIMethods();
        //System.out.println(Arrays.toString(v.cardChoices(2)));
        //System.out.println(v.divinitySelection(GlobalVariables.DivinityCard.values()));
//        System.out.println(Arrays.deepToString(v.firstSetWorkers(new ArrayList<int[]>())));
//        v.printBoard("0100000400002000070000000");

        List<int[]>[] p = new ArrayList[2];
        p[0] = new ArrayList<>();
        p[0].add(new int[]{0, 0});
        p[0].add(new int[]{0, 1});
        p[0].add(new int[]{0, 2});

        p[1] = new ArrayList<>();
        p[1].add(new int[]{1, 1});
        p[1].add(new int[]{1, 2});
        p[1].add(new int[]{1, 3});

        int[][] p1 = new int[2][];
        p1[0] = new int[]{1, 0};

        p1[1] = new int[]{0, 3};

        v.moveChoice(p, p1);
    }
}
