/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex_01_Substiitution_Techniques;

import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Shanmuga Priya M
 */
public class Ex_1A_Ceasar_Cipher {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useDelimiter("\n");

        System.out.print("Enter Key : ");
        int key = sc.nextInt();
        System.out.print("Enter Plain Text : ");
        String txt = sc.next();
        txt=txt.toUpperCase();
        
        char ascii = 65;
        HashMap<Character, Integer> hmap1 = new HashMap<>();
        for (int i = 1; i <= 26; i++) {
                hmap1.put(ascii, i);
                ascii++;
        }
        ascii = 65;
        HashMap<Integer,Character> hmap2 = new HashMap<>();
        for (int i = 1; i <= 26; i++) {
                hmap2.put(i,ascii);
                ascii++;
        }
        String EncryptedData = Encrypt(txt,key,hmap1,hmap2);
        String DecryptedData = Decrypt(txt,key,hmap1,hmap2);
        
        System.out.println("EncryptedData : "+EncryptedData);
        System.out.println("DecryptedData : "+DecryptedData);
    }

    private static String Encrypt(String txt, int key, HashMap<Character, Integer> hmap1, HashMap<Integer, Character> hmap2) {
        String res="";
        for(char c:txt.toCharArray()){
            if(c==' '){
                res+=' ';
                continue;
            }
            int loc=hmap1.get(c);
            loc=(loc+key)%26;
            if(loc<0){
                loc=26+loc;
            }
            res+=hmap2.get(loc);
        }
       return res;
    }

    private static String Decrypt(String txt, int key, HashMap<Character, Integer> hmap1, HashMap<Integer, Character> hmap2) {
        String res="";
        for(char c:txt.toCharArray()){
            if(c==' '){
                res+=' ';
                continue;
            }
            int loc=hmap1.get(c);
            loc=(loc-key);
            if(loc<=0){
                loc=26+loc;
            }
            res+=hmap2.get(loc);
        }
       return res;
    }
}
