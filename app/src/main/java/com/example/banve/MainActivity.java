package com.example.banve;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banve.activities.admin.DashboardQuanLyActivity;
import com.example.banve.activities.user.DangNhapActivity;
import com.example.banve.activities.user.DashboardNguoiDungActivity;
import com.example.banve.utils.Session;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session.khoiPhuc(this);
        chuyenManHinhTheoSession();
    }

    private void chuyenManHinhTheoSession() {
        Intent intent;
        if (!Session.dangDangNhap()) {
            intent = new Intent(this, DangNhapActivity.class);
        } else if (Session.laQuanLy()) {
            intent = new Intent(this, DashboardQuanLyActivity.class);
        } else {
            intent = new Intent(this, DashboardNguoiDungActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
