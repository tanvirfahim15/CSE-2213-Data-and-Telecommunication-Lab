package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(1234);
        Socket s1 = s.accept();
        DataInputStream is = new DataInputStream(s1.getInputStream());
        FileWriter fout[] = new FileWriter[6];
        fout[1] = new FileWriter("out1.txt");
        fout[2] = new FileWriter("out2.txt");
        fout[3] = new FileWriter("out3.txt");
        fout[4] = new FileWriter("out4.txt");
        fout[5] = new FileWriter("out5.txt");
        while (true) {
            String in = is.readUTF();
            if (in.equals("00000")) {
                break;
            }
            Frame fr = new Frame(in);
            System.out.println(fr.toString());
            fout[fr.s1.dst].append(fr.s1.data);
            fout[fr.s2.dst].append(fr.s2.data);
            fout[fr.s3.dst].append(fr.s3.data);
            fout[fr.s4.dst].append(fr.s4.data);
            fout[fr.s5.dst].append(fr.s5.data);

        }
        for (int i = 1; i < 6; i++) {
            fout[i].close();
        }
        is.close();
        s1.close();
        s.close();
    }
}
