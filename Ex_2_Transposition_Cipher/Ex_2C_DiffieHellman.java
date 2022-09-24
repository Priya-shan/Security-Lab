/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ex_2_Transposition_Cipher;

import java.util.*;
import java.util.Scanner;

/**
 *
 * @author Shanmuga Priya M
 */
public class Ex_2C_DiffieHellman {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter Prime Number :");
        int prime=sc.nextInt();
        int alpha,Xa=3,Ya,Xb=4,Yb,k1,k2;
        alpha=find_Alpha(prime);
        System.out.println("Alpha value is : "+alpha);
        Ya=find_Ya(Xa,alpha,prime);
        System.out.println("Ya value is : "+Ya);
        Yb=find_Yb(Xb,alpha,prime);
        System.out.println("Yb value is : "+Yb);
        k1=find_K1(Xa,Yb,prime);
        System.out.println("K1 value is : "+k1);
        k2=find_K2(Xb,Ya,prime);
        System.out.println("K2 value is : "+k2);
        if(k1==k2){
            System.out.println("Channel is Secure");
        }
        else{
            System.out.println("Channel is not Secure");
        }
        
    }

    private static int find_Alpha(int q) {
        int alpha,flag;
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=1;i<q;i++){
            flag=1;
            for(int j=1;j<q;j++){
                list.add((int)(Math.pow(i,j)%q));
            }
            for(int k=1;k<q;k++){
                if(!list.contains(k)){
                    flag=0;
                }
            }
            if(flag==1){
                return i;
            }
            list.clear();
        }
        return -1;
    }

    private static int find_Ya(int Xa,int alpha,int q) {
        return (int)(Math.pow(alpha,Xa) % q);
    }

    private static int find_Yb(int Ya,int alpha, int q) {
        return (int)(Math.pow(alpha,Ya) % q);
    }

    private static int find_K1(int Xa,int Yb,int q) {
        return (int)(Math.pow(Yb,Xa)%q);
    }

    private static int find_K2(int Xb,int Ya,int q) {
        return (int)(Math.pow(Ya,Xb)%q);
    }
            
}
