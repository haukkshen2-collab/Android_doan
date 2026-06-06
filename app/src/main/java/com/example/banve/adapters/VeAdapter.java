package com.example.banve.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banve.R;
import com.example.banve.models.Ve;
import com.example.banve.utils.DinhDangTien;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VeAdapter extends RecyclerView.Adapter<VeAdapter.VeViewHolder> {
    public interface OnVeClickListener {
        void onClick(int maVe);
    }

    private final List<Ve> danhSachVe = new ArrayList<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);
    private final OnVeClickListener listener;

    public VeAdapter(OnVeClickListener listener) {
        this.listener = listener;
    }

    public void capNhatDuLieu(List<Ve> duLieuMoi) {
        danhSachVe.clear();
        if (duLieuMoi != null) {
            danhSachVe.addAll(duLieuMoi);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_ve, parent, false);
        return new VeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VeViewHolder holder, int position) {
        Ve ve = danhSachVe.get(position);
        holder.lblTenVe.setText(ve.getTenVe());
        holder.lblGiaVe.setText(DinhDangTien.dinhDang(ve.getGiaVe()));
        holder.imgAnhVe.setImageResource(R.mipmap.ic_launcher);
        taiAnhVe(ve.getAnhVe(), holder.imgAnhVe);
        holder.btnXem.setOnClickListener(v -> listener.onClick(ve.getMaVe()));
        holder.itemView.setOnClickListener(v -> listener.onClick(ve.getMaVe()));
    }

    @Override
    public int getItemCount() {
        return danhSachVe.size();
    }

    private void taiAnhVe(String duongDanAnh, ImageView imageView) {
        if (duongDanAnh == null || duongDanAnh.trim().isEmpty()) {
            return;
        }

        executorService.execute(() -> {
            try {
                InputStream inputStream = new URL(duongDanAnh).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.post(() -> imageView.setImageBitmap(bitmap));
            } catch (Exception ignored) {
                imageView.post(() -> imageView.setImageResource(R.mipmap.ic_launcher));
            }
        });
    }

    static class VeViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgAnhVe;
        private final TextView lblTenVe;
        private final TextView lblGiaVe;
        private final Button btnXem;

        public VeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhVe = itemView.findViewById(R.id.imgAnhVe);
            lblTenVe = itemView.findViewById(R.id.lblTenVe);
            lblGiaVe = itemView.findViewById(R.id.lblGiaVe);
            btnXem = itemView.findViewById(R.id.btnXem);
        }
    }
}

