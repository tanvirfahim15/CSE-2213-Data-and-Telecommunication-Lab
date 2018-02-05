package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
        st = stk.nextToken();
        src = Integer.parseInt(st);
        st = stk.nextToken();
        dst = Integer.parseInt(st);
        st = stk.nextToken();
        data = st;
    }

    public String toString() {
        return "[-" + src + "-" + dst + "-" + data + "-]";
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
            for (int i = 0; i < fr.sl.length; i++) {
                fout[fr.sl[i].dst].append(fr.sl[i].data);
            }
        }
        for (int i = 1; i < 6; i++) {
            fout[i].close();
        }
        is.close();
        s1.close();
        s.close();
    }
}
