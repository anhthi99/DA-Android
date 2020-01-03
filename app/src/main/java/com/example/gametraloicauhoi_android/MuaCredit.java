package com.example.gametraloicauhoi_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class MuaCredit extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private TextView credit_1, credit_2, credit_3, credit_4, credit_5, socredit_1, socredit_2,
    socredit_3, socredit_4, socredit_5,credit5;
    ArrayList<ImageView> imgs = new ArrayList<>();
    Context context = this;
    String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mua_credit);
        credit_1 = findViewById(R.id.credit_text_1);
        credit_2 = findViewById(R.id.credit_text_2);
        credit_3 = findViewById(R.id.credit_text_3);
        credit_4 = findViewById(R.id.credit_text_4);
        credit_5 = findViewById(R.id.credit_text_5);
        socredit_1 = findViewById(R.id.credit_1);
        socredit_2 = findViewById(R.id.credit_2);
        socredit_3 = findViewById(R.id.credit_3);
        socredit_4 = findViewById(R.id.credit_4);
        socredit_5 = findViewById(R.id.credit_5);
        ImageView img1 = findViewById(R.id.imgMuaC1);
        ImageView img2 = findViewById(R.id.imgMuaC2);
        ImageView img3 = findViewById(R.id.imgMuaC3);
        ImageView img4 = findViewById(R.id.imgMuaC4);
        ImageView img5 = findViewById(R.id.imgMuaC5);
        imgs.add(img1);
        imgs.add(img2);
        imgs.add(img3);
        imgs.add(img4);
        imgs.add(img5);
        for(int i = 0;i<imgs.size();i++){
            imgs.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taoDialog("Bạn có chắc chắn mua gói này không?",v).show();
                }
            });
        }
        credit5 = findViewById(R.id.txtCreditCLV);
        credit5.setText(String.valueOf(ManHinhChinh.credit));

        if(getSupportLoaderManager().getLoader(0)!= null){
            getSupportLoaderManager().restartLoader(0,null,this);
        }
        getSupportLoaderManager().initLoader(0,null,this);
    }

    AlertDialog taoDialog(String message, final View view){
        return new AlertDialog.Builder(this).setMessage(message).setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ManHinhChinh.credit+=(int)view.getTag();
                credit5.setText(String.valueOf(ManHinhChinh.credit));
                data =
                        "id="+ URLEncoder.encode(String.valueOf(ManHinhChinh.ID))+"&credit="+URLEncoder.encode(String.valueOf(ManHinhChinh.credit));
                getSupportLoaderManager().initLoader(1,null,capNhatCredit);
            }
        }).setPositiveButton("Huỷ bỏ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setTitle("Thông báo").create();
    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return  new CreditLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Log.d("DATA",data);
        ArrayList<GoiCredit> arr =new ArrayList<>();
        try {
            JSONObject object = new JSONObject(data);
            JSONArray itemArray = object.getJSONArray("data");
            for (int i=0 ; i<itemArray.length(); i++){
                GoiCredit GR = new GoiCredit();
                GR.setId(itemArray.getJSONObject(i).getInt("id"));
                GR.setSoTien(itemArray.getJSONObject(i).getInt("so_tien"));
                GR.setCredit(itemArray.getJSONObject(i).getInt("credit"));
                arr.add(GR);
            }
            socredit_1.setText(arr.get(0).getCredit()+"");
            socredit_2.setText(arr.get(1).getCredit()+"");
            socredit_3.setText(arr.get(2).getCredit()+"");
            socredit_4.setText(arr.get(3).getCredit()+"");
            socredit_5.setText(arr.get(4).getCredit()+"");
            imgs.get(0).setTag(arr.get(0).getCredit());
            imgs.get(1).setTag(arr.get(1).getCredit());
            imgs.get(2).setTag(arr.get(2).getCredit());
            imgs.get(3).setTag(arr.get(3).getCredit());
            imgs.get(4).setTag(arr.get(4).getCredit());
            credit_1.setText(arr.get(0).getSoTien()+"");
            credit_2.setText(arr.get(1).getSoTien()+"");
            credit_3.setText(arr.get(2).getSoTien()+"");
            credit_4.setText(arr.get(3).getSoTien()+"");
            credit_5.setText(arr.get(4).getSoTien()+"");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    private LoaderManager.LoaderCallbacks<String> capNhatCredit = new LoaderManager.LoaderCallbacks<String>() {
        @NonNull
        @Override
        public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
            return new CapNhatCreditLoader(context,data);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<String> loader, String data) {
            Log.d("CAP",data);
            getSupportLoaderManager().destroyLoader(1);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<String> loader) {

        }
    };
}
class CapNhatCreditLoader extends AsyncTaskLoader<String>{

    String data;
    public CapNhatCreditLoader(@NonNull Context context, String data) {
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
        return NetworkUtils.getJSONPostData("mua-credit",data,NguoiChoi.token);
    }
}