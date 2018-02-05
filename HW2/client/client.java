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
import java.util.Vector;

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

    slot sl[];

    public Frame(slot sl[]) {
        this.sl = sl;
    }

    public Frame(String str) {
        slot sltemp[] = new slot[5];
        int ind = 0;
        StringTokenizer stk = new StringTokenizer(str, "&");
        String strtemp = stk.nextToken();
        while (true) {
            strtemp = stk.nextToken();
            if (strtemp.equals("@")) {
                break;
            } else {
                sltemp[ind] = new slot(strtemp);
                ind++;
            }

        }
        sl = new slot[ind];
        for (int i = 0; i < ind; i++) {
            sl[i] = sltemp[i];

        }
    }

    public String toString() {
        String ret = "#";
        for (int i = 0; i < sl.length; i++) {
            ret = ret + "&" + sl[i].toString();
        }
        ret = ret + "&" + "@";
        return ret;
    }

}

public class client {

    static String mstr(char[] c) {
        String ret = "";
        for (int i = 0; i < c.length; i++) {
            if (c[i] != 0) {
                ret += c[i];
            } else {
                ret += "";
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
        String str1, str2, str3, str4, str5;
        str1 = "";
        str2 = "";
        str3 = "";
        str4 = "";
        str5 = "";

        boolean end[] = new boolean[5];
        for (int i = 0; i < end.length; i++) {
            end[i] = true;
        }
        while (true) {
            char[] c = new char[10];

            if (str1.equals("")) {
                for (int i = 0; i < 10; i++) {
                    char ctmp = (char) br1.read();
                    str1 += ctmp;
                    br1.mark(0);
                    if (br1.readLine() == null) {
                        if (i == 0) {
                            end[0] = false;
                        }
                        break;
                    }
                    br1.reset();

                }

            }

            if (str2.equals("")) {
                for (int i = 0; i < 10; i++) {
                    char ctmp = (char) br2.read();
                    str2 += ctmp;

                    br2.mark(0);
                    if (br2.readLine() == null) {
                        if (i == 0) {
                            end[1] = false;
                        }
                        break;
                    }
                    br2.reset();
                }

            }

            if (str3.equals("")) {
                for (int i = 0; i < 10; i++) {
                    char ctmp = (char) br3.read();
                    str3 += ctmp;
                    br3.mark(0);
                    if (br3.readLine() == null) {
                        if (i == 0) {
                            end[2] = false;
                        }
                        break;
                    }
                    br3.reset();

                }

            }

            if (str4.equals("")) {
                for (int i = 0; i < 10; i++) {
                    char ctmp = (char) br4.read();
                    str4 += ctmp;
                    br4.mark(0);
                    if (br4.readLine() == null) {
                        if (i == 0) {
                            end[3] = false;
                        }
                        break;
                    }
                    br4.reset();

                }

            }

            if (str5.equals("")) {
                for (int i = 0; i < 10; i++) {
                    char ctmp = (char) br5.read();
                    str5 += ctmp;
                    br5.mark(0);
                    if (br5.readLine() == null) {
                        if (i == 0) {
                            end[4] = false;
                        }
                        break;
                    }
                    br5.reset();

                }

            }

            if (end[0] == false && end[1] == false && end[2] == false && end[3] == false && end[4] == false) {
                break;
            }

            Vector<slot> slv = new Vector<slot>();
            if (new Random().nextInt() % 100 < 50 && end[0]) {
                slv.add(new slot(1, 1, str1));
                str1 = "";
            }
            if (new Random().nextInt() % 100 < 50 && end[1]) {
                slv.add(new slot(2, 2, str2));
                str2 = "";
            }
            if (new Random().nextInt() % 100 < 50 && end[2]) {
                slv.add(new slot(3, 3, str3));
                str3 = "";
            }
            if (new Random().nextInt() % 100 < 50 && end[3]) {
                slv.add(new slot(4, 4, str4));
                str4 = "";
            }
            if (new Random().nextInt() % 100 < 50 && end[4]) {
                slv.add(new slot(5, 5, str5));
                str5 = "";
            }
            slot sl[] = new slot[slv.size()];
            for (int i = 0; i < sl.length; i++) {
                sl[i] = slv.get(i);
            }
            Frame fr = new Frame(sl);
            System.out.println(fr.toString());
            os.writeUTF(fr.toString());
        }
        os.writeUTF("00000");
        os.close();
        s.close();
    }
}
