package it.polimi.ingsw.ps60.utils;

import java.util.ArrayList;
import java.util.List;

public class Converters {
    public List<SerializedInteger> serializeListOfInts(List<int[]> listToConvert) {
        List<SerializedInteger> serializedIntegerList = new ArrayList<>();
        for (int[] elem : listToConvert) {
            serializedIntegerList.add(new SerializedInteger(elem));
        }
        return serializedIntegerList;
    }

    public List<int[]> deserializeListOfInts(List<SerializedInteger> positions) {
        List<int[]> list = new ArrayList<>();

        for (SerializedInteger position : positions) {
            list.add(position.serialized);
        }
        return list;
    }

    public List<SerializedInteger>[] serializeArrayOfListOfInts(List<int[]>[] arrayOfListsToConvert) {
        List<SerializedInteger>[] listConverted = new ArrayList[arrayOfListsToConvert.length];
        for (int i = 0; i < arrayOfListsToConvert.length; i++) {
            listConverted[i] = serializeListOfInts(arrayOfListsToConvert[i]);
        }
        return listConverted;
    }

    public List<int[]>[] deserializeArrayOfListOfInts(List<SerializedInteger>[] serializedArrayOfListOfInts) {
        List<int[]>[] lists = new ArrayList[serializedArrayOfListOfInts.length];

        for (int i = 0; i < serializedArrayOfListOfInts.length; i++) {
            lists[i] = new ArrayList<>();
            for (int k = 0; k < serializedArrayOfListOfInts[i].size(); k++) {
                lists[i].add(serializedArrayOfListOfInts[i].get(k).serialized);
            }
        }
        return lists;
    }

    public SerializedInteger[] serialize2DArrayOfInt(int[][] ints) {
        SerializedInteger[] serializedIntegers = new SerializedInteger[ints.length];

        for (int i = 0; i < ints.length; i++){
            serializedIntegers[i] = new SerializedInteger(ints[i]);
        }

        return serializedIntegers;
    }

    public int[][] deserialize2DArrayOfInts(SerializedInteger[] serializedInteger) {
        int[][] ints = new int[serializedInteger.length][];
        for (int i = 0; i < serializedInteger.length; i++)
            ints[i] = serializedInteger[i].serialized;
        return ints;
    }
}