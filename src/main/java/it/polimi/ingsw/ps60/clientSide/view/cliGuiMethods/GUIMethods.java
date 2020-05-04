package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods;

import it.polimi.ingsw.ps60.GlobalVariables;

import it.polimi.ingsw.ps60.utils.StringRegexValidation;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.List;



public class GUIMethods implements ViewMethodSelection {

    private JFrame boardWindow;
    private JFrame userInterations;
    private JFrame setupNicknameBirthday;
    private JFrame number;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    boolean pressed=false;
    private int numberOfPlayers;
    public GUIMethods(){
        boardWindow = new JFrame();


        boardWindow.setResizable(false);

        JFrame.setDefaultLookAndFeelDecorated(true);

        boardWindow.setTitle("Santorini");
        boardWindow.setSize(screenSize.width, screenSize.height);
        boardWindow.setLocationRelativeTo(null);
        boardWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        boardWindow.setLayout(new BorderLayout());
        ImageIcon imagineBoard = new ImageIcon("src/resources/board/SantoriniBoard.png");
        Image scaleImageBoard = imagineBoard.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        JPanel grid = new JPanel();

        JLabel board = new JLabel(new ImageIcon(scaleImageBoard));



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
        boardWindow.setVisible(false);


        userInterations = new JFrame();
        userInterations.setTitle("choose Server");
        userInterations.setPreferredSize(new Dimension(screenSize.width*7/30, screenSize.height /3));
        userInterations.setVisible(false);
        JFrame.setDefaultLookAndFeelDecorated(true);

    }

