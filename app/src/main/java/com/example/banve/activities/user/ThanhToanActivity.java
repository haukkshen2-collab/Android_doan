package com.example.banve.activities.user;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banve.R;
import com.example.banve.adapters.VeThanhToanAdapter;
import com.example.banve.adapters.VoucherAdapter;
import com.example.banve.controllers.GioHangController;
import com.example.banve.controllers.ThanhToanController;
import com.example.banve.controllers.VeController;
import com.example.banve.dao.VoucherDAO;
import com.example.banve.models.ChiTietGioHang;
import com.example.banve.models.HoaDon;
import com.example.banve.models.MucGioHang;
import com.example.banve.models.Ve;
import com.example.banve.models.Voucher;
import com.example.banve.network.ApiCallback;
import com.example.banve.utils.DinhDangTien;
import com.example.banve.utils.Session;
import com.example.banve.utils.TienIch;

import java.util.ArrayList;
import java.util.List;

public class ThanhToanActivity extends AppCompatActivity {
    private RecyclerView rcvDanhSachVeThanhToan;
    private RecyclerView rcvDanhSachVoucher;
    private TextView lblTongTien;
    private TextView lblTienGiam;
    private TextView lblPhaiTra;
    private RadioGroup rgThanhToan;
    private Button btnXacNhanThanhToan;
    private Button btnHuy;
    private VeThanhToanAdapter veThanhToanAdapter;
    private VoucherAdapter voucherAdapter;
    private GioHangController gioHangController;
    private VeController veController;
    private VoucherDAO voucherDAO;
    private ThanhToanController thanhToanController;
    private final List<MucGioHang> danhSachMuc = new ArrayList<>();
    private Voucher voucherDangChon;
    private double tongTien;
    private double tienGiam;
    private double phaiTra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session.khoiPhuc(this);
        setContentView(R.layout.user_activity_thanh_toan);

