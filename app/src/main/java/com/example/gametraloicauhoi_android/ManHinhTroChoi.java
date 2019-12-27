package com.example.gametraloicauhoi_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class ManHinhTroChoi extends AppCompatActivity {

    Button daA,daB,daC,daD,cauHoi;
    int cauHienTai = -1;
    TextView cauHoiHT,thoiGian;
    final long TIME_COUNT = 30;
    long count_down = TIME_COUNT;
    Context _context = this;
    Timer tm = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_tro_choi);
        cauHoi = findViewById(R.id.btnCauHoi);
        daA = findViewById(R.id.btnDapAnA);
        daB = findViewById(R.id.btnDapAnB);
        daC = findViewById(R.id.btnDapAnC);
        daD = findViewById(R.id.btnDapAnD);
        daA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonDapAn(v);
            }
        });
        daB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonDapAn(v);
            }
        });
        daC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonDapAn(v);
            }
        });
        daD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonDapAn(v);
            }
        });
        cauHoiHT = findViewById(R.id.txtSo);
        thoiGian = findViewById(R.id.txtThoiGian);
        cauHoiTiepTheo();
    }
    Handler status;
    public void cauHoiTiepTheo(){
        cauHienTai++;
        if(CauHinhVaLuuTru.mDSCauHoi.size()-1 > cauHienTai){
            loadCauHoi();
            tm = new Timer();
            tm.schedule(new TimerTask() {
                @Override
                public void run() {
                    ((AppCompatActivity)_context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(count_down >= 0){
                                thoiGian.setText(String.format("%02d",count_down));
                                count_down--;
                            }
                        }
                    });
                }
            },0,1000);


        }

    }
//    Runnable demNguoc = new Runnable() {
//        @Override
//        public void run() {
//            if(count_down >= 0){
//                thoiGian.setText(String.format("%02d",count_down));
//                count_down--;
//            }
//            status.postDelayed(this,1000);
//        }
//    };
    public void loadCauHoi(){
        daA.setText(CauHinhVaLuuTru.mDSCauHoi.get(cauHienTai).getPhuongAnA());
        daB.setText(CauHinhVaLuuTru.mDSCauHoi.get(cauHienTai).getPhuongAnB());
        daC.setText(CauHinhVaLuuTru.mDSCauHoi.get(cauHienTai).getPhuongAnC());
        daD.setText(CauHinhVaLuuTru.mDSCauHoi.get(cauHienTai).getPhuongAnD());
        cauHoi.setText(CauHinhVaLuuTru.mDSCauHoi.get(cauHienTai).getNoiDung());
        cauHoiHT.setText(String.format("%02d",(cauHienTai+1)));
    }
    public void chonDapAn(View view){
        if(((TextView)view).getText().equals(CauHinhVaLuuTru.mDSCauHoi.get(cauHienTai).getDapAn())){
            tm.cancel();
            tm.purge();
            count_down = TIME_COUNT;
            cauHoiTiepTheo();
        }

        else{
            Toast.makeText(_context, "Đáp án không chính xác", Toast.LENGTH_SHORT).show();
        }
    }
}
