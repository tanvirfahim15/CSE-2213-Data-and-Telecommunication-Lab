/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class client {

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 1234);
        DataInputStream is = new DataInputStream(s.getInputStream());
        DataOutputStream os = new DataOutputStream(s.getOutputStream());
        Scanner sc = new Scanner(new FileReader("in.txt"));
        while (sc.hasNext()) {
            String strk = sc.nextLine();

            while (true) {
                String str=strk;
                int csum = 0;
                for (int i = 0; i < str.length(); i++) {
                    csum += (int) str.charAt(i);
                }
                csum %= 16;
                if( (new Random().nextInt() % 100) < 30) {
                    csum++;
                }
                str = str + "##" + csum;
                os.writeUTF(str);
                String msg = is.readUTF();
                if (msg.equals("OK")) {
                    break;
                }
            }
        }
        os.writeUTF("000");
        is.close();
        os.close();
        s.close();
    }
}
