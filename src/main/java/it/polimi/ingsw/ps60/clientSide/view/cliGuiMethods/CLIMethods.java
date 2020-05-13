package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.utils.ListContains;
import it.polimi.ingsw.ps60.utils.StringRegexValidation;

import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.ps60.utils.FlushInput.flushInput;

public class CLIMethods implements ViewMethodSelection {

    /* questi sono ascii da 48 a 64
    valore  altezza giocatore
    0       0       no
    1       1       no
    2       2       no
    3       3       no
    4       cupola  no
    5       0       1
    6       1       1
    7       2       1
    8       3       1
    9       0       2
    :       1       2
    ;       2       2
    <       3       2
    =       0       3
    >       1       3
    ?       2       3
    @       3       3
    */
    @Override
    public void printBoard(String board) {

        char[] boardToPrint = board.toCharArray();

        System.out.println("Legend:");
        System.out.println("The number indicates the building level (4 is the dome), the colour indicates the player");
        System.out.println("White: no player");
        System.out.println("Red: 1st player");
        System.out.println("Blue: 2nd player");
        System.out.println("Green: 3rd player\n");

        System.out.println("    1  2  3  4  5");
        System.out.print("    '  '  '  '  '");

        for (int i = 0; i < 25; i++) {
            if (i % 5 == 0)
                System.out.print("\n" + ((i / 5)+1) + "-  ");

            if (boardToPrint[i] < 53) {
                System.out.print(boardToPrint[i]);
            } else if (boardToPrint[i] < 57) {
                System.out.print(GlobalVariables.IdPlayer.PLAYER1.getColour().getString());
                System.out.print((char)(boardToPrint[i] - 5));
            } else if (boardToPrint[i] < 61) {
                System.out.print(GlobalVariables.IdPlayer.PLAYER2.getColour().getString());
                System.out.print((char)(boardToPrint[i] - 9));
            } else {
                System.out.print(GlobalVariables.IdPlayer.PLAYER3.getColour().getString());
                System.out.print((char)(boardToPrint[i] - 13));
            }
            System.out.print(GlobalVariables.Colour.RESET.getString());
            System.out.print("  ");
        }
        System.out.println("\n");
    }

    public int printPossibleMoves(List<int[]>[] moves, int[][]positionsWorkers){
        int choice = 0;

        for (int i = 0; i < moves.length; i++){
            System.out.println("Worker " + (i+1) + " is on " + (positionsWorkers[i][0]+1) + "; " + (positionsWorkers[i][1]+1));
            System.out.println("Available choice for worker " + (i+1));
            for(int j = 0; j < moves[i].size(); j++){
                System.out.println("Press " + (choice + 1) + " in order to move in the cell: [" +
                        (moves[i].get(j)[0] + 1) + ", " + (moves[i].get(j)[1] + 1)+"]");
                choice++;
            }
        }

        return choice;
    }

    @Override
    public int moveChoice(List<int[]>[] moves, int[][] positionsWorkers) {
        System.out.println("Select where to move");
        int i = printPossibleMoves(moves, positionsWorkers);
        flushInput();
        int choice = new Scanner(System.in).nextInt();

        if (choice > i) {
            System.out.println("Wrong input");
            return moveChoice(moves, positionsWorkers);
        } else {
            return choice-1;
        }
    }

    public int printPossibleBuilds(List<int[]> moves){
        int choice = 0;

        for (int[] move : moves) {
            System.out.println("Press " + (choice + 1) + " in order to build on the cell: [" +
                    (move[0] + 1) + ", " + (move[1] + 1)+"]");
            choice++;
        }
        return choice;
    }

    @Override
    public int buildChoice(List<int[]> moves){
        System.out.println("Select where to build");
        int i = printPossibleBuilds(moves);
        flushInput();
        int choice = new Scanner(System.in).nextInt();

        if (choice > i){
            System.out.println("Wrong input");
            return buildChoice(moves);
        }
        else
            return choice-1;
    }

    @Override
    public String[] ipPortChoices() {
        String ip = null;
        String port = null;

        System.out.println("Enter the IP address of server");
        Scanner input = new Scanner(System.in);
        flushInput();

        while (ip == null){
            ip = input.nextLine();
            if (!new StringRegexValidation(GlobalVariables.StringPatterns.IPv4.getPattern()).isValid(ip)) {
                System.out.println("Wrong input");
                ip = null;
            }
        }

        System.out.println("Enter the port number");
        flushInput();

        while (port == null){
            port = input.nextLine();
            if (!new StringRegexValidation(GlobalVariables.StringPatterns.PortNumber.getPattern()).isValid(port)) {
                System.out.println("Wrong input");
                port = null;
            }
        }

        return new String[]{ip, port};
    }

    @Override
    public String[] nicknameBirthdayChoice() {
        String nickname = null;
        String birthday = null;

        Scanner input = new Scanner(System.in);
        flushInput();

        while (nickname == null){
            System.out.println("Enter your nickname");
            nickname = input.nextLine();
            if (!new StringRegexValidation(GlobalVariables.StringPatterns.Nickname.getPattern()).isValid(nickname)) {
                System.out.println("Wrong input");
                nickname = null;
            }
        }
        flushInput();

        while (birthday == null){
            System.out.println("Enter your birthday. [yyyy/MM/gg]");
            birthday = input.nextLine();
            if (!new StringRegexValidation(GlobalVariables.StringPatterns.Date.getPattern()).isValid(birthday)) {
                System.out.println("Wrong input");
                birthday = null;
            }
        }

        return new String[]{nickname, birthday};
    }

