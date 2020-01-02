package com.example.gametraloicauhoi_android;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.URLEncoder;

public class GameOverDialogFragment extends DialogFragment{
    TextView txDCN, txDDD;
    String data;
    public GameOverDialogFragment(){


    }
    public static GameOverDialogFragment newInstance(int diem, int credit){
        GameOverDialogFragment frag = new GameOverDialogFragment();
        Bundle args = new Bundle();
        args.putInt("DIEM",diem);
        args.putInt("CREDIT",credit);
        frag.setArguments(args);
        return frag;
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


        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
//        txDCN.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

}