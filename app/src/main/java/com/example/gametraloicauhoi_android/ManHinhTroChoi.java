package com.example.gametraloicauhoi_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
    TextView cauHoiHT,thoiGian,txDiem;
    int TIME_COUNT = CauHinhVaLuuTru.cauHinhApp.getThoiGianTraLoi();
    int count_down;
    int diem = 0;
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
        txDiem = findViewById(R.id.txtDiem);
        count_down = TIME_COUNT;
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


    @SuppressLint("DefaultLocale")
    public void loadCauHoi(){
        daA.setText(CauHinhVaLuuTru.mDSCauHoi.get(cauHienTai).getPhuongAnA());
        daB.setText(CauHinhVaLuuTru.mDSCauHoi.get(cauHienTai).getPhuongAnB());
        daC.setText(CauHinhVaLuuTru.mDSCauHoi.get(cauHienTai).getPhuongAnC());
        daD.setText(CauHinhVaLuuTru.mDSCauHoi.get(cauHienTai).getPhuongAnD());
        cauHoi.setText(CauHinhVaLuuTru.mDSCauHoi.get(cauHienTai).getNoiDung());
        cauHoiHT.setText(String.format("%02d",(cauHienTai+1)));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void loadDiem(int diem){
        txDiem.setText(String.valueOf(diem));
    }

    public void chonDapAn(View view){
        String pa = ((TextView)view).getText().toString(),da =
                CauHinhVaLuuTru.mDSCauHoi.get(cauHienTai).getDapAn();
        if(kiemTraDapAn(pa,da)){
            congDiem();
            loadDiem(diem);
            tm.cancel();
            tm.purge();
            count_down = TIME_COUNT;
            cauHoiTiepTheo();
        }
        else{
            Toast.makeText(_context, "Đáp án không chính xác", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean kiemTraDapAn(String pa, String da){
        return pa.equals(da);
    }
    private void congDiem(){
        diem+=CauHinhVaLuuTru.cauHinhDiemCauHoi.get(cauHienTai).getDiem();
    }
}
