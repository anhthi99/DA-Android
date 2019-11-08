package com.example.gametraloicauhoi_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChonLinhVuc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_linh_vuc);
    }

    public void HienThiManHinhTroChoi(View view) {
        Intent intent = new Intent(this,ManHinhTroChoi.class);
        startActivity(intent);
    }
}
