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
import com.example.banve.models.MucGioHang;
import com.example.banve.utils.DinhDangTien;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChiTietGioHangAdapter extends RecyclerView.Adapter<ChiTietGioHangAdapter.ChiTietGioHangViewHolder> {
    public interface OnMucGioHangClickListener {
        void onSua(MucGioHang mucGioHang);

        void onXoa(MucGioHang mucGioHang);
    }

    private final List<MucGioHang> danhSachMuc = new ArrayList<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);
    private final OnMucGioHangClickListener listener;

    public ChiTietGioHangAdapter(OnMucGioHangClickListener listener) {
        this.listener = listener;
    }

    public void capNhatDuLieu(List<MucGioHang> duLieuMoi) {
        danhSachMuc.clear();
        if (duLieuMoi != null) {
            danhSachMuc.addAll(duLieuMoi);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChiTietGioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_chi_tiet_gio_hang, parent, false);
        return new ChiTietGioHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietGioHangViewHolder holder, int position) {
        MucGioHang muc = danhSachMuc.get(position);
        holder.lblTenVe.setText(muc.getVe().getTenVe());
        holder.lblNgaySuDung.setText("Ngày sử dụng: " + muc.getChiTietGioHang().getNgaySuDung());
        holder.lblSoLuong.setText(
                "NL: " + muc.getChiTietGioHang().getSoLuongNguoiLon()
                        + " | TE: " + muc.getChiTietGioHang().getSoLuongTreEm()
                        + " | CT: " + muc.getChiTietGioHang().getSoLuongNguoiCaoTuoi()
        );
        holder.lblThanhTien.setText("Thành tiền: " + DinhDangTien.dinhDang(muc.tinhThanhTien()));
        holder.imgAnhVe.setImageResource(R.mipmap.ic_launcher);
        taiAnhVe(muc.getVe().getAnhVe(), holder.imgAnhVe);
        holder.btnSua.setOnClickListener(v -> listener.onSua(muc));
        holder.btnXoa.setOnClickListener(v -> listener.onXoa(muc));
    }

    @Override
    public int getItemCount() {
        return danhSachMuc.size();
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

    static class ChiTietGioHangViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgAnhVe;
        private final TextView lblTenVe;
        private final TextView lblNgaySuDung;
        private final TextView lblSoLuong;
        private final TextView lblThanhTien;
        private final Button btnSua;
        private final Button btnXoa;

        public ChiTietGioHangViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhVe = itemView.findViewById(R.id.imgAnhVe);
            lblTenVe = itemView.findViewById(R.id.lblTenVe);
            lblNgaySuDung = itemView.findViewById(R.id.lblNgaySuDung);
            lblSoLuong = itemView.findViewById(R.id.lblSoLuong);
            lblThanhTien = itemView.findViewById(R.id.lblThanhTien);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
}

