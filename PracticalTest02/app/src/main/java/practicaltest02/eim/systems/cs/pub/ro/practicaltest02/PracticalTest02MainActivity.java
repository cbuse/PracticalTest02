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
    private Button     multButton            = null;
    private Button     addButton            = null;

    private EditText op1EditText = null;
    private EditText op2EditText = null;

    private Spinner      informationTypeSpinner   = null;
    private Button       getInfoButton = null;
    private int op1;
    private int op2;
    private String operation = null;
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

    private AddButtonClickListener  addButtonClickListener = new AddButtonClickListener();
    private class AddButtonClickListener implements Button.OnClickListener {
        public void onClick(View view) {
            //String clientAddress = clientAddressEditText.getText().toString();
            String clientPort = clientPortEditText.getText().toString();

            int op1 = Integer.parseInt(op1EditText.getText().toString());
            int op2 = Integer.parseInt(op2EditText.getText().toString());
            operation = "add";

            infoTextView.setText(Constants.EMPTY_STRING);
            clientThread = new ClientThread("127.0.0.1", Integer.parseInt(clientPort),
                                            operation,
                                            op1,
                                            op2,
                                            infoTextView);
            clientThread.start();

        }
    }

    private MultButtonClickListener multButtonClickListener = new MultButtonClickListener();
    private class MultButtonClickListener implements Button.OnClickListener {
        public void onClick(View view) {
            //String clientAddress = clientAddressEditText.getText().toString();
            String clientPort = clientPortEditText.getText().toString();

            int op1 = Integer.parseInt(op1EditText.getText().toString());
            int op2 = Integer.parseInt(op2EditText.getText().toString());

            operation = "mul";

            infoTextView.setText(Constants.EMPTY_STRING);
            clientThread = new ClientThread("127.0.0.1", Integer.parseInt(clientPort),
                    operation,
                    op1,
                    op2,
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

        //clientAddressEditText = (EditText)findViewById(R.id.client_address_edit_text);
        clientPortEditText = (EditText)findViewById(R.id.server_port_edit_text);

        addButton = (Button)findViewById(R.id.add);
        addButton.setOnClickListener(addButtonClickListener);
        multButton = (Button)findViewById(R.id.mult);
        multButton.setOnClickListener(multButtonClickListener);
            infoTextView = (TextView)findViewById(R.id.result);
            op1EditText = (EditText)findViewById(R.id.op1);
            op2EditText = (EditText)findViewById(R.id.op2);


            /*
            cityEditText = (EditText)findViewById(R.id.city_edit_text);
        informationTypeSpinner = (Spinner)findViewById(R.id.information_type_spinner);
        getInfoButton = (Button)findViewById(R.id.get_weather_forecast_button);
        getInfoButton.setOnClickListener(getInfoButtonClickListener);
        infoTextView = (TextView)findViewById(R.id.weather_forecast_text_view);
        */
    }
}
