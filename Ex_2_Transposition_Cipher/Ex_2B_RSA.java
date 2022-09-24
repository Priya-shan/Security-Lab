/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex_2_Transposition_Cipher;

import java.math.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Shanmuga Priya M
 */
public class Ex_2B_RSA {

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int p = 7;
        int q = 11;
        System.out.print("Enter plain text :");
        char input=sc.next().charAt(0);
        HashMap<Character,Integer> hmap=new HashMap<>();
        HashMap<Integer,Character> hmap2=new HashMap<>();
        int ascii=97;
        for(int i=1;i<=26;i++){
            hmap.put((char)ascii,i);
            hmap2.put(i,(char)ascii);
            ascii++;
        }
        int plainText=hmap.get(input);
        int n = p * q;
        int siN = (p - 1) * (q - 1);

        //find e -> gcd ==1
        int e = find_e(2,siN);
        System.out.println("Value of e is :"+e);
        //find d
        int d = find_d(siN,e);
        System.out.println("Value of d is :"+d);
        // encryption
        double cipher = (Math.pow(plainText, e)) % n;
        
        int ciph=(int)cipher%26;
        System.out.println("Cipher Text Value : "+ciph);
        System.out.println("Cipher Text after Encryption is : "+hmap2.get(ciph));
        
        BigInteger big_n = BigInteger.valueOf(n);
        
        BigInteger big_cipher = BigDecimal.valueOf(cipher).toBigInteger();
        BigInteger decrypt = (big_cipher.pow(d)).mod(big_n);
        System.out.println("Plain Text value is : "+decrypt);
        int intDecrypt = decrypt.intValue();
        System.out.println("Plain Text after decryption is : "+hmap2.get(intDecrypt));
        

    }

    private static int find_e(int a,int b) {
        int gcd = 0;
        while(gcd!=1){
        for (int i = 1; i <= a && i <= b; i++) {
            if (a % i == 0 && b % i == 0) {
                gcd = i;
            }
        }
         a++;
    }
        return a-1;
        
    }

    private static int find_d(int siN,int e) {
        int i=1;
        while(i>0){
            int temp=(siN*i)+1;
            if(temp%e==0){
                return temp/e;
            }
            i++;
        }
        return -1;
    }
}
