package com.example.gametraloicauhoi_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LichSuChoi extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private final ArrayList<LuotChoi> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LichSuNguoiChoiAdapter nguoiChoiAdapter;
    private static  final int PAGE_SIZE = 25;
    private static int curentPage = 1;
    private boolean isLastPage = false;
    private int totalPage ;
    private boolean isLoading =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_choi);
        this.recyclerView = findViewById(R.id.recyclerView_LichSu);
        this.nguoiChoiAdapter = new LichSuNguoiChoiAdapter(this,arrayList);
        this.recyclerView.setAdapter(nguoiChoiAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadData(null);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager != null ? layoutManager.getChildCount() : 0;
                int totalItemCount = layoutManager != null ? layoutManager.getItemCount() : 0;
                int firstVisibleItemPosition = layoutManager != null ? layoutManager.findFirstVisibleItemPosition() : 0;
                if(!isLoading && !isLastPage){
                    if((visibleItemCount + firstVisibleItemPosition)>= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE ){
                        isLoading = true;
                        curentPage ++;
                        arrayList.add(null);
                        nguoiChoiAdapter.notifyItemInserted(arrayList.size() - 1);
                        Bundle data = new Bundle();
                        data.putInt("page", curentPage);
                        data.putInt("limit", PAGE_SIZE);
                        loadData(data);
                    }
                }
            }
        });
    }
    protected  void loadData(Bundle data){
        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().restartLoader(0,data,this);
        }
        getSupportLoaderManager().initLoader(0,data,this);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        int page = 1;
        int limit = 25;
        if(args != null){
            page = args.getInt("page");
            limit = args.getInt("limit");
        }
        return new LichSuNguoiChoiLoader(this,page,limit) ;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Log.d("DATA",data);
        try {
            JSONObject jsonObject = new JSONObject(data);
            int total = jsonObject.getInt("total");
            totalPage = total / PAGE_SIZE;
            if (total % PAGE_SIZE != 0) {
                totalPage++;
            }
            JSONArray itemsArray = jsonObject.getJSONArray("data");
            if(arrayList.size()>0){
                arrayList.remove(arrayList.size() -1);
                int scrollPosition = arrayList.size();
                nguoiChoiAdapter.notifyItemRemoved(scrollPosition);
            }
            for(int i = 0; i< itemsArray.length(); i++){
                int id_nguoiChoi = itemsArray.getJSONObject(i).getInt("nguoi_choi_id");
                int soCau = itemsArray.getJSONObject(i).getInt("so_cau");
                String Diem = itemsArray.getJSONObject(i).getString("diem");
                String NG = itemsArray.getJSONObject(i).getString("ngay_gio");
                this.arrayList.add(new LuotChoi(id_nguoiChoi,soCau,Diem,NG));
            }
            this.nguoiChoiAdapter.notifyDataSetChanged();
            isLastPage = (curentPage == totalPage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


        @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
