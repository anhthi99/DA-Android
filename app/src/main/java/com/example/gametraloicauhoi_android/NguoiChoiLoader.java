package com.example.gametraloicauhoi_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.InputStream;
import java.util.Objects;

public class NguoiChoiLoader extends AsyncTaskLoader<Bitmap> {
    private String urlImage;
    public NguoiChoiLoader(@NonNull Context context,String img) {
        super(context);
        urlImage = img;
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public Bitmap loadInBackground()
    {
        String urldisplay = urlImage;
        Bitmap bmp = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", Objects.requireNonNull(e.getMessage()));
            e.printStackTrace();
        }
        return bmp;
    }
}
