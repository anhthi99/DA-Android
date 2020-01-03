package com.example.gametraloicauhoi_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

public class ManHinhTroChoi extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    Button daA,daB,daC,daD,cauHoi;
    int cauHienTai = -1;
    String data;
    final int CAP_NHAT = 1;
    TextView cauHoiHT,thoiGian,txDiem,credit;
    int TIME_COUNT = CauHinhVaLuuTru.cauHinhApp.getThoiGianTraLoi();
    int count_down;
    public static int diem = 0;
    int co_hoi = 3;
    Context _context = this;
    Timer tm = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_tro_choi);
        mediaPlayer = MediaPlayer.create(ManHinhTroChoi.this,R.raw.bat_dau);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
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
        credit = findViewById(R.id.txtCreditCLV);
        credit.setText(String.valueOf(ManHinhChinh.credit));
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
                        @SuppressLint("DefaultLocale")
                        @Override
                        public void run() {
                            if(count_down >= 0){
                                thoiGian.setText(String.format("%02d",count_down));
                                count_down--;
                            }
                            else{
                                loadCoHoi(0);
                                co_hoi--;
                            }
                        }
                    });
                }
            },0,1000);

        }
        else{
            taoForm();
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

    public void loadCoHoi( int type){
        TextView tx;
        if(co_hoi > 0 && type == 0){
            tm.cancel();
            tm.purge();
            count_down = TIME_COUNT;
            cauHoiTiepTheo();
        }
        switch (co_hoi){
            case 1:
                tx = findViewById(R.id.txtCH1);
                tx.setVisibility(View.INVISIBLE);
                tm.cancel();
                tm.purge();
                taoForm();
                break;
            case 2:
                tx = findViewById(R.id.txtCH2);
                tx.setVisibility(View.INVISIBLE);
                break;
            case 3:
                tx = findViewById(R.id.txtCH3);
                tx.setVisibility(View.INVISIBLE);
                break;

        }
    }

    private void taoForm(){
        mediaPlayer.stop();
        FragmentManager fm = getSupportFragmentManager();
        GameOverDialogFragment gm = new GameOverDialogFragment();
        gm.show(fm,"assassin");
    }



    public void chonDapAn(final View view){
        final String pa = ((TextView)view).getText().toString(),da =
                CauHinhVaLuuTru.mDSCauHoi.get(cauHienTai).getDapAn();
        int color = R.color.yellow;
        view.setBackgroundColor(getResources().getColor(color));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(kiemTraDapAn(pa,da)){
                    view.setBackgroundColor(getResources().getColor(R.color.lightgreen));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            congDiem();
                            tm.cancel();
                            tm.purge();
                            count_down = TIME_COUNT;
                            cauHoiTiepTheo();
                            Drawable drawable = getDrawable(R.drawable.number_game);
                            view.setBackground(drawable);
                        }
                    },1000);

                }
                else{
                    view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    loadCoHoi(1);
                    co_hoi--;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.setBackground(getDrawable(R.drawable.number_game));

                        }
                    },900);

                }
            }

        },1500);
    }

    private boolean kiemTraDapAn(String pa, String da){
        return pa.equals(da);
    }
    private void congDiem(){
        ManHinhTroChoi.diem += CauHinhVaLuuTru.cauHinhDiemCauHoi.get(cauHienTai).getDiem();
        txDiem.setText("Điểm: "+diem);
    }
    private AlertDialog taoDialog(String message, String title){
        return new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Huỷ bỏ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
                .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TruCredit(id);
                        temp.setVisibility(View.INVISIBLE);
                    }
                }).create();
    }
    int id;
    View temp;
    public void LuaChonTroGiup(View view){
        temp = view;
        id = Integer.parseInt(view.getTag().toString());
        String message =
                "Bạn có muốn sử dụng quyền trợ giúp "+ view.getContentDescription().toString().toUpperCase();
        String title = "Thông báo";
        taoDialog(message,title).show();
    }

    public void TruCredit(int id){
        ManHinhChinh.credit-=CauHinhVaLuuTru.cauHinhTroGiup.get(id).getCredit();
        credit.setText(String.valueOf(ManHinhChinh.credit));
    }
}
