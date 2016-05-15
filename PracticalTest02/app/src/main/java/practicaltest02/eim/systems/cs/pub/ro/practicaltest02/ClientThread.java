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
    private String city;
    private String infoType;
    private TextView infoTextView;

    private Socket socket;

    public ClientThread(String address,
                        int port,
                        String city,
                        String infoType,
                        TextView infoTextView) {
        this.address = address;
        this.port = port;
        this.city = city;
        this.infoType = infoType;
        this.infoTextView = infoTextView;

    }

    @Override
    public void run() {
       try {
           socket = new Socket(address, port);
           BufferedReader bufferedReader = Utilities.getReader(socket);
           PrintWriter printWriter = Utilities.getWriter(socket);

           if (bufferedReader != null && printWriter != null) {
               printWriter.println(city);
               printWriter.flush();
               printWriter.println(infoType);
               printWriter.flush();

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
