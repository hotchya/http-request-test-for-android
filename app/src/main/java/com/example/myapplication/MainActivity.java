package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    //Global variable
    Button button;
    TextView textView;
    String text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button1);
        textView = (TextView)findViewById(R.id.text1);

        button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                // thread for http request
                new Thread(){
                    public void run(){
                        try {
                            GetText();
                        }catch (Exception ex){
                            System.out.println("error : " + ex);
                        }
                    }
                }.start();

                // update UI
                textView.setText(text1);
            }
        });
    }



    public  void  GetText()  throws UnsupportedEncodingException
    {
        // Set id value
        String id = "123456789";

        // Create data
        String data = URLEncoder.encode("id", "UTF-8")
                + "=" + URLEncoder.encode(id, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        try {

            // Defined URL where to send data
            URL url = new URL("http://172.30.1.3:3000/");

            // Send POST data request
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }
            text = sb.toString();

        } catch(Exception ex) {
            System.out.println("error : " + ex);
        }

        finally {
            try {
                reader.close();
            } catch(Exception ex) {
                System.out.println("error : " + ex);
            }
        }

        // Update global text
        text1 = text;
    }
}
