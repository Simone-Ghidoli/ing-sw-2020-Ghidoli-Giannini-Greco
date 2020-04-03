package it.polimi.ingsw.ps60.clientSide.view.cli;

import it.polimi.ingsw.ps60.GlobalVariables;

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
        GlobalVariables.Colour colour;

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
}