    @Override
    public void printBoard(String board) {
        userInterations.setVisible(true);

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


        userInterations.setResizable(false);
        userInterations.setLocationRelativeTo(null);
        userInterations.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userInterations.setSize(screenSize.width/5,screenSize.height/3);
        userInterations.setAlwaysOnTop(true);
        userInterations.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        ImageIcon imagineBox = new ImageIcon("src/resources/board/SantoriniBox.png");
        Image scaleImageBox = imagineBox.getImage().getScaledInstance(userInterations.getWidth(), userInterations.getHeight(), Image.SCALE_SMOOTH);
        JLabel box = new JLabel(new ImageIcon(scaleImageBox));
        box.setLayout(new GridLayout(4,1));
        JPanel empty=new JPanel();
        JPanel ipPanel=new JPanel();
        JPanel portPanel =new JPanel();
        JPanel next =new JPanel();
        ipPanel.setLayout(new FlowLayout());
        portPanel.setLayout(new FlowLayout());
        //next.setLayout(new GridBagLayout());
        userInterations.add(box);


        box.add(empty);
        box.add(ipPanel);
        ipPanel.setOpaque(false);
        box.add(portPanel);
        portPanel.setOpaque(false);
        box.add(next);
        next.setOpaque(false);



        empty.setOpaque(false);
        //JLabel serverIp=new JLabel("IP SERVER:");
        //JLabel serverPort=new JLabel("SERVER PORT:");
        final JTextField ip= new JTextField("inserire ip server",14);
        final JTextField port= new JTextField("inserire port server",14);
        final JButton nextButton= new JButton("next");
        next.add(nextButton);
        nextButton.setVisible(true);

        ip.addFocusListener(new FocusListener() {
                                @Override
                                public void focusGained(FocusEvent focusEvent) {
                                    if(ip.getText().equals("")||ip.getText().equals("inserire ip server"))
                                        ip.setText("");

                                }

                                @Override
                                public void focusLost(FocusEvent focusEvent) {

                                    if(ip.getText().equals(new String("")))
                                        ip.setText("inserire ip server");

                                }
                            });
        port.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                if(port.getText().equals("")||port.getText().equals("inserire port server"))
                    port.setText("");
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                if(port.getText().equals(new String("")))
                    port.setText("inserire port server");

            }
        });
        ipPanel.add(ip);
        portPanel.add(port);


        nextButton.grabFocus();
        nextButton.requestFocus();
        next.requestFocus();
        userInterations.setVisible(true);

        nextButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                {
                    if(!new StringRegexValidation(GlobalVariables.StringPatterns.PortNumber.getPattern()).isValid(port.getText())&&
                            !new StringRegexValidation(GlobalVariables.StringPatterns.IPv4.getPattern()).isValid(ip.getText())){
                        JOptionPane.showMessageDialog(userInterations,
                                "Server non trovato, inserire un ip valido",
                                "",
                                JOptionPane.ERROR_MESSAGE);
                    }


                    else if (!new StringRegexValidation(GlobalVariables.StringPatterns.IPv4.getPattern()).isValid(ip.getText())) {
                        JOptionPane.showMessageDialog(userInterations,
                                "Server non trovato, inserire un ip valido",
                                "",
                                JOptionPane.ERROR_MESSAGE);



                    } else if (!new StringRegexValidation(GlobalVariables.StringPatterns.PortNumber.getPattern()).isValid(port.getText())) {
                        JOptionPane.showMessageDialog(userInterations,
                                " inserire un valore di porta valido",
                                "",
                                JOptionPane.WARNING_MESSAGE);




                    }
                    else {
                        /*userInterations.setVisible(false);
                        JFrame tryToConnect=new JFrame("JPROGRESSBAR");
                        tryToConnect.setResizable(false);
                        tryToConnect.setLayout(new GridLayout(3,1));
                        tryToConnect.setLocationRelativeTo(null);
                        tryToConnect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        tryToConnect.setSize(screenSize.width/7,screenSize.height/4);
                        tryToConnect.setLayout(new GridLayout(2,1));
                        JLabel border=new JLabel("Try to connect..");
                        JProgressBar connect= new JProgressBar(0,100);
                        connect.setValue(0);
                        connect.add(border);
                        connect.setStringPainted(true);
                        tryToConnect.add(border);
                        tryToConnect.add(connect);
                        tryToConnect.setVisible(true);
                        */
                        pressed=true;
                        userInterations.dispose();
                        //boardWindow.setVisible(true);

                    }
                }
            }
        });

        JOptionPane.showMessageDialog(userInterations,
                "inserire indirizzo ip e porta del server",
                "",
                JOptionPane.INFORMATION_MESSAGE);
        userInterations.pack();
        while(!pressed) {
        }
        return new String[]{ip.getText(),port.getText()};


    }

    @Override
    public String[] nicknameBirthdayChoice() {
        JFrame setupNicknameBirthday=new JFrame("Nickname & birth date");
        setupNicknameBirthday.setResizable(false);
        setupNicknameBirthday.setLocationRelativeTo(null);
        setupNicknameBirthday.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupNicknameBirthday.setPreferredSize(new Dimension(screenSize.width, screenSize.height /2));
        setupNicknameBirthday.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        String[] day = new String[31];
        String[] month = new String[12];
        String[] year = new String[1021];


        for (int i = 1900; i < 2021; i++)
            year[i - 1900] = String.valueOf(i);

        for (int i = 1; i < 13; i++)
            year[i - 1] = String.valueOf(i);

        for (int i = 1; i < 32; i++)
            day[i - 1] = String.valueOf(i);

        setupNicknameBirthday.setLayout(new GridLayout(4,1));
        JPanel intro=new JPanel();
        intro.setLayout(new GridBagLayout());
        JPanel nickname=new JPanel();
        nickname.setLayout(new FlowLayout());
        JLabel jL= new JLabel("inserire nickname e data di nascita");
        JPanel birthdayPanel=new JPanel();
        birthdayPanel.setLayout(new FlowLayout());
        JPanel nextPanel=new JPanel();
        nextPanel.setLayout(new GridBagLayout());
        JButton next=new JButton("next");
        nextPanel.add(next);
        intro.add(jL);
        setupNicknameBirthday.add(intro);
        setupNicknameBirthday.add(nickname);
        setupNicknameBirthday.add(birthdayPanel);
        setupNicknameBirthday.add(nextPanel);
        JLabel nm=new JLabel("Nickname:");
        jL.setLayout(new GridBagLayout());
        JTextField nicknameText=new JTextField(14);
        JComboBox dayCombo=new JComboBox(day);
        JComboBox monthCombo=new JComboBox(month);
        JComboBox yearCombo= new JComboBox(year);
        dayCombo.setSelectedIndex(0);
        monthCombo.setSelectedIndex(0);
        yearCombo.setSelectedIndex(0);

        nickname.add(nm);
        nickname.add(nicknameText);
        JLabel dayLabel=new JLabel("day:");
        JLabel monthLabel=new JLabel("month:");
        JLabel yearLabel=new JLabel("year:");
        birthdayPanel.add(dayLabel);
        birthdayPanel.add(dayCombo);
        birthdayPanel.add(monthLabel);
        birthdayPanel.add(monthCombo);
        birthdayPanel.add(yearLabel);
        birthdayPanel.add(yearCombo);

        setupNicknameBirthday.setVisible(true);
        setupNicknameBirthday.pack();
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
    public int numberOfPlayers(){
        numberOfPlayers=2;
        pressed=false;

        number =new JFrame();
        number.setLayout(new GridLayout(3,1));
        number.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        number.setResizable(false);
        number.setLocationRelativeTo(null);
        number.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel n=new JLabel("selezionare numero di giocatori");
        JPanel chk=new JPanel();
        chk.setLayout(new FlowLayout());
        JPanel next=new JPanel();
        next.setLayout(new GridBagLayout());
        final JCheckBox two =new JCheckBox("2");
        final JCheckBox three =new JCheckBox("3");
        JButton nextButton=new JButton("next");
        number.add(n);
        next.add(nextButton);
        chk.add(two);
        chk.add(three);
        number.add(chk);
        number.add(next);
        number.setVisible(true);
        two.setSelected(true);
        three.setSelected(false);
        number.setVisible(true);

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
                number.dispose();
                pressed=true;
            }
        });
        while (!pressed) {
        }
        number.pack();


        return numberOfPlayers;}

    @Override
    public void alert(String string) {
         JOptionPane.showMessageDialog(userInterations,
                 string,
                 "",
                 JOptionPane.WARNING_MESSAGE);


    }
}