    @Override
    public GlobalVariables.DivinityCard[] cardChoices(int playerNumber) {

        GlobalVariables.DivinityCard[] allCards = GlobalVariables.DivinityCard.values();
        GlobalVariables.DivinityCard[] cards = new GlobalVariables.DivinityCard[playerNumber];

        String choice;

        System.out.println("Select " + playerNumber + " cards between:");

        for (int i = 0; i < allCards.length; i++){
            System.out.println((i+1) + "- " + allCards[i].toString());
        }

        Scanner input = new Scanner(System.in);
        StringRegexValidation stringRegexValidation = new StringRegexValidation(GlobalVariables.StringPatterns.DivinityCard.getPattern());

        for (int j = 0; j < playerNumber; j++) {
            System.out.println("Enter card number " + (j+1));

            choice = null;
            flushInput();
            while (choice == null) {
                choice = input.nextLine();
                if (!stringRegexValidation.isValid(choice)){
                    choice = null;
                    System.out.println("Wrong input");
                }
            }
            cards[j] = allCards[Integer.parseInt(choice) - 1];
        }
        return cards;
    }

    @Override
    public GlobalVariables.DivinityCard divinitySelection(GlobalVariables.DivinityCard[] card) {
        String string = null;

        System.out.println("Select one card between:");

        for (int i = 0; i < card.length; i++)
            System.out.println((i+1) + "- " + card[i].toString());

        Scanner input = new Scanner(System.in);
        StringRegexValidation stringRegexValidation = new StringRegexValidation("([1-" + card.length + "]){1}");

        flushInput();
        while (string == null) {
            string = input.nextLine();
            if (!stringRegexValidation.isValid(string)){
                string = null;
                System.out.println("Wrong input");
            }
        }

        return card[Integer.parseInt(string) - 1];
    }

    @Override
    public int[][] firstSetWorkers(List<int[]> impossiblePositions) {
        int[][] choice = new int[2][];
        int[] buffer;
        ListContains listContains = new ListContains(impossiblePositions);

        System.out.println("Legend:");
        System.out.println("0 indicates a empty space, 1 indicates that there is already a worker in it");

        System.out.println("\n    1  2  3  4  5");
        System.out.print("    '  '  '  '  '");

        for (int i = 0; i < 5; i++) {
            System.out.print("\n" + (i + 1) + "-  ");

            for (int j = 0; j < 5; j ++) {

                if (listContains.isContained(new int[]{i, j})) {
                    System.out.print(1);
                } else {
                    System.out.print(0);
                }
                System.out.print("  ");
            }
        }

        Scanner input = new Scanner(System.in);
        flushInput();

        for (int i = 0; i < 2; i++) {
            buffer=new int[2];
            while (choice[i] == null) {
                System.out.println("\nEnter the position of the " + (i + 1) + " worker");
                System.out.println("Enter the x coordinate");

                while (buffer[0] == 0) {
                    buffer[0] = input.nextInt();
                    if (buffer[0] > 5 || buffer[0] < 1) {
                        System.out.println("Wrong input");
                        buffer[0] = 0;
                    }
                }

                buffer[0] = buffer[0] - 1;

                System.out.println("Enter the y coordinate");
                while (buffer[1] == 0) {
                    buffer[1] = input.nextInt();
                    if (buffer[1] > 5 || buffer[1] < 1) {
                        System.out.println("Wrong input");
                        buffer[1] = 0;
                    }
                }

                buffer[1] = buffer[1] - 1;

                if (!listContains.isContained(buffer)) {//todo la stessa cosa di prima
                    choice[i] = buffer;
                    impossiblePositions.add(buffer);
                }
                else {
                    System.out.println("Position already taken");
                    choice[i]=null;
                }
            }
        }
        return choice;
    }

    @Override
    public boolean specialChoices(String string) {
        String choice;

        System.out.println(string + "\n Enter 1 for yes or 0 for no");

        Scanner input = new Scanner(System.in);
        StringRegexValidation stringRegexValidation = new StringRegexValidation(GlobalVariables.StringPatterns.Boolean1True0False.getPattern());

        choice = null;
        flushInput();
        while (choice == null){
            choice = input.nextLine();
            if (!stringRegexValidation.isValid(choice)){
                choice =null;
                System.out.println("Wrong input");
            }
        }

        return choice.equals("1");
    }

    @Override
    public int numberOfPlayers(){
        String numberOfPlayers;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of player. [2 or 3]");
        StringRegexValidation stringRegexValidation = new StringRegexValidation(GlobalVariables.StringPatterns.NumberOfPlayer.getPattern());

        numberOfPlayers = null;
        flushInput();
        while (numberOfPlayers == null){
            numberOfPlayers = scanner.nextLine();
            if (!stringRegexValidation.isValid(numberOfPlayers)){
                numberOfPlayers = null;
                System.out.println("Wrong input");
            }
        }

        return Integer.parseInt(numberOfPlayers);
    }

    @Override
    public void alert(String string) {
        System.out.println(string);
    }
}
