package it.polimi.ingsw.ps60.utils;

import java.util.List;

public class ListContains {
    private List<int[]> list;

    public ListContains(List list){
        this.list = list;
    }

    public boolean isContained(int[] nodeToCheck){
        for (int i = 0; i < list.size(); i++){
            if (nodeToCheck.length != list.get(i).length)
                continue;

            for (int j = 0; j < nodeToCheck.length; j++){
                if (nodeToCheck[j] != list.get(i)[j])
                    break;
                if (j == nodeToCheck.length-1)
                    return true;
            }
        }

        return false;
    }
}
