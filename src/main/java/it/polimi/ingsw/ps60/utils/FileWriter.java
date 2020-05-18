package it.polimi.ingsw.ps60.utils;

import it.polimi.ingsw.ps60.serverSide.model.Board;

import java.io.*;

public class FileWriter {
    private static String path = null;
    FileOutputStream outStream;
    ObjectOutputStream objOut;

    public FileWriter(String filePath){
        path=filePath;
        try {
            outStream = new FileOutputStream(path);
            objOut=new ObjectOutputStream(outStream);
        }
        catch(IOException e){

        }
    }

    public void writer(Board board){
        try {
            objOut.writeObject(outStream);
            System.out.println("Items succesfully written to file");
        }
        catch(IOException e_1){
            //todo Gestire il fallimento della scrittura su file
        }
    }
}
