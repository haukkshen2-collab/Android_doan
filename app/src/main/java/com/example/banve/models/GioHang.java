package com.example.banve.models;

import com.google.gson.annotations.SerializedName;

public class GioHang {
    @SerializedName("MaGioHang")
    private int maGioHang;

    @SerializedName("MaNguoiDung")
    private int maNguoiDung;

    @SerializedName("NgayTao")
    private String ngayTao;

    public int getMaGioHang() {
        return maGioHang;
    }

    public void setMaGioHang(int maGioHang) {
        this.maGioHang = maGioHang;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }
}
