package com.example.gametraloicauhoi_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class BangXepHang extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    private final ArrayList<NguoiChoi> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BangXepHangAdapter BXHAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangxephang);
        recyclerView = findViewById(R.id.recyclerView);
        BXHAdapter = new BangXepHangAdapter(this,arrayList);
        recyclerView.setAdapter(BXHAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(getSupportLoaderManager().getLoader(0) != null){
            getSupportLoaderManager().initLoader(0,null,this);
        }
        getSupportLoaderManager().restartLoader(0,null,this);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new BangXepHangLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
