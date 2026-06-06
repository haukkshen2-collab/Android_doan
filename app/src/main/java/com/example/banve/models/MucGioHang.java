package com.example.banve.models;

public class MucGioHang {
    private ChiTietGioHang chiTietGioHang;
    private Ve ve;

    public MucGioHang(ChiTietGioHang chiTietGioHang, Ve ve) {
        this.chiTietGioHang = chiTietGioHang;
        this.ve = ve;
    }

    public ChiTietGioHang getChiTietGioHang() {
        return chiTietGioHang;
    }

    public void setChiTietGioHang(ChiTietGioHang chiTietGioHang) {
        this.chiTietGioHang = chiTietGioHang;
    }

    public Ve getVe() {
        return ve;
    }

    public void setVe(Ve ve) {
        this.ve = ve;
    }

    public double tinhThanhTien() {
        return chiTietGioHang.getSoLuongNguoiLon() * chiTietGioHang.getDonGiaNguoiLon()
                + chiTietGioHang.getSoLuongTreEm() * chiTietGioHang.getDonGiaTreEm()
                + chiTietGioHang.getSoLuongNguoiCaoTuoi() * chiTietGioHang.getDonGiaNguoiCaoTuoi();
    }
}
