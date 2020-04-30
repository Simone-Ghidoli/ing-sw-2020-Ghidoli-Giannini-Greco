package it.polimi.ingsw.ps60.serverSide.controller.turn;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.turnController.*;
import it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects.*;
import it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy.*;

import java.util.List;


public class DivinityStrategy {

    private final Strategy strategy;
    private final Turn effect;
    private final String specialChoice;
    private final TurnController turnController;

    public DivinityStrategy(GlobalVariables.DivinityCard divinityCard){

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
                strategy = new TurnStrategy();
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
                effect = new TurnEffect();
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
            default:
                specialChoice = null;
        }

        switch (divinityCard){
            case ATLAS:
            case HEPHAESTUS:
                turnController = new AtlasHephaestusController();
                break;
            case DEMETER:
                turnController = new DemeterController();
                break;
            case PROMETHEUS:
                turnController = new PrometheusController();
                break;
            case HESTIA:
                turnController = new HestiaController();
                break;
            default:
                turnController = new TurnController();
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
}