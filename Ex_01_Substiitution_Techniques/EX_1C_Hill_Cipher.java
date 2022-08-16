/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ex_01_Substiitution_Techniques;

import java.util.*;

/**
 *
 * @author Shanmuga Priya M
 */
public class EX_1C_Hill_Cipher {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useDelimiter("\n");
        
        //get inputs
        System.out.print("Enter Key : ");
        String k=sc.next();
        System.out.print("Enter Plain Text : ");
        String txt = sc.next();
        
        //create two hashmaps(a->1 && 1->a)
        char ascii = 65;
        HashMap<Character, Integer> hmap1 = new HashMap<>();
        HashMap<Integer, Character> hmap2 = new HashMap<>();
        for (int i = 0; i <26; i++) {
            hmap1.put(ascii, i);
            hmap2.put(i, ascii);
            ascii++;
        }
        
        //create key matrix from key
        int idx=0;
        int[][] key = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                key[i][j]=hmap1.get(k.charAt(idx++))+1;
            }
        }
        
        //Print key Matrix
        System.out.println("Key Matrix : ");
        for(int[] arr:key){
            for(int i:arr){
                System.out.print(i+" ");
            }
            System.out.println("");
        }
        
        //Remove all spaces in the string
        txt = txt.replaceAll("\\s", "");
        
        //seperate in 3,3 pairs and send it to encrypt process
        String res = "";
        String temp;
        for (int start = 0; start <= txt.length()-3; start=start+3) {
            int end=start+3;
            temp =txt.substring(start,end);
            res+=process(temp, key, hmap1, hmap2);
        }
        
        //print the encrypted data
        System.out.println("Encrypted Data : "+res);
    }

    private static String process(String temp, int[][] key, HashMap<Character, Integer> hmap1, HashMap<Integer, Character> hmap2) {
        int mod;
        String res="";
        for (int i = 0; i < 3; i++) {
            int sum=0;
            for (int j = 0; j < 3; j++) {
                char c = temp.charAt(j);
                int loc = hmap1.get(c);
                sum=sum+loc*(key[i][j]);
            }
            mod=sum%26;
            res+=hmap2.get(mod);
        }
        return res;
    }
}
