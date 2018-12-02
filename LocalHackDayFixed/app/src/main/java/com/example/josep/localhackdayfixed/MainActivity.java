package com.example.josep.localhackdayfixed;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    public static TextView mTextViewResult;
    public static Button btBreakfast;
    public static Button btLunch;
    public static Button btDinner;
    private TextView tvTodayTitle;

    private RequestQueue mQueue;
    private String urlString = "https://api.dineoncampus.com/v1/location/menu?site_id=5751fd2790975b60e0489226&platform=0&location_id=587124593191a200db4e68af&date=2018-12-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = findViewById(R.id.tv_Homestyle);
        btBreakfast = findViewById(R.id.bt_Breakfast);
        btLunch = findViewById(R.id.bt_Lunch);
        btDinner = findViewById(R.id.bt_Dinner);

        tvTodayTitle = findViewById(R.id.tv_TodayTitle);
        tvTodayTitle.setPaintFlags(tvTodayTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        mQueue = Volley.newRequestQueue(this);

        btBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchData process = new FetchData();
                process.execute();
                process.passBtNum(0);
            }
        });

        btLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchData process = new FetchData();
                process.execute();
                process.passBtNum(1);
            }
        });

        btDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchData process = new FetchData();
                process.execute();
                process.passBtNum(2);
            }
        });
    }

    /*
    private void jsonParse(String url) {
        Scanner reader = new Scanner(System.in);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                //StringBuilder stringBuilder = new StringBuilder();


                try {

                    JSONObject root = new JSONObject();
                    JSONObject menu = root.getJSONObject("menu");

                    JSONArray periods = menu.getJSONArray("periods");
                    System.out.println(periods.toString());


                    //JSONArray periods = menu.getJSONArray("periods");
                    //System.out.println(periods);

/*
                    //for (int m=0;m<menu.length();m++){
                      //  JSONArray periods = response.getJSONArray("periods");
                        for(int p = 0; p<periods.length(); p++) {
                            String mealTime = periods.getJSONObject(p).getString("name"); //determine which meal
                            JSONArray categories = response.getJSONArray("categories");
                            for(int c = 0; c<categories.length(); c++) {
                                String categoryName = categories.getJSONObject(c).getString("name"); //get category name. (homestyle, rooted, etc)
                                JSONArray items = response.getJSONArray("items");
                                for(int i = 0; i<items.length(); i++) {
                                    JSONObject foodName = items.getJSONObject(i);
                                    System.out.println("made it here");
                                    System.out.println(foodName);

                                }
                            }
                        }

                        //JSONObject periods = menu.getJSONObject(i);


                        //String foodItemName = menu.getJSONObject(i).getString("categories");



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
        mQueue.add(jsonObjectRequest);
    }
*/
}
