package com.example.banve.utils;

import java.util.Locale;

public final class DinhDangTien {
    private DinhDangTien() {
    }

    public static String dinhDang(double soTien) {
        return String.format(Locale.US, "%,.0f VNĐ", soTien);
    }
}
