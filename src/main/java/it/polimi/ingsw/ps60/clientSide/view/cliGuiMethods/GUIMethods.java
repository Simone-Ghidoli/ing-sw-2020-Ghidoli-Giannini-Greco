package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods;

import it.polimi.ingsw.ps60.GlobalVariables;

import it.polimi.ingsw.ps60.clientSide.view.Swing.MainFrame;
import it.polimi.ingsw.ps60.utils.ListContains;
import it.polimi.ingsw.ps60.utils.StringRegexValidation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GUIMethods implements ViewMethodSelection {

    private JFrame boardWindow;
    private boolean pressed;
    private JFrame userInteraction;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private MainFrame santorini;
    private JButton[] jButtons = new JButton[25];
    private int numberOfPlayers;
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
                Container c=boardWindow.getContentPane();
                santorini =new MainFrame();
                c.add(santorini);
                boardWindow.setVisible(true);
                santorini.getJtextSouth().setText("wait the others players");

            }
        });
    }

    @Override
    public void printBoard(String board) {
        userInteraction.setVisible(true);

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

        pressed = false;
        
        userInteraction = new JFrame();
        userInteraction.setTitle("Choose server");
        ImageIcon imagineBoard = new ImageIcon("src/resources/board/SantoriniBox.png");
        Image scaleImageBoard = imagineBoard.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        userInteraction.setSize(screenSize.width / 5, screenSize.height / 3);
        userInteraction.setResizable(false);
        userInteraction.setLocationRelativeTo(boardWindow);
        userInteraction.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userInteraction.setAlwaysOnTop(true);

        JLabel box = new JLabel(new ImageIcon(scaleImageBoard));
        box.setLayout(new GridLayout(4, 1));
        JPanel empty = new JPanel();
        JPanel ipPanel = new JPanel();
        JPanel portPanel = new JPanel();
        JPanel next = new JPanel();
        ipPanel.setLayout(new FlowLayout());
        portPanel.setLayout(new FlowLayout());

        userInteraction.add(box);
        box.add(empty);
        box.add(ipPanel);
        box.add(portPanel);
        box.add(next);

        ipPanel.setOpaque(false);
        portPanel.setOpaque(false);
        next.setOpaque(false);
        empty.setOpaque(false);

        final JTextField ip = new JTextField("Enter IP server", 14);
        final JTextField port = new JTextField("Enter port server", 14);
        final JButton nextButton = new JButton("next");
        next.add(nextButton);
        nextButton.setVisible(true);

        ip.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                if (ip.getText().equals("") || ip.getText().equals("Enter IP server"))
                    ip.setText("");

            }

            @Override
            public void focusLost(FocusEvent focusEvent) {

                if (ip.getText().equals(("")))
                    ip.setText("Enter IP server");
            }
        });
        ipPanel.add(ip);

        port.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                if (port.getText().equals("") || port.getText().equals("Enter port server"))
                    port.setText("");
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                if (port.getText().equals(""))
                    port.setText("Enter port server");

            }
        });
        portPanel.add(port);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                {
                    if (!new StringRegexValidation(GlobalVariables.StringPatterns.PortNumber.getPattern()).isValid(port.getText()) &&
                            !new StringRegexValidation(GlobalVariables.StringPatterns.IPv4.getPattern()).isValid(ip.getText())) {
                        JOptionPane.showMessageDialog(userInteraction,
                                "IP and port number not valid",
                                "",
                                JOptionPane.ERROR_MESSAGE);
                    } else if (!new StringRegexValidation(GlobalVariables.StringPatterns.IPv4.getPattern()).isValid(ip.getText())) {
                        JOptionPane.showMessageDialog(userInteraction,
                                "IP not valid",
                                "",
                                JOptionPane.ERROR_MESSAGE);


                    } else if (!new StringRegexValidation(GlobalVariables.StringPatterns.PortNumber.getPattern()).isValid(port.getText())) {
                        JOptionPane.showMessageDialog(userInteraction,
                                "Port number not valid",
                                "",
                                JOptionPane.ERROR_MESSAGE);


                    } else {
                        pressed = true;
                        System.out.println("INFO : Next button pressed");
                        userInteraction.dispose();
                    }
                }
            }
        });
        userInteraction.setVisible(true);

        while (!pressed){
            System.out.println("INFO : Waiting for input");
        }
        System.out.println("INFO : IpPortChoice is returning");
        return new String[]{ip.getText(), port.getText()};
    }

    @Override
    public String[] nicknameBirthdayChoice() {

        pressed=false;
        userInteraction =new JFrame("Nickname & birth date");
        userInteraction.setResizable(false);
        userInteraction.setLocationRelativeTo(null);
        userInteraction.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userInteraction.setPreferredSize(new Dimension(screenSize.width/4, screenSize.height /4));
        userInteraction.setLayout(new GridLayout(4,1));


        String[] day = new String[31];
        String[] month = new String[12];
        String[] year = new String[121];


        for (int i = 1900; i < 2021; i++)
            year[i - 1900] = String.valueOf(i);

        for (int i = 1; i < 13; i++)
            month[i - 1] = String.valueOf(i);

        for (int i = 1; i < 32; i++)
            day[i - 1] = String.valueOf(i);

        JPanel intro=new JPanel();
        JPanel birthdayPanel=new JPanel();

        intro.setLayout(new GridBagLayout());

        JPanel nickname=new JPanel();
        nickname.setLayout(new FlowLayout());
        JLabel jL= new JLabel("Enter your nickname and birthday");

        birthdayPanel.setLayout(new FlowLayout());
        JPanel nextPanel=new JPanel();
        nextPanel.setLayout(new GridBagLayout());
        JButton next=new JButton("next");
        nextPanel.add(next);
        intro.add(jL);
        userInteraction.add(intro);
        userInteraction.add(nickname);
        userInteraction.add(birthdayPanel);
        userInteraction.add(nextPanel);
        JLabel nicknameLabel =new JLabel("Nickname:");
        jL.setLayout(new GridBagLayout());
        JTextField nicknameText=new JTextField(14);


        JComboBox<String> dayCombo=new JComboBox<>(day);
        JComboBox<String> monthCombo=new JComboBox<>(month);
        JComboBox<String> yearCombo= new JComboBox<>(year);
        dayCombo.setSelectedIndex(0);
        monthCombo.setSelectedIndex(0);
        yearCombo.setSelectedIndex(98);

        nickname.add(nicknameLabel);
        nickname.add(nicknameText);
        JLabel dayLabel=new JLabel("Day:");
        JLabel monthLabel=new JLabel("Month:");
        JLabel yearLabel=new JLabel("Year:");
        birthdayPanel.add(dayLabel);
        birthdayPanel.add(dayCombo);
        birthdayPanel.add(monthLabel);
        birthdayPanel.add(monthCombo);
        birthdayPanel.add(yearLabel);
        birthdayPanel.add(yearCombo);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                userInteraction.dispose();
                pressed=true;
            }
        });

        userInteraction.setVisible(true);
        userInteraction.pack();
        while(!pressed){
            System.out.println("INFO : Waiting for input");
        }
        return new String[]{nicknameText.getText(), yearCombo.getSelectedItem() + "/" + monthCombo.getSelectedItem()+ "/" +dayCombo.getSelectedItem()};
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
    public int[][] firstSetWorkers(List<int[]> impossiblePositions) {
        numberOfWorkers=0;
        int[][] choice = new int[2][];
        final ListContains listContains = new ListContains(impossiblePositions);
        class Listener implements ActionListener{
            JButton button;
            JPanel panel;

            public Listener(JPanel p, JButton button){
                this.button=button;
                panel=p;
            }
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            if(numberOfWorkers<2) {
                button.setEnabled(false);
                numberOfWorkers++;
            }

            }
        }
        santorini.getJtextSouth().setText("select positions of yours workers, if there is label 'set isn't possible' " +
                "you can't set the worker there because another worker's player enjoys that position");
        for (int i = 0; i < 25; i++) {
            jButtons[i] = santorini.getButton(i);
            if(listContains.isContained(santorini.getCoordOfButton(jButtons[i]))) {
             jButtons[i].setText("set isn't possible");
            }
            if(!listContains.isContained(santorini.getCoordOfButton(jButtons[i]))) {
                if (jButtons[i].isEnabled())
                    jButtons[i].addActionListener(new Listener(santorini, jButtons[i]));
                if(numberOfWorkers==2)
                    break;
            }
        }


        while(numberOfWorkers!=1){
            System.out.println("INFO : Waiting for input");

        }

        for (int i = 0; i < 25; i++) {
            if (!jButtons[i].isEnabled()){
                choice[0]=santorini.getCoordOfButton(jButtons[i]);
                jButtons[i].setText("worker 1");
                jButtons[i].setEnabled(true);
                ImageIcon imagineWorker = new ImageIcon(GlobalVariables.IdPlayer.PLAYER1.getSourcePawn());
                Image scaleImageWorker = imagineWorker.getImage().getScaledInstance(jButtons[i].getWidth(), jButtons[i].getHeight(), Image.SCALE_SMOOTH);
                jButtons[i].setIcon(new ImageIcon(scaleImageWorker));
            }
        }
        santorini.getJtextSouth().setText("a worker has been placed");
        while (numberOfWorkers!=2) {
            System.out.println("INFO : Waiting for input");
        }
        for (int i = 0; i < 25; i++) {
            if (!jButtons[i].isEnabled()) {
                choice[1] = santorini.getCoordOfButton(jButtons[i]);
                jButtons[i].setText("worker 2");
                jButtons[i].setEnabled(true);
            }

        }
        santorini.getJtextSouth().setText("both workers have been placed.. wait the others players");
        return choice;
    }

    @Override
    public boolean specialChoices(String string) {
        return true;
    }

    @Override
    public int numberOfPlayers(){
        pressed=false;

        numberOfPlayers=2;

        userInteraction =new JFrame();
        userInteraction.setTitle("Select number of player");
        userInteraction.setSize(screenSize.width/4,screenSize.height/4);
        userInteraction.setResizable(false);
        userInteraction.setLocationRelativeTo(boardWindow);
        userInteraction.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userInteraction.setLayout(new GridLayout(3,1));

        JLabel n=new JLabel("Select number of players");
        JPanel chk=new JPanel();
        chk.setLayout(new FlowLayout());
        JPanel next=new JPanel();
        next.setLayout(new GridBagLayout());
        final JCheckBox two =new JCheckBox("2");
        final JCheckBox three =new JCheckBox("3");
        JButton nextButton=new JButton("next");
        userInteraction.add(n);
        next.add(nextButton);
        chk.add(two);
        chk.add(three);
        userInteraction.add(chk);
        userInteraction.add(next);
        two.setSelected(true);
        three.setSelected(false);

        class ActionHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                JCheckBox checkbox = (JCheckBox) event.getSource();
                if (checkbox == two) {
                    two.setSelected(true);
                    three.setSelected(false);
                    numberOfPlayers=2;


                } else if (checkbox == three) {
                    two.setSelected(false);
                    three.setSelected(true);
                    numberOfPlayers=3;

                }
            }
        }
        ActionListener a =new ActionHandler();
        two.addActionListener(a);
        three.addActionListener(a);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                userInteraction.dispose();
                pressed=true;
            }
        });

        userInteraction.setVisible(true);

        while (!pressed) {
            System.out.println("INFO : Waiting for input");
        }

        return numberOfPlayers;
    }

    @Override
    public void alert(String string) {
         JOptionPane.showMessageDialog(userInteraction,
                 string,
                 "ALERT",
                 JOptionPane.WARNING_MESSAGE);
    }
}
