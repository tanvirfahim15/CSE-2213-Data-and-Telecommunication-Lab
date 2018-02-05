package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(1234);
        Socket s1 = s.accept();
        DataInputStream is = new DataInputStream(s1.getInputStream());
        DataOutputStream os = new DataOutputStream(s1.getOutputStream());
        FileWriter fout = new FileWriter("out.txt");
        while (true) {
            String str = is.readUTF();
            if (str.equals("000")) {
                break;
            }
            StringTokenizer stk = new StringTokenizer(str, "##");
            str = stk.nextToken();
            int csum = 0;
            for (int i = 0; i < str.length(); i++) {
                csum += (int) str.charAt(i);
            }
            csum %= 16;
            int csumtmp = Integer.parseInt(stk.nextToken().toString());
            if (csumtmp != csum) {
                os.writeUTF("error");
                System.out.println("error");

            } else {

                os.writeUTF("OK");
                fout.append(str);
                fout.append(System.getProperty("line.separator"));
            }
        }
        fout.close();
        is.close();
        os.close();
        s1.close();
        s.close();
    }
}
