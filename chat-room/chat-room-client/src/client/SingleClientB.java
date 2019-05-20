package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author:QiyeSmart
 * Created:2019/5/20
 */
public class SingleClientB {
    public static void main(String[] args) {
        //1.客户端和服务器连接127.0.0.1
        String host="192.168.66.1";
        int port=8080;
        try {
            Socket socket=new Socket(host,port);
            //2.数据传输
            //读数据
            InputStream in=socket.getInputStream();
            Scanner scanner=new Scanner(in);
            String message=scanner.nextLine();
            System.out.println("来自服务器的数据"+":"+message);
            //写数据
            OutputStream out=socket.getOutputStream();
            PrintStream printStream=new PrintStream(out);
            printStream.println("你好，我来了");
            printStream.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
