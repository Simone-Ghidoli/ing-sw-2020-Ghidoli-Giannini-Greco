package it.polimi.ingsw.ps60.serverSide.controller.turn;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.turnEffects.*;
import it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy.*;

import java.util.List;


public class DivinityController {

    private Strategy strategy;
    private Turn effect;

    public DivinityController(GlobalVariables.DivinityCard divinityCard){
        switch (divinityCard) {
            case APOLLO:
                strategy = new ApolloStrategy();
                break;
            case ARTEMIS:
                strategy = new ArtemisStrategy();
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
    }

    public List<int[]> getTurnStrategyBuilding() {
        return strategy.baseBuilding();
    }

    public List<int[]>[] getTurnStrategyMovement() {
        return strategy.baseMovement();
    }

    public void setMovemet(int[][] movemet){
        effect.move(movemet);
    }

    public void setBuilding(int[] building){
        effect.build(building);
    }

    public void setEndTurn(){
        effect.endTurn();
    }
}