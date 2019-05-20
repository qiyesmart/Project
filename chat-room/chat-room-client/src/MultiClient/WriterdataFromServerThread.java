package MultiClient;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author:QiyeSmart
 * Created:2019/5/20
 */
//写
public class WriterdataFromServerThread  extends Thread {
    private final Socket socket;

    public WriterdataFromServerThread(Socket socket) {
        this.socket = socket;
    }
    public void run(){
        try {
           OutputStream out=this.socket.getOutputStream();
            PrintStream printStream=new PrintStream(out);
            //从键盘读入
            Scanner scanner= new Scanner(System.in);
            System.out.println(HelpInfo());
            while(true){
                System.out.println("请输入>>");
                String message= scanner.nextLine();//阻塞操作
               printStream.println(message);
               printStream.flush();
               if("quit".equals(message)){
                   break;
               }
            }
            //关闭客户端
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String HelpInfo(){
        StringBuffer sb=new StringBuffer();

        sb.append("使用指南：").append("\n");
        sb.append("1.注册：register:<name> name是注册的名字").append("\n");
        sb.append("2.群聊：group:<meaasge> message是消息").append("\n");
        sb.append("3.私聊：private:<name>:<meaasge>name是私聊对象，message是消息").append("\n");
        sb.append("4.退出：quit 退出").append("\n");
        return sb.toString();
    }
}
