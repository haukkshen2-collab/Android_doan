package com.example.banve.controllers;

import com.example.banve.dao.VeDAO;
import com.example.banve.models.Ve;
import com.example.banve.network.ApiCallback;

import java.util.List;

public class VeController {
    private final VeDAO veDAO;

    public VeController() {
        veDAO = new VeDAO();
    }

    public void layDanhSachVe(Integer maLoaiVe, ApiCallback<List<Ve>> callback) {
        veDAO.layDanhSachVe(maLoaiVe, callback);
    }

    public void layTheoMa(int maVe, ApiCallback<Ve> callback) {
        veDAO.layTheoMa(maVe, callback);
    }

    public void layDanhSachVeQuanLy(ApiCallback<List<Ve>> callback) {
        veDAO.layDanhSachVeQuanLy(callback);
    }

    public void themVe(Ve ve, ApiCallback<Ve> callback) {
        String loi = kiemTraVe(ve, false);
        if (loi != null) {
            callback.onError(loi);
            return;
        }

        veDAO.themVe(chuanHoaVe(ve), callback);
    }

    public void suaVe(Ve ve, ApiCallback<Ve> callback) {
        String loi = kiemTraVe(ve, true);
        if (loi != null) {
            callback.onError(loi);
            return;
        }

        veDAO.suaVe(chuanHoaVe(ve), callback);
    }

    public void xoaVe(int maVe, ApiCallback<Boolean> callback) {
        if (maVe <= 0) {
            callback.onError("Vé không hợp lệ");
            return;
        }

        veDAO.xoaVe(maVe, callback);
    }

    private String kiemTraVe(Ve ve, boolean batBuocMaVe) {
        if (ve == null) {
            return "Thông tin vé không hợp lệ";
        }
        if (batBuocMaVe && ve.getMaVe() <= 0) {
            return "Vé không hợp lệ";
        }
        if (ve.getMaLoaiVe() <= 0) {
            return "Vui lòng chọn loại vé";
        }
        if (rong(ve.getTenVe())) {
            return "Tên vé không được để trống";
        }
        if (ve.getGiaVe() <= 0) {
            return "Giá vé phải lớn hơn 0";
        }
        if (ve.getGiaNguoiLon() <= 0) {
            return "Giá người lớn phải lớn hơn 0";
        }
        if (ve.getGiaTreEm() <= 0) {
            return "Giá trẻ em phải lớn hơn 0";
        }
        if (ve.getGiaNguoiCaoTuoi() <= 0) {
            return "Giá người cao tuổi phải lớn hơn 0";
        }
        if (ve.getSoLuong() < 0) {
            return "Số lượng không được âm";
        }
        return null;
    }

    private Ve chuanHoaVe(Ve ve) {
        ve.setTenVe(ve.getTenVe().trim());
        ve.setMoTa(chuanHoaChuoi(ve.getMoTa()));
        ve.setThongTinVe(chuanHoaChuoi(ve.getThongTinVe()));
        ve.setAnhVe(chuanHoaChuoi(ve.getAnhVe()));
        if (rong(ve.getTrangThai())) {
            ve.setTrangThai("HoatDong");
        }
        return ve;
    }

    private String chuanHoaChuoi(String chuoi) {
        return chuoi == null ? "" : chuoi.trim();
    }

    private boolean rong(String chuoi) {
        return chuoi == null || chuoi.trim().isEmpty();
    }
}
