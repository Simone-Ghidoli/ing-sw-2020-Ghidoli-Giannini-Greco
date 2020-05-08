package it.polimi.ingsw.ps60.clientSide.view.cliGuiMethods;

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
        assertEquals("I did it",result[0].equals("nome"),true);
    }

    @Test
    public void firstSetWorkers(){
        int[][] choice;
        CLIMethods cli=new CLIMethods();
        List<int[]>impossiblePosition=new ArrayList<>();
        String in="5\n4\n4\n5";
        System.setIn(new ByteArrayInputStream(in.getBytes()));
        impossiblePosition.add(new int[]{0,0});
        impossiblePosition.add(new int[]{0,1});
        choice=cli.firstSetWorkers(impossiblePosition);
        System.out.println("ciao");
    }
}
