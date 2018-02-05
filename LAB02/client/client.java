/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

class slot {

    int src, dst;
    String data;

    public slot(int src, int dst, String data) {
        this.src = src;
        this.dst = dst;
        this.data = data;
    }

    public slot(String str) {
        StringTokenizer stk = new StringTokenizer(str, "-");
        String st = stk.nextToken();
        src = Integer.parseInt(st);
        st = stk.nextToken();
        dst = Integer.parseInt(st);
        st = stk.nextToken();
        data = st;

    }

    @Override
    public String toString() {
        return "*-" + src + "-" + dst + "-" + data + "-!";
    }

}

class Frame {

    slot s1, s2, s3, s4, s5;

    public Frame(slot s1, slot s2, slot s3, slot s4, slot s5) {
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
        this.s5 = s5;
    }

    public Frame(String str) {

        StringTokenizer stk = new StringTokenizer(str, "*");
        String st = stk.nextToken();
        st = stk.nextToken();
        s1 = new slot(st);
        st = stk.nextToken();
        s2 = new slot(st);
        st = stk.nextToken();
        s3 = new slot(st);
        st = stk.nextToken();
        s4 = new slot(st);
        st = stk.nextToken();
        s5 = new slot(st);
    }

    @Override
    public String toString() {
        return "# " + s1.toString() + " " + s2.toString() + " " + s3.toString() + " " + s4.toString() + " " + s5.toString() + " @";
    }

}

public class client {

    static String mstr(char[] c) {
        String ret = "";
        for (int i = 0; i < c.length; i++) {
            if (c[i] != 0) {
                ret += c[i];
            } else {
                ret += " ";
            }
        }
        return ret;
    }

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 1234);
        DataOutputStream os = new DataOutputStream(s.getOutputStream());
        BufferedReader br1 = new BufferedReader(new FileReader("in1.txt"));
        BufferedReader br2 = new BufferedReader(new FileReader("in2.txt"));
        BufferedReader br3 = new BufferedReader(new FileReader("in3.txt"));
        BufferedReader br4 = new BufferedReader(new FileReader("in4.txt"));
        BufferedReader br5 = new BufferedReader(new FileReader("in5.txt"));
        while (true) {

            char[] c = new char[10];
            String str1, str2, str3, str4, str5;
            boolean br = false;

            str1 = "";
            for (int i = 0; i < 10; i++) {
                char ct = (char) br1.read();
                str1 += ct;
                br1.mark(0);
                if (br1.readLine() == null) {
                    br = true;
                    break;
                }
                br1.reset();
            }

            str2 = "";
            for (int i = 0; i < 10; i++) {
                char ct = (char) br2.read();
                str2 += ct;
                br2.mark(0);
                if (br2.readLine() == null) {
                    br = true;break;
                }
                br2.reset();
            }

            str3 = "";
            for (int i = 0; i < 10; i++) {
                char ct = (char) br3.read();
                str3 += ct;
                br3.mark(0);
                if (br3.readLine() == null) {
                    br = true;break;
                }
                br3.reset();
            }

            str4 = "";
            for (int i = 0; i < 10; i++) {
                char ct = (char) br4.read();
                str4 += ct;
                br4.mark(0);
                if (br4.readLine() == null) {
                    br = true;break;
                }
                br4.reset();
            }

            str5 = "";
            for (int i = 0; i < 10; i++) {
                char ct = (char) br5.read();
                str5 += ct;
                br5.mark(0);
                if (br5.readLine() == null) {
                    br = true;break;
                }
                br5.reset();
            }
            if (br) {
                break;
            }

            slot sl1, sl2, sl3, sl4, sl5;
            sl1 = new slot(1, 1, str1);
            sl2 = new slot(2, 2, str2);
            sl3 = new slot(3, 3, str3);
            sl4 = new slot(4, 4, str4);
            sl5 = new slot(5, 5, str5);
            Frame fr = new Frame(sl1, sl2, sl3, sl4, sl5);
            System.out.println(fr.toString());
            os.writeUTF(fr.toString());
        }
        os.writeUTF("00000");
        os.close();
        s.close();
    }
}
