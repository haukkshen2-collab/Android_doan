package com.example.banve.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.banve.R;
import com.example.banve.activities.user.DangNhapActivity;
import com.example.banve.fragments.admin.PhanLoaiVeFragment;
import com.example.banve.fragments.admin.QuanLyNguoiDungFragment;
import com.example.banve.fragments.admin.QuanLyVeFragment;
import com.example.banve.fragments.admin.QuanLyVoucherFragment;
import com.example.banve.fragments.admin.TongQuanFragment;
import com.example.banve.utils.Session;

public class DashboardQuanLyActivity extends AppCompatActivity {
    private TextView lblXinChaoAdmin;
    private Button btnDangXuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session.khoiPhuc(this);

        if (!Session.laQuanLy()) {
            Toast.makeText(this, "Bạn không có quyền", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setContentView(R.layout.admin_activity_dashboard_quan_ly);
        anhXa();
        hienXinChao();
        batSuKien();

        if (savedInstanceState == null) {
            moFragment(new TongQuanFragment());
        }
    }

    private void anhXa() {
        lblXinChaoAdmin = findViewById(R.id.lblXinChaoAdmin);
        btnDangXuat = findViewById(R.id.btnDangXuat);
    }

    private void hienXinChao() {
        if (Session.nguoiDungHienTai != null && Session.nguoiDungHienTai.getHoTen() != null) {
            lblXinChaoAdmin.setText("Xin chào quản lý, " + Session.nguoiDungHienTai.getHoTen());
        }
    }

    private void batSuKien() {
        btnDangXuat.setOnClickListener(v -> dangXuat());
        findViewById(R.id.btnTongQuan).setOnClickListener(v -> moFragment(new TongQuanFragment()));
        findViewById(R.id.btnQuanLyVe).setOnClickListener(v -> moFragment(new QuanLyVeFragment()));
        findViewById(R.id.btnLoaiVe).setOnClickListener(v -> moFragment(new PhanLoaiVeFragment()));
        findViewById(R.id.btnVoucher).setOnClickListener(v -> moFragment(new QuanLyVoucherFragment()));
        findViewById(R.id.btnNguoiDung).setOnClickListener(v -> moFragment(new QuanLyNguoiDungFragment()));
        findViewById(R.id.btnHoaDon).setOnClickListener(v -> thongBaoChuaBoSung("Hóa đơn"));
        findViewById(R.id.btnCauHinhAI).setOnClickListener(v -> thongBaoChuaBoSung("Cấu hình AI"));
    }

    private void moFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layNoiDungQuanLy, fragment)
                .commit();
    }

    private void thongBaoChuaBoSung(String tenChucNang) {
        Toast.makeText(this, tenChucNang + " sẽ được bổ sung sau", Toast.LENGTH_SHORT).show();
    }

    private void dangXuat() {
        Session.dangXuat();
        Session.luuLocal(this);
        Intent intent = new Intent(this, DangNhapActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
