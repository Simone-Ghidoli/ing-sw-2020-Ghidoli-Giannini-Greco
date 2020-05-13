package it.polimi.ingsw.ps60.clientSide.view.Swing;

import it.polimi.ingsw.ps60.GlobalVariables;

import javax.swing.*;
import java.awt.*;

public class WorkerImage extends SwingWorker<ImageIcon,Void > {
    private JButton jButton;
    private int numberOfPlayers;
    public WorkerImage(JButton jButton, int numberOfPlayers){
        this.jButton=jButton;
        this.numberOfPlayers=numberOfPlayers;

    }
    @Override
    protected ImageIcon doInBackground() throws Exception {
        Thread.sleep(1000);
        ImageIcon imagineWorker = new ImageIcon(GlobalVariables.IdPlayer.values()[numberOfPlayers].getSourcePawn());
        Image scaleImageWorker = imagineWorker.getImage().getScaledInstance(jButton.getWidth(), jButton.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon worker= new ImageIcon(scaleImageWorker);
        return worker;
    }

}
