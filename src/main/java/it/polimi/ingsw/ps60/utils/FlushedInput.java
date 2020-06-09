package it.polimi.ingsw.ps60.utils;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class is an upgrade of the classic scanner input
 */
public class FlushedInput {

    private final Scanner input = new Scanner(System.in);

    /**
     * This method will empty the buffer before an input in order to not read an input written for error by the player
     */
    private void flushInput() {
        try {
            int i = System.in.read(new byte[System.in.available()]);
            if (i > 0)
                System.out.println(i + "chars are been ignored");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will provide a string input
     *
     * @return is the string provided by input
     */
    public String nextLine() {
        flushInput();
        return input.nextLine();
    }

    /**
     * This method will provide a int input given a upper and lower limit
     *
     * @param upperLimit is the upper limit
     * @param lowerLimit is the lower limit
     * @return is the int read from input
     */
    public int nextInt(int upperLimit, int lowerLimit) {
        if ((upperLimit <= lowerLimit)) throw new AssertionError();
        Integer integer = null;

        while (integer == null) {
            try {
                integer = Integer.parseInt(input.nextLine());
                if (integer > upperLimit || integer < lowerLimit) {
                    System.out.println("Wrong input");
                    integer = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Wrong input");
            }
        }
        return integer;
    }
}