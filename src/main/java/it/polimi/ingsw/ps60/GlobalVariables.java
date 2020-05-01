package it.polimi.ingsw.ps60;

import it.polimi.ingsw.ps60.serverSide.model.Board;
import java.io.Serializable;

public class GlobalVariables {

    public enum IdPlayer {
        PLAYER1(new IdWorker[]{IdWorker.WORKER1, IdWorker.WORKER2}, Colour.RED),
        PLAYER2(new IdWorker[]{IdWorker.WORKER3, IdWorker.WORKER4}, Colour.BLUE),
        PLAYER3(new IdWorker[]{IdWorker.WORKER5, IdWorker.WORKER6}, Colour.GREEN);

        private IdWorker[] idWorkers;
        private Colour colour;

        IdPlayer(IdWorker[] idWorkers, Colour colour) {
            this.idWorkers = idWorkers;
            this.colour = colour;
        }

        public IdWorker[] getIdWorkers() {
            return idWorkers;
        }

        public Colour getColour() {
            return colour;
        }
    }


    public enum IdWorker {
        WORKER1, WORKER2,
        WORKER3, WORKER4,
        WORKER5, WORKER6
    }

    public enum DivinityCard implements Serializable {
        APOLLO, ARTEMIS, ATHENA(false),
        ATLAS, DEMETER, HEPHAESTUS, MINOTAUR, PAN, PROMETHEUS,
        ZEUS, TRITON, CHRONUS, HESTIA, POSEIDON;

        private boolean bitException;

        DivinityCard(boolean bitException) {
            this.bitException = bitException;
        }

        DivinityCard(){}

        public boolean isBitException() {
            return bitException;
        }

        public void setBitException(boolean bitException) {
            this.bitException = bitException;
        }
    }

    public enum Colour {
        RED("\033[0;31m"), BLUE("\033[0;34m"), GREEN("\033[0;32m"), RESET("\033[0m");

        private final String string;

        Colour(String code) {
            this.string = code;
        }

        public String getString() {
            return string;
        }
    }

    public static Board game;

    public enum StringPatterns{
        IPv4("((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[1-9])\\.)((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])\\.){2}" +
                "(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])"),

        PortNumber("(4915[2-9]|491[6-9][0-9]|49[2-9][0-9][0-9]|5[0-9][0-9][0-9][0-9]|6[0-4][0-9][0-9][0-9]|" +
                "65[0-4][0-9][0-9]|655[0-2][0-9]|6553[0-5])"),

        Nickname("([a-z|A-Z|0-9]){3,10}+"),

        /**
         * Trenta giorni ha novembre
         * con april, giugno e settembre.
         * Di ventotto ce n'è uno,
         * tutti gli altri ne han trentuno
         */
        Date("(19[0-9][0-9]|20[0-1][0-9]|2020])/(((01|03|05|07|08|10|12)/(0[1-9]|[1-2][0-9]|3[0-1]))|((04|06|09|11)/(0[1-9]|[1-2][0-9]|30))|((02)/(0[1-9]|1[0-9]|2[0-9])))");

        private final String pattern;

        StringPatterns(String pattern){
            this.pattern = pattern;
        }

        public String getPattern() {
            return pattern;
        }
    }
}