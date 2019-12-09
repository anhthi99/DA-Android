package com.example.gametraloicauhoi_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChonLinhVuc extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private Button btnDapAnA, btnDapAnB, btnDapAnC, btnDapAnD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_linh_vuc);
        btnDapAnA = findViewById(R.id.btnDapAnA);
        btnDapAnB = findViewById(R.id.btnDapAnB);
        btnDapAnC = findViewById(R.id.btnDapAnC);
        btnDapAnD = findViewById(R.id.btnDapAnD);
        if(getSupportLoaderManager().getLoader(0)!= null){
            getSupportLoaderManager().restartLoader(0,null,this);
        }
        getSupportLoaderManager().initLoader(0,null,this);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new LinhVucLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        ArrayList<LinhVuc> arr =new ArrayList<>();
        try {
            JSONObject object = new JSONObject(data);
            JSONArray itemArray = object.getJSONArray("arr");
            for (int i=0 ; i<itemArray.length(); i++){
                LinhVuc lv = new LinhVuc();
                lv.setId(itemArray.getJSONObject(i).getInt("id"));
                lv.setTenLinhVuc(itemArray.getJSONObject(i).getString("ten_linh_vuc"));
                arr.add(lv);
            }
            btnDapAnA.setText(arr.get(0).getTenLinhVuc());
            btnDapAnB.setText(arr.get(1).getTenLinhVuc());
            btnDapAnC.setText(arr.get(2).getTenLinhVuc());
            btnDapAnD.setText(arr.get(3).getTenLinhVuc());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

//    public void HienThiManHinhTroChoi(View view) {
//        Intent intent = new Intent(this,ManHinhTroChoi.class);
//        startActivity(intent);
//    }
}
