package com.example.tommy.cg24.API;

import android.os.AsyncTask;

import com.example.tommy.cg24.Main;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONObjectAPI extends AsyncTask<String, String, JSONObject> {

    JSONObject jsonObject = new JSONObject();
    StringBuilder jsonStringBuilder = new StringBuilder();
    String jsonLine;

    protected JSONObject doInBackground(String...urls) {
        try {

            URL url = new URL(urls[0]);
            HttpURLConnection urlC = (HttpURLConnection) url.openConnection();
            BufferedReader jsonReader = new BufferedReader(new InputStreamReader(urlC.getInputStream()));
            while ((jsonLine = jsonReader.readLine()) != null) {
                jsonStringBuilder.append(jsonLine);
            }

            jsonObject = new JSONObject(jsonStringBuilder.toString());

        } catch (Exception e) {
            //Nothing
        }
        return jsonObject;
    }

    public Main delegate = null;

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        try {
            delegate.processFinish(jsonObject);
        } catch (JSONException e) {
            //
        }
    }
}
