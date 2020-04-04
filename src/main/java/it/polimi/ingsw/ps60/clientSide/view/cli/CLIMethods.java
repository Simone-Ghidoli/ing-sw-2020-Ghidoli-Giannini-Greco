package it.polimi.ingsw.ps60.clientSide.view.cli;

import it.polimi.ingsw.ps60.GlobalVariables;

import java.util.List;
import java.util.Scanner;

public class CLIMethods {

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
    public static void printBoard(char[] board) {

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
                System.out.print("\n\n" + ((i / 5)+1) + "-  ");

            if (board[i] < 53) {
                System.out.print(board[i]);
            } else if (board[i] < 57) {
                System.out.print(GlobalVariables.IdPlayer.PLAYER1.getColour().getString());
                System.out.print((char)(board[i] - 5));
            } else if (board[i] < 61) {
                System.out.print(GlobalVariables.IdPlayer.PLAYER2.getColour().getString());
                System.out.print((char)(board[i] - 9));
            } else {
                System.out.print(GlobalVariables.IdPlayer.PLAYER3.getColour().getString());
                System.out.print((char)(board[i] - 13));
            }
            System.out.print(GlobalVariables.Colour.RESET.getString());
            System.out.print("  ");


        }
    }

    public static int printPossibleMoves(List<int[]>[] moves){
        int choice = 0;
        for (int i = 0; i < moves.length; i++){
            System.out.println("Available choice for worker " + (i+1));
            for(int j = 0; j < moves[i].size(); j++){
                System.out.println("Press " + (choice + 1) + " in order to move in the cell: " +
                        (moves[i].get(j)[0] + 1) + "; " + (moves[i].get(j)[1] + 1));
                choice++;
            }
        }

        return choice;
    }

    public static int[] moveChoice(List<int[]>[] moves) {
        System.out.println("Select where to move");
        int i = printPossibleMoves(moves);
        int choice = new Scanner(System.in).nextInt();

        if (choice > i) {
            System.out.println("Wrong input");
            return moveChoice(moves);
        } else {
            int j = 0;
            while (moves[j].size() < choice) {
                choice = choice - moves[j].size();
                j++;
            }
            return moves[j].get(choice - 1);
        }
    }

    public static int printPossibleBuilds(List<int[]> moves){
        int choice = 0;

        for (int i = 0; i < moves.size(); i++){
            System.out.println("Press " + (choice + 1) + " in order to build on the cell: " +
                    (moves.get(i)[0] + 1) + "; " + (moves.get(i)[1] + 1));
            choice++;
        }
        return choice;
    }

    public static int[] buildChoice(List<int[]> moves){
        System.out.println("Select where to build");
        int i = printPossibleBuilds(moves);
        int choice = new Scanner(System.in).nextInt();

        if (choice > i){
            System.out.println("Wrong input");
            return buildChoice(moves);
        }
        else {
            return moves.get(choice-1);
        }
    }
}
