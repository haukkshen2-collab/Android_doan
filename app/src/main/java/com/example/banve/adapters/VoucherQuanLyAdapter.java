package com.example.banve.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banve.R;
import com.example.banve.models.Voucher;
import com.example.banve.utils.DinhDangTien;
import com.example.banve.utils.HienThi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VoucherQuanLyAdapter extends RecyclerView.Adapter<VoucherQuanLyAdapter.VoucherQuanLyViewHolder> {
    public interface OnVoucherQuanLyClickListener {
        void onSua(Voucher voucher);

        void onXoa(Voucher voucher);
    }

    private final List<Voucher> danhSachVoucher = new ArrayList<>();
    private final OnVoucherQuanLyClickListener listener;

    public VoucherQuanLyAdapter(OnVoucherQuanLyClickListener listener) {
        this.listener = listener;
    }

    public void capNhatDuLieu(List<Voucher> duLieuMoi) {
        danhSachVoucher.clear();
        if (duLieuMoi != null) {
            danhSachVoucher.addAll(duLieuMoi);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoucherQuanLyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_voucher_quan_ly, parent, false);
        return new VoucherQuanLyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherQuanLyViewHolder holder, int position) {
        Voucher voucher = danhSachVoucher.get(position);
        holder.lblMaGiamGia.setText(voucher.getMaGiamGia());
        holder.lblTenVoucher.setText(voucher.getTenVoucher());
        holder.lblGiaTri.setText("Giá trị: " + hienThiGiaTri(voucher));
        holder.lblThoiHan.setText("Thời hạn: " + dinhDangNgay(voucher.getNgayBatDau()) + " - " + dinhDangNgay(voucher.getNgayKetThuc()));
        holder.lblSoLuong.setText("Số lượng: " + voucher.getSoLuong());
        holder.lblTrangThai.setText("Trạng thái: " + HienThi.trangThai(voucher.getTrangThai()));
        holder.btnSua.setOnClickListener(v -> listener.onSua(voucher));
        holder.btnXoa.setOnClickListener(v -> listener.onXoa(voucher));
    }

    @Override
    public int getItemCount() {
        return danhSachVoucher.size();
    }

    private String hienThiGiaTri(Voucher voucher) {
        if ("PhanTram".equals(voucher.getKieuGiamGia())) {
            return String.format(Locale.getDefault(), "%.0f%%", voucher.getGiaTriGiam());
        }
        return DinhDangTien.dinhDang(voucher.getGiaTriGiam());
    }

    private String dinhDangNgay(String ngay) {
        if (ngay == null || ngay.trim().isEmpty()) {
            return "";
        }

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(ngay);
            return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            return ngay;
        }
    }

    static class VoucherQuanLyViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblMaGiamGia;
        private final TextView lblTenVoucher;
        private final TextView lblGiaTri;
        private final TextView lblThoiHan;
        private final TextView lblSoLuong;
        private final TextView lblTrangThai;
        private final Button btnSua;
        private final Button btnXoa;

        public VoucherQuanLyViewHolder(@NonNull View itemView) {
            super(itemView);
            lblMaGiamGia = itemView.findViewById(R.id.lblMaGiamGia);
            lblTenVoucher = itemView.findViewById(R.id.lblTenVoucher);
            lblGiaTri = itemView.findViewById(R.id.lblGiaTri);
            lblThoiHan = itemView.findViewById(R.id.lblThoiHan);
            lblSoLuong = itemView.findViewById(R.id.lblSoLuong);
            lblTrangThai = itemView.findViewById(R.id.lblTrangThai);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
}
