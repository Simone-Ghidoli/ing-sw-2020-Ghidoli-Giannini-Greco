package it.polimi.ingsw.ps60.utils;

import java.io.Serializable;

public class SerializedInteger implements Serializable {
    public int[] serialized;

    public SerializedInteger(int[] vector) {
        this.serialized = vector;
    }
}
