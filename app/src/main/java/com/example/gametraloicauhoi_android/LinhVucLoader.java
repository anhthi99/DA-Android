package com.example.gametraloicauhoi_android;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class LinhVucLoader extends AsyncTaskLoader<String> {
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    public LinhVucLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public String loadInBackground() {
        String token = NguoiChoi.token;
        return NetworkUtils.getJSONData("linh-vuc","GET",token);

    }
}
