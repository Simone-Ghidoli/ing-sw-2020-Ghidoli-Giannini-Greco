package it.polimi.ingsw.ps60.utils;

import java.io.IOException;

public class FlushInput {

    /**
     * This method will empty the buffer before an input in order to not read an input written for error by the player
     */
    public static void flushInput() {
        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}