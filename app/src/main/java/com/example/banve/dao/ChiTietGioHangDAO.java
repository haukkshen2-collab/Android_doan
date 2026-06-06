package com.example.banve.dao;

import com.example.banve.models.ChiTietGioHang;
import com.example.banve.network.ApiCallback;
import com.example.banve.network.ApiService;
import com.example.banve.network.SupabaseClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietGioHangDAO {
    private final ApiService apiService;

    public ChiTietGioHangDAO() {
        apiService = SupabaseClient.layApiService();
    }

    public void layTheoGioHang(int maGioHang, ApiCallback<List<ChiTietGioHang>> callback) {
        Map<String, String> filter = new HashMap<>();
        filter.put("MaGioHang", "eq." + maGioHang);
        filter.put("select", "*");

        apiService.timChiTietGioHang(filter).enqueue(new Callback<List<ChiTietGioHang>>() {
            @Override
            public void onResponse(Call<List<ChiTietGioHang>> call, Response<List<ChiTietGioHang>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể tải chi tiết giỏ hàng");
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<ChiTietGioHang>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void timTrungVeVaNgay(
            int maGioHang,
            int maVe,
            String ngaySuDung,
            ApiCallback<ChiTietGioHang> callback
    ) {
        Map<String, String> filter = new HashMap<>();
        filter.put("MaGioHang", "eq." + maGioHang);
        filter.put("MaVe", "eq." + maVe);
        filter.put("NgaySuDung", "eq." + ngaySuDung);
        filter.put("select", "*");

        apiService.timChiTietGioHang(filter).enqueue(new Callback<List<ChiTietGioHang>>() {
            @Override
            public void onResponse(Call<List<ChiTietGioHang>> call, Response<List<ChiTietGioHang>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể kiểm tra mục trong giỏ hàng");
                    return;
                }

                List<ChiTietGioHang> danhSachMuc = response.body();
                if (danhSachMuc == null || danhSachMuc.isEmpty()) {
                    callback.onSuccess(null);
                    return;
                }

                callback.onSuccess(danhSachMuc.get(0));
            }

            @Override
            public void onFailure(Call<List<ChiTietGioHang>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void themMuc(ChiTietGioHang chiTietGioHang, ApiCallback<ChiTietGioHang> callback) {
        apiService.themChiTietGioHang(taoDuLieuCapNhat(chiTietGioHang)).enqueue(new Callback<List<ChiTietGioHang>>() {
            @Override
            public void onResponse(Call<List<ChiTietGioHang>> call, Response<List<ChiTietGioHang>> response) {
                xuLyKetQuaMotMuc(response, callback, "Không thể thêm vào giỏ hàng");
            }

            @Override
            public void onFailure(Call<List<ChiTietGioHang>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void capNhatMuc(ChiTietGioHang chiTietGioHang, ApiCallback<ChiTietGioHang> callback) {
        apiService.capNhatChiTietGioHang(
                "eq." + chiTietGioHang.getMaChiTietGioHang(),
                taoDuLieuCapNhat(chiTietGioHang)
        ).enqueue(new Callback<List<ChiTietGioHang>>() {
            @Override
            public void onResponse(Call<List<ChiTietGioHang>> call, Response<List<ChiTietGioHang>> response) {
                xuLyKetQuaMotMuc(response, callback, "Không thể cập nhật giỏ hàng");
            }

            @Override
            public void onFailure(Call<List<ChiTietGioHang>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void xoaMuc(int maChiTietGioHang, ApiCallback<Boolean> callback) {
        apiService.xoaChiTietGioHang("eq." + maChiTietGioHang).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể xóa mục trong giỏ hàng");
                    return;
                }

                callback.onSuccess(true);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private void xuLyKetQuaMotMuc(
            Response<List<ChiTietGioHang>> response,
            ApiCallback<ChiTietGioHang> callback,
            String thongBaoLoi
    ) {
        if (!response.isSuccessful()) {
            callback.onError(thongBaoLoi);
            return;
        }

        List<ChiTietGioHang> danhSachMuc = response.body();
        if (danhSachMuc == null || danhSachMuc.isEmpty()) {
            callback.onError("Không nhận được dữ liệu giỏ hàng");
            return;
        }

        callback.onSuccess(danhSachMuc.get(0));
    }

    private Map<String, Object> taoDuLieuCapNhat(ChiTietGioHang chiTietGioHang) {
        Map<String, Object> duLieu = new HashMap<>();
        duLieu.put("MaGioHang", chiTietGioHang.getMaGioHang());
        duLieu.put("MaVe", chiTietGioHang.getMaVe());
        duLieu.put("NgaySuDung", chiTietGioHang.getNgaySuDung());
        duLieu.put("SoLuongNguoiLon", chiTietGioHang.getSoLuongNguoiLon());
        duLieu.put("SoLuongTreEm", chiTietGioHang.getSoLuongTreEm());
        duLieu.put("SoLuongNguoiCaoTuoi", chiTietGioHang.getSoLuongNguoiCaoTuoi());
        duLieu.put("DonGiaNguoiLon", chiTietGioHang.getDonGiaNguoiLon());
        duLieu.put("DonGiaTreEm", chiTietGioHang.getDonGiaTreEm());
        duLieu.put("DonGiaNguoiCaoTuoi", chiTietGioHang.getDonGiaNguoiCaoTuoi());
        return duLieu;
    }
}
