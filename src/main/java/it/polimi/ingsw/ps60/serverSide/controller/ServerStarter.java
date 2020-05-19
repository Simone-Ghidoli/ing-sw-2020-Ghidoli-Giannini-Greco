package it.polimi.ingsw.ps60.serverSide.controller;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.serverSide.model.Board;
import it.polimi.ingsw.ps60.serverSide.model.Player;
import it.polimi.ingsw.ps60.serverSide.server.Server;
import it.polimi.ingsw.ps60.serverSide.server.ServerThread;
import it.polimi.ingsw.ps60.utils.FileAccess;
import it.polimi.ingsw.ps60.utils.StringRegexValidation;
import it.polimi.ingsw.ps60.utils.circularList.CircularListIterator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class ServerStarter {

    private String[] nicknames;
    private final Server server;
    private final FileAccess fileAccess = new FileAccess();

    /**
     * Initialize the server
     */
    public ServerStarter() {
        server = new Server(portSelection());
    }

    /**
     * This method menage the sequence of steps to do in order to start the game
     */
    public void start(){
        sort();
        boolean gameLoaded = loadGame();
        serverThreadBound();
        if (!gameLoaded) {
            selectWorkersPositions();
            selectDivinityCard();
        }
        gameTurn();
    }

    /**
     * This method check if there is a game saved and if the players can access to that save
     */
    private boolean loadGame() {
        game = fileAccess.reader();
        if (game != null && nicknames.length == game.getPlayerMatrix().length) {
            for (int i = 0; i < game.getPlayerMatrix().length; i++) {
                for (int j = 0; j < game.getPlayerMatrix().length; j++) {
                    if (nicknames[i].equals(game.getPlayerMatrix()[j].getNickname()))
                        break;
                    else if (j == game.getPlayerMatrix().length - 1) {
                        game = new Board(nicknames);
                        return false;
                    }
                }
            }
        } else {
            game = new Board(nicknames);
            return false;
        }
        return true;
    }

    /**
     * This method provide a port number checking if that port is free
     * @return the port number
     */
    private int portSelection(){
        Scanner input = new Scanner(System.in);
        String port = null;

        System.out.println("Enter the port number");

        while (port == null){
            port = input.nextLine();
            if (!new StringRegexValidation(GlobalVariables.StringPatterns.PortNumber.getPattern()).isValid(port)) {
                System.out.println("Wrong input");
                port = null;
            }
        }

        return Integer.parseInt(port);
    }

    /**
     * Associate at each Player his serverThread
     */
    private void serverThreadBound(){
        ArrayList<ServerThread> serverThreads = server.getSocketList();
        CircularListIterator<Player> circularListIterator = new CircularListIterator<>(game.getPlayerInGame().getList());

        for (int i = 0; i < nicknames.length; i++) {
            while (!serverThreads.get(i).getPlayerBound().equals(circularListIterator.get().getNickname()))
                circularListIterator.nextNode();
            circularListIterator.get().setServerThread(serverThreads.get(i));
            circularListIterator.nextNode();
        }
    }

    /**
     * This method menage the successions of turns
     */
    private void gameTurn(){

        while (game.getBitWinner() == 0) {
            fileAccess.writer(game);
            game.getPlayerInGame().get().getDivinityStrategy().getTurnController().turn();
        }

        fileAccess.writer(null);

        game.getPlayerWinner().getServerThread().win();
    }

    /**
     * This method only sorts the nicknames by the birthday
     */
    private void sort() {
        String[][] nicknamesAndBirthdays = server.getNickBirth();
        String[] nicknames = new String[nicknamesAndBirthdays.length];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date dateSelected, date;
        String[] strings;

        try {
            for (int i = 0; i < nicknamesAndBirthdays.length; i++) {
                System.out.println("INFO : Client connected number " + i + " : " + nicknamesAndBirthdays[i][0] + " " + nicknamesAndBirthdays[i][1]);
                dateSelected = simpleDateFormat.parse(nicknamesAndBirthdays[i][1]);
                for (int j = i - 1; j > 0; j--){
                    date = simpleDateFormat.parse(nicknamesAndBirthdays[j][1]);
                    if (date.compareTo(dateSelected) < 0){
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

        for (int i = 0; i < nicknames.length; i++)
            System.out.println("INFO : Player number " + i + " : " + nicknames[i]);

        this.nicknames = nicknames;
    }

    /**
     * In this section the first player select n = player number of divinity cards and
     * all the player, starting for the second one, will chose a divinity card for the n
     * divinity cards picked
     */
    private void selectDivinityCard() {
        int choice = game.getPlayerInGame().get().getServerThread().specialChoice("" +
                "Do you want to play with divinity cards?");

        if (choice == 0){
            for (int i = 0; i < game.getPlayersNumber(); i++){
                game.getPlayerMatrix()[i].setDivinityCard(GlobalVariables.DivinityCard.NONE);
            }
            return;
        }

        GlobalVariables.DivinityCard[] divinityCards = game.getPlayerInGame().get().getServerThread().divinityChoice();

        CircularListIterator<Player> circularListIterator = new CircularListIterator<>(game.getPlayerInGame().getList());
        circularListIterator.nextNode();
        GlobalVariables.DivinityCard selected;
        GlobalVariables.DivinityCard[] divinityCards1;
        int k;

        for (int i = 0; i < game.getPlayersNumber(); i++){
            selected = circularListIterator.get().getServerThread().divinitySelection(divinityCards);
            circularListIterator.get().setDivinityCard(selected);

            divinityCards1 = new GlobalVariables.DivinityCard[divinityCards.length - 1];
            k = 0;
            for (GlobalVariables.DivinityCard divinityCard : divinityCards) {
                if (divinityCard != selected) {
                    divinityCards1[k] = divinityCard;
                    k++;
                }
            }
            divinityCards = divinityCards1;
            circularListIterator.nextNode();
        }
    }

    /**
     * This method will asks to all the player where to set its workers and will set it in the board
     */
    private void selectWorkersPositions() {
        int[][][] positions = new int[game.getPlayersNumber()][][];
        List<int[]> list = new ArrayList<>();

        CircularListIterator<Player> circularListIterator = new CircularListIterator<>(game.getPlayerInGame().getList());

        for (int i = 0; i < game.getPlayersNumber(); i++){
            positions[i] = circularListIterator.get().getServerThread().setWorkers(list);

            for (int j = 0; j < 2; j++)
                list.add(positions[i][j]);

            circularListIterator.nextNode();
        }

        for (int j = 0; j < positions.length; j++)
            for (int i = 0; i < 2; i++)
                game.getPlayerMatrix()[j].getWorker(i).moveWorker(game.getCellByPosition(positions[j][i]));
    }
}