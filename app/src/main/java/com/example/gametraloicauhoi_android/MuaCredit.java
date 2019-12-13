package com.example.gametraloicauhoi_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MuaCredit extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private TextView credit_1, credit_2, credit_3, credit_4, credit_5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mua_credit);
        credit_1 = findViewById(R.id.credit_text_1);
        credit_2 = findViewById(R.id.credit_text_2);
        credit_3 = findViewById(R.id.credit_text_3);
        credit_4 = findViewById(R.id.credit_text_4);
        credit_5 = findViewById(R.id.credit_text_5);
        if(getSupportLoaderManager().getLoader(0)!= null){
            getSupportLoaderManager().restartLoader(0,null,this);
        }
        getSupportLoaderManager().initLoader(0,null,this);
    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return  new CreditLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        ArrayList<GoiCredit> arr =new ArrayList<>();
        try {
            JSONObject object = new JSONObject(data);
            JSONArray itemArray = object.getJSONArray("arr");
            for (int i=0 ; i<itemArray.length(); i++){
                GoiCredit GR = new GoiCredit();
                GR.setId(itemArray.getJSONObject(i).getInt("id"));
                GR.setSoTien(itemArray.getJSONObject(i).getInt("so_tien"));
                arr.add(GR);
            }

            credit_1.setText(arr.get(0).getSoTien()+"");
            credit_2.setText(arr.get(1).getSoTien()+"");
            credit_3.setText(arr.get(2).getSoTien()+"");
            credit_4.setText(arr.get(3).getSoTien()+"");
            credit_5.setText(arr.get(4).getSoTien()+"");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
