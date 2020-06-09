package it.polimi.ingsw.ps60.utils;

import java.util.List;

/**
 * This class gives you the possibility to rapidly check if an int[] is contained in a list
 */
public class ListContains {

    private final List<int[]> list;

    /**
     * This constructor associate the list to this class in order to be used
     * @param list is the list where to check if an int[] is contained
     */
    public ListContains(List<int[]> list) {
        this.list = list;
    }

    /**
     * @param nodeToCheck is the node that will be checked if it is in the list
     * @return true if the node is present in the list, false otherwise.
     */
    public boolean isContained(int[] nodeToCheck) {
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