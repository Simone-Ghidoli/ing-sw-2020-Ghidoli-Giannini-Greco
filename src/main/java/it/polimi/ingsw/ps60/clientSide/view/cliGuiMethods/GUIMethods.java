package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.clientSide.view.Swing.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUIMethods implements ViewMethodSelection {

    private JFrame boardWindow;
    private JPanel userInterations;

    public GUIMethods(){
        boardWindow = new JFrame();


        boardWindow.setResizable(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame.setDefaultLookAndFeelDecorated(true);

        boardWindow.setTitle("Santorini");
        boardWindow.setSize(screenSize.width, screenSize.height);
        boardWindow.setLocationRelativeTo(null);
        boardWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        boardWindow.setLayout(new BorderLayout());
        ImageIcon imagine = new ImageIcon("src/resources/board/SantoriniBoard.png");
        Image scaleImage = imagine.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        JPanel grid = new JPanel();

        JLabel board = new JLabel(new ImageIcon(scaleImage));



        board.setLayout(new GridBagLayout());
        board.add(grid);
        grid.setPreferredSize(new Dimension(screenSize.width*12/29,screenSize.height*47/64));

        grid.setLayout(new GridLayout(5,5));
        grid.setOpaque(false);

        JButton[] jButtons = new JButton[25];
        for (int i = 0; i < 25; i++){
            jButtons[i] = new JButton(String.valueOf(i + 1));
            jButtons[i].setContentAreaFilled(false);
            jButtons[i].setOpaque(false);
            grid.add(jButtons[i]);
        }

        boardWindow.add(board, BorderLayout.CENTER);
        boardWindow.pack();
        boardWindow.setVisible(true);


        userInterations = new JPanel();
        board.add(userInterations);
        userInterations.setPreferredSize(new Dimension(screenSize.width*6/30, screenSize.height*1/2));
        userInterations.setVisible(true);
    }

    @Override
    public void printBoard(String board) {

    }

    @Override
    public int moveChoice(List<int[]>[] moves, int[][] positionsWorkers) {
        return 0;
    }

    @Override
    public int buildChoice(List<int[]> moves) {
        return 0;
    }

    @Override
    public String[] ipPortChoices() {
        userInterations.setVisible(true);

//        userInterations.setVisible(false);
        return new String[0];
    }

    @Override
    public String[] nicknameBirthdayChoice() {
        return new String[0];
    }

    @Override
    public GlobalVariables.DivinityCard[] cardChoices(int playerNumber) {
        return new GlobalVariables.DivinityCard[0];
    }

    @Override
    public GlobalVariables.DivinityCard divinitySelection(GlobalVariables.DivinityCard[] card) {
        return card[0];
    }

    @Override
    public int[][] firstSetWorkers(List<int[]> posiiblePositions) {
        return new int[0][];
    }

    @Override
    public boolean specialChoices(String string) {
        return true;
    }

    @Override
    public int numberOfPlayers(){return 0;}

    @Override
    public void alert(String string) {

    }
}
