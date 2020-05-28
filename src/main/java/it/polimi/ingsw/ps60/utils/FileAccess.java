package it.polimi.ingsw.ps60.utils;

import it.polimi.ingsw.ps60.serverSide.model.Board;

import java.io.*;

public class FileAccess {

    private FileOutputStream outputStream = null;
    private ObjectOutputStream objectOutputStream = null;
    private FileInputStream inputStream = null;
    private ObjectInputStream objectInputStream = null;

    /**
     * This method will write the board in a file
     *
     * @param board board to write
     */
    public void writer(Board board) {

        try {
            outputStream = new FileOutputStream("src/resources/save");
            objectOutputStream = new ObjectOutputStream(outputStream);
            System.out.println("Saving...");
            objectOutputStream.writeObject(board);
            System.out.println("Items successfully written to file");
        } catch (IOException e_1) {
            e_1.printStackTrace();
            System.out.println("Writing failed");
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();

                if (objectOutputStream != null)
                    objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * This method will read the board from a file
     *
     * @return board stored in a file
     */
    public Board reader() {
        Board board = null;

        try {
            inputStream = new FileInputStream("src/resources/save");
            objectInputStream = new ObjectInputStream(inputStream);
            board = (Board) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Save not found");
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();

                if (objectInputStream != null)
                    objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return board;
    }
}