package com.example.banve.controllers;

import com.example.banve.dao.ChiTietGioHangDAO;
import com.example.banve.dao.ChiTietHoaDonDAO;
import com.example.banve.dao.HoaDonDAO;
import com.example.banve.dao.VeDAO;
import com.example.banve.dao.VoucherDAO;
import com.example.banve.models.ChiTietGioHang;
import com.example.banve.models.ChiTietHoaDon;
import com.example.banve.models.HoaDon;
import com.example.banve.models.MucGioHang;
import com.example.banve.models.Ve;
import com.example.banve.models.Voucher;
import com.example.banve.network.ApiCallback;

import java.util.List;

public class ThanhToanController {
    private final VeDAO veDAO;
    private final VoucherDAO voucherDAO;
    private final HoaDonDAO hoaDonDAO;
    private final ChiTietHoaDonDAO chiTietHoaDonDAO;
    private final ChiTietGioHangDAO chiTietGioHangDAO;

    public ThanhToanController() {
        veDAO = new VeDAO();
        voucherDAO = new VoucherDAO();
        hoaDonDAO = new HoaDonDAO();
        chiTietHoaDonDAO = new ChiTietHoaDonDAO();
        chiTietGioHangDAO = new ChiTietGioHangDAO();
    }

    public double apDungVoucher(Voucher voucher, double tongTien) {
        if (voucher == null || tongTien <= 0) {
            return 0;
        }

        double tienGiam;
        if ("PhanTram".equals(voucher.getKieuGiamGia())) {
            tienGiam = tongTien * voucher.getGiaTriGiam() / 100;
        } else {
            tienGiam = voucher.getGiaTriGiam();
        }

        return Math.min(tienGiam, tongTien);
    }

    public void hoanTatThanhToan(
            int maNguoiDung,
            List<MucGioHang> danhSachMuc,
            Voucher voucher,
            String hinhThucThanhToan,
            ApiCallback<HoaDon> callback
    ) {
        if (maNguoiDung <= 0) {
            callback.onError("Người dùng không hợp lệ");
            return;
        }
        if (danhSachMuc == null || danhSachMuc.isEmpty()) {
            callback.onError("Giỏ hàng đang trống");
            return;
        }
        if (hinhThucThanhToan == null || hinhThucThanhToan.trim().isEmpty()) {
            callback.onError("Vui lòng chọn hình thức thanh toán");
            return;
        }

        kiemTraTonVe(maNguoiDung, danhSachMuc, voucher, hinhThucThanhToan, callback, 0);
    }

    private void kiemTraTonVe(
            int maNguoiDung,
            List<MucGioHang> danhSachMuc,
            Voucher voucher,
            String hinhThucThanhToan,
            ApiCallback<HoaDon> callback,
            int viTri
    ) {
        if (viTri >= danhSachMuc.size()) {
            truSoLuongVe(maNguoiDung, danhSachMuc, voucher, hinhThucThanhToan, callback, 0);
            return;
        }

        MucGioHang muc = danhSachMuc.get(viTri);
        veDAO.layTheoMa(muc.getChiTietGioHang().getMaVe(), new ApiCallback<Ve>() {
            @Override
            public void onSuccess(Ve ve) {
                int tongSoLuong = tinhTongSoLuong(muc.getChiTietGioHang());
                if (ve.getSoLuong() < tongSoLuong) {
                    callback.onError("Vé '" + ve.getTenVe() + "' không đủ số lượng");
                    return;
                }

                muc.setVe(ve);
                kiemTraTonVe(maNguoiDung, danhSachMuc, voucher, hinhThucThanhToan, callback, viTri + 1);
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
            }
        });
    }

    private void truSoLuongVe(
            int maNguoiDung,
            List<MucGioHang> danhSachMuc,
            Voucher voucher,
            String hinhThucThanhToan,
            ApiCallback<HoaDon> callback,
            int viTri
    ) {
        if (viTri >= danhSachMuc.size()) {
            truVoucher(maNguoiDung, danhSachMuc, voucher, hinhThucThanhToan, callback);
            return;
        }

        MucGioHang muc = danhSachMuc.get(viTri);
        int soLuongMoi = muc.getVe().getSoLuong() - tinhTongSoLuong(muc.getChiTietGioHang());
        veDAO.capNhatSoLuong(muc.getVe().getMaVe(), soLuongMoi, new ApiCallback<Ve>() {
            @Override
            public void onSuccess(Ve data) {
                truSoLuongVe(maNguoiDung, danhSachMuc, voucher, hinhThucThanhToan, callback, viTri + 1);
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
            }
        });
    }

