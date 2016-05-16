package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

import android.provider.DocumentsContract;
import android.util.Log;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                    // INPUT DE LA CLIENT
                    /*String city = bufferedReader.readLine();
                    String infoType = bufferedReader.readLine();
                    */
                    String todo = bufferedReader.readLine();
                    Log.i(Constants.TAG, "[COMMUNICATION THREAD] Waiting for parameters from client!"+todo);

                    String operation = todo.split(",")[0];
                    int op1 = Integer.parseInt(todo.split(",")[1]);
                    int op2 = Integer.parseInt(todo.split(",")[2]);
                    if (operation.equals("mul")) {
                        Thread.sleep(2000);
                        String result = op1*op2+"";
                        printWriter.println(result);
                        printWriter.flush();
                    }
                    else if (operation.equals("mul")) {
                        String result = op1+op2+"";
                        printWriter.println(result);
                        printWriter.flush();
                    }


                    //HashMap<String, Information> data = serverThread.getData();

                    //Information info = null;

                    // incerc sa iau info din cache
                   // if (city != null && !city.isEmpty() && infoType != null && !infoType.isEmpty()) {

                       /* if (data.containsKey(city)) {
                            Log.i(Constants.TAG, "[COMMUNICATION THREAD] Getting the information from the cache...");
                            // iau valoarea coresp cheii city
                            info = data.get(city);
                        }*/

                    // iau info de pe server web
                    //else {
/*
                        Log.i(Constants.TAG, "[COMMUNICATION THREAD] Getting the information from the webservice...");
                        HttpClient httpClient = new DefaultHttpClient();
                        // URL DE UNDE IAU DATE
                        HttpPost httpPost = new HttpPost(Constants.WEB_SERVICE_ADDRESS);
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        //ADAUGARE PARAMETRI PT POST
                        params.add(new BasicNameValuePair(Constants.QUERY_ATTRIBUTE, city));
                        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                        httpPost.setEntity(urlEncodedFormEntity);

*/

                        //ResponseHandler<String> responseHandler = new BasicResponseHandler();
                        //String pageSourceCode = httpClient.execute(httpPost, responseHandler);
                       /* HttpResponse httpResponse = httpClient.execute(httpPost);
                        HttpEntity httpEntity = httpResponse.getEntity();
                        String pageSourceCode = null;
                        if (httpEntity == null) {
                            Log.d(Constants.TAG, "entity is null");
                        } else {
                            pageSourceCode = EntityUtils.toString(httpEntity);
                            Log.d(Constants.TAG, "page: " + pageSourceCode);
                        }

                        org.jsoup.nodes.Document document = Jsoup.parse(pageSourceCode);

                        Log.d("vedem pagina html", document.toString());
                        Element element = document.child(0);
                        Elements scripts = element.getElementsByTag(Constants.SCRIPT_TAG);

                  */


                }

            } catch (Exception ioException) {
                ioException.printStackTrace();

            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
