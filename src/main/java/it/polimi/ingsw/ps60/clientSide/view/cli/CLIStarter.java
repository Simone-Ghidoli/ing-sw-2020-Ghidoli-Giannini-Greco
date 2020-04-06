package it.polimi.ingsw.ps60.clientSide.view.cli;

import it.polimi.ingsw.ps60.clientSide.view.client.ClientStarter;

public class CLIStarter {
    public static ClientStarter client; //Chiamata provvisoria e modificabile in qualiasi momento. Non mi serve per forza qui
    public static void main(String[] args) throws InterruptedException {
        client=new ClientStarter(8996,"localhost");
        client.avvioClient();
    }
}
