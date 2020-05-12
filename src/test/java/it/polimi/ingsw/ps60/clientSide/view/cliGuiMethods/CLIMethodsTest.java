package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods;

import it.polimi.ingsw.ps60.GlobalVariables;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Test;
import it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods.CLIMethods;

import javax.xml.crypto.Data;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLIMethodsTest {

    @Before
    public void setUp(){
        CLIMethods cli=new CLIMethods();

    }

    @Test
    public void nickBirthTest(){
        CLIMethods cli=new CLIMethods();
        System.setIn(new ByteArrayInputStream("nome\n1997/01/01".getBytes()));
        //System.setIn(new ByteArrayInputStream("1997/01/01".getBytes()));
        String[] result=cli.nicknameBirthdayChoice();
    }

    @Test
    public void firstSetWorkers(){
        int[][] choice;
        CLIMethods cli=new CLIMethods();
        List<int[]>impossiblePosition=new ArrayList<>();
        String in="1\n1\n4\n5\n5\n4";
        //String in="4\n5\n5\n4";
        System.setIn(new ByteArrayInputStream(in.getBytes()));
        impossiblePosition.add(new int[]{0,0});
        impossiblePosition.add(new int[]{0,1});
        choice=cli.firstSetWorkers(impossiblePosition);
        System.out.println("ciao");
    }

    @Test
    public void divinceChoiceTest(){
        CLIMethods cli=new CLIMethods();
        GlobalVariables.DivinityCard[] choice;
        String in="3\n4\n5";
        System.setIn(new ByteArrayInputStream(in.getBytes()));
        choice=cli.cardChoices(3);
        System.out.println("ciao");
    }

    @Test
    public void divinitySelectionTest(){
        CLIMethods cli=new CLIMethods();
        GlobalVariables.DivinityCard choice;
        GlobalVariables.DivinityCard[] input=new GlobalVariables.DivinityCard[3];
        input[0]= GlobalVariables.DivinityCard.ATHENA;
        input[1]= GlobalVariables.DivinityCard.DEMETER;
        input[2]= GlobalVariables.DivinityCard.APOLLO;
        String user="1";
        System.setIn(new ByteArrayInputStream(user.getBytes()));
        choice=cli.divinitySelection(input);
        System.out.println("ciao");
    }

    @Test
    public void specialChioceTest(){
        CLIMethods cli=new CLIMethods();
        String input="0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        boolean choice=cli.specialChoices("Bella povcona");
        System.out.println("ciao");
    }


    @Test
    public void moveChoiceTest(){
        CLIMethods cli=new CLIMethods();
        List<int[]>[] possibleMoves=new ArrayList[2];
        possibleMoves[0]=new ArrayList<>();
        possibleMoves[1]=new ArrayList<>();
        int[][] positionWorkers=new int[2][2];
        positionWorkers[0][0]=1;
        positionWorkers[0][1]=1;
        positionWorkers[1][0]=2;
        positionWorkers[1][1]=2;
        for(int i=1;i<4;i++){
            //int[] full=new int[2];
            //full[0]=i;
            //full[1]=i-1;
            possibleMoves[0].add(new int[]{i,i+1});
        }
        for(int i=1;i<4;i++){
            int[] full=new int[2];
            full[0]=i;
            full[1]=i-1;
            possibleMoves[1].add(full);
        }
        String inputs="3";
        System.setIn(new ByteArrayInputStream(inputs.getBytes()));
        int choice = cli.moveChoice(possibleMoves,positionWorkers);
        System.out.println("ciao");
    }
}
