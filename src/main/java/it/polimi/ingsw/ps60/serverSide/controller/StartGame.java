package it.polimi.ingsw.ps60.serverSide.controller;


import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Player;

public class StartGame {
    public StartGame( String[] nicknames){

        startBoard(nicknames);
        giveDivinityCard();
        selectDivinityCard();
        posizionamentoPedine();

    }

    public void checkFirstPlayer(){

    }
    public void createNickname(){

    }
    public void startBoard(String[] nickname){
        Board board = new Board(nickname);
        GlobalVariables.game=board;
    }
    public void giveDivinityCard(){





    }
    public void selectDivinityCard(){
        for(int j=0; j<GlobalVariables.game.getPlayersNumber(); j++)
            GlobalVariables.game.getPlayerById(GlobalVariables.IdPlayer.getPlayerByInt(j)).setDivinityCard( );


    }
    public void posizionamentoPedine() {



    }
}