        anhXa();
        gioHangController = new GioHangController();
        veController = new VeController();
        voucherDAO = new VoucherDAO();
        thanhToanController = new ThanhToanController();
        khoiTaoRecyclerView();
        batSuKien();
        taiDuLieuThanhToan();
        taiVoucher();
    }

    private void anhXa() {
        rcvDanhSachVeThanhToan = findViewById(R.id.rcvDanhSachVeThanhToan);
        rcvDanhSachVoucher = findViewById(R.id.rcvDanhSachVoucher);
        lblTongTien = findViewById(R.id.lblTongTien);
        lblTienGiam = findViewById(R.id.lblTienGiam);
        lblPhaiTra = findViewById(R.id.lblPhaiTra);
        rgThanhToan = findViewById(R.id.rgThanhToan);
        btnXacNhanThanhToan = findViewById(R.id.btnXacNhanThanhToan);
        btnHuy = findViewById(R.id.btnHuy);
    }

    private void khoiTaoRecyclerView() {
        veThanhToanAdapter = new VeThanhToanAdapter();
        rcvDanhSachVeThanhToan.setLayoutManager(new LinearLayoutManager(this));
        rcvDanhSachVeThanhToan.setAdapter(veThanhToanAdapter);

        voucherAdapter = new VoucherAdapter(voucher -> {
            if (voucherDangChon != null && voucherDangChon.getMaVoucher() == voucher.getMaVoucher()) {
                voucherDangChon = null;
            } else {
                voucherDangChon = voucher;
            }
            voucherAdapter.chonVoucher(voucherDangChon);
            tinhLaiTongTien();
        });
        rcvDanhSachVoucher.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcvDanhSachVoucher.setAdapter(voucherAdapter);
    }

    private void batSuKien() {
        btnHuy.setOnClickListener(v -> finish());
        btnXacNhanThanhToan.setOnClickListener(v -> xacNhanThanhToan());
    }

    private void taiDuLieuThanhToan() {
        if (!Session.dangDangNhap()) {
            TienIch.hienAlert(this, "Thông báo", "Vui lòng đăng nhập lại");
            finish();
            return;
        }

        danhSachMuc.clear();
        veThanhToanAdapter.capNhatDuLieu(danhSachMuc);
        gioHangController.layDanhSachMuc(Session.nguoiDungHienTai.getMaNguoiDung(), new ApiCallback<List<ChiTietGioHang>>() {
            @Override
            public void onSuccess(List<ChiTietGioHang> data) {
                if (data == null || data.isEmpty()) {
                    TienIch.hienAlert(ThanhToanActivity.this, "Thông báo", "Giỏ hàng đang trống");
                    tinhLaiTongTien();
                    return;
                }

                taiThongTinVeChoMuc(data, 0);
            }

            @Override
            public void onError(String thongBao) {
                TienIch.hienAlert(ThanhToanActivity.this, "Lỗi thanh toán", thongBao);
            }
        });
    }

    private void taiThongTinVeChoMuc(List<ChiTietGioHang> danhSachChiTiet, int viTri) {
        if (viTri >= danhSachChiTiet.size()) {
            veThanhToanAdapter.capNhatDuLieu(danhSachMuc);
            tinhLaiTongTien();
            return;
        }

        ChiTietGioHang chiTiet = danhSachChiTiet.get(viTri);
        veController.layTheoMa(chiTiet.getMaVe(), new ApiCallback<Ve>() {
            @Override
            public void onSuccess(Ve data) {
                danhSachMuc.add(new MucGioHang(chiTiet, data));
                taiThongTinVeChoMuc(danhSachChiTiet, viTri + 1);
            }

            @Override
            public void onError(String thongBao) {
                taiThongTinVeChoMuc(danhSachChiTiet, viTri + 1);
            }
        });
    }

    private void taiVoucher() {
        voucherDAO.layDanhSachVoucherKhaDung(new ApiCallback<List<Voucher>>() {
            @Override
            public void onSuccess(List<Voucher> data) {
                voucherAdapter.capNhatDuLieu(data);
            }

            @Override
            public void onError(String thongBao) {
                TienIch.hienAlert(ThanhToanActivity.this, "Lỗi voucher", thongBao);
            }
        });
    }

    private void tinhLaiTongTien() {
        tongTien = thanhToanController.tinhTongTien(danhSachMuc);
        tienGiam = thanhToanController.apDungVoucher(voucherDangChon, tongTien);
        phaiTra = tongTien - tienGiam;

        lblTongTien.setText("Tổng tiền: " + DinhDangTien.dinhDang(tongTien));
        lblTienGiam.setText("Tiền giảm: " + DinhDangTien.dinhDang(tienGiam));
        lblPhaiTra.setText("Phải trả: " + DinhDangTien.dinhDang(phaiTra));
    }

    private void xacNhanThanhToan() {
        if (danhSachMuc.isEmpty()) {
            TienIch.hienAlert(this, "Thông báo", "Giỏ hàng đang trống");
            return;
        }

        String hinhThucThanhToan = layHinhThucThanhToan();
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận thanh toán")
                .setMessage("Xác nhận thanh toán " + DinhDangTien.dinhDang(phaiTra) + "?")
                .setPositiveButton("Đồng ý", (dialog, which) -> hoanTatThanhToan(hinhThucThanhToan))
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void hoanTatThanhToan(String hinhThucThanhToan) {
        btnXacNhanThanhToan.setEnabled(false);
        thanhToanController.hoanTatThanhToan(
                Session.nguoiDungHienTai.getMaNguoiDung(),
                danhSachMuc,
                voucherDangChon,
                hinhThucThanhToan,
                new ApiCallback<HoaDon>() {
                    @Override
                    public void onSuccess(HoaDon data) {
                        btnXacNhanThanhToan.setEnabled(true);
                        new AlertDialog.Builder(ThanhToanActivity.this)
                                .setTitle("Đặt vé thành công!")
                                .setMessage("Mã hóa đơn: " + data.getMaHoaDon())
                                .setPositiveButton("OK", (dialog, which) -> veDashboard())
                                .show();
                    }

                    @Override
                    public void onError(String thongBao) {
                        btnXacNhanThanhToan.setEnabled(true);
                        TienIch.hienAlert(ThanhToanActivity.this, "Lỗi thanh toán", thongBao);
                    }
                }
        );
    }

    private String layHinhThucThanhToan() {
        int id = rgThanhToan.getCheckedRadioButtonId();
        if (id == R.id.radChuyenKhoan) {
            return "ChuyenKhoan";
        }
        if (id == R.id.radVNPay) {
            return "VNPay";
        }
        return "TienMat";
    }

    private void veDashboard() {
        Intent intent = new Intent(this, DashboardNguoiDungActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

