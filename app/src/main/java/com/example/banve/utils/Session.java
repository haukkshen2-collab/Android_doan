package com.example.banve.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.banve.models.NguoiDung;
import com.google.gson.Gson;

public final class Session {
    private static final String TEN_FILE = "SessionNguoiDung";
    private static final String KEY_NGUOI_DUNG = "nguoiDungHienTai";

    public static NguoiDung nguoiDungHienTai;

    private Session() {
    }

    public static void dangNhap(NguoiDung nguoiDung) {
        nguoiDungHienTai = nguoiDung;
    }

    public static void dangXuat() {
        nguoiDungHienTai = null;
    }

    public static boolean laQuanLy() {
        return nguoiDungHienTai != null
                && "QuanLy".equals(nguoiDungHienTai.getVaiTro());
    }

    public static boolean dangDangNhap() {
        return nguoiDungHienTai != null
                && nguoiDungHienTai.getMaNguoiDung() > 0
                && nguoiDungHienTai.getTaiKhoan() != null
                && !nguoiDungHienTai.getTaiKhoan().trim().isEmpty();
    }

    public static void luuLocal(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TEN_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (nguoiDungHienTai == null) {
            editor.remove(KEY_NGUOI_DUNG);
        } else {
            editor.putString(KEY_NGUOI_DUNG, new Gson().toJson(nguoiDungHienTai));
        }

        editor.apply();
    }

    public static void xoaLocal(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TEN_FILE, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(KEY_NGUOI_DUNG).apply();
    }

    public static void khoiPhuc(Context context) {
        if (nguoiDungHienTai != null) {
            return;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(TEN_FILE, Context.MODE_PRIVATE);
        String jsonNguoiDung = sharedPreferences.getString(KEY_NGUOI_DUNG, null);

        if (jsonNguoiDung == null || jsonNguoiDung.trim().isEmpty()) {
            return;
        }

        nguoiDungHienTai = new Gson().fromJson(jsonNguoiDung, NguoiDung.class);
    }
}
