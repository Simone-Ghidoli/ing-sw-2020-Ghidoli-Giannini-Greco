package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods;

import it.polimi.ingsw.ps60.GlobalVariables;

import it.polimi.ingsw.ps60.serverSide.server.ServerThread;
import it.polimi.ingsw.ps60.serverSide.server.Server_new;
import it.polimi.ingsw.ps60.utils.StringRegexValidation;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.event.*;


public class GUIMethods implements ViewMethodSelection {

    private JFrame boardWindow;
    private JFrame userInterations;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    boolean pressed=false;
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
        userInterations.setPreferredSize(new Dimension(screenSize.width*7/30, screenSize.height*1/3));
        userInterations.setVisible(false);
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
        next.requestFocus();

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
                        userInterations.setVisible(false);
                        pressed=true;
                        boardWindow.setVisible(true);


                    }
                }
            }
        });
        userInterations.setVisible(true);
        userInterations.pack();
        while(!pressed) {
        }
        return new String[]{ip.getText(),port.getText()};


    }

    @Override
    public String[] nicknameBirthdayChoice() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        String day[]={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        int month[]={1,2,3,4,5,6,7,8,9,10,11,12};
        int year[]={1940,1941,1942,1943,1944,1945,1946,1947,1948,1949,1950,1951,1952,1953,1954,1955,1956,1957,1958,1959,1960,1961,1962,1963,1964,1965,1966,1967,1968,1969,1970,1971,1972,1973,1974,1975,1976,1977,1978,1979,1980,1981,1982,1983,1984,1985,1986, 1987,1988,1989,1990,1991,1992,1993,1994,1995,1996,1997,1998,1999,2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015,2016,2017,2018,2019,2020};
        JFrame setupNicknameBirthday=new JFrame("Nickname & birth date");
        setupNicknameBirthday.setResizable(false);
        setupNicknameBirthday.setLocationRelativeTo(null);
        setupNicknameBirthday.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupNicknameBirthday.setPreferredSize(new Dimension(screenSize.width*1/4, screenSize.height*1/2));
        setupNicknameBirthday.setAlwaysOnTop(true);
        setupNicknameBirthday.setVisible(true);
        boardWindow.add(setupNicknameBirthday, BorderLayout.CENTER);
        setupNicknameBirthday.setLayout(new GridLayout(3,1));
        JPanel nickname=new JPanel();
        nickname.setLayout(new FlowLayout());
        JPanel birthdayPanel=new JPanel();
        birthdayPanel.setLayout(new FlowLayout());
        JButton next=new JButton("next");
        setupNicknameBirthday.add(nickname);
        setupNicknameBirthday.add(birthdayPanel);
        setupNicknameBirthday.add(next);
        JLabel nm=new JLabel("insert your nickname");
        JTextField nicknameText=new JTextField(14);
        JComboBox dayCombo=new JComboBox(day);
        JComboBox monthCombo=new JComboBox();
        JComboBox yearCombo= new JComboBox();
        nickname.add(nm);
        nickname.add(nicknameText);
        JLabel dayLabel=new JLabel("day:");
        birthdayPanel.add(dayLabel);
        birthdayPanel.add(dayCombo);
        dayCombo.setSize(birthdayPanel.getWidth()/5,birthdayPanel.getHeight()/7);

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
    public int numberOfPlayers(){return 0;}

    @Override
    public void alert(String string) {
         JOptionPane.showMessageDialog(userInterations,
                 string,
                 "",
                 JOptionPane.WARNING_MESSAGE);

    }
}
