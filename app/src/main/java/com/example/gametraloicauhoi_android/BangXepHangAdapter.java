package com.example.gametraloicauhoi_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BangXepHangAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final static int TYPE_VIEW_ITEM = 0 ;
        private final static int TYPE_VIEW_LOADING = 1;
        private final ArrayList<NguoiChoi> arrayList;
        private LayoutInflater inflater;

        public BangXepHangAdapter(Context context, ArrayList<NguoiChoi> arrayList){
            this.inflater = LayoutInflater.from(context);
            this.arrayList = arrayList;

        }
    public  int getTypeViewItem(int position){
            return arrayList.get(position) == null ? TYPE_VIEW_LOADING : TYPE_VIEW_ITEM;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           if(viewType == TYPE_VIEW_ITEM){
               View itemView = this.inflater.inflate(R.layout.item_bangxephang,parent,false);
               return new BangXepHangViewHolder(itemView,this);
           }
           else if(viewType == TYPE_VIEW_LOADING) {
               View itemView = this.inflater.inflate(R.layout.item_loading, parent, false);
               return new LoadingViewHolder(itemView);
           }
           return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BangXepHangViewHolder) {
            HienThiThongTin((BangXepHangViewHolder)holder, position);
        } else if(holder instanceof LoadingViewHolder) {
            HienThiProgressBar((LoadingViewHolder)holder);
        }
    }


    @Override
    public int getItemCount() {
        return this.arrayList == null?0:this.arrayList.size();
    }

    public class BangXepHangViewHolder extends RecyclerView.ViewHolder {
            private final TextView mTenNguoiChoi, mDiemNguoiChoi;
            private final ImageView imgADD;
            private final BangXepHangAdapter bangXepHangAdapter;

        public BangXepHangViewHolder(@NonNull View itemView,BangXepHangAdapter mbangXepHangAdapter) {
            super(itemView);
            this.bangXepHangAdapter =  mbangXepHangAdapter;
            this.mTenNguoiChoi = itemView.findViewById(R.id.txtTenNguoiChoi);
            this.mDiemNguoiChoi = itemView.findViewById(R.id.txtDiemSo);
            this.imgADD = itemView.findViewById(R.id.imgAnhDaiDien);
        }
    }

     class LoadingViewHolder extends RecyclerView.ViewHolder {
            private final ProgressBar Bar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            this.Bar = itemView.findViewById(R.id.progressBar);

        }
    }
    private void HienThiThongTin(BangXepHangViewHolder holder, int position){
        NguoiChoi nguoiChoi = arrayList.get(position);
        holder.mTenNguoiChoi.setText(nguoiChoi.getTenDangNhap());
        holder.mDiemNguoiChoi.setText(nguoiChoi.getDiemCaoNhat()+"");
        Picasso.get().load("http://localhost:8000/assets/images/users/a.jpg").into(holder.imgADD);

    }
    private void HienThiProgressBar(LoadingViewHolder holder){

    }
}
