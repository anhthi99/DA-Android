package com.example.gametraloicauhoi_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
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

public class ManHinhChinh extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Bitmap> {

    boolean doubleBackToExitPressedOnce = false;
    private ImageView img;
    private TextView tvPlayerName;
    private Profile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh);
        tvPlayerName = findViewById(R.id.txtPlayerName);
        img = findViewById(R.id.imgAvatar);
        if(GoogleSignIn.getLastSignedInAccount(this) != null)
            tvPlayerName.setText(GoogleSignIn.getLastSignedInAccount(this).getDisplayName());
        else{
            if(AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired())
                tvPlayerName.setText(Profile.getCurrentProfile().getName());
        }
//        if(getSupportLoaderManager().getLoader(0) != null){
//            getSupportLoaderManager().restartLoader(0, null, this);
//        }
//        getSupportLoaderManager().initLoader(0,null,this);
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
    }

    @NonNull
    @Override
    public Loader<Bitmap> onCreateLoader(int id, @Nullable Bundle args) {
//        if(GoogleSignIn.getLastSignedInAccount(this) != null){
//            if(GoogleSignIn.getLastSignedInAccount(this).getPhotoUrl() != null)
//                return new NguoiChoiLoader(this,
//                    GoogleSignIn.getLastSignedInAccount(this).getPhotoUrl().toString());
//            else
//                return new NguoiChoiLoader(this,"https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg");
//        }
//        return new NguoiChoiLoader(this,"https://graph.facebook.com/me/picture?type=square&access_token="+AccessToken.getCurrentAccessToken().getToken());
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Bitmap> loader, Bitmap data) {
        img.setImageBitmap(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Bitmap> loader) {

    }

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
