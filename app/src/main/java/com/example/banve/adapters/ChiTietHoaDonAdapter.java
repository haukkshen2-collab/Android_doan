package com.example.banve.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banve.R;
import com.example.banve.models.ChiTietHoaDon;
import com.example.banve.models.Ve;
import com.example.banve.utils.DinhDangTien;

import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonAdapter extends RecyclerView.Adapter<ChiTietHoaDonAdapter.ChiTietHoaDonViewHolder> {
    private final List<ChiTietHoaDon> danhSachChiTiet = new ArrayList<>();

    public void capNhatDuLieu(List<ChiTietHoaDon> duLieuMoi) {
        danhSachChiTiet.clear();
        if (duLieuMoi != null) {
            danhSachChiTiet.addAll(duLieuMoi);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChiTietHoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_chi_tiet_hoa_don, parent, false);
        return new ChiTietHoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietHoaDonViewHolder holder, int position) {
        ChiTietHoaDon chiTiet = danhSachChiTiet.get(position);
        Ve ve = chiTiet.getVe();
        String tenVe = ve == null ? "Vé mã " + chiTiet.getMaVe() : ve.getTenVe();

        holder.lblTenVe.setText(tenVe);
        holder.lblNgaySuDung.setText("Ngày sử dụng: " + giaTri(chiTiet.getNgaySuDung()));
        holder.lblSoLuong.setText(
                "Số lượng: Người lớn " + chiTiet.getSoLuongNguoiLon()
                        + " • Trẻ em " + chiTiet.getSoLuongTreEm()
                        + " • Người cao tuổi " + chiTiet.getSoLuongNguoiCaoTuoi()
        );
        holder.lblDonGia.setText(
                "Đơn giá: Người lớn " + DinhDangTien.dinhDang(chiTiet.getDonGiaNguoiLon())
                        + " • Trẻ em " + DinhDangTien.dinhDang(chiTiet.getDonGiaTreEm())
                        + " • Người cao tuổi " + DinhDangTien.dinhDang(chiTiet.getDonGiaNguoiCaoTuoi())
        );
        holder.lblThanhTien.setText("Thành tiền: " + DinhDangTien.dinhDang(chiTiet.getThanhTien()));
    }

    @Override
    public int getItemCount() {
        return danhSachChiTiet.size();
    }

    private String giaTri(String giaTri) {
        return giaTri == null ? "" : giaTri;
    }

    static class ChiTietHoaDonViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblTenVe;
        private final TextView lblNgaySuDung;
        private final TextView lblSoLuong;
        private final TextView lblDonGia;
        private final TextView lblThanhTien;

        public ChiTietHoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTenVe = itemView.findViewById(R.id.lblTenVe);
            lblNgaySuDung = itemView.findViewById(R.id.lblNgaySuDung);
            lblSoLuong = itemView.findViewById(R.id.lblSoLuong);
            lblDonGia = itemView.findViewById(R.id.lblDonGia);
            lblThanhTien = itemView.findViewById(R.id.lblThanhTien);
        }
    }
}
