package it.polimi.ingsw.ps60.utils;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class TestUtilities {

    public void buildsNTimes(int[] position, int numberOfTimes){
        for (int i = 0; i < numberOfTimes; i++)
            game.getCellByPosition(position).incrementBuildingLevel();
    }

    public void buildDome(int[] position){
        game.getCellByPosition(position).buildDome();
    }
}
