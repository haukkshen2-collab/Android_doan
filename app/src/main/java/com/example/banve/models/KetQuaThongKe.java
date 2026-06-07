package com.example.banve.models;

import java.util.List;

public class KetQuaThongKe {
    private ThongKeTongQuan thongKeTongQuan;
    private List<ThongKeTheoLoaiVe> danhSachTheoLoaiVe;
    private List<ThongKeTheoNgay> danhSachTheoNgay;
    private List<ThongKeTheoThang> danhSachTheoThang;

    public ThongKeTongQuan getThongKeTongQuan() {
        return thongKeTongQuan;
    }

    public void setThongKeTongQuan(ThongKeTongQuan thongKeTongQuan) {
        this.thongKeTongQuan = thongKeTongQuan;
    }

    public List<ThongKeTheoLoaiVe> getDanhSachTheoLoaiVe() {
        return danhSachTheoLoaiVe;
    }

    public void setDanhSachTheoLoaiVe(List<ThongKeTheoLoaiVe> danhSachTheoLoaiVe) {
        this.danhSachTheoLoaiVe = danhSachTheoLoaiVe;
    }

    public List<ThongKeTheoNgay> getDanhSachTheoNgay() {
        return danhSachTheoNgay;
    }

    public void setDanhSachTheoNgay(List<ThongKeTheoNgay> danhSachTheoNgay) {
        this.danhSachTheoNgay = danhSachTheoNgay;
    }

    public List<ThongKeTheoThang> getDanhSachTheoThang() {
        return danhSachTheoThang;
    }

    public void setDanhSachTheoThang(List<ThongKeTheoThang> danhSachTheoThang) {
        this.danhSachTheoThang = danhSachTheoThang;
    }
}
