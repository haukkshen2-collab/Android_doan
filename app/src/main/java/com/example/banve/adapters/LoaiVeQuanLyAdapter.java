package com.example.banve.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banve.R;
import com.example.banve.models.LoaiVe;

import java.util.ArrayList;
import java.util.List;

public class LoaiVeQuanLyAdapter extends RecyclerView.Adapter<LoaiVeQuanLyAdapter.LoaiVeQuanLyViewHolder> {
    public interface OnLoaiVeClickListener {
        void onSua(LoaiVe loaiVe);

        void onXoa(LoaiVe loaiVe);
    }

    private final List<LoaiVe> danhSachLoaiVe = new ArrayList<>();
    private final OnLoaiVeClickListener listener;

    public LoaiVeQuanLyAdapter(OnLoaiVeClickListener listener) {
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
    public LoaiVeQuanLyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_loai_ve_quan_ly, parent, false);
        return new LoaiVeQuanLyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiVeQuanLyViewHolder holder, int position) {
        LoaiVe loaiVe = danhSachLoaiVe.get(position);
        holder.lblTenLoaiVe.setText(loaiVe.getTenLoaiVe());
        holder.lblMoTa.setText("Mô tả: " + giaTri(loaiVe.getMoTa()));
        holder.lblTrangThai.setText("Trạng thái: " + loaiVe.getTrangThai());
        holder.btnSua.setOnClickListener(v -> listener.onSua(loaiVe));
        holder.btnXoa.setOnClickListener(v -> listener.onXoa(loaiVe));
    }

    @Override
    public int getItemCount() {
        return danhSachLoaiVe.size();
    }

    private String giaTri(String chuoi) {
        return chuoi == null ? "" : chuoi;
    }

    static class LoaiVeQuanLyViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblTenLoaiVe;
        private final TextView lblMoTa;
        private final TextView lblTrangThai;
        private final Button btnSua;
        private final Button btnXoa;

        public LoaiVeQuanLyViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTenLoaiVe = itemView.findViewById(R.id.lblTenLoaiVe);
            lblMoTa = itemView.findViewById(R.id.lblMoTa);
            lblTrangThai = itemView.findViewById(R.id.lblTrangThai);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
}
