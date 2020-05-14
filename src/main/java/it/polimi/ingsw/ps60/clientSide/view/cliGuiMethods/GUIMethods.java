package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods;

import it.polimi.ingsw.ps60.GlobalVariables;

import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.Swing.MainFrame;
import it.polimi.ingsw.ps60.utils.ListContains;
import it.polimi.ingsw.ps60.utils.StringRegexValidation;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GUIMethods implements ViewMethodSelection {

    private JFrame boardWindow;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private MainFrame santorini;
    private int numberOfWorkers;

    public GUIMethods(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                boardWindow = new JFrame();
                boardWindow.setResizable(false);
                JFrame.setDefaultLookAndFeelDecorated(true);
                boardWindow.setTitle("Santorini");
                boardWindow.setSize(screenSize.width, screenSize.height);
                boardWindow.setLocationRelativeTo(null);
                boardWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Container c = boardWindow.getContentPane();
                santorini = new MainFrame();
                c.add(santorini);
                boardWindow.setVisible(true);
                santorini.getJtextSouth().setText("wait the others players");

            }
        });
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

        String[] ipPort = new String[2];

        while (ipPort[0] == null) {

            ipPort[0] = JOptionPane.showInputDialog("Enter IP server");

            if (ipPort[0] == null || !new StringRegexValidation(GlobalVariables.StringPatterns.IPv4.getPattern()).isValid(ipPort[0]))
                ipPort[0] = null;
        }

        while (ipPort[1] == null) {

            ipPort[1] = JOptionPane.showInputDialog("Enter server port number");

            if (ipPort[1] == null || !new StringRegexValidation(GlobalVariables.StringPatterns.PortNumber.getPattern()).isValid(ipPort[1]))
                ipPort[1] = null;
        }

        return ipPort;
    }

    @Override
    public String[] nicknameBirthdayChoice() {

        String[] nicknameBirthday = new String[2];

        while (nicknameBirthday[0] == null) {

            nicknameBirthday[0] = JOptionPane.showInputDialog("Enter nickname");

            if (nicknameBirthday[0] == null || !new StringRegexValidation(GlobalVariables.StringPatterns.Nickname.getPattern()).isValid(nicknameBirthday[0]))
                nicknameBirthday[0] = null;
        }

        while (nicknameBirthday[1] == null) {

            nicknameBirthday[1] = JOptionPane.showInputDialog("Enter your birthday");

            if (nicknameBirthday[1] == null || !new StringRegexValidation(GlobalVariables.StringPatterns.PortNumber.getPattern()).isValid(nicknameBirthday[1]))
                nicknameBirthday[1] = null;
        }

        return nicknameBirthday;
    }

    @Override
    public GlobalVariables.DivinityCard[] cardChoices(int playerNumber) {
        List<GlobalVariables.DivinityCard> divinityCards = new ArrayList<>();
        GlobalVariables.DivinityCard[] divinityCardsToReturn = new GlobalVariables.DivinityCard[playerNumber];
        for (int x = 0 ; x < playerNumber; x++){
            divinityCards.add(singleDivinitySelection(divinityCards));
            divinityCardsToReturn[x] = divinityCards.get(x);
        }

        return divinityCardsToReturn;
    }

    private GlobalVariables.DivinityCard singleDivinitySelection(List<GlobalVariables.DivinityCard> divinityCards){

        JLabel[] godLabel = new JLabel[14 - divinityCards.size()];
        String[] options = new String[godLabel.length];
        Image image;
        JPanel panel = new JPanel();
        GlobalVariables.DivinityCard[] divinityCards1 = new  GlobalVariables.DivinityCard[options.length];

        for (int i = 0, j = 0; i < godLabel.length; i++, j++){
            godLabel[i] = new JLabel();
            godLabel[i].setSize(screenSize.width/20, screenSize.height/8);
            while (divinityCards.contains(GlobalVariables.DivinityCard.values()[j]))
                j++;
            image  = new ImageIcon(GlobalVariables.DivinityCard.values()[j].getSourcePosition()).getImage().getScaledInstance(godLabel[i].getWidth(), godLabel[i].getHeight(), Image.SCALE_SMOOTH);
            godLabel[i].setIcon(new ImageIcon(image));
            options[i] = GlobalVariables.DivinityCard.values()[j].name();
            divinityCards1[i] = GlobalVariables.DivinityCard.values()[j];
        }

        JOptionPane pane = new JOptionPane("Select a divinity card", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);

        for (JLabel label : godLabel){
            panel.add(label);
        }

        pane.add(panel);
        JDialog dialog = pane.createDialog("Divinity card");
        pane.selectInitialValue();
        dialog.setVisible(true);
        dialog.dispose();

        Object selectedValue = pane.getValue();

        if(selectedValue == null)
            return singleDivinitySelection(divinityCards);

        for(int counter = 0, maxCounter = options.length;
            counter < maxCounter; counter++) {
            if(options[counter].equals(selectedValue))
                return divinityCards1[counter];
        }

        return GlobalVariables.DivinityCard.values()[0];
    }

    @Override
    public GlobalVariables.DivinityCard divinitySelection(GlobalVariables.DivinityCard[] card) {
        JLabel[] godLabel = new JLabel[card.length];
        String[] options = new String[godLabel.length];
        Image image;
        JPanel panel = new JPanel();

        for (int i = 0; i < godLabel.length; i++){
            godLabel[i] = new JLabel();
            godLabel[i].setSize(screenSize.width/20, screenSize.height/8);
            image  = new ImageIcon(card[i].getSourcePosition()).getImage().getScaledInstance(godLabel[i].getWidth(), godLabel[i].getHeight(), Image.SCALE_SMOOTH);
            godLabel[i].setIcon(new ImageIcon(image));
            options[i] = card[i].name();
        }

        JOptionPane pane = new JOptionPane("Select your divinity card", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);

        for (JLabel label : godLabel)
            panel.add(label);

        pane.add(panel);
        JDialog dialog = pane.createDialog("Divinity card selection");
        pane.selectInitialValue();
        dialog.setVisible(true);
        dialog.dispose();

        Object selectedValue = pane.getValue();

        if(selectedValue == null)
            return divinitySelection(card);

        for(int counter = 0, maxCounter = options.length;
            counter < maxCounter; counter++) {
            if(options[counter].equals(selectedValue))
                return card[counter];
        }

        return GlobalVariables.DivinityCard.values()[0];
    }

    @Override
    public int[][] firstSetWorkers(List<int[]> impossiblePositions) {
        int[][] choice = new int[2][];
        numberOfWorkers = 0;
        final ListContains listContains = new ListContains(impossiblePositions);

        class Listener implements ActionListener {
            final JButton button;
            final int[][] choice;

            public Listener(int[][] choice, JButton button) {
                this.button = button;
                this.choice = choice;
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (numberOfWorkers < 2) {
                    button.setEnabled(false);
                    choice[numberOfWorkers] = santorini.getCoordOfButton(button);
                    numberOfWorkers++;
                    button.setText("");
                    ImageIcon imagineWorker = new ImageIcon(GlobalVariables.IdPlayer.values()[0].getSourcePawn());
                    Image scaleImageWorker = imagineWorker.getImage().getScaledInstance(button.getWidth() / 2, button.getHeight() / 2, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(scaleImageWorker));
                    button.setFocusPainted(false);
                }
            }
        }

        santorini.getJtextSouth().setText("Select positions of yours workers. Only free positions are allowed");

        for (int i = 0; i < 25; i++) {

            if (listContains.isContained(santorini.getCoordOfButton(santorini.getButton(i)))) {
                santorini.getButton(i).setText("");
                ImageIcon imagineWorker = new ImageIcon(GlobalVariables.IdPlayer.values()[0].getSourcePawn());
                Image scaleImageWorker = imagineWorker.getImage().getScaledInstance(santorini.getButton(i).getWidth() / 2, santorini.getButton(i).getHeight() / 2, Image.SCALE_SMOOTH);
                santorini.getButton(i).setIcon(new ImageIcon(scaleImageWorker));
            } else
                santorini.getButton(i).addActionListener(new Listener(choice, santorini.getButton(i)));
        }

        while (numberOfWorkers != 2) {
            System.out.println("INFO : Waiting for input");
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        santorini.getJtextSouth().setText("Waiting for the others players");
        return choice;
    }

    @Override
    public boolean specialChoices(String string) {
        Object[] options = {"Yes", "No"};
        int n = JOptionPane.showOptionDialog(null, string, "Special choice", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (n == JOptionPane.YES_OPTION)
            return true;
        else if (n == JOptionPane.NO_OPTION)
            return false;
        else return specialChoices(string);
    }

    @Override
    public int numberOfPlayers(){
        Object[] options = {"2", "3"};
        int n = JOptionPane.showOptionDialog(null, "Select number of players", "Number of players", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (n == JOptionPane.YES_OPTION)
            return 2;
        else if (n == JOptionPane.NO_OPTION)
            return 3;
        else
            return numberOfPlayers();
    }

    @Override
    public void alert(String string) {
         JOptionPane.showMessageDialog(null,
                 string,
                 "ALERT",
                 JOptionPane.WARNING_MESSAGE);
    }
}
