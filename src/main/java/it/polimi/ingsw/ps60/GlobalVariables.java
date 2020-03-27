package it.polimi.ingsw.ps60;

import it.polimi.ingsw.ps60.serverSide.model.Board;

public class GlobalVariables {

    public enum IdPlayer {PLAYER1, PLAYER2, PLAYER3}

    public enum IdWorker {WORKER1, WORKER2,
        WORKER3, WORKER4,
        WORKER5, WORKER6}

    public enum DivinityCard {
        APOLLO, ARTEMIS, ATHENA(false),
        ATLAS, DEMETER, HEPHAESTUS, MINOTAUR, PAN, PROMETHEUS;

        private boolean bitException;

        private DivinityCard(boolean bitException) {
            this.bitException = bitException;
        }

        private DivinityCard() {}

        public boolean isBitException() {
            return bitException;
        }

        public void setBitException(boolean bitException) {
            this.bitException = bitException;
        }
    }

    public enum Colour {RED, BLUE, GREEN}

    public static Board game;
}