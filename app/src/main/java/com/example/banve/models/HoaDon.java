package com.example.banve.models;

import com.google.gson.annotations.SerializedName;

public class HoaDon {
    @SerializedName("MaHoaDon")
    private int maHoaDon;

    @SerializedName("MaNguoiDung")
    private int maNguoiDung;

    @SerializedName("NgayLap")
    private String ngayLap;

    @SerializedName("TongTien")
    private double tongTien;

    @SerializedName("MaVoucher")
    private Integer maVoucher;

    @SerializedName("TienGiam")
    private double tienGiam;

    @SerializedName("ThanhToan")
    private String thanhToan;

    @SerializedName("TrangThai")
    private String trangThai;

    @SerializedName("NguoiDung")
    private NguoiDung nguoiDung;

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public Integer getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(Integer maVoucher) {
        this.maVoucher = maVoucher;
    }

    public double getTienGiam() {
        return tienGiam;
    }

    public void setTienGiam(double tienGiam) {
        this.tienGiam = tienGiam;
    }

    public String getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(String thanhToan) {
        this.thanhToan = thanhToan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }
}
