package com.example.josep.localhackday;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class FetchData extends AsyncTask<Void,Void, Void> {
    String data = "";
    private ArrayList<String> dinnerItems = new ArrayList<String>();
    private ArrayList<String> lunchItems = new ArrayList<String>();
    private ArrayList<String> breakfastItems = new ArrayList<String>();
    private int btNum = 0;

    private boolean breakfast = false;
    private boolean lunch = false;
    private boolean dinner = false;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Calendar today = Calendar.getInstance();
            int year = today.get(today.YEAR);
            int month = today.get(today.MONTH) + 1;
            int day = today.get(today.DATE);
            String date = (year + "-" + month + "-" + day);
            System.out.println(date);

            URL url = new URL("https://api.dineoncampus.com/v1/location/menu?site_id=5751fd2790975b60e0489226&platform=0&location_id=587124593191a200db4e68af&date=" + date);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while(line!=null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONObject root = new JSONObject(data);
            JSONObject menu = root.getJSONObject("menu");
            JSONArray periods = menu.getJSONArray("periods");
            for (int j = 0; j <periods.length() ; j++) {
                if(periods.getJSONObject(j).getString("name").equals("Breakfast")) {
                    breakfast = true;
                } else if(periods.getJSONObject(j).getString("name").equals("Lunch")) {
                    lunch = true;
                } else if(periods.getJSONObject(j).getString("name").equals("Dinner")) {
                    dinner = true;
                }

                JSONArray categories = periods.getJSONObject(j).getJSONArray("categories");
                for (int i = 0; i < categories.length(); i++) {
                    JSONArray items = categories.getJSONObject(i).getJSONArray("items");
                    for(int k = 0; k<items.length(); k++) {
                        String foodName = items.getJSONObject(k).getString("name");

                        if(breakfast) {
                            breakfastItems.add(foodName);
                        } else if(lunch){
                            lunchItems.add(foodName);
                        } else if(dinner) {
                            dinnerItems.add(foodName);
                        }
                    }
                    breakfast = false;
                    lunch = false;
                    dinner = false;
                }

            }

            Log.d("demo","Breakfast________________________:");
            for(int i = 0; i<breakfastItems.size(); i++) {
                Log.d("demo", "Breakfast: " + breakfastItems.get(i));
            }
            Log.d("demo", "Lunch________________________:");
            for(int i = 0; i<lunchItems.size(); i++) {
                Log.d("demo", "Lunch: " + lunchItems.get(i));
            }
            Log.d("demo", "Dinner________________________:");
            for(int i = 0; i<dinnerItems.size(); i++) {
                Log.d("demo", "Dinner: " + dinnerItems.get(i));
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        showItems();
    }

    public void showItems() {
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        if(btNum == 0) {
            for(int i = 0; i<breakfastItems.size(); i++) {
                stringBuilder.append(breakfastItems.get(i) + "\n");
                count++;
                if(count > 4) {
                    break;
                }
            }
            MainActivity.mTextViewResult.setText(stringBuilder);
        } else if(btNum == 1) {
            for(int i = 0; i<lunchItems.size(); i++) {
                stringBuilder.append(lunchItems.get(i) + "\n");
                count++;
                if(count > 4) {
                    break;
                }
            }
            MainActivity.mTextViewResult.setText(stringBuilder);
        } else if(btNum == 2) {
            for(int i = 0; i<dinnerItems.size(); i++) {
                stringBuilder.append(dinnerItems.get(i) + "\n");
                count++;
                if(count > 4) {
                    break;
                }
            }
            MainActivity.mTextViewResult.setText(stringBuilder);
        }
    }

    public void passBtNum(int btNum) {
        this.btNum = btNum;
    }
}
