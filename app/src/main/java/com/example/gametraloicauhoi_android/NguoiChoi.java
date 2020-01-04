package com.example.gametraloicauhoi_android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import static android.content.Context.MODE_PRIVATE;

public class NguoiChoi {
    private int id;
    private String tenDangNhap;
    private String matKhau;
    private String email;
    private String hinhDaiDien;
    private int diemCaoNhat;
    private int credit;
    public static String token = null;

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getDiemCaoNhat() {
        return diemCaoNhat;
    }

    public void setDiemCaoNhat(int diemCaoNhat) {
        this.diemCaoNhat = diemCaoNhat;
    }

    public String getHinhDaiDien() {
        return hinhDaiDien;
    }

    public void setHinhDaiDien(String hinhDaiDien) {
        this.hinhDaiDien = hinhDaiDien;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public NguoiChoi(int id, String tenDangNhap, int diem, int credit ){
        this.id = id;
        this.tenDangNhap = tenDangNhap;
        this.diemCaoNhat = diem;
        this.credit = credit;
    }
}
class NguoiChoiAsync{
    private Context _context;
    private WeakReference<TextView> playerName,txcredit;
    private WeakReference<ImageView> img;

    //private final String MAIN_URL = "http://10.0.3.2:8000";
    private final String MAIN_URL = "http://10.0.2.2:8000";
    public NguoiChoiAsync(Context context, TextView playerName, TextView credit, ImageView img){
        _context = context;
        this.playerName = new WeakReference<>(playerName);
        this.img = new WeakReference<>(img);
        this.txcredit = new WeakReference<>(credit);
    }
    public LoaderManager.LoaderCallbacks nguoiChoi = new LoaderManager.LoaderCallbacks<String>() {
        @NonNull
        @Override
        public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
            return new ThongTinNguoiChoiLoader(_context);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<String> loader, String data) {
            if(data == null){
                taoDialog("Phiên đăng nhập hết hạn").show();
            }
            else{
                try{
                    JSONObject jsonObject = new JSONObject(data);
                    int id = jsonObject.getInt("id");
                    String tenDangNhap = jsonObject.getString("ten_dang_nhap");
                    int diem = jsonObject.getInt("diem_cao_nhat");
                    int credit = jsonObject.getInt("credit");
                    String avatar = jsonObject.getString("hinh_dai_dien");
                    if(playerName.get() != null)
                        playerName.get().setText(new NguoiChoi(id,tenDangNhap,diem,credit).getTenDangNhap());
                    ManHinhChinh.ID = id;
                    ManHinhChinh.tenNguoiDung = tenDangNhap;
                    ManHinhChinh.credit = credit;
                    if( txcredit.get()!= null){
                        txcredit.get().setText(credit+"");
                    }

                    Picasso.get().load(MAIN_URL+"/assets/images/users/"+avatar).into(img.get());
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }

        }

        @Override
        public void onLoaderReset(@NonNull Loader<String> loader) {

        }
    };
    private AlertDialog taoDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(_context);
        return builder.setTitle("Thông báo").setMessage(message).setNegativeButton("Đồng ý",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logOut();
                    }
                }).create();
    }
    public void logOut(){
        NguoiChoi.token = null;
        SharedPreferences sharedPreferences = _context.getSharedPreferences("LUU_TOKEN",
                MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(_context,MainActivity.class);
        _context.startActivity(intent);
    }
}
