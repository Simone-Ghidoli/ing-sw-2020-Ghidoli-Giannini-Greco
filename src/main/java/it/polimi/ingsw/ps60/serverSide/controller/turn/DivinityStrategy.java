package it.polimi.ingsw.ps60.serverSide.controller.turn;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.turnController.*;
import it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects.*;
import it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy.*;

import java.util.List;


public class DivinityStrategy {

    private final Strategy strategy;
    private final Effect effect;
    private final String specialChoice;
    private final TurnController turnController;
    private Boolean bitException;

    /**
     * This class implements the right methods based on which divinity card a player has
     * @param divinityCard is the divinity card that a player has
     */
    public DivinityStrategy(GlobalVariables.DivinityCard divinityCard){

        bitException = false;

        switch (divinityCard) {
            case APOLLO:
                strategy = new ApolloStrategy();
                break;
            case ARTEMIS:
                strategy = new ArtemisStrategy();
                break;
            case ATLAS:
                strategy = new AtlasStrategy();
                break;
            case HEPHAESTUS:
                strategy = new HephaestusStrategy();
                break;
            case MINOTAUR:
                strategy = new MinotaurStrategy();
                break;
            case PROMETHEUS:
                strategy = new PrometheusStrategy();
                break;
            case TRITON:
                strategy = new TritonStrategy();
                break;
            case ZEUS:
                strategy = new ZeusStrategy();
                break;
            default:
                strategy = new BaseStrategy();
        }

        switch (divinityCard){
            case APOLLO:
                effect = new ApolloEffect();
                break;
            case ATHENA:
                effect = new AthenaEffect();
                break;
            case ATLAS:
                effect = new AtlasEffect();
                break;
            case CHRONUS:
                effect = new ChronusEffect();
                break;
            case HEPHAESTUS:
                effect = new HephaestusEffect();
                break;
            case MINOTAUR:
                effect = new MinotaurEffect();
                break;
            case PAN:
                effect = new PanEffect();
                break;
            default:
                effect = new BaseEffect();
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
        return strategy.baseBuilding();
    }

    public List<int[]>[] getTurnStrategyMovement() {
        return strategy.baseMovement();
    }

    public void setMovement(int[][] movement){
        effect.move(movement);
    }

    public void setBuilding(int[] building){
        effect.build(building);
    }

    public void setEndTurn(){
        effect.endTurn();
    }

    public String getSpecialChoice() {
        return specialChoice;
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public Boolean isBitException() {
        return bitException;
    }

    public void setBitException(Boolean bitException) {
        this.bitException = bitException;
    }
}