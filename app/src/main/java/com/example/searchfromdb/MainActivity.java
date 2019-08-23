package com.example.searchfromdb;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private TextView iid,main,descc,iccon,basee,time;
    EditText da;
    private RequestQueue mQueue;
    String search = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iid = findViewById(R.id.id);
        main=findViewById(R.id.MainTxt);
        descc=findViewById(R.id.destxt);
        iccon=findViewById(R.id.IconTxt);
        basee=findViewById(R.id.BaseTxt);
        time=findViewById(R.id.timetxt);
        Button buttonParse = findViewById(R.id.button_parse);
        da = findViewById(R.id.textView);
        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(MainActivity.this, ""+search, Toast.LENGTH_SHORT).show();
                jsonParse();
            }
        });
    }

    private void jsonParse() {
        search = da.getText().toString();
        String url = "http://api.openweathermap.org/data/2.5/weather?appid=51bd186d81e9633cd022b45a26cdf1f2&q=" + search;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            iid.setText("");
                            time.setText("");
                            descc.setText("");
                            main.setText("");
                            basee.setText("");
                            iccon.setText("");

                            JSONArray jsonArray = response.getJSONArray("weather");

                            JSONObject weatherData = jsonArray.getJSONObject(0);

                            String desc = weatherData.getString("description");
                            int id = weatherData.getInt("id");
                            String main1 = weatherData.getString("main");
                            String icon = weatherData.getString("icon");

                            String base = response.getString("base");
                            int det = response.getInt("dt");
                            Timestamp timestamp = new Timestamp(det);
//Log.e("Time",String.valueOf(timestamp));
                            String dt = String.valueOf(timestamp);

                            iid.append(id+"");
                            main.append(main1);
                            time.append(dt);
                            descc.append(desc);
                            iccon.append(icon);
                            basee.append(base);
                           // mTextViewResult.append(desc + " , " + id + " , " + main1 + " , " + icon + " , " + base + " , " + dt);

//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject employee = jsonArray.getJSONObject(i);
//
//                                String firstName = employee.getString("firstname");
//                                int age = employee.getInt("age");
//                                String mail = employee.getString("mail");
//
//                                mTextViewResult.append(firstName + ", " + String.valueOf(age) + ", " + mail + "\n\n");
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}