    private void truVoucher(
            int maNguoiDung,
            List<MucGioHang> danhSachMuc,
            Voucher voucher,
            String hinhThucThanhToan,
            ApiCallback<HoaDon> callback
    ) {
        if (voucher == null) {
            taoHoaDon(maNguoiDung, danhSachMuc, null, hinhThucThanhToan, callback);
            return;
        }

        if (voucher.getSoLuong() <= 0) {
            callback.onError("Voucher đã hết lượt sử dụng");
            return;
        }

        voucherDAO.capNhatSoLuong(voucher.getMaVoucher(), voucher.getSoLuong() - 1, new ApiCallback<Voucher>() {
            @Override
            public void onSuccess(Voucher data) {
                taoHoaDon(maNguoiDung, danhSachMuc, data, hinhThucThanhToan, callback);
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
            }
        });
    }

    private void taoHoaDon(
            int maNguoiDung,
            List<MucGioHang> danhSachMuc,
            Voucher voucher,
            String hinhThucThanhToan,
            ApiCallback<HoaDon> callback
    ) {
        double tongTien = tinhTongTien(danhSachMuc);
        double tienGiam = apDungVoucher(voucher, tongTien);

        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaNguoiDung(maNguoiDung);
        hoaDon.setTongTien(tongTien);
        hoaDon.setTienGiam(tienGiam);
        hoaDon.setMaVoucher(voucher == null ? null : voucher.getMaVoucher());
        hoaDon.setThanhToan(hinhThucThanhToan);
        hoaDon.setTrangThai("DaThanhToan");

        hoaDonDAO.taoHoaDon(hoaDon, new ApiCallback<HoaDon>() {
            @Override
            public void onSuccess(HoaDon data) {
                taoChiTietHoaDon(data, danhSachMuc, callback, 0);
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
            }
        });
    }

    private void taoChiTietHoaDon(
            HoaDon hoaDon,
            List<MucGioHang> danhSachMuc,
            ApiCallback<HoaDon> callback,
            int viTri
    ) {
        if (viTri >= danhSachMuc.size()) {
            xoaGioHangSauThanhToan(hoaDon, danhSachMuc, callback, 0);
            return;
        }

        MucGioHang muc = danhSachMuc.get(viTri);
        ChiTietHoaDon chiTietHoaDon = taoChiTietHoaDonTuMuc(hoaDon.getMaHoaDon(), muc);
        chiTietHoaDonDAO.taoChiTietHoaDon(chiTietHoaDon, new ApiCallback<ChiTietHoaDon>() {
            @Override
            public void onSuccess(ChiTietHoaDon data) {
                taoChiTietHoaDon(hoaDon, danhSachMuc, callback, viTri + 1);
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
            }
        });
    }

    private void xoaGioHangSauThanhToan(
            HoaDon hoaDon,
            List<MucGioHang> danhSachMuc,
            ApiCallback<HoaDon> callback,
            int viTri
    ) {
        if (viTri >= danhSachMuc.size()) {
            callback.onSuccess(hoaDon);
            return;
        }

        chiTietGioHangDAO.xoaMuc(danhSachMuc.get(viTri).getChiTietGioHang().getMaChiTietGioHang(), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                xoaGioHangSauThanhToan(hoaDon, danhSachMuc, callback, viTri + 1);
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
            }
        });
    }

    private ChiTietHoaDon taoChiTietHoaDonTuMuc(int maHoaDon, MucGioHang muc) {
        ChiTietGioHang chiTietGioHang = muc.getChiTietGioHang();
        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
        chiTietHoaDon.setMaHoaDon(maHoaDon);
        chiTietHoaDon.setMaVe(chiTietGioHang.getMaVe());
        chiTietHoaDon.setNgaySuDung(chiTietGioHang.getNgaySuDung());
        chiTietHoaDon.setSoLuongNguoiLon(chiTietGioHang.getSoLuongNguoiLon());
        chiTietHoaDon.setSoLuongTreEm(chiTietGioHang.getSoLuongTreEm());
        chiTietHoaDon.setSoLuongNguoiCaoTuoi(chiTietGioHang.getSoLuongNguoiCaoTuoi());
        chiTietHoaDon.setDonGiaNguoiLon(chiTietGioHang.getDonGiaNguoiLon());
        chiTietHoaDon.setDonGiaTreEm(chiTietGioHang.getDonGiaTreEm());
        chiTietHoaDon.setDonGiaNguoiCaoTuoi(chiTietGioHang.getDonGiaNguoiCaoTuoi());
        chiTietHoaDon.setThanhTien(muc.tinhThanhTien());
        return chiTietHoaDon;
    }

    public double tinhTongTien(List<MucGioHang> danhSachMuc) {
        double tongTien = 0;
        if (danhSachMuc == null) {
            return tongTien;
        }

        for (MucGioHang muc : danhSachMuc) {
            tongTien += muc.tinhThanhTien();
        }
        return tongTien;
    }

    private int tinhTongSoLuong(ChiTietGioHang chiTietGioHang) {
        return chiTietGioHang.getSoLuongNguoiLon()
                + chiTietGioHang.getSoLuongTreEm()
                + chiTietGioHang.getSoLuongNguoiCaoTuoi();
    }
}
