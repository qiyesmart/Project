package MultiClient;

import java.io.IOException;
import java.net.Socket;
/**
 * Author:QiyeSmart
 * Created:2019/5/20
 */
//多线程客户端
public class MultithreadClient {
    public static void main(String[] args) {
        //通过命令行参数设置host和port
        String defaultHost="192.168.66.1";
        int defaultPort=8080;
        String host=defaultHost;
        int port=defaultPort;
        for (String arg : args) {
            if (arg.startsWith("--port=")) {
                String portStr = arg.substring("--port=".length());
                try {

                    port = Integer.parseInt(portStr);
                } catch (NumberFormatException e) {
                    port = defaultPort;
                }
            }
            if (arg.startsWith("--host=")) {
               host=arg.substring("--host=".length());

            }
        }
        try {
            Socket socket=new Socket(host,port);
           new ReaddataFromServerThread(socket).start();
            new WriterdataFromServerThread(socket).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
