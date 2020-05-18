package it.polimi.ingsw.ps60.utils;

import it.polimi.ingsw.ps60.serverSide.model.Board;

import java.io.*;

public class FileAccess {

    ObjectOutputStream objOut;
    ObjectInputStream objectInputStream;

    public FileAccess(){
        try {
            FileOutputStream outStream = new FileOutputStream("src/resources/save");
            objOut = new ObjectOutputStream(outStream);
            FileInputStream inputStream = new FileInputStream("src/resources/save");
            objectInputStream = new ObjectInputStream(inputStream);
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Problems found");
        }
    }

    public void writer(Board board){
        try {
            System.out.println("Saving...");
            objOut.writeObject(board);
            System.out.println("Items successfully written to file");
        }
        catch(IOException e_1){
            System.out.println("Writing failed");
        }
    }

    public Board reader(){
        try {
            return (Board) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Save not found");
            return null;
        }
    }
}
