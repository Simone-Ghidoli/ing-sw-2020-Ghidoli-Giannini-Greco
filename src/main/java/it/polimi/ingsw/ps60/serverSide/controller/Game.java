package it.polimi.ingsw.ps60.serverSide.controller;


import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Player;
import it.polimi.ingsw.ps60.serverSide.server.Server;
import it.polimi.ingsw.ps60.serverSide.server.ServerThread;
import it.polimi.ingsw.ps60.utils.circularList.CircularListIterator;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class Game {

    /**
     * This class initialize the board and every turn
     * @param server is the instance of the server of the game
     */
    public Game(@NotNull Server server){
        String[] strings = sort(server.getNickBirth());
        game = new Board(strings);

        ArrayList<ServerThread> serverThreads = server.getSocketList();
        String string;

        for (int i = 0; i < strings.length; i++) {
            string = serverThreads.get(i).nicknameBirthday()[0];
            for (int k = 0; k < strings.length; k++){
                if (string.equals(strings[k])){
                    game.getPlayerById(GlobalVariables.IdPlayer.values()[k]).setServerThread(serverThreads.get(i));
                }
            }
        }

        selectWorkersPositions();
        selectDivinityCard();

        while (game.getBitWinner() == 0){
            game.getPlayerInGame().getNode().getValue().getDivinityStrategy().getTurnController().turn();
        }

    }

    /**
     * This method only sorts the nicknames by the birthday
     * @param nicknamesAndBirthdays is an array with all the nickname of each player associated of its birthday
     * @return returns all the nicknames sorted by the birthday
     */
    private String[] sort(@NotNull String[][] nicknamesAndBirthdays) {
        String[] nicknames = new String[nicknamesAndBirthdays.length];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date dateSelected, date;
        String[] strings;

        try {
            for (int i = 0; i < nicknamesAndBirthdays.length; i++) {
                System.out.println(nicknamesAndBirthdays[i][0] + " " + nicknamesAndBirthdays[i][1]);
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

    /**
     * In this section the first player select n = player number of divinity cards and
     * all the player, starting for the second one, will chose a divinity card for the n
     * divinity cards picked
     */
    public void selectDivinityCard() {

        GlobalVariables.DivinityCard[] divinityCards = game.getPlayerInGame().getNode().getValue().getServerThread().divinity_Choice();

        GlobalVariables.DivinityCard divinityCard;
        CircularListIterator<Player> circularListIterator = new CircularListIterator<>(game.getPlayerInGame().getList());
        circularListIterator.nextNode();
        GlobalVariables.DivinityCard[] divinityCards1;
        int k;

        for (int i = 0; i < divinityCards.length - 1; i++){
            divinityCard = circularListIterator.getNode().getValue().getServerThread().divinity_Selection(divinityCards)[0];

            circularListIterator.getNode().getValue().setDivinityCard(divinityCard);

            divinityCards1 = new GlobalVariables.DivinityCard[divinityCards.length - 1];
            k = 0;

            for (GlobalVariables.DivinityCard card : divinityCards) {
                if (divinityCard != card) {
                    divinityCards1[k] = card;
                    k++;
                }
            }

            divinityCards = divinityCards1;
            circularListIterator.nextNode();
        }

        circularListIterator.getNode().getValue().setDivinityCard(divinityCards[0]);
    }

    /**
     * This method will asks to all the player where to set its workers
     */
    public void selectWorkersPositions(){

        int[][][] positions = new int[game.getPlayersNumber()][][];

        List<int[]> list = new ArrayList<>();

        CircularListIterator<Player> circularListIterator = new CircularListIterator<>(game.getPlayerInGame().getList());

        for (int i = 0; i < game.getPlayersNumber(); i++){
            positions[i] = circularListIterator.getNode().getValue().getServerThread().setWorkers(list);

            for (int j = 0; j < 2; j++)
                list.add(positions[i][j]);

            circularListIterator.nextNode();
        }

        setWorkersPositions(positions);
    }

    public void setWorkersPositions(int[][][] positions) {
        for (int[][] position : positions) {
            for (int i = 0; i < 2; i++) {
                game.getPlayerById(GlobalVariables.IdPlayer.values()[i]).getWorker(i).moveWorker(game.getCellByPosition(position[i]));
            }
        }
    }
}