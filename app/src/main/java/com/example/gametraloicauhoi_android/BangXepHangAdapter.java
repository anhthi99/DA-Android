package com.example.gametraloicauhoi_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BangXepHangAdapter extends RecyclerView.Adapter<BangXepHangAdapter.BangXepHangViewHolder> {
        private final ArrayList<NguoiChoi> arrayList;
        private LayoutInflater inflater;

        public BangXepHangAdapter(Context context, ArrayList<NguoiChoi> arrayList){
            this.inflater = LayoutInflater.from(context);
            this.arrayList = arrayList;

        }
    @NonNull
    @Override
    public BangXepHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.item_bangxephang,parent,false);
        return new BangXepHangViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull BangXepHangViewHolder holder, int position) {
            NguoiChoi nguoiChoi = arrayList.get(position);
            holder.mTenNguoiChoi.setText(nguoiChoi.getTenDangNhap());
            holder.mDiemNguoiChoi.setText(nguoiChoi.getDiemCaoNhat());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class BangXepHangViewHolder extends RecyclerView.ViewHolder {
            private TextView mTenNguoiChoi, mDiemNguoiChoi;
            private BangXepHangAdapter bangXepHangAdapter;

        public BangXepHangViewHolder(@NonNull View itemView,BangXepHangAdapter bangXepHangAdapter) {
            super(itemView);
            this.bangXepHangAdapter =  bangXepHangAdapter;
            this.mTenNguoiChoi = itemView.findViewById(R.id.txtTenNguoiChoi);
            this.mDiemNguoiChoi = itemView.findViewById(R.id.txtDiemSo);

        }
    }
}
