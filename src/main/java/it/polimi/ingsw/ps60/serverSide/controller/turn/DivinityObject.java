package it.polimi.ingsw.ps60.serverSide.controller.turn;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.controller.turn.turnStrategy.*;
import it.polimi.ingsw.ps60.serverSide.model.Player;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

import java.util.List;


public class DivinityObject {

    private DivinityCard card;

    public DivinityObject(){
        switch (game.getPlayerInGame().getNode().getValue().getDivinityCard()) {
            case APOLLO:
                card = new ApolloStrategy();
                break;
            case ARTEMIS:
                card = new ArtemisStrategy();
                break;
            case HEPHAESTUS:
                card = new HephaestusStrategy();
                break;
            case MINOTAUR:
                card = new MinotaurStrategy();
                break;
            case PROMETHEUS:
                card = new PrometheusStrategy();
                break;
            case TRITON:
                card = new TritonStrategy();
                break;
            case ZEUS:
                card = new ZeusStrategy();
                break;
            default:
                card = new TurnStrategy();
        }
    }

    public DivinityObject(GlobalVariables.DivinityCard divinityCard){
        switch (divinityCard) {
            case APOLLO:
                card = new ApolloStrategy();
                break;
            case ARTEMIS:
                card = new ArtemisStrategy();
                break;
            case HEPHAESTUS:
                card = new HephaestusStrategy();
                break;
            case MINOTAUR:
                card = new MinotaurStrategy();
                break;
            case PROMETHEUS:
                card = new PrometheusStrategy();
                break;
            case TRITON:
                card = new TritonStrategy();
                break;
            case ZEUS:
                card = new ZeusStrategy();
                break;
            default:
                card = new TurnStrategy();
        }
    }

    public DivinityObject(Player player){
        switch (player.getDivinityCard()) {
            case APOLLO:
                card = new ApolloStrategy();
                break;
            case ARTEMIS:
                card = new ArtemisStrategy();
                break;
            case HEPHAESTUS:
                card = new HephaestusStrategy();
                break;
            case MINOTAUR:
                card = new MinotaurStrategy();
                break;
            case PROMETHEUS:
                card = new PrometheusStrategy();
                break;
            case TRITON:
                card = new TritonStrategy();
                break;
            case ZEUS:
                card = new ZeusStrategy();
                break;
            default:
                card = new TurnStrategy();
        }
    }

    public List<int[]> getTurnStrategyBuilding() {
        return card.baseBuilding();
    }

    public List<int[]>[] getTurnStrategyMovement() {
        return card.baseMovement();
    }
}