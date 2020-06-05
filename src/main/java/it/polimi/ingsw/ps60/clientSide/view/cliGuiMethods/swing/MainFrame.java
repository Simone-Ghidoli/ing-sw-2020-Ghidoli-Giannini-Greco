package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.swing;
import it.polimi.ingsw.ps60.GlobalVariables;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;


/**
 * This class menage the main frame of the program
 */
public class MainFrame extends JPanel {
    private final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final JButton[] jButtons = new JButton[25];
    final JPanel grid = new JPanel();
    final JPanel opponents = new JPanel();
    final JPanel divinityCard = new JPanel();
    final JPanel workers = new JPanel();
    final JPanel info = new JPanel();


    public MainFrame() {
        super();

        JLabel board = new JLabel();
        try {
            board.setIcon(new ImageIcon(ImageIO.read(imageFileReader("board/SantoriniBoard.png")).getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH)));
        } catch (IOException e){
            System.out.println("Error in image reading");
        }

        setLayout(new GridBagLayout());
        grid.setPreferredSize(new Dimension(screenSize.width * 12 / 29, screenSize.height * 47 / 64));
        grid.setLayout(new GridLayout(5, 5));
        grid.setOpaque(false);
        opponents.setPreferredSize(new Dimension(screenSize.width * 17 / 58, screenSize.height));
        opponents.setOpaque(false);
        opponents.setLayout(new GridBagLayout());
        divinityCard.setPreferredSize(new Dimension(screenSize.width * 17 / 58, screenSize.height));
        divinityCard.setOpaque(false);
        divinityCard.setLayout(new GridBagLayout());
        workers.setPreferredSize(new Dimension(screenSize.width * 12 / 29, screenSize.height * 7 / 55));
        workers.setOpaque(false);
        workers.setLayout(new FlowLayout());
        info.setPreferredSize(new Dimension(screenSize.width * 12 / 29, screenSize.height * 2 / 17));
        info.setOpaque(false);
        info.setLayout(new GridBagLayout());
        board.setLayout(new BorderLayout());
        board.add(grid, BorderLayout.CENTER);
        board.add(opponents, BorderLayout.WEST);
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

    /**
     * This method returns a jButton from the grid of jButtons
     *
     * @param i the number of button
     * @return the jButton selected
     */
    public JButton getButton(int i) {
        return jButtons[i];
    }

    /**
     * This method with a button returns his position of the grid
     *
     * @param jButton is the jButton of which do you want the position
     * @return the coordinates of the button
     */
    public int[] getButtonCoords(JButton jButton) {
        for (int i = 0; i < jButtons.length; i++) {
            if (jButtons[i].equals(jButton))
                return new int[]{i / 5, i % 5};
        }
        return null;
    }

    /**
     * This method returns the dimension of the screen
     *
     * @return the dimension of the screen
     */
    public Dimension getScreenSize() {
        return screenSize;
    }

    /**
     * This method sets the images of divinity cards in the main frame
     *
     * @param divinityCards contains the number associated to all divinity cards of the players ordered by the player number
     * @param turnNumber    is the player number of the player
     */
    public void setDivinityCardImage(int[] divinityCards, int turnNumber) {

        try {
        JLabel divinityCardImage;
        for (int i = 0; i < divinityCards.length; i++) {
            if (i == turnNumber) {

                divinityCardImage = new JLabel(new ImageIcon(ImageIO.read(imageFileReader(GlobalVariables.DivinityCard.values()[divinityCards[i]].getSourcePosition())).getScaledInstance(screenSize.width * 9 / 58, screenSize.height / 2, Image.SCALE_SMOOTH)));
                divinityCardImage.setBorder(
                        BorderFactory.createTitledBorder(
                                BorderFactory.createEtchedBorder(
                                        EtchedBorder.RAISED, GlobalVariables.Colour.values()[i].getColor(),
                                        GlobalVariables.Colour.values()[i].getColor()), "You are player number " + (i + 1)));
                divinityCard.add(divinityCardImage);
            } else {

                divinityCardImage = new JLabel(new ImageIcon(ImageIO.read(imageFileReader(GlobalVariables.DivinityCard.values()[divinityCards[i]].getSourcePosition())).getScaledInstance(screenSize.width * 9 / 116, screenSize.height / 4, Image.SCALE_SMOOTH)));
                divinityCardImage.setBorder(
                        BorderFactory.createTitledBorder(
                                BorderFactory.createEtchedBorder(
                                        EtchedBorder.RAISED, GlobalVariables.Colour.values()[i].getColor(),
                                        GlobalVariables.Colour.values()[i].getColor()), "Player number " + (i + 1)));
                opponents.add(divinityCardImage);
            }
        }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * This methods removes all the listeners from the jButtons
     */
    public void resetButtons() {
        for (JButton button : jButtons) {
            for (ActionListener actionListener : button.getActionListeners()) {
                button.removeActionListener(actionListener);
            }
            button.setEnabled(false);
        }
    }

    public File imageFileReader(String pathToImage){
        return new File(Objects.requireNonNull(classLoader.getResource(pathToImage)).getFile());
    }
}