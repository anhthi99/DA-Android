package com.example.gametraloicauhoi_android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    Button loginFBButton;
    Button loginGGButon;
    String data = "";
    TextView tUser, tPass;
    Button btnDangNhap;
    final int RC_SIGN_IN = 9001;
    ProgressDialog progressDialog;
    //int index = 1;
    CallbackManager callbackManager;
    boolean doubleBackToExitPressedOnce = false;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        tUser = findViewById(R.id.txtUserName);
        tPass = findViewById(R.id.txtPassword);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ManHinhChinh.class);
                startActivity(intent);
            }
        });
        loginFBButton = findViewById(R.id.btnfacebook);
        loginGGButon = findViewById(R.id.btnGoogle);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Intent intent = new Intent(MainActivity.this,ManHinhChinh.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if(isLoggedIn){
            Intent intent = new Intent(MainActivity.this,ManHinhChinh.class);
            startActivity(intent);
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            Intent intent = new Intent(this,ManHinhChinh.class);
            startActivity(intent);
        }


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
            if(requestCode == RC_SIGN_IN){
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Intent intent = new Intent(this,ManHinhChinh.class);
            startActivity(intent);
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("LOGINGG", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    public void HienThiQuenMatKhau(View view) {
        Intent intent = new Intent(this,QuenMatKhau.class);
        startActivity(intent);
    }

    public void HienThiDangKi(View view) {
        Intent intent = new Intent(this,DangKi.class);
        startActivity(intent);
    }

    public void DangNhapFB(View view) {
        //index = 1;
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    public void DangNhapGG(View view) {
        //index = 2;
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }
    public void dangNhap(View view) {
        String user = tUser.getText().toString();
        String pass = tPass.getText().toString();
        this.data = URLEncoder.encode("ten_dang_nhap")
                +"="+URLEncoder.encode(user)
                +"&"+ URLEncoder.encode("mat_khau")
                +"="+URLEncoder.encode(pass);
        progressDialog = new ProgressDialog(this);
        progressDialog.create();
        progressDialog.setTitle(R.string.app_name);
        progressDialog.setMessage("Đang đăng nhập");
        progressDialog.show();
        if(getSupportLoaderManager().getLoader(0) != null)
            getSupportLoaderManager().restartLoader(0,null,this);
        getSupportLoaderManager().initLoader(0,null,this);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new DangNhapLoader(this,this.data);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Log.d("sss",data);
        progressDialog.dismiss();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }


}
