/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex_1_Substiitution_Techniques;

import java.util.*;
import java.util.Scanner;

/**
 *
 * @author Shanmuga Priya M
 */
public class Ex_1B_Playfair_Cipher {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useDelimiter("\n");

        System.out.print("Enter Key : ");
        String key = sc.next();
        System.out.print("Enter Plain Text : ");
        String txt = sc.next();
        System.out.print("Enter Filler Letter : ");
        char filler = sc.next().charAt(0);

        //Convert all letters into upper case
        key = key.toUpperCase();
        txt = txt.toUpperCase();
        filler = ("" + filler).toUpperCase().charAt(0);

        //Display the recieved Details
        System.out.println("Key : " + key + "\n");
        System.out.println("Plain Text : " + txt + "\n");
        System.out.println("Filler Letter : " + filler + "\n");

        //Remove all spaces from Plain Text
        txt = txt.replaceAll("\\s", "");

        //Modify -> (OO into OXOX) (OOL into OXOL)  [let 'X' be the filler letter]
        StringBuilder sb = new StringBuilder(txt);
        for (int ii = 0; ii < sb.length() - 1; ii = ii + 2) {
            //if its not the last pair
            if (sb.charAt(ii) == sb.charAt(ii + 1) && (ii + 1) != sb.length() - 1) {
                sb.insert(ii + 1, 'X');
            }
            //if it is the last pair
            if (sb.charAt(ii) == sb.charAt(ii + 1) && (ii + 1) == sb.length() - 1) {
                sb.insert(ii + 1, 'X');
                //sb.append(filler);
            }
        }

        //Add Filler if length is odd
        txt = sb.toString();
        if (txt.length() % 2 != 0) {
            txt += filler;
        }

        //Fill the Key in Matrix
        char[][] board = new char[5][5];
        List<Character> lst = new ArrayList<>();
        int idx=0;
        int i=0,j=0;
        for( i=0;i<5;i++){
            for(j=0;j<5;j++){
                if(idx==key.length()){
                    break;
                }
                char c=key.charAt(idx++);
                if (c == 'J') {
                    c = 'I';
                }   
                if (!lst.contains(c)){
                    board[i][j]=c;
                    lst.add(c);
                }
                
            }
            if(idx==key.length()){
                    break;
                }
        }
        
        //Fill Remaining Spaces in Matrix
        char ascii = 65;
        for (int k1 = i; k1 < 5; k1++) {
            for (int k2 = j; k2 < 5; k2++) {
                if (!lst.contains(ascii) && ascii != 'J') {
                    board[k1][k2] = ascii;
                    lst.add(ascii);
                } else {
                    k2--;
                }
                ascii++;
            }
            j = 0;
        }

        //Print Matrix
        System.out.println("Cipher Matrix :\n");
        printMatrix(board);
        
        //Do Encryption
        String res = encrypt(txt, board);

        //Format Output
        String OriginalTxt = txt.replaceAll("..", "$0 ");

        //Display Output
        System.out.println("Original Txt  : " + OriginalTxt);
        System.out.println("Encrypted Txt : " + res);

    }

    private static void printMatrix(char[][] board) {
        for (int k1 = 0; k1 < 5; k1++) {
            for (int k2 = 0; k2 < 5; k2++) {
                System.out.print(board[k1][k2] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    private static String encrypt(String txt, char[][] board) {
        String res = "";
        HashMap<String, Integer> hmap = new HashMap<>();
        for (int ii = 0; ii < txt.length(); ii = ii + 2) {
            char x = txt.charAt(ii);
            char y = txt.charAt(ii + 1);
            if (x == y) {
                y = 'X';
            }
            findIdx(x, y, hmap, board);
            int xi = hmap.get("xi");
            int xj = hmap.get("xj");
            int yi = hmap.get("yi");
            int yj = hmap.get("yj");

            //same column
            if (xj == yj) {
                res += board[(xi + 1) % 5][xj];
                res += board[(yi + 1) % 5][yj];
            } 
            //same row
            else if (xi == yi) {
                res += board[xi][(xj + 1) % 5];
                res += board[yi][(yj + 1) % 5];
            }
            //neither
            else {
                res += board[xi][yj];
                res += board[yi][xj];
            }
            hmap.clear();
            res += " ";
        }
        return res;
    }

    private static void findIdx(char x, char y, HashMap<String, Integer> hmap, char[][] board) {
        if (x == 'J') {
            x = 'I';
        }
        if (y == 'J') {
            y = 'I';
        }
        for (int k1 = 0; k1 < 5; k1++) {
            for (int k2 = 0; k2 < 5; k2++) {
                if (board[k1][k2] == x) {
                    hmap.put("xi", k1);
                    hmap.put("xj", k2);
                }
                if (board[k1][k2] == y) {
                    hmap.put("yi", k1);
                    hmap.put("yj", k2);
                }
            }
        }
    }
}
