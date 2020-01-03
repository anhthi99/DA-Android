package com.example.gametraloicauhoi_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URLEncoder;

public class KetThucGame extends AppCompatActivity {

    TextView txDCN,txDDD;
    final int CAP_NHAT_SO = 1;
    Context _context = this;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        txDCN = findViewById(R.id.txtDiemCaoNhat);
        txDDD = findViewById(R.id.txtDiemDatDuoc);
        Log.d("CC",data);
        //Toast.makeText(_context, "đâsdsad", Toast.LENGTH_SHORT).show();
    }



}