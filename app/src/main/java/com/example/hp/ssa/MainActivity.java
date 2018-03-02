package com.example.hp.ssa;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText name, password;
    String Name, Password;
    Context ctx=this;
    String NAME=null, PASSWORD=null, EMAIL=null;
    private ProgressBar progressBar;
    private int progressbar_status=0;
    private Handler mhandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText)findViewById(R.id.login_user);
        password=(EditText)findViewById(R.id.login_password);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        Button login_btn=(Button) findViewById(R.id.login_btn);
       login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Name = name.getText().toString();
                Password = password.getText().toString();
                BackGround b = new BackGround();
                b.execute(Name, Password);*/
             progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       while (progressbar_status<100){
                           progressbar_status++;
                           android.os.SystemClock.sleep(2);
                           mhandler.post(new Runnable() {
                               @Override
                               public void run() {
                                   progressBar.setProgress(progressbar_status);
                               }
                           });


                      }
                        mhandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent in =new Intent(MainActivity.this,Main_menu.class);
                                startActivity(in);
                            }
                        });
                    }
                }).start();


            }

           class BackGround extends AsyncTask<String, String, String> {

               @Override
               protected String doInBackground(String... params) {
                   String name = params[0];
                   String password = params[1];
                   String data="";
                   int tmp;

                   try {
                       URL url = new URL("https://lpcapi.azurewebsites.net/token");
                       String urlParams = "username="+name+"&password="+password;
                       HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                       httpURLConnection.setDoOutput(true);
                       OutputStream os = httpURLConnection.getOutputStream();
                       os.write(urlParams.getBytes());
                       os.flush();
                       os.close();
                       InputStream is = httpURLConnection.getInputStream();
                       while((tmp=is.read())!=-1){
                           data+= (char)tmp;
                       }

                       is.close();
                       httpURLConnection.disconnect();

                       return data;
                   } catch (MalformedURLException e) {
                       e.printStackTrace();
                       return "Exception: "+e.getMessage();
                   } catch (IOException e) {
                       e.printStackTrace();
                       return "Exception: "+e.getMessage();

                   }
               }

               @Override
               protected void onPostExecute(String s) {
                   String err=null;
                   try {
                       JSONObject root = new JSONObject(s);
                       JSONObject user_data = root.getJSONObject("user_data");
                       NAME = user_data.getString("username");
                       PASSWORD = user_data.getString("password");
                   } catch (JSONException e) {
                       e.printStackTrace();
                       err = "Exception: "+e.getMessage();
                   }


               }
           }

        });



    }
}
