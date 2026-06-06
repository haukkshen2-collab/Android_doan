package com.example.banve.controllers;

import com.example.banve.dao.ChiTietGioHangDAO;
import com.example.banve.dao.GioHangDAO;
import com.example.banve.models.ChiTietGioHang;
import com.example.banve.models.GioHang;
import com.example.banve.network.ApiCallback;

import java.util.List;

public class GioHangController {
    private final GioHangDAO gioHangDAO;
    private final ChiTietGioHangDAO chiTietGioHangDAO;

    public GioHangController() {
        gioHangDAO = new GioHangDAO();
        chiTietGioHangDAO = new ChiTietGioHangDAO();
    }

    public void themHoacGopMuc(
            int maNguoiDung,
            ChiTietGioHang mucMoi,
            ApiCallback<ChiTietGioHang> callback
    ) {
        if (maNguoiDung <= 0) {
            callback.onError("Người dùng không hợp lệ");
            return;
        }
        if (mucMoi == null) {
            callback.onError("Thông tin vé không hợp lệ");
            return;
        }
        if (mucMoi.getSoLuongNguoiLon() <= 0
                && mucMoi.getSoLuongTreEm() <= 0
                && mucMoi.getSoLuongNguoiCaoTuoi() <= 0) {
            callback.onError("Vui lòng chọn ít nhất một vé");
            return;
        }

        gioHangDAO.layHoacTaoGioHang(maNguoiDung, new ApiCallback<GioHang>() {
            @Override
            public void onSuccess(GioHang gioHang) {
                mucMoi.setMaGioHang(gioHang.getMaGioHang());
                timVaLuuMuc(mucMoi, callback);
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
            }
        });
    }

    public void layDanhSachMuc(int maNguoiDung, ApiCallback<List<ChiTietGioHang>> callback) {
        if (maNguoiDung <= 0) {
            callback.onError("Người dùng không hợp lệ");
            return;
        }

        gioHangDAO.layHoacTaoGioHang(maNguoiDung, new ApiCallback<GioHang>() {
            @Override
            public void onSuccess(GioHang gioHang) {
                chiTietGioHangDAO.layTheoGioHang(gioHang.getMaGioHang(), callback);
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
            }
        });
    }

    public void capNhatMuc(ChiTietGioHang mucCapNhat, ApiCallback<ChiTietGioHang> callback) {
        if (mucCapNhat == null || mucCapNhat.getMaChiTietGioHang() <= 0) {
            callback.onError("Mục giỏ hàng không hợp lệ");
            return;
        }
        if (mucCapNhat.getSoLuongNguoiLon() <= 0
                && mucCapNhat.getSoLuongTreEm() <= 0
                && mucCapNhat.getSoLuongNguoiCaoTuoi() <= 0) {
            callback.onError("Vui lòng chọn ít nhất một vé");
            return;
        }

        chiTietGioHangDAO.capNhatMuc(mucCapNhat, callback);
    }

    public void xoaMuc(int maChiTietGioHang, ApiCallback<Boolean> callback) {
        if (maChiTietGioHang <= 0) {
            callback.onError("Mục giỏ hàng không hợp lệ");
            return;
        }

        chiTietGioHangDAO.xoaMuc(maChiTietGioHang, callback);
    }

    private void timVaLuuMuc(ChiTietGioHang mucMoi, ApiCallback<ChiTietGioHang> callback) {
        chiTietGioHangDAO.timTrungVeVaNgay(
                mucMoi.getMaGioHang(),
                mucMoi.getMaVe(),
                mucMoi.getNgaySuDung(),
                new ApiCallback<ChiTietGioHang>() {
                    @Override
                    public void onSuccess(ChiTietGioHang mucCu) {
                        if (mucCu == null) {
                            chiTietGioHangDAO.themMuc(mucMoi, callback);
                            return;
                        }

                        mucCu.setSoLuongNguoiLon(mucCu.getSoLuongNguoiLon() + mucMoi.getSoLuongNguoiLon());
                        mucCu.setSoLuongTreEm(mucCu.getSoLuongTreEm() + mucMoi.getSoLuongTreEm());
                        mucCu.setSoLuongNguoiCaoTuoi(mucCu.getSoLuongNguoiCaoTuoi() + mucMoi.getSoLuongNguoiCaoTuoi());
                        mucCu.setDonGiaNguoiLon(mucMoi.getDonGiaNguoiLon());
                        mucCu.setDonGiaTreEm(mucMoi.getDonGiaTreEm());
                        mucCu.setDonGiaNguoiCaoTuoi(mucMoi.getDonGiaNguoiCaoTuoi());
                        chiTietGioHangDAO.capNhatMuc(mucCu, callback);
                    }

                    @Override
                    public void onError(String thongBao) {
                        callback.onError(thongBao);
                    }
                }
        );
    }
}
