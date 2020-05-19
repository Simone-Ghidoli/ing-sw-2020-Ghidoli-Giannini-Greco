package it.polimi.ingsw.ps60.serverSide.controller.turn;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.turnController.*;
import it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects.*;
import it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy.*;

import java.io.Serializable;
import java.util.List;


public class DivinityStrategy {

    private final TurnStrategy turnStrategy;
    private final TurnEffect turnEffect;
    private final String specialChoice;
    private final TurnController turnController;
    private boolean bitException;

    /**
     * This class implements the right methods based on which divinity card a player has
     * @param divinityCard is the divinity card that a player has
     */
    public DivinityStrategy(GlobalVariables.DivinityCard divinityCard){

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

        switch (divinityCard){
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

        switch (divinityCard){
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
                specialChoice = "Do you want to build also before moving?\nWith which worker?";
                break;
            case POSEIDON:
                specialChoice = "Do you want to build again with your unmoved worker?";
                break;
            default:
                specialChoice = null;
        }

        switch (divinityCard){
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

    public List<int[]> getTurnStrategyBuilding() {
        return turnStrategy.baseBuilding();
    }

    public List<int[]>[] getTurnStrategyMovement() {
        return turnStrategy.baseMovement();
    }

    public void setMovement(int[][] movement){
        turnEffect.move(movement);
    }

    public void setBuilding(int[] building){
        turnEffect.build(building);
    }

    public void setEndTurn(){
        turnEffect.endTurn();
    }

    public String getSpecialChoice() {
        return specialChoice;
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public boolean isBitException() {
        return bitException;
    }

    public void setBitException(boolean bitException) {
        this.bitException = bitException;
    }
}