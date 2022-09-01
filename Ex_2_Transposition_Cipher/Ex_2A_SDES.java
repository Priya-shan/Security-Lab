/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex_2_Transposition_Cipher;

import java.util.*;

/**
 *
 * @author Shanmuga Priya M
 */
public class Ex_2A_SDES {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String key = "1010000010";
        String plainText = "10010111";
        System.out.println("Given Key :"+key);
        System.out.println("Given Plain Text :"+plainText);
        //Encryption Process
        System.out.println("\n----------------------------------------------------");
        System.out.println("Encryption Process Starts");
        String cipherText = Encryption(key, plainText);
        System.out.println("\nEncrypted Cipher Text :" + cipherText);
        System.out.println("----------------------------------------------------\n");
        //Decryption Process
        System.out.println("----------------------------------------------------");
        System.out.println("Decryption Process Starts");
        String OriginalMsg = Decryption(key, cipherText);
        System.out.println("\nDecrypted Plain Text :" + OriginalMsg+"\n");
        System.out.println("----------------------------------------------------");
    }

    
    private static String Encryption(String key, String plainText) {
        
        //key generation
        String[] keys = generateKey(key);
        String key1 = keys[0];
        String key2 = keys[1];
        System.out.println("\nStep 0 -> Generating Keys");
        System.out.println("Key1 : "+key1);
        System.out.println("Key2 : "+key2+"\n");
        
        
        //Initial Permutation
        String IpResult = ApplyIP(plainText);
        String IpLeft = getLeft(IpResult);
        String IpRight = getRight(IpResult);
        System.out.println("Step 1 -> After Initial Permutation :"+IpResult);
        
        //Function with key1
        String first_func_result = function(key1, IpLeft, IpRight);
        String first_func_result_left = getLeft(first_func_result);
        String first_func_result_right = getRight(first_func_result);
        System.out.println("\nStep 2 -> After Function F(x) with Key1 :"+first_func_result);
        
        //Switching
        String switchResult = Switch(first_func_result_left, first_func_result_right);
        String switch_left = getLeft(switchResult);
        String switch_right = getRight(switchResult);
        System.out.println("\nStep 3 -> After Switching :"+switchResult);
        
        //Function with Key2
        String second_func_result = function(key2, switch_left, switch_right);
        System.out.println("\nStep 4 -> After Function F(x) with Key2 :"+second_func_result);
        
        //Inverse Initial Permutation
        String cipherText = ApplyIPInverse(second_func_result);
        System.out.println("\nStep 5 -> After Inverse Initial Permutation :"+cipherText);
        return cipherText;
    }

    private static String Decryption(String key, String cipherText) {
        
        //key generation
        String[] keys = generateKey(key);
        String key1 = keys[0];
        String key2 = keys[1];
        System.out.println("\nStep 0 -> Generating Keys");
        System.out.println("Key1 : "+key1);
        System.out.println("Key2 : "+key2);
        
        //Initial Permutation
        String IpResult = ApplyIP(cipherText);
        String IpLeft = getLeft(IpResult);
        String IpRight = getRight(IpResult);
        System.out.println("\nStep 1 -> After Initial Permutation :"+IpResult);
        
        //Function with key2
        String first_func_result = function(key2, IpLeft, IpRight);
        String first_func_result_left = getLeft(first_func_result);
        String first_func_result_right = getRight(first_func_result);
        System.out.println("\nStep 2 -> After Function F(x) with Key2 :"+first_func_result);
        
        //Switching
        String switchResult = Switch(first_func_result_left, first_func_result_right);
        String switch_left = getLeft(switchResult);
        String switch_right = getRight(switchResult);
        System.out.println("\nStep 3 -> After Switching :"+switchResult);
        
        //Function with key1
        String second_func_result = function(key1, switch_left, switch_right);
        System.out.println("\nStep 4 -> After Function F(x) with Key1 :"+second_func_result);
        
        //Inverse Initial Permutation
        String plainText = ApplyIPInverse(second_func_result);
        System.out.println("\nStep 5 -> After Inverse Initial Permutation :"+plainText);
        return plainText;
    }

    private static String function(String key, String leftText, String rightText) {
        String EpResult = ApplyEP(rightText);
//            System.out.println("epresult" + EpResult);
        EpResult = xor(key, EpResult);
//            System.out.println("xorepresult" + EpResult);
        String EpLeft = getLeft(EpResult);
        String EpRight = getRight(EpResult);
        String SboxResult = getSbox(EpLeft, EpRight);
//            System.out.println("sbox" + SboxResult);
        String P4Result = ApplyP4(SboxResult);
        P4Result = xor(P4Result, leftText);
        String funcResult = P4Result + rightText;
        return funcResult;
    }

    private static String Switch(String left, String right) {
        return right + left;
    }

    private static String getSbox(String left, String right) {
        int[][] s0 = {{1, 0, 3, 2}, {3, 2, 1, 0}, {0, 2, 1, 3}, {3, 1, 3, 2}};
        int[][] s1 = {{0, 1, 2, 3}, {2, 0, 1, 3}, {3, 0, 1, 0}, {2, 1, 0, 3}};
        int row, col;
        String s0_row = left.charAt(0) + "" + left.charAt(3);
        String s0_col = left.charAt(1) + "" + left.charAt(2);
        row = BinaryToDecimal(s0_row);
        col = BinaryToDecimal(s0_col);
        int value_in_s0 = s0[row][col];
        String fHalf = DecimalToBinary(value_in_s0);
        String s1_row = right.charAt(0) + "" + right.charAt(3);
        String s1_col = right.charAt(1) + "" + right.charAt(2);
        row = BinaryToDecimal(s1_row);
        col = BinaryToDecimal(s1_col);
        int value_in_s1 = s1[row][col];
        String sHalf = DecimalToBinary(value_in_s1);
        return fHalf + sHalf;
    }

    private static String DecimalToBinary(int n) {
        int rem;
        String res = "";
        while (n != 1) {
            rem = n % 2;
            res = rem + res;
            n = n / 2;
        }
        res = n + res;
        return res;
    }

    private static int BinaryToDecimal(String s) {
        int pow = 0;
        int curr = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            curr = curr + (int) (Integer.parseInt(s.charAt(i) + "") * Math.pow(2, pow));
            pow++;
        }
        return curr;
    }

    private static String getLeft(String s) {
        return s.substring(0, s.length() / 2);
    }

    private static String getRight(String s) {
        return s.substring(s.length() / 2, s.length());
    }

    private static String[] generateKey(String key) {
        String[] keys = new String[2];
        String temp = ApplyP10(key);
        String left = temp.substring(0, key.length() / 2);
        String right = temp.substring(key.length() / 2, key.length());
        String leftAfterShift = leftShift(left, 1);
        String rightAfterShift = leftShift(right, 1);
        String key1 = ApplyP8(leftAfterShift + rightAfterShift);
        keys[0] = key1;
        leftAfterShift = leftShift(leftAfterShift, 2);
        rightAfterShift = leftShift(rightAfterShift, 2);
        String key2 = ApplyP8(leftAfterShift + rightAfterShift);
        keys[1] = key2;
        return keys;

    }

    private static String leftShift(String s, int t) {
        char x;
        for (int i = 0; i < t; i++) {
            x = s.charAt(0);
            s += x;
            s = s.substring(1);
        }
        return s;
    }

    private static String xor(String s1, String s2) {
        String res = "";
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                res += '0';
            } else {
                res += '1';
            }
        }
        return res;
    }

    private static String ApplyP10(String s) {
        char[] p10 = {2, 4, 1, 6, 3, 9, 0, 8, 7, 5};
        for (int i = 0; i < p10.length; i++) {
            p10[i] = s.charAt(p10[i]);
        }
        String res = "";
        for (char c : p10) {
            res += c;
        }
        return res;
    }

    private static String ApplyP8(String s) {
        char[] p8 = {5, 2, 6, 3, 7, 4, 9, 8};
        for (int i = 0; i < p8.length; i++) {
            p8[i] = s.charAt(p8[i]);
        }
        String res = "";
        for (char c : p8) {
            res += c;
        }
        return res;
    }

    private static String ApplyP4(String s) {
        char[] p4 = {1, 3, 2, 0};
        for (int i = 0; i < p4.length; i++) {
            p4[i] = s.charAt(p4[i]);
        }
        String res = "";
        for (char c : p4) {
            res += c;
        }
        return res;
    }

    public static String ApplyIP(String s) {
        char[] ip = {1, 5, 2, 0, 3, 7, 4, 6};
        for (int i = 0; i < ip.length; i++) {
            ip[i] = s.charAt(ip[i]);
        }
        String res = "";
        for (char c : ip) {
            res += c;
        }
        return res;
    }

    public static String ApplyEP(String s) {
        char[] ep = {3, 0, 1, 2, 1, 2, 3, 0};
        for (int i = 0; i < ep.length; i++) {
            ep[i] = s.charAt(ep[i]);
        }
        String res = "";
        for (char c : ep) {
            res += c;
        }
        return res;
    }

    public static String ApplyIPInverse(String s) {
        char[] ip1 = {3, 0, 2, 4, 6, 1, 7, 5};
        for (int i = 0; i < ip1.length; i++) {
            ip1[i] = s.charAt(ip1[i]);
        }
        String res = "";
        for (char c : ip1) {
            res += c;
        }
        return res;
    }
}
