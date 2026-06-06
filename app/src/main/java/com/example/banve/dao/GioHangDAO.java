package com.example.banve.dao;

import com.example.banve.models.GioHang;
import com.example.banve.network.ApiCallback;
import com.example.banve.network.ApiService;
import com.example.banve.network.SupabaseClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangDAO {
    private final ApiService apiService;

    public GioHangDAO() {
        apiService = SupabaseClient.layApiService();
    }

    public void layHoacTaoGioHang(int maNguoiDung, ApiCallback<GioHang> callback) {
        Map<String, String> filter = new HashMap<>();
        filter.put("MaNguoiDung", "eq." + maNguoiDung);
        filter.put("select", "*");

        apiService.timGioHang(filter).enqueue(new Callback<List<GioHang>>() {
            @Override
            public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể tải giỏ hàng");
                    return;
                }

                List<GioHang> danhSachGioHang = response.body();
                if (danhSachGioHang != null && !danhSachGioHang.isEmpty()) {
                    callback.onSuccess(danhSachGioHang.get(0));
                    return;
                }

                taoGioHang(maNguoiDung, callback);
            }

            @Override
            public void onFailure(Call<List<GioHang>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private void taoGioHang(int maNguoiDung, ApiCallback<GioHang> callback) {
        Map<String, Object> gioHang = new HashMap<>();
        gioHang.put("MaNguoiDung", maNguoiDung);

        apiService.taoGioHang(gioHang).enqueue(new Callback<List<GioHang>>() {
            @Override
            public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể tạo giỏ hàng");
                    return;
                }

                List<GioHang> danhSachGioHang = response.body();
                if (danhSachGioHang == null || danhSachGioHang.isEmpty()) {
                    callback.onError("Không nhận được dữ liệu giỏ hàng");
                    return;
                }

                callback.onSuccess(danhSachGioHang.get(0));
            }

            @Override
            public void onFailure(Call<List<GioHang>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}
