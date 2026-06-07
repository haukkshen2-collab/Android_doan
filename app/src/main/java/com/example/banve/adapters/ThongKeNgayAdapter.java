package com.example.banve.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banve.R;
import com.example.banve.models.ThongKeTheoNgay;
import com.example.banve.utils.DinhDangTien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ThongKeNgayAdapter extends RecyclerView.Adapter<ThongKeNgayAdapter.ThongKeNgayViewHolder> {
    private final List<ThongKeTheoNgay> danhSachThongKe = new ArrayList<>();

    public void capNhatDuLieu(List<ThongKeTheoNgay> duLieuMoi) {
        danhSachThongKe.clear();
        if (duLieuMoi != null) {
            danhSachThongKe.addAll(duLieuMoi);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThongKeNgayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_thong_ke_ngay, parent, false);
        return new ThongKeNgayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongKeNgayViewHolder holder, int position) {
        ThongKeTheoNgay thongKe = danhSachThongKe.get(position);
        holder.lblNgay.setText("Ngày: " + dinhDangNgay(thongKe.getNgay()));
        holder.lblDoanhThu.setText("Doanh thu: " + DinhDangTien.dinhDang(thongKe.getDoanhThu()));
    }

    @Override
    public int getItemCount() {
        return danhSachThongKe.size();
    }

    private String dinhDangNgay(String ngay) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(ngay);
            return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            return ngay == null ? "" : ngay;
        }
    }

    static class ThongKeNgayViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblNgay;
        private final TextView lblDoanhThu;

        public ThongKeNgayViewHolder(@NonNull View itemView) {
            super(itemView);
            lblNgay = itemView.findViewById(R.id.lblNgay);
            lblDoanhThu = itemView.findViewById(R.id.lblDoanhThu);
        }
    }
}
