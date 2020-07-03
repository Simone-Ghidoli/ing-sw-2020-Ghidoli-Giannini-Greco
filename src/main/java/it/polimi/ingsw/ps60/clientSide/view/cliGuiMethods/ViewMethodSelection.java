package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods;

import it.polimi.ingsw.ps60.GlobalVariables;

import java.util.List;

/**
 * This are all the methods that allows to play in Gui, CLI or whatever you implements
 */
public interface ViewMethodSelection {
    /*
     *
     * value   high    player  ascii
     * 0       0       no      #48
     * 1       1       no      #49
     * 2       2       no      #50
     * 3       3       no      #51
     * 4       0       dome    #52
     * 5       1       dome    #53
     * 6       2       dome    #54
     * 7       3       dome    #55
     * 8       0       1       #56
     * 9       1       1       #57
     * :       2       1       #58
     * ;       3       1       #59
     * <       0       2       #60
     * =       1       2       #61
     * >       2       2       #62
     * ?       3       2       #63
     * @       0       3       #64
     * A       1       3       #65
     * B       2       3       #66
     * C       3       3       #67
     */

    /**
     * This method will only print the board
     * These are the ascii (from #48 to #67) value in the string provided as parameter
     *
     * @param board the 25 characters represents every position of the board as declared previously
     */
    void printBoard(String board);

    /**
     * This method will return number associated to the move selected
     *
     * @param moves            is the array of lists of possible moves calculated from the server
     * @param positionsWorkers is the position of both workers of the player
     * @return the number associated to the move selected
     */
    int moveChoice(List<int[]>[] moves, int[][] positionsWorkers);

    /**
     * This method will return number associated to the built selected
     *
     * @param moves is the list of possible built calculated from the server
     * @return the number associated to the built selected
     */
    int buildChoice(List<int[]> moves);

    /**
     * This method will return an array of strings with ip and port
     *
     * @return ip in 0 position and port in 1 position
     */
    String[] ipPortChoices();

    /**
     * This method will return an array of strings with nickname and birthday
     *
     * @return nickname in position 0 and birthday in position 1
     */
    String[] nicknameBirthdayChoice();

    /**
     * This method will select n divinity cards in order to be selected by all players
     *
     * @param playerNumber is the number of players in game
     * @return array of n (= player number) divinity cards
     */
    GlobalVariables.DivinityCard[] cardChoices(int playerNumber);

    /**
     * This method will select a card through all cards provided by parameters
     *
     * @param cards is the cards selected by the first player
     * @return a card selected by the player
     */
    GlobalVariables.DivinityCard divinitySelection(GlobalVariables.DivinityCard[] cards);

    /**
     * This method will select the first positions of the workers
     *
     * @param impossiblePositions is the positions already taken by other players
     * @return the positions selected for workers
     */
    int[][] firstSetWorkers(List<int[]> impossiblePositions);

    /**
     * This method will return an answer (true or false) for a question provided
     *
     * @param string is the question asked to the player
     * @return true or false based on the answer
     */
    boolean specialChoices(String string);

    /**
     * This method will return the number of player in order to create a new game
     *
     * @return 2 or 3
     */
    int numberOfPlayers();

    /**
     * This method will print a message
     *
     * @param string is the message that has to be displayed
     */
    void alert(String string);

    /**
     * This method will update the infos of the game
     *
     * @param divinityCards are the divinity card of all players
     * @param turnNumber    is your turn number
     * @param nicknames     are the nicknames of the players
     */
    void status(int[] divinityCards, int turnNumber, String[] nicknames);
}