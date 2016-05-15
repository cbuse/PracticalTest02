package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest02MainActivity extends Activity {

    private EditText serverPortEditText = null;
    private Button connectButton = null;

    private EditText     clientAddressEditText    = null;
    private EditText     clientPortEditText       = null;
    private EditText     cityEditText             = null;
    private Spinner      informationTypeSpinner   = null;
    private Button       getInfoButton = null;
    private TextView     infoTextView  = null;

    private ServerThread serverThread             = null;
    private ClientThread clientThread             = null;

    private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
    private class ConnectButtonClickListener implements Button.OnClickListener {

        public void onClick(View view) {

            String serverPort = serverPortEditText.getText().toString();

            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        "Server port should be filled!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() != null)
                serverThread.start();
        }
    }

    private GetInfoButtonClickListener getInfoButtonClickListener = new GetInfoButtonClickListener();
    private class GetInfoButtonClickListener implements Button.OnClickListener {
        public void onClick(View view) {
            String clientAddress = clientAddressEditText.getText().toString();
            String clientPort = clientPortEditText.getText().toString();

            String city = cityEditText.getText().toString();
            String infoType = informationTypeSpinner.getSelectedItem().toString();

            infoTextView.setText(Constants.EMPTY_STRING);
            clientThread = new ClientThread(clientAddress, Integer.parseInt(clientPort),
                                            city,
                                            infoType,
                                            infoTextView);
            clientThread.start();

        }
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        serverPortEditText = (EditText)findViewById(R.id.server_port_edit_text);
        connectButton = (Button)findViewById(R.id.connect_button);
        connectButton.setOnClickListener(connectButtonClickListener);

        clientAddressEditText = (EditText)findViewById(R.id.client_address_edit_text);
        clientPortEditText = (EditText)findViewById(R.id.client_port_edit_text);
        cityEditText = (EditText)findViewById(R.id.city_edit_text);
        informationTypeSpinner = (Spinner)findViewById(R.id.information_type_spinner);
        getInfoButton = (Button)findViewById(R.id.get_weather_forecast_button);
        getInfoButton.setOnClickListener(getInfoButtonClickListener);
        infoTextView = (TextView)findViewById(R.id.weather_forecast_text_view);
    }
}
