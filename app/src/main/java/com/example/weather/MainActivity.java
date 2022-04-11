package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

private EditText editTextVille;
private ListView listViewMeteo;

private List<MeteoList> data=new ArrayList<>();
private ListItemModel model;
private Button buttonOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextVille=findViewById(R.id.editTextVille);
        listViewMeteo=findViewById(R.id.listViewMeteo);
        buttonOk=findViewById(R.id.button);
        model=new ListItemModel(getApplicationContext(), R.layout.list_item_layout,data);
        listViewMeteo.setAdapter(model);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Mylog", "..........");
                data.clear();
                model.notifyDataSetChanged();
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                String ville=editTextVille.getText().toString();
                Log.i("Mylog", ville);
                String url="https://samples.openweathermap.org/data/2.5/forecast?q="+ville+"&appid=a4578e39643716894ec78b28a71c7110";

                StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("Mylog", "-----------------------------");
                            Log.i("Mylog", response);
                            List<MeteoList> meteoLists = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("list");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                MeteoList meteoList = new MeteoList();
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                Date date = new Date(jsonObject1.getLong("dt") * 1000);
                                SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm");
                                String dateString = f.format(date);
                                JSONObject main = jsonObject1.getJSONObject("main");
                                JSONArray weather = jsonObject1.getJSONArray("weather");
                                int tempMin = (int) (main.getDouble("temp_min") - 273.15);
                                int tempMax = (int) (main.getDouble("temp_max") - 273.15);
                                int pression = main.getInt("pressure");
                                int humidity = main.getInt("humidity");
                                meteoList.tempMin = tempMin;
                                meteoList.tempMax = tempMax;
                                meteoList.pression = pression;
                                meteoList.humidite = humidity;
                                meteoList.date = dateString;
                                meteoList.image = weather.getJSONObject(0).getString("main");
                                data.add(meteoList);
                            }
                            model.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Mylog", "Connection problem!!");
                    }
                });
                queue.add(stringRequest);

            }
        });

    }
}