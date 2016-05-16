package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by cristina on 15.05.2016.
 */
public class ClientThread extends Thread {
    private String address;
    private int port;
    private String operation;
    int op1;
    int op2;
    private TextView infoTextView;

    private Socket socket;

   /* public ClientThread(String address,
                        int port,
                        String city,
                        String infoType,
                        TextView infoTextView) {
        this.address = address;
        this.port = port;
        this.city = city;
        this.infoType = infoType;
        this.infoTextView = infoTextView;

    }*/
   public ClientThread(String address,
                       int port,
                       String operation,
                       int op1,
                       int op2,
                       TextView infoTextView
                       ) {
       this.address = "127.0.0.1";
       this.port = port;
       this.op1 = op1;
       this.op2 = op2;
       this.operation = operation;

       this.infoTextView = infoTextView;
   }

    @Override
    public void run() {
       try {
           socket = new Socket(address, port);
           BufferedReader bufferedReader = Utilities.getReader(socket);
           PrintWriter printWriter = Utilities.getWriter(socket);

           if (bufferedReader != null && printWriter != null) {
               printWriter.println(operation+","+op1+","+op2);
               printWriter.flush();
               /*printWriter.println(op2);
               printWriter.flush();
               printWriter.println(operation);
               printWriter.flush();*/

               String info = null;

               while ((info = bufferedReader.readLine()) != null) {
                   final String finalizedInfo = info;
                   infoTextView.post(new Runnable() {
                       @Override
                       public void run() {
                           infoTextView.append(finalizedInfo + "\n");
                           Log.d("aici", finalizedInfo);
                       }
                   });
               }

           }
       } catch (UnknownHostException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
    }
}
