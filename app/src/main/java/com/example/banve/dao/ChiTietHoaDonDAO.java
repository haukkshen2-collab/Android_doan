package com.example.banve.dao;

import com.example.banve.models.ChiTietHoaDon;
import com.example.banve.network.ApiCallback;
import com.example.banve.network.ApiService;
import com.example.banve.network.SupabaseClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietHoaDonDAO {
    private final ApiService apiService;

    public ChiTietHoaDonDAO() {
        apiService = SupabaseClient.layApiService();
    }

    public void taoChiTietHoaDon(ChiTietHoaDon chiTietHoaDon, ApiCallback<ChiTietHoaDon> callback) {
        apiService.taoChiTietHoaDon(taoDuLieuChiTiet(chiTietHoaDon)).enqueue(new Callback<List<ChiTietHoaDon>>() {
            @Override
            public void onResponse(Call<List<ChiTietHoaDon>> call, Response<List<ChiTietHoaDon>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể tạo chi tiết hóa đơn");
                    return;
                }

                List<ChiTietHoaDon> danhSachChiTiet = response.body();
                if (danhSachChiTiet == null || danhSachChiTiet.isEmpty()) {
                    callback.onError("Không nhận được dữ liệu chi tiết hóa đơn");
                    return;
                }

                callback.onSuccess(danhSachChiTiet.get(0));
            }

            @Override
            public void onFailure(Call<List<ChiTietHoaDon>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private Map<String, Object> taoDuLieuChiTiet(ChiTietHoaDon chiTietHoaDon) {
        Map<String, Object> duLieu = new HashMap<>();
        duLieu.put("MaHoaDon", chiTietHoaDon.getMaHoaDon());
        duLieu.put("MaVe", chiTietHoaDon.getMaVe());
        duLieu.put("NgaySuDung", chiTietHoaDon.getNgaySuDung());
        duLieu.put("SoLuongNguoiLon", chiTietHoaDon.getSoLuongNguoiLon());
        duLieu.put("SoLuongTreEm", chiTietHoaDon.getSoLuongTreEm());
        duLieu.put("SoLuongNguoiCaoTuoi", chiTietHoaDon.getSoLuongNguoiCaoTuoi());
        duLieu.put("DonGiaNguoiLon", chiTietHoaDon.getDonGiaNguoiLon());
        duLieu.put("DonGiaTreEm", chiTietHoaDon.getDonGiaTreEm());
        duLieu.put("DonGiaNguoiCaoTuoi", chiTietHoaDon.getDonGiaNguoiCaoTuoi());
        duLieu.put("ThanhTien", chiTietHoaDon.getThanhTien());
        return duLieu;
    }
}
