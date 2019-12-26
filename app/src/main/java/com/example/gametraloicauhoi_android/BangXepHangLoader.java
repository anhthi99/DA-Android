package com.example.gametraloicauhoi_android;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.HashMap;

public class BangXepHangLoader extends AsyncTaskLoader<String> {

   private final int page;
   private final int limit;
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    public BangXepHangLoader(@NonNull Context context, int page, int limit) {
        super(context);
        this.limit = limit;
        this.page = page;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        HashMap<String , String> queryParams = new HashMap<>();
        queryParams.put("page", Integer.toString(this.page));
        queryParams.put("limit", Integer.toString(this.limit));
        String json = "";
        try {
            json =  NetworkUtils.getJSONData("nguoi-choi","GET", queryParams,NguoiChoi.token);
        }catch (Exception ex){
            return null;
        }
        return json;

    }
}
