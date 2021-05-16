/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.tictactoe.Utils;

/**
 *
 * @author son
 */
public class utils {
    public static String getCoords(String pos){
        String[] segment = pos.split(",");
        return segment[1] + "_" + segment[2];
    }
}
