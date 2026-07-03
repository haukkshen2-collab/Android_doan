package com.example.banve.utils;

import com.example.banve.config.SePayConfig;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

public final class SePayUtil {
    private SePayUtil() {
    }

    public static String taoNoiDungChuyenKhoan(int maHoaDon) {
        return SePayConfig.TIEN_TO_NOI_DUNG + maHoaDon;
    }

    public static String taoLinkQr(double soTien, String noiDungChuyenKhoan) {
        long soTienLamTron = Math.round(soTien);
        return String.format(
                Locale.US,
                "%s/%s-%s-%s.png?amount=%d&addInfo=%s&accountName=%s",
                SePayConfig.QR_BASE_URL,
                SePayConfig.NGAN_HANG,
                SePayConfig.SO_TAI_KHOAN,
                SePayConfig.MAU_QR,
                soTienLamTron,
                maHoaUrl(noiDungChuyenKhoan),
                maHoaUrl(SePayConfig.TEN_CHU_TAI_KHOAN)
        );
    }

    private static String maHoaUrl(String giaTri) {
        try {
            return URLEncoder.encode(giaTri == null ? "" : giaTri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
