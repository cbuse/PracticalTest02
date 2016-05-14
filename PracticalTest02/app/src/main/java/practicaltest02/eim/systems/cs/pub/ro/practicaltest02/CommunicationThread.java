package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by cristina on 14.05.2016.
 */
public class CommunicationThread extends Thread {

    private ServerThread serverThread;
    private Socket socket;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket != null) {
            try {
                BufferedReader bufferedReader = Utilities.getReader(socket);
                PrintWriter printWriter = Utilities.getWriter(socket);
                if (bufferedReader != null && printWriter != null) {
                    Log.i(Constants.TAG, "[COMMUNICATION THREAD] Waiting for parameters from client (city / information type)!");
                    // INPUT DE LA CLIENT
                    String city = bufferedReader.readLine();
                    String infoType = bufferedReader.readLine();

                }

            } catch (Exception ioException) {
                ioException.printStackTrace();

            }
        }
    }
}
