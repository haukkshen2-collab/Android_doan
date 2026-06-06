package com.example.banve.models;

import com.google.gson.annotations.SerializedName;

public class Voucher {
    @SerializedName("MaVoucher")
    private int maVoucher;

    @SerializedName("MaGiamGia")
    private String maGiamGia;

    @SerializedName("TenVoucher")
    private String tenVoucher;

    @SerializedName("KieuGiamGia")
    private String kieuGiamGia;

    @SerializedName("GiaTriGiam")
    private double giaTriGiam;

    @SerializedName("NgayBatDau")
    private String ngayBatDau;

    @SerializedName("NgayKetThuc")
    private String ngayKetThuc;

    @SerializedName("SoLuong")
    private int soLuong;

    @SerializedName("TrangThai")
    private String trangThai;

    public int getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(int maVoucher) {
        this.maVoucher = maVoucher;
    }

    public String getMaGiamGia() {
        return maGiamGia;
    }

    public void setMaGiamGia(String maGiamGia) {
        this.maGiamGia = maGiamGia;
    }

    public String getTenVoucher() {
        return tenVoucher;
    }

    public void setTenVoucher(String tenVoucher) {
        this.tenVoucher = tenVoucher;
    }

    public String getKieuGiamGia() {
        return kieuGiamGia;
    }

    public void setKieuGiamGia(String kieuGiamGia) {
        this.kieuGiamGia = kieuGiamGia;
    }

    public double getGiaTriGiam() {
        return giaTriGiam;
    }

    public void setGiaTriGiam(double giaTriGiam) {
        this.giaTriGiam = giaTriGiam;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
