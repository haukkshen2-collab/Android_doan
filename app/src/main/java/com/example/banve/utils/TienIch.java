package com.example.banve.utils;

import android.app.AlertDialog;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.banve.activities.user.DangNhapActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class TienIch {
    private static final String DINH_DANG_NGAY = "dd/MM/yyyy";

    private TienIch() {
    }

    public static String dinhDangNgay(Date ngay) {
        if (ngay == null) {
            return "";
        }
        return new SimpleDateFormat(DINH_DANG_NGAY, Locale.getDefault()).format(ngay);
    }

    public static Date parseNgay(String chuoiNgay) {
        try {
            return new SimpleDateFormat(DINH_DANG_NGAY, Locale.getDefault()).parse(chuoiNgay);
        } catch (ParseException e) {
            return null;
        }
    }

    public static void hienToast(Context context, String thongBao) {
        Toast.makeText(context, thongBao, Toast.LENGTH_SHORT).show();
    }

    public static void hienAlert(Context context, String tieuDe, String noiDung) {
        new AlertDialog.Builder(context)
                .setTitle(tieuDe)
                .setMessage(noiDung)
                .setPositiveButton("Đồng ý", null)
                .show();
    }

    public static void dangXuat(Activity hoatDong) {
        new AlertDialog.Builder(hoatDong)
                .setTitle("Đăng xuất")
                .setMessage("Bạn có chắc muốn đăng xuất?")
                .setPositiveButton("Có", (dialog, which) -> {
                    Session.dangXuat();
                    Session.xoaLocal(hoatDong);
                    Intent intent = new Intent(hoatDong, DangNhapActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    hoatDong.startActivity(intent);
                    hoatDong.finish();
                })
                .setNegativeButton("Không", null)
                .show();
    }
}
