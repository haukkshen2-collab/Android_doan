package com.example.banve.models;

import com.google.gson.annotations.SerializedName;

public class ChiTietHoaDon {
    @SerializedName("MaChiTietHoaDon")
    private int maChiTietHoaDon;

    @SerializedName("MaHoaDon")
    private int maHoaDon;

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

    @SerializedName("ThanhTien")
    private double thanhTien;

    @SerializedName("Ve")
    private Ve ve;

    public int getMaChiTietHoaDon() {
        return maChiTietHoaDon;
    }

    public void setMaChiTietHoaDon(int maChiTietHoaDon) {
        this.maChiTietHoaDon = maChiTietHoaDon;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
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

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public Ve getVe() {
        return ve;
    }

    public void setVe(Ve ve) {
        this.ve = ve;
    }
}
