package com.example.gametraloicauhoi_android;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class CreditLoader extends AsyncTaskLoader<String>{
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    public CreditLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getJSONData("goi-credit","GET","sdadsad");
    }
}
