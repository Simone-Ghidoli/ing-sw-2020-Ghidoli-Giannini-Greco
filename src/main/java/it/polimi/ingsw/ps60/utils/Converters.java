package it.polimi.ingsw.ps60.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class have all the converters to be able to send not serialized type through socket
 */
public class Converters {

    /**
     * This method serialize a list of ints arrays
     *
     * @param listToSerialize is the list not serialized
     * @return is the list serialized
     */
    public List<SerializedInteger> serializeListOfInts(List<int[]> listToSerialize) {
        List<SerializedInteger> serializedIntegerList = new ArrayList<>();
        for (int[] elem : listToSerialize)
            serializedIntegerList.add(new SerializedInteger(elem));
        return serializedIntegerList;
    }

    /**
     * This method deserialize a list of ints arrays
     *
     * @param listToDeserialize is the list serialized
     * @return is the list serialized
     */
    public List<int[]> deserializeListOfInts(List<SerializedInteger> listToDeserialize) {
        List<int[]> list = new ArrayList<>();
        for (SerializedInteger position : listToDeserialize)
            list.add(position.serialized);
        return list;
    }

    /**
     * This method serialize an array of list of ints array
     *
     * @param arrayOfListsToSerialize is the array of lists not serialized
     * @return the array of list serialized
     */
    public List<SerializedInteger>[] serializeArrayOfListOfInts(List<int[]>[] arrayOfListsToSerialize) {
        List<SerializedInteger>[] listConverted = new ArrayList[arrayOfListsToSerialize.length];
        for (int i = 0; i < arrayOfListsToSerialize.length; i++)
            listConverted[i] = serializeListOfInts(arrayOfListsToSerialize[i]);
        return listConverted;
    }

    /**
     * This method deserialize an array of list of serialized ints array
     *
     * @param arrayOfListsToDeserialize is the array of lists serialized
     * @return the array of list deserialize
     */
    public List<int[]>[] deserializeArrayOfListOfInts(List<SerializedInteger>[] arrayOfListsToDeserialize) {
        List<int[]>[] lists = new ArrayList[arrayOfListsToDeserialize.length];
        for (int i = 0; i < arrayOfListsToDeserialize.length; i++) {
            lists[i] = new ArrayList<>();
            for (int k = 0; k < arrayOfListsToDeserialize[i].size(); k++)
                lists[i].add(arrayOfListsToDeserialize[i].get(k).serialized);
        }
        return lists;
    }

    /**
     * This method serialize an 2D array of ints
     *
     * @param ints is the 2D array to serialize
     * @return the serialized array
     */
    public SerializedInteger[] serialize2DArrayOfInt(int[][] ints) {
        SerializedInteger[] serializedIntegers = new SerializedInteger[ints.length];
        for (int i = 0; i < ints.length; i++)
            serializedIntegers[i] = new SerializedInteger(ints[i]);
        return serializedIntegers;
    }

    /**
     * This method deserialize an 2D array of serializedInteger
     *
     * @param serializedInteger is the 2D array to deserialize
     * @return the deserialize array
     */
    public int[][] deserialize2DArrayOfInts(SerializedInteger[] serializedInteger) {
        int[][] ints = new int[serializedInteger.length][];
        for (int i = 0; i < serializedInteger.length; i++)
            ints[i] = serializedInteger[i].serialized;
        return ints;
    }
}