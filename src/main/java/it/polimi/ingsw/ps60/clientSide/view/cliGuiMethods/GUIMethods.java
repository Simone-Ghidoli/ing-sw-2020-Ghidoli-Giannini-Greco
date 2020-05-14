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

            if (nicknameBirthday[1] == null || !new StringRegexValidation(GlobalVariables.StringPatterns.Date.getPattern()).isValid(nicknameBirthday[1]))
                nicknameBirthday[1] = null;
        }

        return nicknameBirthday;
    }

    @Override
    public GlobalVariables.DivinityCard[] cardChoices(final int playerNumber) {
        JButton[] godButtons = new JButton[14];
        final JButton okButton[] = new JButton[]{new JButton("OK")};
        Image image;
        final List<GlobalVariables.DivinityCard> list = new ArrayList<>();
        JPanel panel = new JPanel();

        class Listener implements ActionListener {

            final JButton button;
            final GlobalVariables.DivinityCard divinityCard;

            public Listener(JButton button, GlobalVariables.DivinityCard divinityCard) {
                this.button = button;
                this.divinityCard = divinityCard;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.size() < playerNumber) {
                    list.add(divinityCard);
                    button.setEnabled(false);
                }
                if (list.size() == playerNumber)
                    okButton[0].setEnabled(true);
            }
        }

        class ListenerOkButton implements ActionListener {

            final JOptionPane pane;
            final JButton button;

            public ListenerOkButton(JOptionPane pane, JButton button) {
                this.pane = pane;
                this.button = button;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.size() == playerNumber) {
                    pane.setValue(button);
                }
            }
        }

        okButton[0].setEnabled(false);

        for (int i = 0; i < godButtons.length; i++) {
            godButtons[i] = new JButton();
            godButtons[i].setSize(screenSize.width / 20, screenSize.height / 8);
            image = new ImageIcon(GlobalVariables.DivinityCard.values()[i].getSourcePosition()).getImage().getScaledInstance(godButtons[i].getWidth(), godButtons[i].getHeight(), Image.SCALE_SMOOTH);
            godButtons[i].setIcon(new ImageIcon(image));
            godButtons[i].addActionListener(new Listener(godButtons[i], GlobalVariables.DivinityCard.values()[i]));
        }


        for (JButton jButton : godButtons)
            panel.add(jButton);

        JOptionPane pane = new JOptionPane("Select a divinity card", JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_OPTION, null, okButton, okButton[0]);

        okButton[0].addActionListener(new ListenerOkButton(pane, okButton[0]));
        pane.add(panel);

        JDialog dialog = pane.createDialog("Divinity card");
        pane.selectInitialValue();
        dialog.setVisible(true);
        dialog.dispose();

        Object selectedValue = pane.getValue();

        GlobalVariables.DivinityCard[] divinityCardsToReturn = new GlobalVariables.DivinityCard[playerNumber];

        for (int i = 0; i < playerNumber; i++) {
            divinityCardsToReturn[i] = list.get(i);
        }

        if (selectedValue == null)
            return cardChoices(playerNumber);


        if (okButton[0].equals(selectedValue))
            return divinityCardsToReturn;

        return GlobalVariables.DivinityCard.values();
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
        final JButton okButton[] = new JButton[]{new JButton("OK"), new JButton("Cancel")};
        numberOfWorkers = 0;
        JPanel jPanel=new JPanel();
        final ListContains listContains = new ListContains(impossiblePositions);
        final JOptionPane pane = new JOptionPane("confirm", JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, okButton, okButton[0]);
        pane.setVisible(false);
        final JDialog dialog = pane.createDialog("do you confirm first worker's positions?");
        pane.selectInitialValue();
        dialog.setVisible(false);

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
                    if(numberOfWorkers==2){
                        pane.setVisible(true);
                        dialog.setVisible(true);



                    }
                }
            }
        }
        class ListenerOkButton implements ActionListener{
            final JOptionPane pane;
            final JButton button;

            public ListenerOkButton(JOptionPane pane, JButton button) {
                this.pane = pane;
                this.button = button;
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(numberOfWorkers==2)
                    pane.setValue(button);
            }
        }
        class ListenerCancelButton implements ActionListener{
            final JOptionPane pane;
            final JButton button;

            public ListenerCancelButton(JOptionPane pane, JButton button) {
                this.pane = pane;
                this.button = button;
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {

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
        okButton[0].addActionListener(new ListenerOkButton(pane,okButton[0]));
        okButton[1].addActionListener(new ListenerCancelButton(pane,okButton[1]));
        for (JButton jButton : okButton)
            jPanel.add(jButton);
        pane.add(jPanel);




        dialog.dispose();

        Object selectedValue = pane.getValue();
        if(okButton[0].equals(selectedValue)) {
            santorini.getJtextSouth().setText("Waiting for the others players");
            return choice;
        }
        else if (okButton[1].equals(selectedValue))
            return firstSetWorkers(impossiblePositions);
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
