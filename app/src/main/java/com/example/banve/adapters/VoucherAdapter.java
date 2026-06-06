package com.example.banve.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banve.R;
import com.example.banve.models.Voucher;
import com.example.banve.utils.DinhDangTien;

import java.util.ArrayList;
import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {
    public interface OnVoucherClickListener {
        void onClick(Voucher voucher);
    }

    private final List<Voucher> danhSachVoucher = new ArrayList<>();
    private final OnVoucherClickListener listener;
    private int maVoucherDangChon = -1;

    public VoucherAdapter(OnVoucherClickListener listener) {
        this.listener = listener;
    }

    public void capNhatDuLieu(List<Voucher> duLieuMoi) {
        danhSachVoucher.clear();
        if (duLieuMoi != null) {
            danhSachVoucher.addAll(duLieuMoi);
        }
        notifyDataSetChanged();
    }

    public void chonVoucher(Voucher voucher) {
        maVoucherDangChon = voucher == null ? -1 : voucher.getMaVoucher();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_voucher, parent, false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        Voucher voucher = danhSachVoucher.get(position);
        holder.lblTenVoucher.setText(voucher.getTenVoucher());
        holder.lblMoTaGiam.setText(taoMoTaGiam(voucher));
        holder.itemView.setBackgroundColor(voucher.getMaVoucher() == maVoucherDangChon ? Color.rgb(227, 242, 253) : Color.WHITE);
        holder.itemView.setOnClickListener(v -> listener.onClick(voucher));
    }

    @Override
    public int getItemCount() {
        return danhSachVoucher.size();
    }

    private String taoMoTaGiam(Voucher voucher) {
        if ("PhanTram".equals(voucher.getKieuGiamGia())) {
            return "Giảm " + String.format("%.0f", voucher.getGiaTriGiam()) + "%";
        }
        return "Giảm " + DinhDangTien.dinhDang(voucher.getGiaTriGiam());
    }

    static class VoucherViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblTenVoucher;
        private final TextView lblMoTaGiam;

        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTenVoucher = itemView.findViewById(R.id.lblTenVoucher);
            lblMoTaGiam = itemView.findViewById(R.id.lblMoTaGiam);
        }
    }
}

