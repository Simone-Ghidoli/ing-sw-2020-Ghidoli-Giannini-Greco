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

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    boolean pressed=false;
    private int numberOfPlayers;

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

            }
        });






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


        userInterations = new JFrame();
        userInterations.setTitle("choose Server");
        userInterations.setPreferredSize(new Dimension(screenSize.width*7/30, screenSize.height /3));
        userInterations.setVisible(false);
        JFrame.setDefaultLookAndFeelDecorated(true);


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

                                    if(ip.getText().equals(("")))
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
                                "inserire un valore di porta valido",
                                "",
                                JOptionPane.WARNING_MESSAGE);




                    }
                    else {

                        pressed=true;
                        userInterations.dispose();
                        //boardWindow.setVisible(true);

                    }
                }
            }
        });

        nextButton.grabFocus();
        nextButton.requestFocus();
        next.requestFocus();

        JOptionPane.showMessageDialog(userInterations,
                "inserire indirizzo ip e porta del server",
                "",
                JOptionPane.INFORMATION_MESSAGE);

        while(!pressed) {
        }
        userInterations.pack();
        return new String[]{ip.getText(),port.getText()};
    }

    @Override
    public String[] nicknameBirthdayChoice() {

        pressed=false;
        userInterations =new JFrame("Nickname & birth date");
        userInterations.setResizable(false);
        userInterations.setLocationRelativeTo(null);
        JFrame.setDefaultLookAndFeelDecorated(true);
        userInterations.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userInterations.setPreferredSize(new Dimension(screenSize.width/4, screenSize.height /4));
        userInterations.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        String[] day = new String[31];
        String[] month = new String[12];
        String[] year = new String[121];


        for (int i = 1900; i < 2021; i++)
            year[i - 1900] = String.valueOf(i);

        for (int i = 1; i < 13; i++)
            month[i - 1] = String.valueOf(i);

        for (int i = 1; i < 32; i++)
            day[i - 1] = String.valueOf(i);

        userInterations.setLayout(new GridLayout(4,1));
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
        userInterations.add(intro);
        userInterations.add(nickname);
        userInterations.add(birthdayPanel);
        userInterations.add(nextPanel);
        JLabel nm=new JLabel("Nickname:");
        jL.setLayout(new GridBagLayout());
        JTextField nicknameText=new JTextField(14);
        JComboBox dayCombo=new JComboBox(day);
        JComboBox monthCombo=new JComboBox(month);
        JComboBox yearCombo= new JComboBox(year);
        dayCombo.setSelectedIndex(0);
        monthCombo.setSelectedIndex(0);
        yearCombo.setSelectedIndex(98);

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
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                userInterations.dispose();
                pressed=true;
            }
        });

        userInterations.setVisible(true);
        userInterations.pack();
        while(!pressed){

        }
        String nameAndBirthday[]=new String[2];
        nameAndBirthday[0]=nicknameText.getText();
        nameAndBirthday[1]=yearCombo.getSelectedItem() + "/" + monthCombo.getSelectedItem()+ "/" +dayCombo.getSelectedItem();
        return nameAndBirthday;
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
        userInterations =new JFrame();
        userInterations.setLayout(new GridLayout(3,1));
        userInterations.setSize(screenSize.width/4,screenSize.height/4);
        userInterations.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        userInterations.setResizable(false);
        userInterations.setLocationRelativeTo(null);
        JFrame.setDefaultLookAndFeelDecorated(true);
        userInterations.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel n=new JLabel("selezionare numero di giocatori");
        JPanel chk=new JPanel();
        chk.setLayout(new FlowLayout());
        JPanel next=new JPanel();
        next.setLayout(new GridBagLayout());
        final JCheckBox two =new JCheckBox("2");
        final JCheckBox three =new JCheckBox("3");
        JButton nextButton=new JButton("next");
        userInterations.add(n);
        next.add(nextButton);
        chk.add(two);
        chk.add(three);
        userInterations.add(chk);
        userInterations.add(next);
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
                userInterations.dispose();
                pressed=true;
            }
        });
        while (!pressed) {
        }
        userInterations.setVisible(true);
        userInterations.pack();


        return numberOfPlayers;}

    @Override
    public void alert(String string) {
         JOptionPane.showMessageDialog(userInterations,
                 string,
                 "",
                 JOptionPane.WARNING_MESSAGE);


    }
}
