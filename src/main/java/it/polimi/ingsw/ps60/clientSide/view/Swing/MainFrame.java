package it.polimi.ingsw.ps60.clientSide.view.Swing;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    private JFrame boardWindow;
    private JFrame userInterations;
    private JFrame setupNicknameBirthday;
    private JFrame number;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    boolean pressed=false;
    private int numberOfPlayers;


    private final JButton[][] tiles = new JButton[5][5];

    public static void main(String[] args) {

        MainFrame mainFrame = new MainFrame();

    }


    public MainFrame() {
        {
            number =new JFrame();
            number.setLayout(new GridLayout(3,1));
            number.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent){
                    System.exit(0);
                }
            });
            number.setSize(screenSize.width/4,screenSize.height/4);
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
        }
    }
}
