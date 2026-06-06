package com.example.banve.models;

import com.google.gson.annotations.SerializedName;

public class CauHinhAI {
    @SerializedName("MaCauHinhAI")
    private int maCauHinhAI;

    @SerializedName("NhaCungCap")
    private String nhaCungCap;

    @SerializedName("KhoaApi")
    private String khoaApi;

    @SerializedName("MoHinh")
    private String moHinh;

    @SerializedName("NhacLenh")
    private String nhacLenh;

    public int getMaCauHinhAI() {
        return maCauHinhAI;
    }

    public void setMaCauHinhAI(int maCauHinhAI) {
        this.maCauHinhAI = maCauHinhAI;
    }

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public String getKhoaApi() {
        return khoaApi;
    }

    public void setKhoaApi(String khoaApi) {
        this.khoaApi = khoaApi;
    }

    public String getMoHinh() {
        return moHinh;
    }

    public void setMoHinh(String moHinh) {
        this.moHinh = moHinh;
    }

    public String getNhacLenh() {
        return nhacLenh;
    }

    public void setNhacLenh(String nhacLenh) {
        this.nhacLenh = nhacLenh;
    }
}
