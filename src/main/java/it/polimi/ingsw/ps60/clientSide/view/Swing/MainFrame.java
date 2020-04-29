package it.polimi.ingsw.ps60.clientSide.view.Swing;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    public static void main(String[] args) {

        MainFrame mainFrame = new MainFrame();

    }


    /**
     * sets up the GUI
     */

    public MainFrame() {

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame window = new JFrame("SANTORINI");
        //window.setResizable(false);

        window.setVisible(true);
        window.setLayout(new GridLayout());

        //set the behaviour when the window is closed

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setSize(1920,1080);
        window.setLocationRelativeTo(null);
        ImageIcon imagine=new ImageIcon("src/resources/board/SantoriniBoard.png");
        Image scaleImage=imagine.getImage().getScaledInstance(1366  ,768,Image.SCALE_SMOOTH);


        JLabel board_imagine=new JLabel(new ImageIcon(scaleImage));
        board_imagine.setHorizontalAlignment(SwingConstants.CENTER);
        board_imagine.setVerticalAlignment(SwingConstants.CENTER);

        window.add(board_imagine);



/*
        JButton jb1 = new JButton("1");
        JButton jb2 = new JButton("2");
        JButton jb3 = new JButton("3");
        JButton jb4 = new JButton("4");
        JButton jb5 = new JButton("5");
        JButton jb6 = new JButton("6");
        JButton jb7 = new JButton("");
        JButton jb8 = new JButton("");
        JButton jb9 = new JButton("");
        JButton jb10 = new JButton("");
        JButton jb11= new JButton("");
        JButton jb12= new JButton("");
        JButton jb13= new JButton("");
        JButton jb14= new JButton("");
        JButton jb15= new JButton("");
        JButton jb16= new JButton("");
        JButton jb17= new JButton("");
        JButton jb18= new JButton("");
        JButton jb19= new JButton("");
        JButton jb20= new JButton("");
        JButton jb21= new JButton("");
        JButton jb22= new JButton("");
        JButton jb23= new JButton("");
        JButton jb24= new JButton("");
        JButton jb25= new JButton("");

*/

        // Define the panel to hold the buttons





        // Define the panel to hold the buttons




    /*    panel.add(jb1);
        panel.add(jb2);
        panel.add(jb3);
        panel.add(jb4);
        panel.add(jb5);
        panel.add(jb6);
        panel.add(jb7);
        panel.add(jb8);
        panel.add(jb9);
        panel.add(jb10);
        panel.add(jb11);
        panel.add(jb12);
        panel.add(jb13);
        panel.add(jb14);
        panel.add(jb15);
        panel.add(jb16);
        panel.add(jb17);
        panel.add(jb18);
        panel.add(jb19);
        panel.add(jb20);
        panel.add(jb21);
        panel.add(jb22);
        panel.add(jb23);
        panel.add(jb24);
        panel.add(jb25);
*/



        //add panel to the frame




        //Sizes the frame so that all its contents are at or above their preferred sizes.

        window.pack();
    }
}
