package com.example.banve.models;

import com.google.gson.annotations.SerializedName;

public class NguoiDung {
    @SerializedName(value = "MaNguoiDung", alternate = {"maNguoiDung"})
    private int maNguoiDung;

    @SerializedName(value = "HoTen", alternate = {"hoTen"})
    private String hoTen;

    @SerializedName(value = "Email", alternate = {"email"})
    private String email;

    @SerializedName(value = "SoDienThoai", alternate = {"soDienThoai"})
    private String soDienThoai;

    @SerializedName(value = "TaiKhoan", alternate = {"taiKhoan"})
    private String taiKhoan;

    @SerializedName(value = "MatKhau", alternate = {"matKhau"})
    private String matKhau;

    @SerializedName(value = "VaiTro", alternate = {"vaiTro"})
    private String vaiTro;

    @SerializedName(value = "NgayDangKy", alternate = {"ngayDangKy"})
    private String ngayDangKy;

    @SerializedName(value = "TrangThai", alternate = {"trangThai"})
    private String trangThai;

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(String ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
