package com.example.banve.controllers;

import com.example.banve.dao.HoaDonDAO;
import com.example.banve.models.ChiTietHoaDon;
import com.example.banve.models.HoaDon;
import com.example.banve.models.KetQuaThongKe;
import com.example.banve.models.LoaiVe;
import com.example.banve.models.ThongKeTheoLoaiVe;
import com.example.banve.models.ThongKeTheoNgay;
import com.example.banve.models.ThongKeTheoThang;
import com.example.banve.models.ThongKeTongQuan;
import com.example.banve.models.Ve;
import com.example.banve.network.ApiCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ThongKeController {
    private final HoaDonDAO hoaDonDAO;

    public ThongKeController() {
        hoaDonDAO = new HoaDonDAO();
    }

    public void layThongKeTongQuan(Date tuNgay, Date denNgay, ApiCallback<KetQuaThongKe> callback) {
        if (tuNgay == null || denNgay == null) {
            callback.onError("Ngày lọc không hợp lệ");
            return;
        }

        Date tuNgayChuan = dauNgay(tuNgay);
        Date denNgayChuan = cuoiNgay(denNgay);
        if (tuNgayChuan.after(denNgayChuan)) {
            callback.onError("Từ ngày không được lớn hơn đến ngày");
            return;
        }

        hoaDonDAO.layDanhSachHoaDonQuanLy(new ApiCallback<List<HoaDon>>() {
            @Override
            public void onSuccess(List<HoaDon> data) {
                List<HoaDon> danhSachHoaDon = locHoaDonDaThanhToan(data, tuNgayChuan, denNgayChuan);
                taiChiTietVaTinhThongKe(danhSachHoaDon, callback);
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
            }
        });
    }

    private void taiChiTietVaTinhThongKe(List<HoaDon> danhSachHoaDon, ApiCallback<KetQuaThongKe> callback) {
        if (danhSachHoaDon.isEmpty()) {
            callback.onSuccess(taoKetQuaRong());
            return;
        }

        List<Integer> danhSachMaHoaDon = new ArrayList<>();
        for (HoaDon hoaDon : danhSachHoaDon) {
            danhSachMaHoaDon.add(hoaDon.getMaHoaDon());
        }

        hoaDonDAO.layChiTietHoaDonTheoDanhSach(danhSachMaHoaDon, new ApiCallback<List<ChiTietHoaDon>>() {
            @Override
            public void onSuccess(List<ChiTietHoaDon> data) {
                callback.onSuccess(tinhThongKe(danhSachHoaDon, data));
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
            }
        });
    }

    private List<HoaDon> locHoaDonDaThanhToan(List<HoaDon> danhSachHoaDon, Date tuNgay, Date denNgay) {
        List<HoaDon> ketQua = new ArrayList<>();
        if (danhSachHoaDon == null) {
            return ketQua;
        }

        for (HoaDon hoaDon : danhSachHoaDon) {
            if (!"DaThanhToan".equals(hoaDon.getTrangThai())) {
                continue;
            }

            Date ngayLap = parseNgayGio(hoaDon.getNgayLap());
            if (ngayLap == null) {
                continue;
            }

            if (!ngayLap.before(tuNgay) && !ngayLap.after(denNgay)) {
                ketQua.add(hoaDon);
            }
        }
        return ketQua;
    }

    private KetQuaThongKe tinhThongKe(List<HoaDon> danhSachHoaDon, List<ChiTietHoaDon> danhSachChiTiet) {
        ThongKeTongQuan tongQuan = tinhTongQuan(danhSachHoaDon, danhSachChiTiet);

        KetQuaThongKe ketQua = new KetQuaThongKe();
        ketQua.setThongKeTongQuan(tongQuan);
        ketQua.setDanhSachTheoLoaiVe(tinhTheoLoaiVe(danhSachChiTiet));
        ketQua.setDanhSachTheoNgay(tinhTheoNgay(danhSachHoaDon));
        ketQua.setDanhSachTheoThang(tinhTheoThang(danhSachHoaDon));
        return ketQua;
    }

    private ThongKeTongQuan tinhTongQuan(List<HoaDon> danhSachHoaDon, List<ChiTietHoaDon> danhSachChiTiet) {
        ThongKeTongQuan tongQuan = new ThongKeTongQuan();
        tongQuan.setTongHoaDon(danhSachHoaDon.size());

        double tongDoanhThu = 0;
        double tongTienGiam = 0;
        double tongThanhTien = 0;
        for (HoaDon hoaDon : danhSachHoaDon) {
            tongDoanhThu += hoaDon.getTongTien();
            tongTienGiam += hoaDon.getTienGiam();
            tongThanhTien += Math.max(0, hoaDon.getTongTien() - hoaDon.getTienGiam());
        }

        tongQuan.setTongDoanhThu(tongDoanhThu);
        tongQuan.setTongTienGiam(tongTienGiam);
        tongQuan.setTongThanhTien(tongThanhTien);
        tongQuan.setTongVeBan(tinhTongVeBan(danhSachChiTiet));
        return tongQuan;
    }

    private int tinhTongVeBan(List<ChiTietHoaDon> danhSachChiTiet) {
        int tongVeBan = 0;
        if (danhSachChiTiet == null) {
            return tongVeBan;
        }

        for (ChiTietHoaDon chiTiet : danhSachChiTiet) {
            tongVeBan += tinhSoVeBan(chiTiet);
        }
        return tongVeBan;
    }

    private List<ThongKeTheoLoaiVe> tinhTheoLoaiVe(List<ChiTietHoaDon> danhSachChiTiet) {
        Map<String, ThongKeTheoLoaiVe> thongKeMap = new HashMap<>();
        if (danhSachChiTiet != null) {
            for (ChiTietHoaDon chiTiet : danhSachChiTiet) {
                String tenLoaiVe = layTenLoaiVe(chiTiet);
                ThongKeTheoLoaiVe thongKe = thongKeMap.get(tenLoaiVe);
                if (thongKe == null) {
                    thongKe = new ThongKeTheoLoaiVe();
                    thongKe.setTenLoaiVe(tenLoaiVe);
                    thongKeMap.put(tenLoaiVe, thongKe);
                }

                thongKe.setSoVeBan(thongKe.getSoVeBan() + tinhSoVeBan(chiTiet));
                thongKe.setDoanhThu(thongKe.getDoanhThu() + chiTiet.getThanhTien());
            }
        }

        List<String> danhSachTenLoai = new ArrayList<>(thongKeMap.keySet());
        Collections.sort(danhSachTenLoai);

        List<ThongKeTheoLoaiVe> ketQua = new ArrayList<>();
        for (String tenLoai : danhSachTenLoai) {
            ketQua.add(thongKeMap.get(tenLoai));
        }
        return ketQua;
    }

    private List<ThongKeTheoNgay> tinhTheoNgay(List<HoaDon> danhSachHoaDon) {
        Map<String, Double> doanhThuMap = new HashMap<>();
        for (HoaDon hoaDon : danhSachHoaDon) {
            Date ngayLap = parseNgayGio(hoaDon.getNgayLap());
            if (ngayLap == null) {
                continue;
            }

            String ngay = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(ngayLap);
            double doanhThu = Math.max(0, hoaDon.getTongTien() - hoaDon.getTienGiam());
            doanhThuMap.put(ngay, doanhThuMap.containsKey(ngay) ? doanhThuMap.get(ngay) + doanhThu : doanhThu);
        }

        List<String> danhSachNgay = new ArrayList<>(doanhThuMap.keySet());
        Collections.sort(danhSachNgay);

        List<ThongKeTheoNgay> ketQua = new ArrayList<>();
        for (String ngay : danhSachNgay) {
            ThongKeTheoNgay thongKe = new ThongKeTheoNgay();
            thongKe.setNgay(ngay);
            thongKe.setDoanhThu(doanhThuMap.get(ngay));
            ketQua.add(thongKe);
        }
        return ketQua;
    }

    private List<ThongKeTheoThang> tinhTheoThang(List<HoaDon> danhSachHoaDon) {
        Map<String, Double> doanhThuMap = new HashMap<>();
        for (HoaDon hoaDon : danhSachHoaDon) {
            Date ngayLap = parseNgayGio(hoaDon.getNgayLap());
            if (ngayLap == null) {
                continue;
            }

            String thang = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(ngayLap);
            double doanhThu = Math.max(0, hoaDon.getTongTien() - hoaDon.getTienGiam());
            doanhThuMap.put(thang, doanhThuMap.containsKey(thang) ? doanhThuMap.get(thang) + doanhThu : doanhThu);
        }

        List<String> danhSachThang = new ArrayList<>(doanhThuMap.keySet());
        Collections.sort(danhSachThang);

        List<ThongKeTheoThang> ketQua = new ArrayList<>();
        for (String thang : danhSachThang) {
            ThongKeTheoThang thongKe = new ThongKeTheoThang();
            thongKe.setThang(thang);
            thongKe.setDoanhThu(doanhThuMap.get(thang));
            ketQua.add(thongKe);
        }
        return ketQua;
    }

    private int tinhSoVeBan(ChiTietHoaDon chiTiet) {
        return chiTiet.getSoLuongNguoiLon() + chiTiet.getSoLuongTreEm() + chiTiet.getSoLuongNguoiCaoTuoi();
    }

    private String layTenLoaiVe(ChiTietHoaDon chiTiet) {
        Ve ve = chiTiet.getVe();
        if (ve == null) {
            return "Loại vé khác";
        }

        LoaiVe loaiVe = ve.getLoaiVe();
        if (loaiVe != null && loaiVe.getTenLoaiVe() != null && !loaiVe.getTenLoaiVe().trim().isEmpty()) {
            return loaiVe.getTenLoaiVe();
        }

        if (ve.getTenVe() != null && !ve.getTenVe().trim().isEmpty()) {
            return ve.getTenVe();
        }
        return "Loại vé khác";
    }

    private KetQuaThongKe taoKetQuaRong() {
        KetQuaThongKe ketQua = new KetQuaThongKe();
        ketQua.setThongKeTongQuan(new ThongKeTongQuan());
        ketQua.setDanhSachTheoLoaiVe(new ArrayList<>());
        ketQua.setDanhSachTheoNgay(new ArrayList<>());
        ketQua.setDanhSachTheoThang(new ArrayList<>());
        return ketQua;
    }

    private Date dauNgay(Date ngay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ngay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date cuoiNgay(Date ngay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ngay);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    private Date parseNgayGio(String ngayGio) {
        if (ngayGio == null || ngayGio.trim().isEmpty()) {
            return null;
        }

        String[] dinhDangNguon = {
                "yyyy-MM-dd'T'HH:mm:ss.SSSSSS",
                "yyyy-MM-dd'T'HH:mm:ss.SSS",
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd HH:mm:ss",
                "yyyy-MM-dd"
        };

        for (String dinhDang : dinhDangNguon) {
            try {
                return new SimpleDateFormat(dinhDang, Locale.getDefault()).parse(ngayGio);
            } catch (ParseException ignored) {
            }
        }
        return null;
    }
}
