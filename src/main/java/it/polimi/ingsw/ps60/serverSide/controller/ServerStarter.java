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
import java.util.List;

import static it.polimi.ingsw.ps60.GlobalVariables.input;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

/**
 * This class menage the logic of the game
 */
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
    public void start() {
        sort();
        boolean gameLoaded = loadGame();
        serverThreadBound();
        if (!gameLoaded) {
            System.out.println("Game has't been loaded");
            selectWorkersPositions();
            for (ServerThread serverThread : server.getSocketList()) {
                serverThread.sendBoard(game.getCellToSend());
            }
            selectDivinityCard();
        } else {
            for (int i = 0; i < game.getPlayerMatrix().length; i++) {
                game.getPlayerMatrix()[i].getServerThread().sendAlert("Game loaded from save. "
                        + "You are: " + GlobalVariables.IdPlayer.values()[i].getColour() +
                        ". Your divinity card is: " + game.getPlayerMatrix()[i].getDivinityCard().toString());
            }
        }

        String[][] playersStatus = playersStatus();


        for (int i = 0; i < game.getPlayerMatrix().length; i++) {
            game.getPlayerMatrix()[i].getServerThread().sendStatus(playersStatus, i);
        }

        gameTurn();
    }

    /**
     * This method returns an array of int associated to the divinity cards that has to be sent to the client
     *
     * @return an array of ints associated to the divinity cards ordered by the player number whom this divinity cards belong
     */
    private String[][] playersStatus() {
        String[][] divinityNumbers = new String[game.getPlayerMatrix().length][2];

        for (int j = 0; j < game.getPlayerMatrix().length; j++) {
            for (int i = 0; i < GlobalVariables.DivinityCard.values().length; i++) {
                if (GlobalVariables.DivinityCard.values()[i] == game.getPlayerMatrix()[j].getDivinityCard()) {
                    divinityNumbers[j][0] = String.valueOf(i);
                    break;
                }
            }
            divinityNumbers[j][1] = game.getPlayerMatrix()[j].getNickname();
        }
        return divinityNumbers;
    }

    /**
     * This method check if there is a game saved and if the players can access to that save
     *
     * @return true if the game has benn loaded, false otherwise
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
     *
     * @return the port number
     */
    private int portSelection() {
        String port = null;

        while (port == null) {
            System.out.println("Enter the port number");
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
    private void serverThreadBound() {
        ArrayList<ServerThread> serverThreads = server.getSocketList();
        int j;

        for (int i = 0; i < nicknames.length; i++) {
            j = 0;
            while (!serverThreads.get(i).getPlayerBound().equals(game.getPlayerMatrix()[j].getNickname()))
                j++;
            game.getPlayerMatrix()[j].setServerThread(serverThreads.get(i));
        }
    }

    /**
     * This method menage the successions of turns
     */
    private void gameTurn() {

        while (game.isNotWon()) {
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
        String[][] nicknamesAndBirthdays = server.getNicknameAndBirthday();
        nicknames = new String[nicknamesAndBirthdays.length];


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        String[] youngest = new String[0];
        try {
            for (int i = 0; i < nicknamesAndBirthdays.length; i++) {
                for (String[] nicknamesAndBirthday : nicknamesAndBirthdays) {
                    if (nicknamesAndBirthday != null) {
                        youngest = nicknamesAndBirthday;
                        break;
                    }
                }
                for (int j = i; j < nicknamesAndBirthdays.length; j++) {
                    if (nicknamesAndBirthdays[j] != null)
                        if (simpleDateFormat.parse(youngest[1]).compareTo(simpleDateFormat.parse(nicknamesAndBirthdays[j][1])) < 0) {
                            youngest = nicknamesAndBirthdays[j];
                        }
                }
                nicknames[i] = youngest[0];
                for (int j = 0; j < nicknamesAndBirthdays.length; j++) {
                    if (nicknamesAndBirthdays[j] == youngest) {
                        nicknamesAndBirthdays[j] = null;
                        break;
                    }
                }
                System.out.println("INFO: Player number " + (i + 1) + ": " + youngest[0] + " born in" + youngest[1]);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * In this section the first player select n = player number of divinity cards and
     * all the player, starting for the second one, will chose a divinity card for the n
     * divinity cards picked
     */
    private void selectDivinityCard() {
        int choice = game.getPlayerInGame().get().getServerThread().specialChoice("" +
                "Do you want to play with divinity cards?");

        if (choice == 0) {
            for (int i = 0; i < game.getPlayersNumber(); i++) {
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

        for (int i = 0; i < game.getPlayersNumber(); i++) {
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

        for (int i = 0; i < game.getPlayersNumber(); i++) {
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