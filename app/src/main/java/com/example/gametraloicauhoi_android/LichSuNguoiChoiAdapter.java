package com.example.gametraloicauhoi_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LichSuNguoiChoiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int TYPE_VIEW_ITEM = 0 ;
    private final static int TYPE_VIEW_LOADING = 1;
    private final ArrayList<LuotChoi> listLuotChoi;
    private LayoutInflater inflater;
    public LichSuNguoiChoiAdapter(Context context, ArrayList<LuotChoi> arrayList){
        this.inflater = LayoutInflater.from(context);
        this.listLuotChoi = arrayList;
    }
    public  int getTypeViewItem(int position){
        return listLuotChoi.get(position) == null ? TYPE_VIEW_LOADING : TYPE_VIEW_ITEM;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_VIEW_ITEM){
            View itemView = this.inflater.inflate(R.layout.item_lich_su_choi,parent,false);
            return new lichSuChoiViewHolder(itemView,this);
        }
        else if(viewType == TYPE_VIEW_LOADING) {
            View itemView = this.inflater.inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
           if(holder instanceof lichSuChoiViewHolder){
               HienThiThongTin((lichSuChoiViewHolder)holder,position);
           } else if(holder instanceof LoadingViewHolder){
               HienThiProgressBar((LoadingViewHolder)holder);
           }
    }

    @Override
    public int getItemCount() {
        return this.listLuotChoi == null?0:this.listLuotChoi.size();
    }
    public class lichSuChoiViewHolder extends RecyclerView.ViewHolder{
        private final TextView mSoCau, mDiem, mThoiGian;
        private final LichSuNguoiChoiAdapter nguoiChoiAdapter;

        public lichSuChoiViewHolder(@NonNull View itemView, LichSuNguoiChoiAdapter lichSuNguoiChoiAdapter) {
            super(itemView);
            this.nguoiChoiAdapter = lichSuNguoiChoiAdapter;
            this.mSoCau = itemView.findViewById(R.id.txtSoCau);
            this.mDiem = itemView.findViewById(R.id.txtSoDiem);
            this.mThoiGian = itemView.findViewById(R.id.txtNgayGio);
        }
    }
     class LoadingViewHolder extends RecyclerView.ViewHolder{
         private final ProgressBar Bar;
         public LoadingViewHolder(@NonNull View itemView) {
             super(itemView);
             this.Bar = itemView.findViewById(R.id.progressBar);
         }
     }
   private void HienThiThongTin(lichSuChoiViewHolder holder, int position){
        LuotChoi luotChoi = listLuotChoi.get(position);
        holder.mSoCau.setText(luotChoi.getSoCau()+"");
        holder.mDiem.setText(luotChoi.getDiem()+"");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long dt = Long.parseLong(luotChoi.getNgayGio());
        Date date = new Date(dt);
        holder.mThoiGian.setText(formatter.format(date).toString());
       //holder.mThoiGian.setText(luotChoi.getNgayGio());
    }
    private void HienThiProgressBar(LoadingViewHolder holder) {
    }


}
