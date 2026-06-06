package com.example.banve.models;

import com.google.gson.annotations.SerializedName;

public class Ve {
    @SerializedName("MaVe")
    private int maVe;

    @SerializedName("MaLoaiVe")
    private int maLoaiVe;

    @SerializedName("TenVe")
    private String tenVe;

    @SerializedName("GiaVe")
    private double giaVe;

    @SerializedName("GiaNguoiLon")
    private double giaNguoiLon;

    @SerializedName("GiaTreEm")
    private double giaTreEm;

    @SerializedName("GiaNguoiCaoTuoi")
    private double giaNguoiCaoTuoi;

    @SerializedName("SoLuong")
    private int soLuong;

    @SerializedName("MoTa")
    private String moTa;

    @SerializedName("ThongTinVe")
    private String thongTinVe;

    @SerializedName("AnhVe")
    private String anhVe;

    @SerializedName("TrangThai")
    private String trangThai;

    @SerializedName("LoaiVe")
    private LoaiVe loaiVe;

    public int getMaVe() {
        return maVe;
    }

    public void setMaVe(int maVe) {
        this.maVe = maVe;
    }

    public int getMaLoaiVe() {
        return maLoaiVe;
    }

    public void setMaLoaiVe(int maLoaiVe) {
        this.maLoaiVe = maLoaiVe;
    }

    public String getTenVe() {
        return tenVe;
    }

    public void setTenVe(String tenVe) {
        this.tenVe = tenVe;
    }

    public double getGiaVe() {
        return giaVe;
    }

    public void setGiaVe(double giaVe) {
        this.giaVe = giaVe;
    }

    public double getGiaNguoiLon() {
        return giaNguoiLon;
    }

    public void setGiaNguoiLon(double giaNguoiLon) {
        this.giaNguoiLon = giaNguoiLon;
    }

    public double getGiaTreEm() {
        return giaTreEm;
    }

    public void setGiaTreEm(double giaTreEm) {
        this.giaTreEm = giaTreEm;
    }

    public double getGiaNguoiCaoTuoi() {
        return giaNguoiCaoTuoi;
    }

    public void setGiaNguoiCaoTuoi(double giaNguoiCaoTuoi) {
        this.giaNguoiCaoTuoi = giaNguoiCaoTuoi;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getThongTinVe() {
        return thongTinVe;
    }

    public void setThongTinVe(String thongTinVe) {
        this.thongTinVe = thongTinVe;
    }

    public String getAnhVe() {
        return anhVe;
    }

    public void setAnhVe(String anhVe) {
        this.anhVe = anhVe;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public LoaiVe getLoaiVe() {
        return loaiVe;
    }

    public void setLoaiVe(LoaiVe loaiVe) {
        this.loaiVe = loaiVe;
    }
}
