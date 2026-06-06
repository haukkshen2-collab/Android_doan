package com.example.banve.models;

import com.google.gson.annotations.SerializedName;

public class ChiTietGioHang {
    @SerializedName("MaChiTietGioHang")
    private int maChiTietGioHang;

    @SerializedName("MaGioHang")
    private int maGioHang;

    @SerializedName("MaVe")
    private int maVe;

    @SerializedName("NgaySuDung")
    private String ngaySuDung;

    @SerializedName("SoLuongNguoiLon")
    private int soLuongNguoiLon;

    @SerializedName("SoLuongTreEm")
    private int soLuongTreEm;

    @SerializedName("SoLuongNguoiCaoTuoi")
    private int soLuongNguoiCaoTuoi;

    @SerializedName("DonGiaNguoiLon")
    private double donGiaNguoiLon;

    @SerializedName("DonGiaTreEm")
    private double donGiaTreEm;

    @SerializedName("DonGiaNguoiCaoTuoi")
    private double donGiaNguoiCaoTuoi;

    public int getMaChiTietGioHang() {
        return maChiTietGioHang;
    }

    public void setMaChiTietGioHang(int maChiTietGioHang) {
        this.maChiTietGioHang = maChiTietGioHang;
    }

    public int getMaGioHang() {
        return maGioHang;
    }

    public void setMaGioHang(int maGioHang) {
        this.maGioHang = maGioHang;
    }

    public int getMaVe() {
        return maVe;
    }

    public void setMaVe(int maVe) {
        this.maVe = maVe;
    }

    public String getNgaySuDung() {
        return ngaySuDung;
    }

    public void setNgaySuDung(String ngaySuDung) {
        this.ngaySuDung = ngaySuDung;
    }

    public int getSoLuongNguoiLon() {
        return soLuongNguoiLon;
    }

    public void setSoLuongNguoiLon(int soLuongNguoiLon) {
        this.soLuongNguoiLon = soLuongNguoiLon;
    }

    public int getSoLuongTreEm() {
        return soLuongTreEm;
    }

    public void setSoLuongTreEm(int soLuongTreEm) {
        this.soLuongTreEm = soLuongTreEm;
    }

    public int getSoLuongNguoiCaoTuoi() {
        return soLuongNguoiCaoTuoi;
    }

    public void setSoLuongNguoiCaoTuoi(int soLuongNguoiCaoTuoi) {
        this.soLuongNguoiCaoTuoi = soLuongNguoiCaoTuoi;
    }

    public double getDonGiaNguoiLon() {
        return donGiaNguoiLon;
    }

    public void setDonGiaNguoiLon(double donGiaNguoiLon) {
        this.donGiaNguoiLon = donGiaNguoiLon;
    }

    public double getDonGiaTreEm() {
        return donGiaTreEm;
    }

    public void setDonGiaTreEm(double donGiaTreEm) {
        this.donGiaTreEm = donGiaTreEm;
    }

    public double getDonGiaNguoiCaoTuoi() {
        return donGiaNguoiCaoTuoi;
    }

    public void setDonGiaNguoiCaoTuoi(double donGiaNguoiCaoTuoi) {
        this.donGiaNguoiCaoTuoi = donGiaNguoiCaoTuoi;
    }
}
