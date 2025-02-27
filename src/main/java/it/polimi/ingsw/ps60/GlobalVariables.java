package it.polimi.ingsw.ps60;

import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.utils.FlushedInput;

import java.awt.*;
import java.io.Serializable;

/**
 * This class stores all the global variables
 */
public class GlobalVariables {

    public static final FlushedInput input = new FlushedInput();

    /**
     * These are all the static properties associated to a given player
     */
    public enum IdPlayer {
        PLAYER1(Colour.RED, "Red "),
        PLAYER2(Colour.BLUE, "Blue "),
        PLAYER3(Colour.GREEN, "Green ");

        private final Colour colour;
        private final String sourcePawn;

        IdPlayer(Colour colour, String sourcePawn) {
            this.colour = colour;
            this.sourcePawn = "/board/" + sourcePawn + "pawn.png";
        }

        public Colour getColour() {
            return colour;
        }

        public String getSourcePawn() {
            return sourcePawn;
        }
    }

    /**
     * These are all the divinity cards implemented in the game with the source for the images
     */
    public enum DivinityCard implements Serializable {
        APOLLO("01"), ARTEMIS("02"), ATHENA("03"),
        ATLAS("04"), DEMETER("05"), HEPHAESTUS("06"), MINOTAUR("08"),
        PAN("09"), PROMETHEUS("10"), ZEUS("30"), TRITON("29"),
        CHRONUS("16"), HESTIA("21"), POSEIDON("27"), NONE("00");

        private final String sourcePosition;

        DivinityCard(String sourcePosition) {
            this.sourcePosition = "/godCards/" + sourcePosition + ".png";
        }

        public String getSourcePosition() {
            return sourcePosition;
        }
    }

    /**
     * These are all the colour used in the game
     */
    public enum Colour {
        RED("\033[0;31m", Color.RED), BLUE("\033[0;34m", Color.BLUE), GREEN("\033[0;32m", Color.GREEN),
        YELLOW("\033[0;33m", Color.YELLOW), RESET("\033[0m", Color.BLACK);

        private final String string;
        private final Color color;

        Colour(String code, Color color) {
            this.string = code;
            this.color = color;
        }

        public String getString() {
            return string;
        }

        public Color getColor() {
            return color;
        }
    }

    public static Board game;

    /**
     * These are all the pattern for a string that has to be checked
     */
    public enum StringPatterns {
        IPv4("((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[1-9])\\.)" +
                "((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])\\.){2}" +
                "(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])"),

        PortNumber("(4915[2-9]|491[6-9][0-9]|49[2-9][0-9][0-9]|5[0-9][0-9][0-9][0-9]|6[0-4][0-9][0-9][0-9]|" +
                "65[0-4][0-9][0-9]|655[0-2][0-9]|6553[0-5])"),

        Nickname("([a-z|A-Z|0-9]){3,10}+"),

        NumberOfPlayer("[2-3]"),

        DivinityCard("([1-9]|1[0-4])"),

        Boolean1True0False("([0-1])"),

        Date("(19[0-9][0-9]|20[0-1][0-9]|2020])/(((01|03|05|07|08|10|12)/" +
                "(0[1-9]|[1-2][0-9]|3[0-1]))|((04|06|09|11)/" +
                "(0[1-9]|[1-2][0-9]|30))|((02)/(0[1-9]|1[0-9]|2[0-9])))");

        private final String pattern;

        StringPatterns(String pattern) {
            this.pattern = pattern;
        }

        public String getPattern() {
            return pattern;
        }
    }
}