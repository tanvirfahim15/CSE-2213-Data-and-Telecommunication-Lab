package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author tanvir
 */
public class server {
    static int num1(int x){
        int ret=0;
        while(x>0){
            ret+=x%2;
            x/=2;
        }
        return ret;
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        ServerSocket s=new ServerSocket(1234);
        Socket s1=s.accept();
        DataOutputStream os=new DataOutputStream(s1.getOutputStream());
        BufferedReader br = new BufferedReader(new FileReader("in.txt"));
        while (true) {
            int x=br.read();
            x=x<<1;
            Random r=new Random();                    
            if(num1(x)%2==0)
                x=x|1;
            System.out.println(x);
            os.writeInt(x);
            br.mark(0);
            if (br.readLine() == null) {
                break;
            }
            br.reset();
        }
        os.writeInt(-1);
        os.close();
        s1.close();
        s.close();

    }

}
