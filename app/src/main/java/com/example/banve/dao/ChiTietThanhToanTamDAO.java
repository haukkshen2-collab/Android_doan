package com.example.banve.dao;

import com.example.banve.models.ChiTietThanhToanTam;
import com.example.banve.network.ApiCallback;
import com.example.banve.network.ApiService;
import com.example.banve.network.SupabaseClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietThanhToanTamDAO {
    private final ApiService apiService;

    public ChiTietThanhToanTamDAO() {
        apiService = SupabaseClient.layApiService();
    }

    public void taoChiTietThanhToanTam(ChiTietThanhToanTam chiTiet, ApiCallback<ChiTietThanhToanTam> callback) {
        apiService.taoChiTietThanhToanTam(taoDuLieuChiTiet(chiTiet)).enqueue(new Callback<List<ChiTietThanhToanTam>>() {
            @Override
            public void onResponse(Call<List<ChiTietThanhToanTam>> call, Response<List<ChiTietThanhToanTam>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể tạo chi tiết thanh toán tạm");
                    return;
                }

                List<ChiTietThanhToanTam> danhSachChiTiet = response.body();
                if (danhSachChiTiet == null || danhSachChiTiet.isEmpty()) {
                    callback.onError("Không nhận được dữ liệu chi tiết thanh toán tạm");
                    return;
                }

                callback.onSuccess(danhSachChiTiet.get(0));
            }

            @Override
            public void onFailure(Call<List<ChiTietThanhToanTam>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private Map<String, Object> taoDuLieuChiTiet(ChiTietThanhToanTam chiTiet) {
        Map<String, Object> duLieu = new HashMap<>();
        duLieu.put("MaHoaDon", chiTiet.getMaHoaDon());
        duLieu.put("MaChiTietGioHang", chiTiet.getMaChiTietGioHang());
        duLieu.put("MaVe", chiTiet.getMaVe());
        duLieu.put("NgaySuDung", chiTiet.getNgaySuDung());
        duLieu.put("SoLuongNguoiLon", chiTiet.getSoLuongNguoiLon());
        duLieu.put("SoLuongTreEm", chiTiet.getSoLuongTreEm());
        duLieu.put("SoLuongNguoiCaoTuoi", chiTiet.getSoLuongNguoiCaoTuoi());
        duLieu.put("DonGiaNguoiLon", chiTiet.getDonGiaNguoiLon());
        duLieu.put("DonGiaTreEm", chiTiet.getDonGiaTreEm());
        duLieu.put("DonGiaNguoiCaoTuoi", chiTiet.getDonGiaNguoiCaoTuoi());
        duLieu.put("ThanhTien", chiTiet.getThanhTien());
        return duLieu;
    }
}
