package MultiServer;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Author:QiyeSmart
 * Created:2019/5/20
 */
//处理业务逻辑
public class ClientHandler implements Runnable {
    //此处服务器socket和客户端socket进行数据传输
    //SOCKER_MAPS存储所有的链接到服务器的客户端Socket和name
    //存储所有的注册到服务端的客户端Socket和name
    private static final Map<String, Socket> SOCKET_MAPS = new ConcurrentHashMap<>();

    private final Socket client;

    private String name;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        //此处服务器socket和客户端socket进行数据传业务逻辑
        try {
            InputStream in = this.client.getInputStream();
            Scanner scanner = new Scanner(in);
            while (true) {
                String line = scanner.nextLine();
                if (line.startsWith("register:")) {
                    //register:<name>
                    String[] segments = line.split(":");
                    if (segments[0].equals("register")) {
                        String name = segments[1];
                        this.register(name);
                    }
                    continue;
                }
                if (line.startsWith("group:")) {
                    String[] segments = line.split(":");
                    if (segments[0].equals("group")) {
                        String message = segments[1];
                        this.group(message);
                    }
                    continue;
                }
                if (line.startsWith("private:")) {
                    String[] segments = line.split(":");
                    if (segments[0].equals("private")) {
                        String targetName = segments[1];
                        String message = segments[2];
                        this.privateChat(targetName, message);
                    }
                    continue;
                }
                if (line.equals("quit")) {
                    this.quit();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void privateChat(String targetName, String message) {
        Socket socket = SOCKET_MAPS.get(targetName);
        if (socket == null) {
            return;
        }
        this.sendMessage(socket, this.name + " 说 ：" + message);
    }

    private void group(String message) {
        //this.client  群聊给自己不发送
        for (Map.Entry<String, Socket> entry : SOCKET_MAPS.entrySet()) {
            Socket socket = entry.getValue();
            if (socket == this.client) {
                continue;
            }
            // xx 说 :  xxx
            this.sendMessage(socket, this.name + " 说：" + message);
        }
    }

    private void quit() {
        SOCKET_MAPS.remove(this.name);
        this.printOnlineClient();
    }

    private void register(String name) {
        //this.client  表示当前客户端连接的Socket
        //this.name    表示当前客户端注册的名称
        this.name = name;
        SOCKET_MAPS.put(name, this.client);
        this.sendMessage(this.client, "恭喜" + name + "注册成功");

        //打印当前在线的所有客户端
        printOnlineClient();
    }

    private void printOnlineClient() {
        System.out.println("当前在线用户数：" + SOCKET_MAPS.size() + " 名称列表如下：");
        for (String name : SOCKET_MAPS.keySet()) {
            System.out.println(name);
        }
    }

    private void sendMessage(Socket socket, String message) {
        try {
            OutputStream out = socket.getOutputStream();
            PrintStream printStream = new PrintStream(out);
            printStream.println(message);
            printStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
