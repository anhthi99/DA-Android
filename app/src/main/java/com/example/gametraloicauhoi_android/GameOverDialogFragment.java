package com.example.gametraloicauhoi_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.Objects;

public class GameOverDialogFragment extends DialogFragment{
    TextView txDCN, txDDD;
    String data = "id="+ URLEncoder.encode(String.valueOf(ManHinhChinh.ID))+"&diem" +
            "="+URLEncoder.encode(String.valueOf(ManHinhTroChoi.diem))+"&credit="+URLEncoder.encode(String.valueOf(ManHinhChinh.credit));;
    private Context _context;
    public GameOverDialogFragment(){
    }
//    public static GameOverDialogFragment newInstance(int diem, int credit){
//        GameOverDialogFragment frag = new GameOverDialogFragment();
//        Bundle args = new Bundle();
//        args.putInt("DIEM",diem);
//        args.putInt("CREDIT",credit);
//        frag.setArguments(args);
//        return frag;
//    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_end_game,container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txDCN = (TextView) view.findViewById(R.id.txtDiemCaoNhat);
        txDDD = (TextView) view.findViewById(R.id.txtDiemDatDuoc);
        Button btnChoiLai = view.findViewById(R.id.btnChoiLai);
        txDDD.setText(String.valueOf(ManHinhTroChoi.diem));
        _context = getContext();
        getLoaderManager().initLoader(0,null,capNhat);
        //Toast.makeText(_context, String.valueOf(ManHinhTroChoi.diem), Toast.LENGTH_SHORT).show();
        Button btnTrangChinh = view.findViewById(R.id.btnTrangChinh);
        btnTrangChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                (Objects.requireNonNull(getActivity())).finish();
            }
        });
        btnChoiLai.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dismiss();
                Intent intent = new Intent(Objects.requireNonNull(getActivity()),ChonLinhVuc.class);
                (Objects.requireNonNull(getActivity())).finish();
                startActivity(intent);

            }
        });

        // Fetch arguments from bundle and set title
//        String title = getArguments().getString("title", "Enter Name");
//        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
//        txDCN.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setCanceledOnTouchOutside(false);
//        if(((AppCompatActivity)(_context)).getSupportLoaderManager().getLoader(0) != null)
//            ((AppCompatActivity)(_context)).getSupportLoaderManager().restartLoader(0,null
//                    ,capNhat);
//        ((AppCompatActivity)(_context)).getSupportLoaderManager().initLoader(0,null,capNhat);
    }

    LoaderManager.LoaderCallbacks<String> capNhat = new LoaderManager.LoaderCallbacks<String>() {
        @NonNull
        @Override
        public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
            return new CapNhatNguoiChoiLoader(_context,data);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<String> loader, String data) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                boolean success = jsonObject.getBoolean("success");
                int dcn = jsonObject.getInt("diem_cao_nhat");
                if(success){
                    if(ManHinhTroChoi.diem > dcn)
                        txDCN.setText(String.valueOf(ManHinhTroChoi.diem));

                    else
                        txDCN.setText(String.valueOf(dcn));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onLoaderReset(@NonNull Loader<String> loader) {

        }
    };

}

class CapNhatNguoiChoiLoader extends AsyncTaskLoader<String> {

    String data;
    public CapNhatNguoiChoiLoader(@NonNull Context context, String data) {
        super(context);
        this.data = data;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getJSONPostData("cap-nhat-game",data,NguoiChoi.token);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}