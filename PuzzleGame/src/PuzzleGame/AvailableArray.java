/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PuzzleGame;

import java.util.Random;

/**
 *
 * @author viet1
 */
public class AvailableArray {
    private Integer[][] available = {
        {7, 2, 1, 8, 9, 3, 6, 5, 4},
        {7, 6, 8, 4, 9, 5, 1, 2, 3},
        {7, 5, 8, 2, 3, 6, 9, 4, 1},
        {8, 6, 2, 1, 5, 9, 7, 4, 3},
        {9, 6, 1, 3, 8, 2, 7, 4, 5},
        {4, 2, 7, 9, 5, 3, 1, 8, 6},
        {7, 5, 3, 1, 4, 8, 9, 6, 2},
        {3, 1, 6, 4, 5, 2, 9, 8, 7},
        {2, 4, 5, 9, 3, 1, 7, 8, 6},
        {2, 6, 8, 9, 4, 5, 1, 3, 7},
        {3, 6, 1, 7, 8, 2, 4, 5, 9},
        {1, 9, 8, 4, 6, 2, 3, 7, 5},
        {8, 4, 6, 9, 2, 3, 5, 7, 1},
        {8, 1, 7, 4, 9, 2, 6, 3, 5},
        {6, 5, 1, 7, 3, 9, 8, 4, 2},
        {4, 9, 5, 1, 3, 7, 8, 6, 2},
        {2, 1, 5, 7, 9, 8, 6, 4, 3}
    };
    
    public Integer[] ranD(){
        Random rd = new Random();
        return available[rd.nextInt(available.length)];
    }
}
