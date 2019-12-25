package com.example.gametraloicauhoi_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

public class DangKi extends AppCompatActivity {

    private final int DANG_KY = 1;
    private String data = "";
    EditText ten,email,pass,repass;
    Context _context;
    Button nutDangKy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        nutDangKy = findViewById(R.id.btnDangKi);
        ten = findViewById(R.id.txtTenDangNhap);
        email = findViewById(R.id.txtEmail);
        pass = findViewById(R.id.txtPassword);
        repass = findViewById(R.id.txtComfirmPassword);
        _context = this;
    }
    private LoaderManager.LoaderCallbacks<String> dangKy = new LoaderManager.LoaderCallbacks<String>() {
        @NonNull
        @Override
        public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
            return new DangKyLoader(_context,data);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<String> loader, String data) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");
                Toast.makeText(DangKi.this, message, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<String> loader) {

        }
    };

    public void dangKy(View view) {
        String tenDangNhap = URLEncoder.encode(ten.getText().toString());
        String matKhau = URLEncoder.encode(pass.getText().toString());
        String nlMatKhau = URLEncoder.encode(repass.getText().toString());
        String mail = URLEncoder.encode(email.getText().toString());
        if(!nlMatKhau.equals(matKhau)){
            Toast.makeText(this, "Mật khẩu nhập lại không giống nhau", Toast.LENGTH_SHORT).show();
        }
        else if(mail.contains("@")) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
        }
        else{
            data = URLEncoder.encode("ten_dang_nhap")+"="+tenDangNhap+"&"+URLEncoder.encode("mat_khau")+"="+matKhau+"&"+URLEncoder.encode("email")+"="+mail;
            getSupportLoaderManager().initLoader(DANG_KY,null,dangKy);
        }
    }
}
class DangKyLoader extends AsyncTaskLoader<String>{

    final String data;
    public DangKyLoader(@NonNull Context context, String data) {
        super(context);
        this.data = data;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getJSONPostData("dang-ky",this.data);
    }
}
