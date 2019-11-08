package com.example.gametraloicauhoi_android;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String BASE_URL =  "http://10.0.3.2:8000/"; // Genymotion
    //private static final String BASE_URL =  "http://10.0.2.2:8000/api/"; // AVD

    static String getJSONData(String uri, String method) {
        HttpURLConnection urlConnection = null;
        String jsonString = null;
        Uri builtURI = Uri.parse(BASE_URL + uri).buildUpon().build();

        try {

            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.connect();

            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            jsonString = convertToString(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        //Log.d("TEST", jsonString);
        return jsonString;
    }

    static String getJSONData(String uri, String method, HashMap<String,String> params) {
        HttpURLConnection urlConnection = null;
        String jsonString = null;
        Uri.Builder builder =  Uri.parse(BASE_URL + uri).buildUpon();
        for (String p: params.keySet()) {
            builder.appendQueryParameter(p, params.get(p));
        }
//        for(int i=0; i<nameParams.length; i++) {
//            builder.appendQueryParameter(nameParams[i].toString(), valueParams[i].toString());
//        }
        Uri builtURI = builder.build();

        try {

            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.connect();

            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            jsonString = convertToString(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        //Log.d(LOG_TAG, jsonString);
        return jsonString;
    }

    static String convertToString(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (builder.length() == 0) {
            return null;
        }
        return builder.toString();
    }
}
