package com.example.banve.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banve.R;
import com.example.banve.models.LoaiVe;

import java.util.ArrayList;
import java.util.List;

public class LoaiVeAdapter extends RecyclerView.Adapter<LoaiVeAdapter.LoaiVeViewHolder> {
    public interface OnLoaiVeClickListener {
        void onClick(Integer maLoaiVe);
    }

    private final List<LoaiVe> danhSachLoaiVe = new ArrayList<>();
    private final OnLoaiVeClickListener listener;
    private Integer maLoaiVeDangChon;

    public LoaiVeAdapter(OnLoaiVeClickListener listener) {
        this.listener = listener;
    }

    public void capNhatDuLieu(List<LoaiVe> duLieuMoi) {
        danhSachLoaiVe.clear();
        if (duLieuMoi != null) {
            danhSachLoaiVe.addAll(duLieuMoi);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LoaiVeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_loai_ve, parent, false);
        return new LoaiVeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiVeViewHolder holder, int position) {
        LoaiVe loaiVe = danhSachLoaiVe.get(position);
        holder.lblTenLoaiVe.setText(loaiVe.getTenLoaiVe());
        holder.itemView.setSelected(maLoaiVeDangChon != null && maLoaiVeDangChon == loaiVe.getMaLoaiVe());
        holder.itemView.setOnClickListener(v -> {
            maLoaiVeDangChon = loaiVe.getMaLoaiVe();
            notifyDataSetChanged();
            listener.onClick(loaiVe.getMaLoaiVe());
        });
    }

    @Override
    public int getItemCount() {
        return danhSachLoaiVe.size();
    }

    static class LoaiVeViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblTenLoaiVe;

        public LoaiVeViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTenLoaiVe = itemView.findViewById(R.id.lblTenLoaiVe);
        }
    }
}

