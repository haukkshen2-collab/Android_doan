package com.example.banve.controllers;

import com.example.banve.dao.VoucherDAO;
import com.example.banve.models.Voucher;
import com.example.banve.network.ApiCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class VoucherController {
    private final VoucherDAO voucherDAO;

    public VoucherController() {
        voucherDAO = new VoucherDAO();
    }

    public void layDanhSachVoucher(ApiCallback<List<Voucher>> callback) {
        voucherDAO.layDanhSachVoucher(callback);
    }

    public void themVoucher(Voucher voucher, ApiCallback<Voucher> callback) {
        String loi = kiemTraVoucher(voucher, false);
        if (loi != null) {
            callback.onError(loi);
            return;
        }

        kiemTraTrungMa(voucher, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean biTrung) {
                if (biTrung) {
                    callback.onError("Mã giảm giá đã tồn tại");
                    return;
                }

                voucherDAO.themVoucher(chuanHoaVoucher(voucher), callback);
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
            }
        });
    }

    public void suaVoucher(Voucher voucher, ApiCallback<Voucher> callback) {
        String loi = kiemTraVoucher(voucher, true);
        if (loi != null) {
            callback.onError(loi);
            return;
        }

        kiemTraTrungMa(voucher, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean biTrung) {
                if (biTrung) {
                    callback.onError("Mã giảm giá đã tồn tại");
                    return;
                }

                voucherDAO.suaVoucher(chuanHoaVoucher(voucher), callback);
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
            }
        });
    }

    public void xoaVoucher(int maVoucher, ApiCallback<Boolean> callback) {
        if (maVoucher <= 0) {
            callback.onError("Voucher không hợp lệ");
            return;
        }

        voucherDAO.xoaVoucher(maVoucher, callback);
    }

    private void kiemTraTrungMa(Voucher voucher, ApiCallback<Boolean> callback) {
        voucherDAO.layDanhSachVoucher(new ApiCallback<List<Voucher>>() {
            @Override
            public void onSuccess(List<Voucher> data) {
                String maCanKiemTra = voucher.getMaGiamGia().trim();
                if (data != null) {
                    for (Voucher item : data) {
                        if (item.getMaVoucher() != voucher.getMaVoucher()
                                && item.getMaGiamGia() != null
                                && item.getMaGiamGia().trim().equalsIgnoreCase(maCanKiemTra)) {
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

    private String kiemTraVoucher(Voucher voucher, boolean batBuocMaVoucher) {
        if (voucher == null) {
            return "Thông tin voucher không hợp lệ";
        }
        if (batBuocMaVoucher && voucher.getMaVoucher() <= 0) {
            return "Voucher không hợp lệ";
        }
        if (rong(voucher.getMaGiamGia())) {
            return "Mã giảm giá không được để trống";
        }
        if (rong(voucher.getTenVoucher())) {
            return "Tên voucher không được để trống";
        }
        if (!"PhanTram".equals(voucher.getKieuGiamGia()) && !"TienMat".equals(voucher.getKieuGiamGia())) {
            return "Kiểu giảm giá không hợp lệ";
        }
        if (voucher.getGiaTriGiam() <= 0) {
            return "Giá trị giảm phải lớn hơn 0";
        }
        if ("PhanTram".equals(voucher.getKieuGiamGia()) && voucher.getGiaTriGiam() > 100) {
            return "Giảm theo phần trăm không được vượt quá 100";
        }
        if (voucher.getSoLuong() < 0) {
            return "Số lượng không được âm";
        }
        if (!ngayHopLe(voucher.getNgayBatDau()) || !ngayHopLe(voucher.getNgayKetThuc())) {
            return "Ngày không hợp lệ";
        }
        if (voucher.getNgayBatDau().compareTo(voucher.getNgayKetThuc()) > 0) {
            return "Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc";
        }
        return null;
    }

    private Voucher chuanHoaVoucher(Voucher voucher) {
        voucher.setMaGiamGia(voucher.getMaGiamGia().trim().toUpperCase(Locale.ROOT));
        voucher.setTenVoucher(voucher.getTenVoucher().trim());
        if (rong(voucher.getTrangThai())) {
            voucher.setTrangThai("HoatDong");
        }
        return voucher;
    }

    private boolean ngayHopLe(String ngay) {
        if (rong(ngay)) {
            return false;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(ngay);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean rong(String chuoi) {
        return chuoi == null || chuoi.trim().isEmpty();
    }
}
