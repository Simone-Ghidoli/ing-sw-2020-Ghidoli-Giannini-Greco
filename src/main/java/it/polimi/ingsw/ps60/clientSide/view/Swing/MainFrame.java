package it.polimi.ingsw.ps60.clientSide.view.Swing;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final JButton[][] tiles = new JButton[5][5];

    public static void main(String[] args) {

        MainFrame mainFrame = new MainFrame();

    }


    public MainFrame() {
        {
            setResizable(false);

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            setDefaultLookAndFeelDecorated(true);


            this.setTitle("Santorini");
            setSize(screenSize.width, screenSize.height);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setVisible(true);

            setLayout(new BorderLayout());
            ImageIcon imagine = new ImageIcon("src/resources/board/SantoriniBoard.png");
            Image scaleImage = imagine.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
            JPanel grid = new JPanel();

            JLabel board = new JLabel(new ImageIcon(scaleImage));



        board.setLayout(new GridBagLayout());
        //board.setLayout(new BorderLayout());
        board.add(grid);
        grid.setPreferredSize(new Dimension(screenSize.width*12/29,screenSize.height*47/64));

        grid.setLayout(new GridLayout(5,5));
        grid.setOpaque(false);



        JButton jb1 = new JButton("1");
        jb1.setContentAreaFilled(false);
        jb1.setOpaque(false);
        JButton jb2 = new JButton("2");
        jb2.setContentAreaFilled(false);
        jb2.setOpaque(false);
        JButton jb3 = new JButton("3");
        jb3.setContentAreaFilled(false);
        jb3.setOpaque(false);
        JButton jb4 = new JButton("4");
        jb4.setContentAreaFilled(false);
        jb4.setOpaque(false);
        JButton jb5 = new JButton("5");
        jb5.setContentAreaFilled(false);
        jb5.setOpaque(false);
        JButton jb6 = new JButton("6");
        jb6.setContentAreaFilled(false);
        jb6.setOpaque(false);
        JButton jb7 = new JButton("");
        jb7.setContentAreaFilled(false);
        jb7.setOpaque(false);
        JButton jb8 = new JButton("");
        jb8.setContentAreaFilled(false);
        jb8.setOpaque(false);
        JButton jb9 = new JButton("");
        jb9.setContentAreaFilled(false);
        jb9.setOpaque(false);
        JButton jb10 = new JButton("");
        jb10.setContentAreaFilled(false);
        jb10.setOpaque(false);
        JButton jb11 = new JButton("");
        jb11.setContentAreaFilled(false);
        jb11.setOpaque(false);
        JButton jb12 = new JButton("");
        jb12.setContentAreaFilled(false);
        jb12.setOpaque(false);
        JButton jb13 = new JButton("");
        jb13.setContentAreaFilled(false);
        jb13.setOpaque(false);
        JButton jb14 = new JButton("");
        jb14.setContentAreaFilled(false);
        jb14.setOpaque(false);
        JButton jb15 = new JButton("");
        jb15.setContentAreaFilled(false);
        jb15.setOpaque(false);
        JButton jb16 = new JButton("");
        jb16.setContentAreaFilled(false);
        jb16.setOpaque(false);
        JButton jb17 = new JButton("");
        jb17.setContentAreaFilled(false);
        jb17.setOpaque(false);
        JButton jb18 = new JButton("");
        jb18.setContentAreaFilled(false);
        jb18.setOpaque(false);
        JButton jb19 = new JButton("");
        jb19.setContentAreaFilled(false);
        jb19.setOpaque(false);
        JButton jb20 = new JButton("");
        jb20.setContentAreaFilled(false);
        jb20.setOpaque(false);
        JButton jb21 = new JButton("");
        jb21.setContentAreaFilled(false);
        jb21.setOpaque(false);
        JButton jb22 = new JButton("");
        jb22.setContentAreaFilled(false);
        jb22.setOpaque(false);
        JButton jb23 = new JButton("");
        jb23.setContentAreaFilled(false);
        jb23.setOpaque(false);
        JButton jb24 = new JButton("");
        jb24.setContentAreaFilled(false);
        jb24.setOpaque(false);
        JButton jb25 = new JButton("");
        jb25.setContentAreaFilled(false);
        jb25.setOpaque(false);


        grid.add(jb1);
        grid.add(jb2);
        grid.add(jb3);
        grid.add(jb4);
        grid.add(jb5);
        grid.add(jb6);
        grid.add(jb7);
        grid.add(jb8);
        grid.add(jb9);
        grid.add(jb10);
        grid.add(jb11);
        grid.add(jb12);
        grid.add(jb13);
        grid.add(jb14);
        grid.add(jb15);
        grid.add(jb16);
        grid.add(jb17);
        grid.add(jb18);
        grid.add(jb19);
        grid.add(jb20);
        grid.add(jb21);
        grid.add(jb22);
        grid.add(jb23);
        grid.add(jb24);
        grid.add(jb25);

        add(board, BorderLayout.CENTER);
        pack();


        }
    }
}
