package com.example.banve.models;

import com.google.gson.annotations.SerializedName;

public class LoaiVe {
    @SerializedName("MaLoaiVe")
    private int maLoaiVe;

    @SerializedName("TenLoaiVe")
    private String tenLoaiVe;

    @SerializedName("MoTa")
    private String moTa;

    @SerializedName("TrangThai")
    private String trangThai;

    public int getMaLoaiVe() {
        return maLoaiVe;
    }

    public void setMaLoaiVe(int maLoaiVe) {
        this.maLoaiVe = maLoaiVe;
    }

    public String getTenLoaiVe() {
        return tenLoaiVe;
    }

    public void setTenLoaiVe(String tenLoaiVe) {
        this.tenLoaiVe = tenLoaiVe;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
