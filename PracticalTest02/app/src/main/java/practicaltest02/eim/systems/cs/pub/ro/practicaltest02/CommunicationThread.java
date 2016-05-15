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
                    Log.i(Constants.TAG, "[COMMUNICATION THREAD] Waiting for parameters from client (city / information type)!");
                    // INPUT DE LA CLIENT
                    String city = bufferedReader.readLine();
                    String infoType = bufferedReader.readLine();


                    HashMap<String, Information> data = serverThread.getData();

                    Information info = null;

                    // incerc sa iau info din cache
                    if (city != null && !city.isEmpty() && infoType != null && !infoType.isEmpty()) {

                        if (data.containsKey(city)) {
                            Log.i(Constants.TAG, "[COMMUNICATION THREAD] Getting the information from the cache...");
                            // iau valoarea coresp cheii city
                            info = data.get(city);
                        }

                    // iau info de pe server web
                    else {

                        Log.i(Constants.TAG, "[COMMUNICATION THREAD] Getting the information from the webservice...");
                        HttpClient httpClient = new DefaultHttpClient();
                        // URL DE UNDE IAU DATE
                        HttpPost httpPost = new HttpPost(Constants.WEB_SERVICE_ADDRESS);
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        //ADAUGARE PARAMETRI PT POST
                        params.add(new BasicNameValuePair(Constants.QUERY_ATTRIBUTE, city));
                        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                        httpPost.setEntity(urlEncodedFormEntity);



                        //TODO ResponseHandler
                        //ResponseHandler<String> responseHandler = new BasicResponseHandler();
                        //String pageSourceCode = httpClient.execute(httpPost, responseHandler);
                        HttpResponse httpResponse = httpClient.execute(httpPost);
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
                        for (Element script : scripts) {
                            String scriptData = script.data();

                            if (scriptData.contains(Constants.SEARCH_KEY)) {
                                // Identificare date neceesare; search_key = json
                                //current_observation = datele despre vreme
                                int position = scriptData.indexOf(Constants.SEARCH_KEY) + Constants.SEARCH_KEY.length();
                                scriptData = scriptData.substring(position);

                                JSONObject content = new JSONObject(scriptData);

                                JSONObject currentObservation = content.getJSONObject(Constants.CURRENT_OBSERVATION);
                                String temperature = currentObservation.getString(Constants.TEMPERATURE);
                                String windSpeed = currentObservation.getString(Constants.WIND_SPEED);
                                String condition = currentObservation.getString(Constants.CONDITION);
                                String pressure = currentObservation.getString(Constants.PRESSURE);
                                String humidity = currentObservation.getString(Constants.HUMIDITY);

                                info = new Information(temperature,
                                                        windSpeed,
                                                        condition,
                                                        pressure,
                                                        humidity);

                                serverThread.setData(city, info);
                                break;
                            }

                        }
                    if (info != null) {
                        String result = null;
                        if (Constants.ALL.equals(infoType))
                            result = info.toString();
                        else if (Constants.TEMPERATURE.equals(infoType))
                            result = info.getTemperature();
                        else if (Constants.WIND_SPEED.equals(infoType))
                            result = info.getWindSpeed();
                        else if (Constants.CONDITION.equals(infoType))
                            result = info.getCondition();
                        else if (Constants.HUMIDITY.equals(infoType))
                            result = info.getHumidity();
                        else if (Constants.PRESSURE.equals(infoType))
                            result = info.getPressure();

                        printWriter.println(result);
                        printWriter.flush();

                    }

                    }}
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
