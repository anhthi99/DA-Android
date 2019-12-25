package com.example.gametraloicauhoi_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class ManHinhChinh extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    private ImageView img;
    Context _context;
    private TextView tvPlayerName;
    private Profile profile;
    final int LAY_THONG_TIN = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh);
        tvPlayerName = findViewById(R.id.txtPlayerName);
        img = findViewById(R.id.imageView2);
        _context = this;
//        if(GoogleSignIn.getLastSignedInAccount(this) != null)
//            tvPlayerName.setText(GoogleSignIn.getLastSignedInAccount(this).getDisplayName());
//        else{
//            if(AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired())
//                tvPlayerName.setText(Profile.getCurrentProfile().getName());
//        }

        getSupportLoaderManager().initLoader(LAY_THONG_TIN,null,new LoaderManager.LoaderCallbacks<String>() {
            @NonNull
            @Override
            public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
                return new ThongTinNguoiChoiLoader(_context);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<String> loader, String data) {
                if(data.equals("")) {
                    finish();
                }
                try{
                    JSONObject jsonObject = new JSONObject(data);
                    int id = jsonObject.getInt("id");
                    String tenDangNhap = jsonObject.getString("ten_dang_nhap");
                    int diem = jsonObject.getInt("diem_cao_nhat");
                    int credit = jsonObject.getInt("credit");
                    tvPlayerName.setText(new NguoiChoi(id,tenDangNhap,diem,credit).getTenDangNhap());
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<String> loader) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(doubleBackToExitPressedOnce){
            super.onBackPressed();

            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 3000);
    }
    public void HienThiQuanLyTaiKhoan(View view) {
        Intent intent = new Intent(this,QuanLyTaiKhoan.class);
        startActivity(intent);
    }

    public void HienThiMuaCredit(View view) {
        Intent intent = new Intent(this,MuaCredit.class);
        startActivity(intent);
    }

    public void HienThiBXH(View view) {
        Intent intent = new Intent(this,BangXepHang.class);
        startActivity(intent);
    }

    public void HienThiLichSuChoi(View view) {
        Intent intent = new Intent(this,LichSuChoi.class);
        startActivity(intent);
    }

    public void HienThiChonLinhVuc(View view) {
        Intent intent = new Intent(this,ChonLinhVuc.class);
        startActivity(intent);
        finishActivity(0);
    }
    private LoaderManager.LoaderCallbacks<Bitmap> loadAnh = new LoaderManager.LoaderCallbacks<Bitmap>() {
        @NonNull
        @Override
        public Loader<Bitmap> onCreateLoader(int id, @Nullable Bundle args) {
            return null;
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Bitmap> loader, Bitmap data) {

        }

        @Override
        public void onLoaderReset(@NonNull Loader<Bitmap> loader) {

        }
    };

    GoogleSignInClient mGoogleClient;
    public void DangXuat(View view) {
        if(AccessToken.getCurrentAccessToken() != null){
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else if(GoogleSignIn.getLastSignedInAccount(this) != null)
        {
            GoogleSignIn.getClient(this,GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).signOut();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }

}
class ThongTinNguoiChoiLoader extends AsyncTaskLoader<String>{

    public ThongTinNguoiChoiLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getJSONData("lay-thong-tin","GET",NguoiChoi.token);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
