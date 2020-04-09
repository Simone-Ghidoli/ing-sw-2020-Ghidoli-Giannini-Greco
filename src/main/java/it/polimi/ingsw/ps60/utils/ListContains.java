package it.polimi.ingsw.ps60.utils;

import java.util.List;

public class ListContains {
    private List<int[]> list;

    public ListContains(List<int[]> list){
        this.list = list;
    }

    public boolean isContained(int[] nodeToCheck){
        int length;

        for (int[] ints : list) {
            length = Math.min(nodeToCheck.length, ints.length);

            for (int j = 0; j < length; j++) {
                if (nodeToCheck[j] != ints[j])
                    break;
                if (j == length - 1)
                    return true;
            }
        }

        return false;
    }
}
