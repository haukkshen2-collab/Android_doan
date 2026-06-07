package com.example.banve.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banve.R;
import com.example.banve.models.ThongKeTheoLoaiVe;
import com.example.banve.utils.DinhDangTien;

import java.util.ArrayList;
import java.util.List;

public class ThongKeLoaiVeAdapter extends RecyclerView.Adapter<ThongKeLoaiVeAdapter.ThongKeLoaiVeViewHolder> {
    private final List<ThongKeTheoLoaiVe> danhSachThongKe = new ArrayList<>();

    public void capNhatDuLieu(List<ThongKeTheoLoaiVe> duLieuMoi) {
        danhSachThongKe.clear();
        if (duLieuMoi != null) {
            danhSachThongKe.addAll(duLieuMoi);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThongKeLoaiVeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_thong_ke_loai_ve, parent, false);
        return new ThongKeLoaiVeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongKeLoaiVeViewHolder holder, int position) {
        ThongKeTheoLoaiVe thongKe = danhSachThongKe.get(position);
        holder.lblTenLoai.setText(thongKe.getTenLoaiVe());
        holder.lblSoVe.setText("Số vé bán: " + thongKe.getSoVeBan());
        holder.lblDoanhThu.setText("Doanh thu: " + DinhDangTien.dinhDang(thongKe.getDoanhThu()));
    }

    @Override
    public int getItemCount() {
        return danhSachThongKe.size();
    }

    static class ThongKeLoaiVeViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblTenLoai;
        private final TextView lblSoVe;
        private final TextView lblDoanhThu;

        public ThongKeLoaiVeViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTenLoai = itemView.findViewById(R.id.lblTenLoai);
            lblSoVe = itemView.findViewById(R.id.lblSoVe);
            lblDoanhThu = itemView.findViewById(R.id.lblDoanhThu);
        }
    }
}
