package com.example.banve.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banve.R;
import com.example.banve.models.NguoiDung;
import com.example.banve.utils.HienThi;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NguoiDungAdapter extends RecyclerView.Adapter<NguoiDungAdapter.NguoiDungViewHolder> {
    public interface OnNguoiDungClickListener {
        void onDoiTrangThai(NguoiDung nguoiDung, boolean khoa);

        void onResetMatKhau(NguoiDung nguoiDung);
    }

    private final List<NguoiDung> danhSachGoc = new ArrayList<>();
    private final List<NguoiDung> danhSachHienThi = new ArrayList<>();
    private final OnNguoiDungClickListener listener;

    public NguoiDungAdapter(OnNguoiDungClickListener listener) {
        this.listener = listener;
    }

    public void capNhatDuLieu(List<NguoiDung> duLieuMoi) {
        danhSachGoc.clear();
        danhSachHienThi.clear();
        if (duLieuMoi != null) {
            danhSachGoc.addAll(duLieuMoi);
            danhSachHienThi.addAll(duLieuMoi);
        }
        notifyDataSetChanged();
    }

    public void locDuLieu(String tuKhoa, String vaiTro) {
        String tuKhoaChuan = tuKhoa == null ? "" : tuKhoa.trim().toLowerCase(Locale.getDefault());
        String vaiTroChuan = vaiTro == null ? "" : vaiTro.trim();

        danhSachHienThi.clear();
        for (NguoiDung nguoiDung : danhSachGoc) {
            boolean khopTuKhoa = tuKhoaChuan.isEmpty()
                    || chuaTuKhoa(nguoiDung.getTaiKhoan(), tuKhoaChuan)
                    || chuaTuKhoa(nguoiDung.getHoTen(), tuKhoaChuan);
            boolean khopVaiTro = vaiTroChuan.isEmpty() || vaiTroChuan.equals(nguoiDung.getVaiTro());

            if (khopTuKhoa && khopVaiTro) {
                danhSachHienThi.add(nguoiDung);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NguoiDungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_nguoi_dung, parent, false);
        return new NguoiDungViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiDungViewHolder holder, int position) {
        NguoiDung nguoiDung = danhSachHienThi.get(position);
        holder.lblTaiKhoan.setText("Tài khoản: " + giaTri(nguoiDung.getTaiKhoan()));
        holder.lblHoTen.setText("Họ tên: " + giaTri(nguoiDung.getHoTen()));
        holder.lblVaiTro.setText("Vai trò: " + HienThi.vaiTro(nguoiDung.getVaiTro()));
        holder.lblTrangThai.setText("Trạng thái: " + HienThi.trangThai(nguoiDung.getTrangThai()));

        holder.swtKhoa.setOnCheckedChangeListener(null);
        holder.swtKhoa.setChecked("Khoa".equals(nguoiDung.getTrangThai()));
        holder.swtKhoa.setOnCheckedChangeListener((buttonView, isChecked) -> listener.onDoiTrangThai(nguoiDung, isChecked));
        holder.btnResetMatKhau.setOnClickListener(v -> listener.onResetMatKhau(nguoiDung));
    }

    @Override
    public int getItemCount() {
        return danhSachHienThi.size();
    }

    private boolean chuaTuKhoa(String giaTri, String tuKhoa) {
        return giaTri != null && giaTri.toLowerCase(Locale.getDefault()).contains(tuKhoa);
    }

    private String giaTri(String giaTri) {
        return giaTri == null ? "" : giaTri;
    }

    static class NguoiDungViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblTaiKhoan;
        private final TextView lblHoTen;
        private final TextView lblVaiTro;
        private final TextView lblTrangThai;
        private final Switch swtKhoa;
        private final Button btnResetMatKhau;

        public NguoiDungViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTaiKhoan = itemView.findViewById(R.id.lblTaiKhoan);
            lblHoTen = itemView.findViewById(R.id.lblHoTen);
            lblVaiTro = itemView.findViewById(R.id.lblVaiTro);
            lblTrangThai = itemView.findViewById(R.id.lblTrangThai);
            swtKhoa = itemView.findViewById(R.id.swtKhoa);
            btnResetMatKhau = itemView.findViewById(R.id.btnResetMatKhau);
        }
    }
}
