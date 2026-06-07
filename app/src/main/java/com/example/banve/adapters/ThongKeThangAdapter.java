package com.example.banve.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banve.R;
import com.example.banve.models.ThongKeTheoThang;
import com.example.banve.utils.DinhDangTien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ThongKeThangAdapter extends RecyclerView.Adapter<ThongKeThangAdapter.ThongKeThangViewHolder> {
    private final List<ThongKeTheoThang> danhSachThongKe = new ArrayList<>();

    public void capNhatDuLieu(List<ThongKeTheoThang> duLieuMoi) {
        danhSachThongKe.clear();
        if (duLieuMoi != null) {
            danhSachThongKe.addAll(duLieuMoi);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThongKeThangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_thong_ke_thang, parent, false);
        return new ThongKeThangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongKeThangViewHolder holder, int position) {
        ThongKeTheoThang thongKe = danhSachThongKe.get(position);
        holder.lblThang.setText("Tháng: " + dinhDangThang(thongKe.getThang()));
        holder.lblDoanhThu.setText("Doanh thu: " + DinhDangTien.dinhDang(thongKe.getDoanhThu()));
    }

    @Override
    public int getItemCount() {
        return danhSachThongKe.size();
    }

    private String dinhDangThang(String thang) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).parse(thang);
            return new SimpleDateFormat("MM/yyyy", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            return thang == null ? "" : thang;
        }
    }

    static class ThongKeThangViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblThang;
        private final TextView lblDoanhThu;

        public ThongKeThangViewHolder(@NonNull View itemView) {
            super(itemView);
            lblThang = itemView.findViewById(R.id.lblThang);
            lblDoanhThu = itemView.findViewById(R.id.lblDoanhThu);
        }
    }
}
