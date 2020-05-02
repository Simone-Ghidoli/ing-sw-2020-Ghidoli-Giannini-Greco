package it.polimi.ingsw.ps60.clientSide.view.Swing;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private final JButton[][] tiles = new JButton[5][5];

    public static void main(String[] args) {

        MainFrame mainFrame = new MainFrame();

    }


    public MainFrame() {
        {
            JFrame setupNicknameBirthday=new JFrame("Nickname & birth date");
            setupNicknameBirthday.setResizable(false);
            setupNicknameBirthday.setLocationRelativeTo(null);
            setupNicknameBirthday.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setupNicknameBirthday.setPreferredSize(new Dimension(screenSize.width, screenSize.height*1/2));
            setupNicknameBirthday.setVisible(true);
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


            setupNicknameBirthday.pack();
        }
    }
}
