package it.polimi.ingsw.ps60.serverSide.controller.turn;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.turnController.*;
import it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects.*;
import it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy.*;

import java.util.List;


public class DivinityStrategy {

    private final TurnStrategy turnStrategy;
    private final TurnEffect turnEffect;
    private final String specialChoice;
    private final TurnController turnController;
    private boolean bitException;

    /**
     * This class implements the right methods based on which divinity card a player has
     *
     * @param divinityCard is the divinity card that a player has
     */
    public DivinityStrategy(GlobalVariables.DivinityCard divinityCard) {

        bitException = false;

        switch (divinityCard) {
            case APOLLO:
                turnStrategy = new ApolloTurnStrategy();
                break;
            case ARTEMIS:
                turnStrategy = new ArtemisTurnStrategy();
                break;
            case ATLAS:
                turnStrategy = new AtlasTurnStrategy();
                break;
            case HEPHAESTUS:
                turnStrategy = new HephaestusTurnStrategy();
                break;
            case MINOTAUR:
                turnStrategy = new MinotaurTurnStrategy();
                break;
            case PROMETHEUS:
                turnStrategy = new PrometheusTurnStrategy();
                break;
            case TRITON:
                turnStrategy = new TritonTurnStrategy();
                break;
            case ZEUS:
                turnStrategy = new ZeusTurnStrategy();
                break;
            default:
                turnStrategy = new BaseTurnStrategy();
        }

        switch (divinityCard) {
            case APOLLO:
                turnEffect = new ApolloTurnEffect();
                break;
            case ATHENA:
                turnEffect = new AthenaTurnEffect();
                break;
            case ATLAS:
                turnEffect = new AtlasTurnEffect();
                break;
            case CHRONUS:
                turnEffect = new ChronusTurnEffect();
                break;
            case HEPHAESTUS:
                turnEffect = new HephaestusTurnEffect();
                break;
            case MINOTAUR:
                turnEffect = new MinotaurTurnEffect();
                break;
            case PAN:
                turnEffect = new PanTurnEffect();
                break;
            default:
                turnEffect = new BaseTurnEffect();
                break;
        }

        switch (divinityCard) {
            case ATLAS:
                specialChoice = "Do you want to build a dome on it?";
                break;
            case DEMETER:
            case HESTIA:
                specialChoice = "Do you want to build again?";
                break;
            case HEPHAESTUS:
                specialChoice = "Do you want to build again on it?";
                break;
            case PROMETHEUS:
                specialChoice = "Do you want to build also before moving?\nSelect where to build";
                break;
            case POSEIDON:
                specialChoice = "Do you want to build again with your unmoved worker?";
                break;
            default:
                specialChoice = null;
        }

        switch (divinityCard) {
            case ATLAS:
            case HEPHAESTUS:
                turnController = new AtlasHephaestusTurnController();
                break;
            case DEMETER:
                turnController = new DemeterTurnController();
                break;
            case PROMETHEUS:
                turnController = new PrometheusTurnController();
                break;
            case HESTIA:
                turnController = new HestiaTurnController();
                break;
            case POSEIDON:
                turnController = new PoseidonTurnController();
                break;
            default:
                turnController = new BaseTurnController();
        }


    }

    /**
     * This method return the strategy of building associated to a specific divinity card
     *
     * @return all the possible building of a player calculated from the server
     */
    public List<int[]> getTurnStrategyBuilding() {
        return turnStrategy.baseBuilding();
    }

    /**
     * This method return the strategy of movement associated to a specific divinity card
     *
     * @return all the possible movements of a player calculated from the server
     */
    public List<int[]>[] getTurnStrategyMovement() {
        return turnStrategy.baseMovement();
    }

    /**
     * Using the specific divinity effect change the model with a movement
     *
     * @param movement is where and with which worker the player wants to move
     */
    public void setMovement(int[][] movement) {
        turnEffect.move(movement);
    }

    /**
     * Using the specific divinity effect change the model with a build
     *
     * @param building is where the player wants to build
     */
    public void setBuilding(int[] building) {
        turnEffect.build(building);
    }

    /**
     * Using the specific divinity effect a player ends his turn
     */
    public void setEndTurn() {
        turnEffect.endTurn();
    }

    /**
     * Returns the string of special choice associated to the divinity card
     *
     * @return the string that a player will read in case of a special choice
     */
    public String getSpecialChoice() {
        return specialChoice;
    }

    /**
     * This method return the turn controller that has all the sequence of the turn of a specific divinity card
     *
     * @return the turn controller associated to that divinity card
     */
    public TurnController getTurnController() {
        return turnController;
    }

    /**
     * This method return true or false if the divinity card trows an exception in the turn of the other divinity cards
     *
     * @return the bit exception of the divinity card
     */
    public boolean isBitException() {
        return bitException;
    }

    /**
     * This method set if the divinity card trows an exception in the turn of the other divinity cards
     *
     * @param bitException the bit exception of the divinity card
     */
    public void setBitException(boolean bitException) {
        this.bitException = bitException;
    }
}