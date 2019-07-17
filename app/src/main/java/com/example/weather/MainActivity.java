package com.example.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView result;
    EditText city;
    ImageView image;
   String baseurl="https://api.openweathermap.org/data/2.5/weather?q=";
        //&appid=577b7d3e60200e91c523139eecab4f8b"
    String API="&appid=577b7d3e60200e91c523139eecab4f8b";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button);
        result=(TextView)findViewById(R.id.result);
        city=(EditText)findViewById(R.id.city);
        image=(ImageView)findViewById(R.id.imageView);
        final int r=R.drawable.rainy;
        final int h=R.drawable.hazy;
        final int c=R.drawable.clear;
        final int s=R.drawable.sunny;
        final int[] j = new int[1];
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myurl=baseurl+city.getText().toString()+API;
                Log.i("url","URL"+myurl);
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,myurl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject responce) {
                                Log.i("JSON", "JSON: " + responce);

                                try {
                                    String info = responce.getString("weather");
                                    Log.i("INFO", "INFO: " + info);
                                    String data=responce.getString("main");
                                    Log.i("Data", "Data: " + data);
                                    JSONArray ar = new JSONArray(info);

                                        JSONObject jobject=new JSONObject(data);
                                        String temp=jobject.getString("temp");

                                        Double t=Double.parseDouble(temp);
                                        Double sum=t-273.15;
                                        int intresult=Integer.valueOf(sum.intValue());
                                        String Temp=Double.toString(intresult);
                                        String Tempresult=Temp+"°C";
                                    Log.i("temprature","TEMPRATURE :"+sum);

                                    for (int i = 0; i < ar.length(); i++) {
                                        JSONObject parObj = ar.getJSONObject(i);

                                        String myWeather = parObj.getString("main");

                                      //  result.setText(myWeather);

                                        Log.i("ID", "ID: " + parObj.getString("id"));
                                        Log.i("MAIN", "MAIN: " + parObj.getString("main"));
                                        switch (myWeather)
                                        {
                                            case "Rain":
                                                image.setImageResource(r);
                                                break;
                                            case "Haze":
                                                image.setImageResource(h);
                                                break;
                                            case "Clear":
                                                image.setImageResource(c);
                                                break;
                                            case "Sunny":
                                                image.setImageResource(s);
                                                break;
                                             default:
                                                 Log.i("weather","Weather :"+myWeather);

                                        }
                                    }
                               result.setText(Tempresult);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },

                         new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                Log.i("Frror","Error :"+error);
                    }
                });
                MySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);
            }
        });

    }
}