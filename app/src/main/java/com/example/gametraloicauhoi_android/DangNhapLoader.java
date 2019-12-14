package com.example.gametraloicauhoi_android;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class DangNhapLoader extends AsyncTaskLoader<String> {
    String data;
    final String BASE_URL = "http://10.0.3.2:8000/api/dang-nhap";
    public DangNhapLoader(@NonNull Context context, String data) {
        super(context);
        this.data = data;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {
            // Defined URL  where to send data
            URL url = new URL(BASE_URL);

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
                sb.append(line).append("\n");
            }


            text = sb.toString();
            Log.d("AFF",text);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if (reader != null) {
                    reader.close();
                }
            }

            catch(Exception ex) {ex.printStackTrace();}
        }
        return text;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
