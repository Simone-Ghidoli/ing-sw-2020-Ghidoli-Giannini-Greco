package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.Swing;
import it.polimi.ingsw.ps60.GlobalVariables;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JPanel {
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final JButton[] jButtons = new JButton[25];
    private JLabel divinityCardImage;

    public MainFrame() {
        super();
        final JPanel grid = new JPanel();
        final JPanel players = new JPanel();
        final JPanel divinityCard = new JPanel();
        final JPanel workers = new JPanel();
        final JPanel info = new JPanel();
        final JLabel board = new JLabel(new ImageIcon(new ImageIcon("src/resources/board/SantoriniBoard.png").getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH)));

        setLayout(new GridBagLayout());
        grid.setPreferredSize(new Dimension(screenSize.width * 12 / 29, screenSize.height * 47 / 64));
        grid.setLayout(new GridLayout(5, 5));
        grid.setOpaque(false);
        players.setPreferredSize(new Dimension(screenSize.width * 17 / 58, screenSize.height));
        players.setOpaque(false);
        players.setLayout(new GridLayout(3, 1));
        divinityCard.setSize(new Dimension(screenSize.width * 17 / 58, screenSize.height));
        divinityCard.setOpaque(false);
        divinityCardImage = new JLabel(new ImageIcon(new ImageIcon(GlobalVariables.DivinityCard.NONE.getSourcePosition()).getImage().getScaledInstance(screenSize.width * 17 / 58, screenSize.height, Image.SCALE_SMOOTH)));
        divinityCard.add(divinityCardImage);
        workers.setPreferredSize(new Dimension(screenSize.width * 12 / 29, screenSize.height * 2 / 17));
        workers.setOpaque(false);
        workers.setLayout(new FlowLayout());
        info.setPreferredSize(new Dimension(screenSize.width * 12 / 29, screenSize.height * 2 / 17));
        info.setOpaque(false);
        info.setLayout(new GridBagLayout());
        board.setLayout(new BorderLayout());
        board.add(grid, BorderLayout.CENTER);
        board.add(players, BorderLayout.WEST);
        board.add(divinityCard, BorderLayout.EAST);
        board.add(workers, BorderLayout.NORTH);
        board.add(info, BorderLayout.SOUTH);

        for (int i = 0; i < 25; i++) {
            jButtons[i] = new JButton();
            jButtons[i].setContentAreaFilled(false);
            jButtons[i].setOpaque(false);
            grid.add(jButtons[i]);
        }
        add(board);
    }

    public JButton getButton(int i){
        return jButtons[i];
    }

    public int[] getButtonCoords(JButton jButton){
        for (int i = 0; i < jButtons.length; i++){
            if (jButtons[i].equals(jButton))
                return new int[]{i / 5, i % 5};
        }
        return null;
    }

    public Dimension getScreenSize() {
        return screenSize;
    }

    public void setDivinityCardImage(GlobalVariables.DivinityCard divinityCard) {
        divinityCardImage = new JLabel(new ImageIcon(new ImageIcon(divinityCard.getSourcePosition()).getImage().getScaledInstance(screenSize.width * 17 / 58, screenSize.height, Image.SCALE_SMOOTH)));
    }
}
