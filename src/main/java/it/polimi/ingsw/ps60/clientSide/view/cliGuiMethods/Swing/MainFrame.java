package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.Swing;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JPanel {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JButton[] jButtons = new JButton[25];
    private JLabel board;
    private JPanel grid;
    private JPanel players;
    private JPanel info;
    private JPanel divinityCards;
    private JTextField information;
    JButton player1=new JButton("Player1");
    JButton player2=new JButton("Player2");
    JButton player3=new JButton("Player3");

    public MainFrame() {
            super();
            setLayout(new GridBagLayout());
            ImageIcon imagineBoard = new ImageIcon("src/resources/board/SantoriniBoard.png");
            Image scaleImageBoard = imagineBoard.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
            board = new JLabel(new ImageIcon(scaleImageBoard));
            grid = new JPanel();
            grid.setPreferredSize(new Dimension(screenSize.width*12/29,screenSize.height*47/64));
            grid.setLayout(new GridLayout(5,5));
            grid.setOpaque(false);
            players=new JPanel();
            players.setPreferredSize(new Dimension(screenSize.width*17/58,screenSize.height));
            players.setOpaque(false);
            players.setLayout(new GridLayout(3,1));
            player1.setOpaque(false);
            player2.setOpaque(false);
            player3.setOpaque(false);
            JPanel player1Panel= new JPanel();
            player1Panel.setOpaque(false);
            JPanel player2Panel= new JPanel();
            player2Panel.setOpaque(false);
            JPanel player3Panel= new JPanel();
            player3Panel.setOpaque(false);
            player1Panel.add(player1);
            player2Panel.add(player2);
            player3Panel.add(player3);
            players.add(player1Panel);
            players.add(player2Panel);
            players.add(player3Panel);
            divinityCards=new JPanel();
            divinityCards.setPreferredSize(new Dimension(screenSize.width*17/58,screenSize.height));
            divinityCards.setOpaque(false);
            JPanel workers=new JPanel();
            workers.setPreferredSize(new Dimension(screenSize.width*12/29,screenSize.height*2/17));
            workers.setOpaque(false);
            workers.setLayout(new FlowLayout());
            information=new JTextField();
            information.setEditable(false);
            info=new JPanel();
            info.setPreferredSize(new Dimension(screenSize.width*12/29,screenSize.height*2/17));
            info.setOpaque(false);
            info.setLayout(new GridBagLayout());
            info.add(information);
            board.setLayout(new BorderLayout());
            board.add(grid,BorderLayout.CENTER);
            board.add(players,BorderLayout.WEST);
            board.add(divinityCards,BorderLayout.EAST);
            board.add(workers,BorderLayout.NORTH);
            board.add(info,BorderLayout.SOUTH);


            for (int i = 0; i < 25; i++){
                jButtons[i] = new JButton(String.valueOf(i + 1));
                jButtons[i].setContentAreaFilled(false);
                jButtons[i].setOpaque(false);
                grid.add(jButtons[i]);
            }
            add(board);
    }

    public JButton[] getjButtons() {
        return jButtons;
    }

    public JButton getButton(int i){
        return jButtons[i];
    }
    public JTextField getJtextSouth(){
        return information;
    }
    public JPanel getGodsPanel(){
        return divinityCards;
    }
    public int[] getCoordOfButton(JButton jButton){
        if(jButton.equals(jButtons[0]))
            return new int[]{0,0};
        else if (jButton.equals(jButtons[1]))
            return new int[]{0,1};
        else if (jButton.equals(jButtons[2]))
            return new int[]{0,2};
        else if (jButton.equals(jButtons[3]))
            return new int[]{0,3};
        else if (jButton.equals(jButtons[4]))
            return new int[]{0,4};
        else if (jButton.equals(jButtons[5]))
            return new int[]{1,0};
        else if (jButton.equals(jButtons[6]))
            return new int[]{1,1};
        else if (jButton.equals(jButtons[7]))
            return new int[]{1,2};
        else if (jButton.equals(jButtons[8]))
            return new int[]{1,3};
        else if (jButton.equals(jButtons[9]))
            return new int[]{1,4};
        else if (jButton.equals(jButtons[10]))
            return new int[]{2,0};
        else if (jButton.equals(jButtons[11]))
            return new int[]{2,1};
        else if (jButton.equals(jButtons[12]))
            return new int[]{2,2};
        else if (jButton.equals(jButtons[13]))
            return new int[]{2,3};
        else if (jButton.equals(jButtons[14]))
            return new int[]{2,4};
        else if (jButton.equals(jButtons[15]))
            return new int[]{3,0};
        else if (jButton.equals(jButtons[16]))
            return new int[]{3,1};
        else if (jButton.equals(jButtons[17]))
            return new int[]{3,2};
        else if (jButton.equals(jButtons[18]))
            return new int[]{3,3};
        else if (jButton.equals(jButtons[19]))
            return new int[]{3,4};
        else if (jButton.equals(jButtons[20]))
            return new int[]{4,0};
        else if (jButton.equals(jButtons[21]))
            return new int[]{4,1};
        else if (jButton.equals(jButtons[22]))
            return new int[]{4,2};
        else if (jButton.equals(jButtons[23]))
            return new int[]{4,3};
        else if (jButton.equals(jButtons[24]))
            return new int[]{4,4};
        else return null;
    }


}
