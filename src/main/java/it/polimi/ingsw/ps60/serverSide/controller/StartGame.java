package it.polimi.ingsw.ps60.serverSide.controller;


import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.server.Server;
import it.polimi.ingsw.ps60.serverSide.server.ServerThread;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class StartGame {

    public StartGame(Server server){
        String[] strings = sort(server.getNick_birth());
        game = new Board(strings);

        ArrayList<ServerThread> serverThreads = server.getsocketlist();
        String string;

        for (int i = 0; i < strings.length; i++) {
            string = serverThreads.get(i).nickname_birthday()[0];
            for (int k = 0; k < strings.length; k++){
                if (string.equals(strings[k])){
                    game.getPlayerById(GlobalVariables.IdPlayer.values()[k]).setServerThread(serverThreads.get(i));
                }
            }
        }

    }

    private String[] sort(String[][] nicknamesAndBirthdays) {
        String[] nicknames = new String[nicknamesAndBirthdays.length];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date dateSelected, date;
        String[] strings;

        try {
            for (int i = 0; i < nicknamesAndBirthdays.length; i++) {
                dateSelected = simpleDateFormat.parse(nicknamesAndBirthdays[i][1]);
                for (int j = i - 1; j >= 0; j--){
                    date = simpleDateFormat.parse(nicknamesAndBirthdays[j][1]);
                    if (date.compareTo(dateSelected) > 0){
                        strings = nicknamesAndBirthdays[i];
                        nicknamesAndBirthdays[i] = nicknamesAndBirthdays[j];
                        nicknamesAndBirthdays[j] = strings;
                    }
                    else
                        break;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int k = 0; k < nicknames.length; k++)
            nicknames[k] = nicknamesAndBirthdays[k][0];

        return nicknames;
    }

    public void selectDivinityCard (GlobalVariables.DivinityCard[] divinityCards) {
        for (int j = 0; j < divinityCards.length; j++)
            game.getPlayerById(GlobalVariables.IdPlayer.getPlayerByInt(j)).setDivinityCard(divinityCards[j]);
    }

    public void setWorkersPositions(int[][][] positions) {
        for (int j = 0; j < positions.length; j++) {
            for (int i = 0; i < 2; i++) {
                game.getPlayerById(GlobalVariables.IdPlayer.getPlayerByInt(j)).getWorker(i).moveWorker(game.getCellByPosition(positions[j][i]));
            }
        }
    }
}