package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods;

import it.polimi.ingsw.ps60.GlobalVariables;

import it.polimi.ingsw.ps60.clientSide.view.Swing.MainFrame;
import it.polimi.ingsw.ps60.utils.StringRegexValidation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

public class GUIMethods implements ViewMethodSelection {

    private JFrame boardWindow;
    private JFrame userInterations;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

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

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
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
        userInterations.setVisible(true);
        userInterations.pack();
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



                    }


                }
            });



        //ipPanel.add(serverIp);
        //portPanel.add(serverPort);
        return new String[]{ip.getText(),port.getText()};

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
