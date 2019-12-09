package com.example.gametraloicauhoi_android;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class BangXepHangLoader extends AsyncTaskLoader<String> {
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    public BangXepHangLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getJSONData("nguoi-choi","GET");
    }
}
