package it.polimi.ingsw.ps60.utils;

import java.util.List;

import static it.polimi.ingsw.ps60.GlobalVariables.game;

public class TestUtilities {

    public boolean checkNodes(List<int[]> current,int[] expected){
        for(int i=0;i<current.size();i++){
            if(expected[0]==current.get(i)[0]) {
                if (expected[1]==current.get(i)[1]){
                    return true;
                }
            }
        }
        return false;
    }

    public void buildsNTimes(int[] position, int numberOfTimes){
        for (int i = 0; i < numberOfTimes; i++)
            game.getCellByPosition(position).incrementBuildingLevel();
    }

    public void buildDome(int[] position){
        game.getCellByPosition(position).buildDome();
    }
}
