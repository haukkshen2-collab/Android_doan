package com.example.banve.controllers;

import com.example.banve.dao.HoaDonDAO;
import com.example.banve.models.ChiTietHoaDon;
import com.example.banve.models.HoaDon;
import com.example.banve.network.ApiCallback;

import java.util.List;

public class HoaDonController {
    private final HoaDonDAO hoaDonDAO;

    public HoaDonController() {
        hoaDonDAO = new HoaDonDAO();
    }

    public void layDanhSachDaThanhToan(int maNguoiDung, ApiCallback<List<HoaDon>> callback) {
        if (maNguoiDung <= 0) {
            callback.onError("Người dùng không hợp lệ");
            return;
        }

        hoaDonDAO.layDanhSachDaThanhToan(maNguoiDung, callback);
    }

    public void layChiTietHoaDon(int maHoaDon, ApiCallback<List<ChiTietHoaDon>> callback) {
        if (maHoaDon <= 0) {
            callback.onError("Hóa đơn không hợp lệ");
            return;
        }

        hoaDonDAO.layChiTietHoaDon(maHoaDon, callback);
    }
}
