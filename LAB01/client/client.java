/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author tanvir
 */
public class client {

    static int num1(int x){
        int ret=0;
        while(x>0){
            ret+=x%2;
            x/=2;
        }
        return ret;
    }

    public static void main(String[] args) throws IOException {
        FileWriter fout= new FileWriter("out.txt");

        Socket s = new Socket("localhost", 1234);
        DataInputStream din = new DataInputStream(s.getInputStream());

        while(true){
            int x=din.readInt();
            if(x==-1)break;
            if(num1(x)%2==0)System.err.println("error");
            x=x>>1;
            System.out.println(x);
            fout.append((char)x);
        }
        fout.close();
        din.close();
        s.close();
    }
}
