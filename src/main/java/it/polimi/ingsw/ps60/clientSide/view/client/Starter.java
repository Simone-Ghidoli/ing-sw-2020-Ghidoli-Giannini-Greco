package it.polimi.ingsw.ps60.clientSide.view.client;

import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.ViewMethodSelection;

public class Starter {

    public static void start(ViewMethodSelection viewMethodSelection) throws InterruptedException {
        String[] strings = viewMethodSelection.ipPortChoices();
    ClientStarter clientStarter = new ClientStarter(Integer.parseInt(strings[1]), strings[0],viewMethodSelection);
    clientStarter.pippo();
    }
}
