/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.tictactoe.Utils;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author son
 */
public class utils {
    public static String getCoords(String pos){
        String[] segment = pos.split(",");
        return segment[1] + "_" + segment[2];
    }

    public static void writeFile(String filename, String content){
        try
        {
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write(content + "\n");//appends the string to the file
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
}
