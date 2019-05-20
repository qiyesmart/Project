package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author:QiyeSmart
 * Created:2019/5/20
 */
public class SingleServer {
    public static void main(String[] args) {
        //1。实例化 ServerSocket 对象
        try {
            ServerSocket   serverSocket=new ServerSocket(8080);//0-65535，端口号不要选择太小
            System.out.println("服务器启动"+serverSocket.getInetAddress()+serverSocket.getLocalPort());
            while(true){
                //2.等待接收客户端连接
                //accept是个阻塞的方法，直到客户端连接，返回
                final Socket socket=serverSocket.accept();
                System.out.println(""+socket.getRemoteSocketAddress()+socket.getPort());
                //3.写数据
                OutputStream  out=socket.getOutputStream();
                PrintStream printStream=new PrintStream(out);
                printStream.println("欢迎你");
                //4.读数据
                InputStream in=socket.getInputStream();
                Scanner scanner=new Scanner(in);
                String message=scanner.nextLine();
                System.out.println("来自客户端的数据"+":"+message);
                //socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
