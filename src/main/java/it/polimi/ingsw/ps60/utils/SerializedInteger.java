package it.polimi.ingsw.ps60.utils;

import java.io.Serializable;

public class SerializedInteger implements Serializable {
        public final int[] serialized;

    public SerializedInteger(int[] vector) {
        this.serialized = vector;
    }
}
