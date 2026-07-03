package com.example.banve.models;

import com.google.gson.annotations.SerializedName;

public class ThanhToanTam {
    @SerializedName("MaHoaDon")
    private int maHoaDon;

    @SerializedName("MaNguoiDung")
    private int maNguoiDung;

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

    @SerializedName("NoiDungChuyenKhoan")
    private String noiDungChuyenKhoan;

    @SerializedName("ThongBaoLoi")
    private String thongBaoLoi;

    @SerializedName("NgayTao")
    private String ngayTao;

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

    public String getNoiDungChuyenKhoan() {
        return noiDungChuyenKhoan;
    }

    public void setNoiDungChuyenKhoan(String noiDungChuyenKhoan) {
        this.noiDungChuyenKhoan = noiDungChuyenKhoan;
    }

    public String getThongBaoLoi() {
        return thongBaoLoi;
    }

    public void setThongBaoLoi(String thongBaoLoi) {
        this.thongBaoLoi = thongBaoLoi;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }
}
