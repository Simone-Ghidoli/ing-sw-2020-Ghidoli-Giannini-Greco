package it.polimi.ingsw.ps60;

import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.server.ServerThread;

public class GlobalVariables {

    public enum IdPlayer {
        PLAYER1(new IdWorker[]{IdWorker.WORKER1, IdWorker.WORKER2}, Colour.RED),
        PLAYER2(new IdWorker[]{IdWorker.WORKER3, IdWorker.WORKER4}, Colour.BLUE),
        PLAYER3(new IdWorker[]{IdWorker.WORKER5, IdWorker.WORKER6}, Colour.GREEN);

        private IdWorker[] idWorkers;
        private Colour colour;
        //private ServerThread serverThread;

        IdPlayer(IdWorker[] idWorkers, Colour colour) {
            this.idWorkers = idWorkers;
            this.colour = colour;
        }

        public IdWorker[] getIdWorkers() {
            return idWorkers;
        }

        public static IdPlayer getPlayerByInt(int i) {
            switch (i) {
                case 0:
                    return PLAYER1;
                case 1:
                    return PLAYER2;
                case 2:
                    return PLAYER3;
                default:
                    return null;
            }
        }

        //public void setServerThread(ServerThread serverThread) {
        //    this.serverThread = serverThread;
        //}

        public Colour getColour() {
            return colour;
        }
    }


    public enum IdWorker {
        WORKER1, WORKER2,
        WORKER3, WORKER4,
        WORKER5, WORKER6
    }

    public enum DivinityCard {
        APOLLO, ARTEMIS, ATHENA(false),
        ATLAS, DEMETER, HEPHAESTUS, MINOTAUR, PAN, PROMETHEUS,
        ZEUS, TRITON, CHRONUS;

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
}