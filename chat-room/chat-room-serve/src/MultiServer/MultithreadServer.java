package MultiServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author:QiyeSmart
 * Created:2019/5/20
 */
//多线程服务端
public class MultithreadServer {
    public static void main(String[] args) {
        int defaultPort = 8080;
        int defaultThread = 10;
        int port = defaultPort;
        int thread = defaultThread;
        //通过命令行参数设置thread和port
        for (String arg : args) {
            if (arg.startsWith("--port=")) {
                String portStr = arg.substring("--port=".length());
                try {

                    port = Integer.parseInt(portStr);
                } catch (NumberFormatException e) {
                    port = defaultPort;
                }
            }
            if (arg.startsWith("--thread=")) {
                String threadStr = arg.substring("--thread=".length());
                try {
                    thread = Integer.parseInt(threadStr);
                } catch (NumberFormatException e) {
                    thread = defaultThread;
                }

            }
        }
        try {
            ServerSocket serverSocket=new ServerSocket(port);
            System.out.println("服务器启动"+serverSocket.getInetAddress()+"  当前端口号："+serverSocket.getLocalPort()
            +"线程数："+thread);
           //线程池调度器
            final ExecutorService executorService= Executors.newFixedThreadPool(thread);
            while(true){
                //2.等待接收客户端连接
                //accept是个阻塞的方法，直到客户端连接，返回
                final Socket socket=serverSocket.accept();
              executorService.execute(new ClientHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
