package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.Swing;

import it.polimi.ingsw.ps60.GlobalVariables;
import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.GUIMethods;
import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.ViewMethodSelection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class WorkerImage {

    public static void main(String[] args) {
        ViewMethodSelection v = new GUIMethods();
       GlobalVariables.DivinityCard[] cards=v.cardChoices(3);
       v.divinitySelection(cards);
    }
}
