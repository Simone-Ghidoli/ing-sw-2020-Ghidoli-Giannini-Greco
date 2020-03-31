package it.polimi.ingsw.ps60;

import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.server.ServerThread;

public class GlobalVariables {

    public enum IdPlayer {
        PLAYER1(new IdWorker[] {IdWorker.WORKER1, IdWorker.WORKER2}),
        PLAYER2(new IdWorker[] {IdWorker.WORKER3, IdWorker.WORKER4}),
        PLAYER3(new IdWorker[] {IdWorker.WORKER5, IdWorker.WORKER6});

        private IdWorker[] idWorkers;
        private ServerThread serverThread;

        IdPlayer(IdWorker[] idWorkers) {
            this.idWorkers = idWorkers;
        }

        public IdWorker[] getIdWorkers() {
            return idWorkers;
        }

        public static IdPlayer getPlayerByInt(int i){
            switch(i){
                case 0: return PLAYER1;
                case 1: return PLAYER2;
                case 2: return PLAYER3;
                default: return null;
            }
        }

        public void setServerThread(ServerThread serverThread) {
            this.serverThread = serverThread;
        }
    }


    public enum IdWorker {WORKER1, WORKER2,
        WORKER3, WORKER4,
        WORKER5, WORKER6}

    public enum DivinityCard {
        APOLLO, ARTEMIS, ATHENA(false),
        ATLAS, DEMETER, HEPHAESTUS, MINOTAUR, PAN, PROMETHEUS,
        ZEUS, TRITON, LIMUS(false), CHRONUS;

        private boolean bitException;

        DivinityCard(boolean bitException) {
            this.bitException = bitException;
        }

        DivinityCard() {}

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