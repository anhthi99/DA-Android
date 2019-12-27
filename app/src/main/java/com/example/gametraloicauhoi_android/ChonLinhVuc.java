package com.example.gametraloicauhoi_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ChonLinhVuc extends AppCompatActivity {
    private Button btnLV1, btnLV2, btnLV3, btnLV4;
    private final int LAY_LINH_VUC = 1, LAY_DS_CAU_HOI = 2;
    Context _context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_linh_vuc);
        btnLV1 = findViewById(R.id.btnDapAnA);
        btnLV2 = findViewById(R.id.btnDapAnB);
        btnLV3 = findViewById(R.id.btnDapAnC);
        btnLV4 = findViewById(R.id.btnDapAnD);
        CauHinhVaLuuTru.mDSCauHoi = new ArrayList<>();
        _context = this;
        if(getSupportLoaderManager().getLoader(0)!= null){
            getSupportLoaderManager().restartLoader(LAY_LINH_VUC,null,layLinhVuc);
        }
        getSupportLoaderManager().initLoader(LAY_LINH_VUC,null,layLinhVuc);
//        if(getSupportLoaderManager().getLoader(LAY_DS_CAU_HOI) != null)
//            getSupportLoaderManager().destroyLoader(LAY_DS_CAU_HOI);
    }
    private LoaderManager.LoaderCallbacks<String> layLinhVuc = new LoaderManager.LoaderCallbacks<String>() {
        @NonNull
        @Override
        public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
            return new LinhVucLoader(_context);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<String> loader, String data) {
            ArrayList<LinhVuc> arr =new ArrayList<>();
            try {

                JSONObject object = new JSONObject(data);
                JSONArray itemArray = object.getJSONArray("arr");
                for (int i=0 ; i<itemArray.length(); i++){
                    LinhVuc lv = new LinhVuc();
                    lv.setId(itemArray.getJSONObject(i).getInt("id"));
                    lv.setTenLinhVuc(itemArray.getJSONObject(i).getString("ten_linh_vuc"));
                    arr.add(lv);
                }
                btnLV1.setText(arr.get(0).getTenLinhVuc());
                btnLV2.setText(arr.get(1).getTenLinhVuc());
                btnLV3.setText(arr.get(2).getTenLinhVuc());
                btnLV4.setText(arr.get(3).getTenLinhVuc());
                btnLV1.setTag(arr.get(0).getId());
                btnLV2.setTag(arr.get(1).getId());
                btnLV3.setTag(arr.get(2).getId());
                btnLV4.setTag(arr.get(3).getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<String> loader) {

        }
    };

    public void batDauTroChoi(View view){
        CauHinhVaLuuTru.mDSCauHoi.clear();
        int id = (int)view.getTag();
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        if(getSupportLoaderManager().getLoader(LAY_DS_CAU_HOI)!=null)
            getSupportLoaderManager().restartLoader(LAY_DS_CAU_HOI,bundle,layDSCauHoi);
        getSupportLoaderManager().initLoader(LAY_DS_CAU_HOI,bundle,layDSCauHoi);

    }
    private LoaderManager.LoaderCallbacks<String> layDSCauHoi = new LoaderManager.LoaderCallbacks<String>() {
        @NonNull
        @Override
        public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
            return new CauHoiLoader(_context, args != null ? args.getInt("id") : 0);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<String> loader, String data) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray itemArray = jsonObject.getJSONArray("data");
                for (int i = 0;i<itemArray.length();i++){
                   int id = itemArray.getJSONObject(i).getInt("id");
                   String tieuDe = itemArray.getJSONObject(i).getString("noi_dung");
                   int lv_id = itemArray.getJSONObject(i).getInt("linh_vuc_id");
                   String daA = itemArray.getJSONObject(i).getString("phuong_an_a");
                   String daB = itemArray.getJSONObject(i).getString("phuong_an_b");
                   String daC = itemArray.getJSONObject(i).getString("phuong_an_c");
                   String daD = itemArray.getJSONObject(i).getString("phuong_an_d");
                   String dapAn = itemArray.getJSONObject(i).getString("dap_an");
                    CauHinhVaLuuTru.mDSCauHoi.add(new CauHoi(id,tieuDe,lv_id,daA,daB,daC,daD,dapAn));
                }
                if(CauHinhVaLuuTru.mDSCauHoi.size()>0){
                    getSupportLoaderManager().destroyLoader(LAY_DS_CAU_HOI);
                    Intent intent = new Intent(_context,ManHinhTroChoi.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(_context, "Không có câu hỏi", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void onLoaderReset(@NonNull Loader<String> loader) {

        }
    };

//    public void HienThiManHinhTroChoi(View view) {
//        Intent intent = new Intent(this,ManHinhTroChoi.class);
//        startActivity(intent);
//    }
}
class CauHoiLoader extends AsyncTaskLoader<String>{
    int id;
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    public CauHoiLoader(@NonNull Context context, int id) {
        super(context);
        this.id = id;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("id",String.valueOf(id));
        return NetworkUtils.getJSONData("cau-hoi","GET",hashMap
        ,NguoiChoi.token);
    }
}
