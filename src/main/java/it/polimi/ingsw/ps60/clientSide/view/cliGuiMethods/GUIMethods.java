package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods;

import it.polimi.ingsw.ps60.GlobalVariables;

import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.swing.MainFrame;
import it.polimi.ingsw.ps60.utils.ListContains;
import it.polimi.ingsw.ps60.utils.StringRegexValidation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all "GUI methods". If the player choose to use the cli the software gonna call these methods
 */
public class GUIMethods implements ViewMethodSelection {

    private final MainFrame santorini = new MainFrame();

    /**
     * This constrictor create the JFrame in order to play in GUI
     */
    public GUIMethods() {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame boardWindow = new JFrame();
                boardWindow.setResizable(false);
                boardWindow.setTitle("Santorini");
                boardWindow.setSize(Toolkit.getDefaultToolkit().getScreenSize());
                boardWindow.setLocationRelativeTo(null);
                boardWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Container c = boardWindow.getContentPane();
                c.add(santorini);
                boardWindow.setVisible(true);
            }
        });
    }

    @Override
    public void printBoard(final String board) {

        santorini.resetButtons();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                char[] boardToPrint = board.toCharArray();
                List<BufferedImage> imageToMerge;
                BufferedImage combined;
                Graphics g;

                for (int i = 0; i < 25; i++) {
                    imageToMerge = new ArrayList<>();


                    switch (boardToPrint[i] % 4) {
                        case 0:
                            break;
                        case 1:
                            imageToMerge.add(santorini.imageFileReader("/board/Buildings/1 floor.png"));
                            break;
                        case 2:
                            imageToMerge.add(santorini.imageFileReader("/board/Buildings/1 floor.png"));
                            imageToMerge.add(santorini.imageFileReader("/board/Buildings/2 floor.png"));
                            break;
                        case 3:
                            imageToMerge.add(santorini.imageFileReader("/board/Buildings/1 floor.png"));
                            imageToMerge.add(santorini.imageFileReader("/board/Buildings/2 floor.png"));
                            imageToMerge.add(santorini.imageFileReader("/board/Buildings/3 floor.png"));
                            break;
                    }

                    switch ((boardToPrint[i] - 48) / 4) {
                        case 0:
                            break;
                        case 1:
                            imageToMerge.add(santorini.imageFileReader("/board/Buildings/Dome.png"));
                            break;
                        case 2:
                            imageToMerge.add(santorini.imageFileReader(GlobalVariables.IdPlayer.PLAYER1.getSourcePawn()));
                            break;
                        case 3:
                            imageToMerge.add(santorini.imageFileReader(GlobalVariables.IdPlayer.PLAYER2.getSourcePawn()));
                            break;
                        case 4:
                            imageToMerge.add(santorini.imageFileReader(GlobalVariables.IdPlayer.PLAYER3.getSourcePawn()));
                            break;
                    }

                    combined = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
                    g = combined.getGraphics();

                    for (BufferedImage bufferedImage : imageToMerge) {
                        g.drawImage(bufferedImage, 0, 0, null);
                    }

                    g.dispose();
                    santorini.getButton(i).setIcon(new ImageIcon(new ImageIcon(combined).getImage().getScaledInstance(santorini.getButton(i).getWidth() / 2, santorini.getButton(i).getHeight() / 2, Image.SCALE_SMOOTH)));
                    santorini.getButton(i).setDisabledIcon(new ImageIcon(new ImageIcon(combined).getImage().getScaledInstance(santorini.getButton(i).getWidth() / 2, santorini.getButton(i).getHeight() / 2, Image.SCALE_SMOOTH)));
                }
            }
        });
    }

    @Override
    public int moveChoice(final List<int[]>[] moves, int[][] positionsWorkers) {

        santorini.resetButtons();

        final int[] choiceToReturn = new int[1];
        final JButton[] buttonWorkers = new JButton[2];

        class ReturnListener implements ActionListener {
            final int numberToReturn;

            public ReturnListener(int numberToReturn) {

                this.numberToReturn = numberToReturn;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (choiceToReturn) {
                    choiceToReturn[0] = numberToReturn;
                    choiceToReturn.notifyAll();
                }
            }
        }

        class Listener implements ActionListener {
            final int workerNumber;

            public Listener(int workerNumber) {
                this.workerNumber = workerNumber;
            }

            @Override
            public void actionPerformed(ActionEvent e) {

                buttonWorkers[0].setEnabled(false);
                buttonWorkers[1].setEnabled(false);
                JButton button;
                for (int i = 0; i < moves[workerNumber].size(); i++) {
                    button = santorini.getButton(moves[workerNumber].get(i)[0] * 5 + moves[workerNumber].get(i)[1]);
                    button.addActionListener(new ReturnListener(workerNumber * moves[0].size() + i));
                    button.setEnabled(true);
                }
            }
        }

        for (int i = 0; i < 2; i++) {
            buttonWorkers[i] = santorini.getButton(positionsWorkers[i][0] * 5 + positionsWorkers[i][1]);
            if (moves[i].size() > 0) {
                buttonWorkers[i].addActionListener(new Listener(i));
                buttonWorkers[i].setEnabled(true);
            }
        }

        synchronized (choiceToReturn) {
            try {
                choiceToReturn.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        santorini.resetButtons();

        if (confirmOrRetry())
            return choiceToReturn[0];
        else
            return moveChoice(moves, positionsWorkers);
    }

    @Override
    public int buildChoice(List<int[]> moves) {

        santorini.resetButtons();

        final int[] choiceToReturn = new int[1];

        class Listener implements ActionListener {

            final int numberToReturn;

            public Listener(int numberToReturn) {
                this.numberToReturn = numberToReturn;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (choiceToReturn) {
                    choiceToReturn[0] = numberToReturn;
                    choiceToReturn.notifyAll();
                }
            }
        }

        for (int i = 0; i < 25; i++)
            santorini.getButton(i).setEnabled(false);

        JButton button;
        for (int i = 0; i < moves.size(); i++) {
            button = santorini.getButton(moves.get(i)[0] * 5 + moves.get(i)[1]);
            button.addActionListener(new Listener(i));
            button.setEnabled(true);
        }

        synchronized (choiceToReturn) {
            try {
                choiceToReturn.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        santorini.resetButtons();

        if (confirmOrRetry())
            return choiceToReturn[0];
        else
            return buildChoice(moves);
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

            JTextField dayText = new JTextField(3);
            JTextField monthText = new JTextField(3);
            JTextField yearText = new JTextField(4);
            JPanel message = new JPanel();
            message.add(dayText);
            message.add(new JLabel("/"));
            message.add(monthText);
            message.add(new JLabel("/"));
            message.add(yearText);

            int result = JOptionPane.showConfirmDialog(null, message, "Enter birthday", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                nicknameBirthday[1] = yearText.getText() + "/" + monthText.getText() + "/" + dayText.getText();

                if (!new StringRegexValidation(GlobalVariables.StringPatterns.Date.getPattern()).isValid(nicknameBirthday[1]))
                    nicknameBirthday[1] = null;
            }
        }

        return nicknameBirthday;
    }

    @Override
    public GlobalVariables.DivinityCard[] cardChoices(final int playerNumber) {
        final JButton[] godButtons = new JButton[14];
        final List<GlobalVariables.DivinityCard> list = new ArrayList<>();
        final GlobalVariables.DivinityCard[] divinityCardsToReturn;

        for (int i = 0; i < godButtons.length; i++) {
            godButtons[i] = new JButton();
            godButtons[i].setSize(santorini.getScreenSize().width / 20, santorini.getScreenSize().height / 8);
            godButtons[i].setIcon(new ImageIcon(new ImageIcon(GlobalVariables.DivinityCard.values()[i].getSourcePosition()).getImage().getScaledInstance(godButtons[i].getWidth(), godButtons[i].getHeight(), Image.SCALE_SMOOTH)));
        }

        final JOptionPane pane = new JOptionPane("", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, godButtons, godButtons[0]);
        final JDialog dialog = pane.createDialog("Select " + playerNumber + " divinity cards");

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
                    dialog.setVisible(false);
            }
        }

        for (int i = 0; i < godButtons.length; i++)
            godButtons[i].addActionListener(new Listener(godButtons[i], GlobalVariables.DivinityCard.values()[i]));

        dialog.setVisible(true);
        dialog.dispose();

        if (pane.getValue() == null)
            return cardChoices(playerNumber);

        divinityCardsToReturn = new GlobalVariables.DivinityCard[playerNumber];

        for (int i = 0; i < playerNumber; i++)
            divinityCardsToReturn[i] = list.get(i);

        return divinityCardsToReturn;
    }

    @Override
    public GlobalVariables.DivinityCard divinitySelection(GlobalVariables.DivinityCard[] card) {
        final JButton[] godButtons = new JButton[card.length];
        final GlobalVariables.DivinityCard[] divinityCardToReturn = new GlobalVariables.DivinityCard[1];

        for (int i = 0; i < godButtons.length; i++) {
            godButtons[i] = new JButton();
            godButtons[i].setSize(santorini.getScreenSize().width / 20, santorini.getScreenSize().height / 8);
            godButtons[i].setIcon(new ImageIcon(new ImageIcon(card[i].getSourcePosition()).getImage().getScaledInstance(godButtons[i].getWidth(), godButtons[i].getHeight(), Image.SCALE_SMOOTH)));
        }

        final JOptionPane pane = new JOptionPane("", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, godButtons, godButtons[0]);
        final JDialog dialog = pane.createDialog("Select your divinity card");

        class Listener implements ActionListener {
            final GlobalVariables.DivinityCard divinityCard;

            public Listener(GlobalVariables.DivinityCard divinityCard) {
                this.divinityCard = divinityCard;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                divinityCardToReturn[0] = divinityCard;
                dialog.setVisible(false);
            }
        }

        for (int i = 0; i < godButtons.length; i++)
            godButtons[i].addActionListener(new Listener(card[i]));

        dialog.setVisible(true);
        dialog.dispose();

        Object selectedValue = pane.getValue();

        if (selectedValue == null)
            return divinitySelection(card);

        return divinityCardToReturn[0];
    }

    @Override
    public int[][] firstSetWorkers(List<int[]> impossiblePositions) {

        santorini.resetButtons();

        alert("Select positions of yours workers. Only free positions are allowed");

        final int[][] workersPositions = new int[2][];
        final ListContains listContains = new ListContains(impossiblePositions);

        class Listener implements ActionListener {

            final JButton jButton;

            public Listener(JButton jButton) {
                this.jButton = jButton;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (workersPositions) {
                    for (int i = 0; i < 2; i++) {
                        if (workersPositions[i] == null) {
                            jButton.setEnabled(false);
                            workersPositions[i] = santorini.getButtonCoords(jButton);
                            jButton.setIcon(new ImageIcon(new ImageIcon(GlobalVariables.IdPlayer.values()[0].getSourcePawn()).getImage().getScaledInstance(jButton.getWidth() / 2, jButton.getHeight() / 2, Image.SCALE_SMOOTH)));
                            if (i == 0)
                                break;
                            else
                                workersPositions.notifyAll();
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 25; i++) {
            if (listContains.isContained(santorini.getButtonCoords(santorini.getButton(i)))) {
                ImageIcon imagineWorker = new ImageIcon(GlobalVariables.IdPlayer.values()[0].getSourcePawn());
                Image scaleImageWorker = imagineWorker.getImage().getScaledInstance(santorini.getButton(i).getWidth() / 2, santorini.getButton(i).getHeight() / 2, Image.SCALE_SMOOTH);
                santorini.getButton(i).setIcon(new ImageIcon(scaleImageWorker));
                santorini.getButton(i).setEnabled(false);
            } else {
                santorini.getButton(i).setIcon(null);
                santorini.getButton(i).addActionListener(new Listener(santorini.getButton(i)));
                santorini.getButton(i).setEnabled(true);
            }
        }

        synchronized (workersPositions) {
            try {
                workersPositions.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        santorini.resetButtons();

        if (confirmOrRetry())
            return workersPositions;
        else
            return firstSetWorkers(impossiblePositions);

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
    public int numberOfPlayers() {
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
        final Object[] option = new Object[]{"OK"};
        final JOptionPane pane = new JOptionPane(string, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, option, option[0]);
        final JDialog dialog = pane.createDialog("INFO:");

        dialog.setVisible(true);
        dialog.dispose();
    }

    @Override
    public void status(int[] divinityCards, int turnNumber) {
        santorini.setDivinityCardImage(divinityCards, turnNumber);
    }

    /**
     * This method asks to the user if he want to confirm what he choose or want to insert the input again
     *
     * @return true if he confirms, false otherwise
     */
    private boolean confirmOrRetry() {
        final Object[] options = new Object[]{"YES", "NO"};
        final JOptionPane pane = new JOptionPane("", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
        final JDialog dialog = pane.createDialog("Confirm selection?");

        dialog.setVisible(true);
        dialog.dispose();

        return pane.getValue() != null && pane.getValue() != options[1];
    }
}