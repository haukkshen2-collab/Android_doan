package com.example.banve.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banve.R;
import com.example.banve.controllers.NguoiDungController;
import com.example.banve.models.NguoiDung;
import com.example.banve.network.ApiCallback;
import com.example.banve.utils.TienIch;

public class DangKyActivity extends AppCompatActivity {
    private EditText edtTaiKhoan;
    private EditText edtMatKhau;
    private EditText edtNhapLaiMatKhau;
    private EditText edtHoTen;
    private EditText edtEmail;
    private EditText edtSoDienThoai;
    private Button btnDangKy;
    private Button btnQuayLai;
    private ProgressBar pgbDangTai;
    private NguoiDungController nguoiDungController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_dang_ky);

        anhXa();
        nguoiDungController = new NguoiDungController();
        batSuKien();
    }

    private void anhXa() {
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtNhapLaiMatKhau = findViewById(R.id.edtNhapLaiMatKhau);
        edtHoTen = findViewById(R.id.edtHoTen);
        edtEmail = findViewById(R.id.edtEmail);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnQuayLai = findViewById(R.id.btnQuayLai);
        pgbDangTai = findViewById(R.id.pgbDangTai);
    }

    private void batSuKien() {
        btnDangKy.setOnClickListener(v -> dangKy());
        btnQuayLai.setOnClickListener(v -> finish());
    }

    private void dangKy() {
        hienDangTai(true);

        nguoiDungController.dangKy(
                layChuoi(edtTaiKhoan),
                layChuoi(edtMatKhau),
                layChuoi(edtNhapLaiMatKhau),
                layChuoi(edtHoTen),
                layChuoi(edtEmail),
                layChuoi(edtSoDienThoai),
                new ApiCallback<NguoiDung>() {
                    @Override
                    public void onSuccess(NguoiDung data) {
                        hienDangTai(false);
                        Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        moDangNhap();
                    }

                    @Override
                    public void onError(String thongBao) {
                        hienDangTai(false);
                        TienIch.hienAlert(DangKyActivity.this, "Lỗi đăng ký", thongBao);
                    }
                }
        );
    }

    private void moDangNhap() {
        Intent intent = new Intent(this, DangNhapActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void hienDangTai(boolean dangTai) {
        pgbDangTai.setVisibility(dangTai ? View.VISIBLE : View.GONE);
        btnDangKy.setEnabled(!dangTai);
        btnQuayLai.setEnabled(!dangTai);
    }

    private String layChuoi(EditText editText) {
        return editText.getText().toString();
    }
}

