/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ex_1_Substiitution_Techniques;

import java.util.*;

/**
 *
 * @author Shanmuga Priya M
 */
public class Ex_1E_Rail_Fence {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useDelimiter("\n");
        String txt = sc.next();
        String key = sc.next();
        int row = (txt.length() / key.length()) + 1;
        int col = key.length();
        char[][] board = new char[row][col];
        int idx = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (idx == txt.length()) {
                    break;
                }
                board[i][j] = txt.charAt(idx++);
            }
            if (idx == txt.length()) {
                break;
            }
        }
        for (char[] arr : board) {
            for (char c : arr) {
                System.out.print(c + " ");
            }
            System.out.println("");
        }
        HashMap<Integer, Integer> hmap = new HashMap<>();
       
        int ii = 0;
        for (char c : key.toCharArray()) {
            hmap.put(Integer.parseInt(c+""), ii);
            ii++;
        }
       
        //ENCRYPTION
        String cipher="";
        for (int i = 0; i < col; i++) {
            int val = hmap.get(i+1);
            for (int j=0;j<row;j++) {
                cipher+=board[j][val];
               
            }
        }
        System.out.println("Encrypted Text : "+cipher);
       
        //DECRYPTION                                                                  
       
    }
}
