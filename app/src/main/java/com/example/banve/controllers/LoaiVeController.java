package com.example.banve.controllers;

import com.example.banve.dao.LoaiVeDAO;
import com.example.banve.models.LoaiVe;
import com.example.banve.network.ApiCallback;

import java.util.List;

public class LoaiVeController {
    private final LoaiVeDAO loaiVeDAO;

    public LoaiVeController() {
        loaiVeDAO = new LoaiVeDAO();
    }

    public void layDanhSachLoaiVe(ApiCallback<List<LoaiVe>> callback) {
        loaiVeDAO.layDanhSachLoaiVe(callback);
    }

    public void layDanhSachQuanLy(ApiCallback<List<LoaiVe>> callback) {
        loaiVeDAO.layDanhSachQuanLy(callback);
    }

    public void themLoaiVe(LoaiVe loaiVe, ApiCallback<LoaiVe> callback) {
        String loi = kiemTraLoaiVe(loaiVe, false);
        if (loi != null) {
            callback.onError(loi);
            return;
        }

        kiemTraTrungTen(loaiVe, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean biTrung) {
                if (biTrung) {
                    callback.onError("Tên loại vé đã tồn tại");
                    return;
                }

                loaiVeDAO.themLoaiVe(chuanHoaLoaiVe(loaiVe), callback);
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
            }
        });
    }

    public void suaLoaiVe(LoaiVe loaiVe, ApiCallback<LoaiVe> callback) {
        String loi = kiemTraLoaiVe(loaiVe, true);
        if (loi != null) {
            callback.onError(loi);
            return;
        }

        kiemTraTrungTen(loaiVe, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean biTrung) {
                if (biTrung) {
                    callback.onError("Tên loại vé đã tồn tại");
                    return;
                }

                loaiVeDAO.suaLoaiVe(chuanHoaLoaiVe(loaiVe), callback);
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
            }
        });
    }

    public void xoaLoaiVe(int maLoaiVe, ApiCallback<Boolean> callback) {
        if (maLoaiVe <= 0) {
            callback.onError("Loại vé không hợp lệ");
            return;
        }

        loaiVeDAO.xoaLoaiVe(maLoaiVe, callback);
    }

    private void kiemTraTrungTen(LoaiVe loaiVe, ApiCallback<Boolean> callback) {
        loaiVeDAO.layDanhSachQuanLy(new ApiCallback<List<LoaiVe>>() {
            @Override
            public void onSuccess(List<LoaiVe> data) {
                String tenCanKiemTra = loaiVe.getTenLoaiVe().trim();
                if (data != null) {
                    for (LoaiVe item : data) {
                        if (item.getMaLoaiVe() != loaiVe.getMaLoaiVe()
                                && item.getTenLoaiVe() != null
                                && item.getTenLoaiVe().trim().equalsIgnoreCase(tenCanKiemTra)) {
                            callback.onSuccess(true);
                            return;
                        }
                    }
                }

                callback.onSuccess(false);
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
            }
        });
    }

    private String kiemTraLoaiVe(LoaiVe loaiVe, boolean batBuocMaLoaiVe) {
        if (loaiVe == null) {
            return "Thông tin loại vé không hợp lệ";
        }
        if (batBuocMaLoaiVe && loaiVe.getMaLoaiVe() <= 0) {
            return "Loại vé không hợp lệ";
        }
        if (rong(loaiVe.getTenLoaiVe())) {
            return "Tên loại vé không được để trống";
        }
        return null;
    }

    private LoaiVe chuanHoaLoaiVe(LoaiVe loaiVe) {
        loaiVe.setTenLoaiVe(loaiVe.getTenLoaiVe().trim());
        loaiVe.setMoTa(loaiVe.getMoTa() == null ? "" : loaiVe.getMoTa().trim());
        if (rong(loaiVe.getTrangThai())) {
            loaiVe.setTrangThai("HoatDong");
        }
        return loaiVe;
    }

    private boolean rong(String chuoi) {
        return chuoi == null || chuoi.trim().isEmpty();
    }
}
