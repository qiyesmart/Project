package MultiClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author:QiyeSmart
 * Created:2019/5/20
 */
//读
public class ReaddataFromServerThread  extends  Thread{
    //两个线程共用一个对象
    private final Socket socket;

    public ReaddataFromServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run(){
        try {
            InputStream in=this.socket.getInputStream();
            Scanner scanner=new Scanner(in);
            while(true){
              String message= scanner.nextLine();//阻塞操作
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
