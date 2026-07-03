package com.example.banve.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banve.R;

public class ThanhToanThanhCongActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_thanh_toan_thanh_cong);

        TextView lblMaHoaDon = findViewById(R.id.lblMaHoaDon);
        TextView lblNoiDungChuyenKhoan = findViewById(R.id.lblNoiDungChuyenKhoan);
        Button btnVeTrangChu = findViewById(R.id.btnVeTrangChu);

        int maHoaDon = getIntent().getIntExtra("maHoaDon", 0);
        String noiDungChuyenKhoan = getIntent().getStringExtra("noiDungChuyenKhoan");

        lblMaHoaDon.setText("Mã hóa đơn: " + maHoaDon);
        if (noiDungChuyenKhoan == null || noiDungChuyenKhoan.trim().isEmpty()) {
            lblNoiDungChuyenKhoan.setText("Thanh toán đã được xác nhận.");
        } else {
            lblNoiDungChuyenKhoan.setText("Nội dung chuyển khoản: " + noiDungChuyenKhoan);
        }

        btnVeTrangChu.setOnClickListener(v -> {
            Intent intent = new Intent(this, DashboardNguoiDungActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
