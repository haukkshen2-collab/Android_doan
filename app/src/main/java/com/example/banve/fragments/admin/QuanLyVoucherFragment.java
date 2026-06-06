package com.example.banve.fragments.admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banve.R;
import com.example.banve.adapters.VoucherQuanLyAdapter;
import com.example.banve.controllers.VoucherController;
import com.example.banve.models.Voucher;
import com.example.banve.network.ApiCallback;
import com.example.banve.utils.TienIch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QuanLyVoucherFragment extends Fragment {
    private Button btnThemVoucher;
    private RecyclerView rcvDanhSachVoucher;
    private VoucherQuanLyAdapter adapter;
    private VoucherController voucherController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_quan_ly_voucher, container, false);
        anhXa(view);
        voucherController = new VoucherController();
        khoiTaoRecyclerView();
        batSuKien();
        taiDanhSachVoucher();
        return view;
    }

    private void anhXa(View view) {
        btnThemVoucher = view.findViewById(R.id.btnThemVoucher);
        rcvDanhSachVoucher = view.findViewById(R.id.rcvDanhSachVoucher);
    }

    private void khoiTaoRecyclerView() {
        adapter = new VoucherQuanLyAdapter(new VoucherQuanLyAdapter.OnVoucherQuanLyClickListener() {
            @Override
            public void onSua(Voucher voucher) {
                moDialogNhapVoucher(voucher);
            }

            @Override
            public void onXoa(Voucher voucher) {
                xacNhanXoaVoucher(voucher);
            }
        });
        rcvDanhSachVoucher.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvDanhSachVoucher.setAdapter(adapter);
    }

    private void batSuKien() {
        btnThemVoucher.setOnClickListener(v -> moDialogNhapVoucher(null));
    }

    private void taiDanhSachVoucher() {
        voucherController.layDanhSachVoucher(new ApiCallback<List<Voucher>>() {
            @Override
            public void onSuccess(List<Voucher> data) {
                adapter.capNhatDuLieu(data);
            }

            @Override
            public void onError(String thongBao) {
                baoLoi("Lỗi voucher", thongBao);
            }
        });
    }

    private void moDialogNhapVoucher(Voucher voucherCanSua) {
        View view = getLayoutInflater().inflate(R.layout.admin_dialog_nhap_voucher, null);
        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle(voucherCanSua == null ? "Thêm voucher" : "Sửa voucher")
                .setView(view)
                .create();

        EditText edtMaGiamGia = view.findViewById(R.id.edtMaGiamGia);
        EditText edtTenVoucher = view.findViewById(R.id.edtTenVoucher);
        RadioButton radPhanTram = view.findViewById(R.id.radPhanTram);
        RadioButton radTienMat = view.findViewById(R.id.radTienMat);
        EditText edtGiaTriGiam = view.findViewById(R.id.edtGiaTriGiam);
        DatePicker dtpNgayBatDau = view.findViewById(R.id.dtpNgayBatDau);
        DatePicker dtpNgayKetThuc = view.findViewById(R.id.dtpNgayKetThuc);
        EditText edtSoLuong = view.findViewById(R.id.edtSoLuong);
        Switch swtTrangThai = view.findViewById(R.id.swtTrangThai);
        Button btnLuu = view.findViewById(R.id.btnLuu);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        if (voucherCanSua == null) {
            cauHinhNgayMacDinh(dtpNgayBatDau, dtpNgayKetThuc);
        } else {
            doDuLieuLenDialog(voucherCanSua, edtMaGiamGia, edtTenVoucher, radPhanTram, radTienMat, edtGiaTriGiam, dtpNgayBatDau, dtpNgayKetThuc, edtSoLuong, swtTrangThai);
        }

        btnLuu.setOnClickListener(v -> luuVoucherTuDialog(
                dialog,
                voucherCanSua,
                edtMaGiamGia,
                edtTenVoucher,
                radPhanTram,
                edtGiaTriGiam,
                dtpNgayBatDau,
                dtpNgayKetThuc,
                edtSoLuong,
                swtTrangThai
        ));
        btnHuy.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void cauHinhNgayMacDinh(DatePicker dtpNgayBatDau, DatePicker dtpNgayKetThuc) {
        Calendar homNay = Calendar.getInstance();
        dtpNgayBatDau.updateDate(homNay.get(Calendar.YEAR), homNay.get(Calendar.MONTH), homNay.get(Calendar.DAY_OF_MONTH));
        homNay.add(Calendar.DAY_OF_MONTH, 30);
        dtpNgayKetThuc.updateDate(homNay.get(Calendar.YEAR), homNay.get(Calendar.MONTH), homNay.get(Calendar.DAY_OF_MONTH));
    }

    private void doDuLieuLenDialog(
            Voucher voucher,
            EditText edtMaGiamGia,
            EditText edtTenVoucher,
            RadioButton radPhanTram,
            RadioButton radTienMat,
            EditText edtGiaTriGiam,
            DatePicker dtpNgayBatDau,
            DatePicker dtpNgayKetThuc,
            EditText edtSoLuong,
            Switch swtTrangThai
    ) {
        edtMaGiamGia.setText(voucher.getMaGiamGia());
        edtTenVoucher.setText(voucher.getTenVoucher());
        radPhanTram.setChecked("PhanTram".equals(voucher.getKieuGiamGia()));
        radTienMat.setChecked("TienMat".equals(voucher.getKieuGiamGia()));
        edtGiaTriGiam.setText(String.valueOf(voucher.getGiaTriGiam()));
        edtSoLuong.setText(String.valueOf(voucher.getSoLuong()));
        swtTrangThai.setChecked("HoatDong".equals(voucher.getTrangThai()));
        ganNgayChoDatePicker(dtpNgayBatDau, voucher.getNgayBatDau());
        ganNgayChoDatePicker(dtpNgayKetThuc, voucher.getNgayKetThuc());
    }

    private void luuVoucherTuDialog(
            AlertDialog dialog,
            Voucher voucherCanSua,
            EditText edtMaGiamGia,
            EditText edtTenVoucher,
            RadioButton radPhanTram,
            EditText edtGiaTriGiam,
            DatePicker dtpNgayBatDau,
            DatePicker dtpNgayKetThuc,
            EditText edtSoLuong,
            Switch swtTrangThai
    ) {
        try {
            Voucher voucher = voucherCanSua == null ? new Voucher() : voucherCanSua;
            if (voucherCanSua != null) {
                voucher.setMaVoucher(voucherCanSua.getMaVoucher());
            }

            voucher.setMaGiamGia(edtMaGiamGia.getText().toString());
            voucher.setTenVoucher(edtTenVoucher.getText().toString());
            voucher.setKieuGiamGia(radPhanTram.isChecked() ? "PhanTram" : "TienMat");
            voucher.setGiaTriGiam(docDouble(edtGiaTriGiam));
            voucher.setNgayBatDau(layNgayTuDatePicker(dtpNgayBatDau));
            voucher.setNgayKetThuc(layNgayTuDatePicker(dtpNgayKetThuc));
            voucher.setSoLuong(docInt(edtSoLuong));
            voucher.setTrangThai(swtTrangThai.isChecked() ? "HoatDong" : "Khoa");

            if (voucherCanSua == null) {
                themVoucher(dialog, voucher);
            } else {
                suaVoucher(dialog, voucher);
            }
        } catch (NumberFormatException e) {
            TienIch.hienToast(requireContext(), "Vui lòng nhập đúng định dạng số");
        }
    }

    private void themVoucher(AlertDialog dialog, Voucher voucher) {
        voucherController.themVoucher(voucher, new ApiCallback<Voucher>() {
            @Override
            public void onSuccess(Voucher data) {
                Toast.makeText(requireContext(), "Đã thêm voucher", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                taiDanhSachVoucher();
            }

            @Override
            public void onError(String thongBao) {
                baoLoi("Lỗi thêm voucher", thongBao);
            }
        });
    }

    private void suaVoucher(AlertDialog dialog, Voucher voucher) {
        voucherController.suaVoucher(voucher, new ApiCallback<Voucher>() {
            @Override
            public void onSuccess(Voucher data) {
                Toast.makeText(requireContext(), "Đã cập nhật voucher", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                taiDanhSachVoucher();
            }

            @Override
            public void onError(String thongBao) {
                baoLoi("Lỗi cập nhật voucher", thongBao);
            }
        });
    }

    private void xacNhanXoaVoucher(Voucher voucher) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Xóa voucher")
                .setMessage("Bạn có chắc muốn xóa voucher này?")
                .setPositiveButton("Đồng ý", (dialog, which) -> voucherController.xoaVoucher(voucher.getMaVoucher(), new ApiCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        Toast.makeText(requireContext(), "Đã xóa voucher", Toast.LENGTH_SHORT).show();
                        taiDanhSachVoucher();
                    }

                    @Override
                    public void onError(String thongBao) {
                        baoLoi("Lỗi xóa voucher", thongBao);
                    }
                }))
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void ganNgayChoDatePicker(DatePicker datePicker, String ngay) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(ngay);
            if (date != null) {
                calendar.setTime(date);
            }
        } catch (ParseException ignored) {
        }
        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private String layNgayTuDatePicker(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());
    }

    private double docDouble(EditText editText) {
        String chuoi = editText.getText().toString().trim();
        return chuoi.isEmpty() ? 0 : Double.parseDouble(chuoi);
    }

    private int docInt(EditText editText) {
        String chuoi = editText.getText().toString().trim();
        return chuoi.isEmpty() ? 0 : Integer.parseInt(chuoi);
    }

    private void baoLoi(String tieuDe, String thongBao) {
        if (getContext() != null) {
            TienIch.hienAlert(requireContext(), tieuDe, thongBao);
        }
    }
}
