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
import android.widget.TextView;

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
        data =
                "id="+ URLEncoder.encode(String.valueOf(ManHinhChinh.ID))+"&diem="+URLEncoder.encode(String.valueOf(ManHinhTroChoi.diem))+"&credit="+URLEncoder.encode(String.valueOf(ManHinhChinh.credit));
        Log.d("CC",data);

    }


}
class CapNhatNguoiChoiLoader extends AsyncTaskLoader<String>{

    String data;
    public CapNhatNguoiChoiLoader(@NonNull Context context, String data) {
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
        return NetworkUtils.getJSONPostData("cap-nhat-game",data,NguoiChoi.token);
    }
}