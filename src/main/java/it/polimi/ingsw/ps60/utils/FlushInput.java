package it.polimi.ingsw.ps60.utils;

import java.io.IOException;

public class FlushInput {
    public static void flushInput() {
        try {
            System.in.read(new byte[System.in.available()]);
        }
        catch(IOException e_0){}
    }
}
