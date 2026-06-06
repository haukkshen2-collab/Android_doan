package com.example.banve.dao;

import com.example.banve.models.LoaiVe;
import com.example.banve.network.ApiCallback;
import com.example.banve.network.ApiService;
import com.example.banve.network.SupabaseClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoaiVeDAO {
    private final ApiService apiService;

    public LoaiVeDAO() {
        apiService = SupabaseClient.layApiService();
    }

    public void layDanhSachLoaiVe(ApiCallback<List<LoaiVe>> callback) {
        apiService.layDanhSachLoaiVe("eq.HoatDong").enqueue(new Callback<List<LoaiVe>>() {
            @Override
            public void onResponse(Call<List<LoaiVe>> call, Response<List<LoaiVe>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể tải danh sách loại vé");
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<LoaiVe>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void layDanhSachQuanLy(ApiCallback<List<LoaiVe>> callback) {
        Map<String, String> filter = new HashMap<>();
        filter.put("select", "*");
        filter.put("order", "MaLoaiVe.desc");

        apiService.timLoaiVe(filter).enqueue(new Callback<List<LoaiVe>>() {
            @Override
            public void onResponse(Call<List<LoaiVe>> call, Response<List<LoaiVe>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể tải danh sách loại vé");
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<LoaiVe>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void themLoaiVe(LoaiVe loaiVe, ApiCallback<LoaiVe> callback) {
        apiService.themLoaiVe(taoDuLieuLoaiVe(loaiVe)).enqueue(new Callback<List<LoaiVe>>() {
            @Override
            public void onResponse(Call<List<LoaiVe>> call, Response<List<LoaiVe>> response) {
                xuLyKetQuaMotLoaiVe(response, callback, "Không thể thêm loại vé");
            }

            @Override
            public void onFailure(Call<List<LoaiVe>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void suaLoaiVe(LoaiVe loaiVe, ApiCallback<LoaiVe> callback) {
        apiService.capNhatLoaiVe("eq." + loaiVe.getMaLoaiVe(), taoDuLieuLoaiVe(loaiVe)).enqueue(new Callback<List<LoaiVe>>() {
            @Override
            public void onResponse(Call<List<LoaiVe>> call, Response<List<LoaiVe>> response) {
                xuLyKetQuaMotLoaiVe(response, callback, "Không thể cập nhật loại vé");
            }

            @Override
            public void onFailure(Call<List<LoaiVe>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void xoaLoaiVe(int maLoaiVe, ApiCallback<Boolean> callback) {
        apiService.xoaLoaiVe("eq." + maLoaiVe).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể xóa loại vé");
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

    private Map<String, Object> taoDuLieuLoaiVe(LoaiVe loaiVe) {
        Map<String, Object> duLieu = new HashMap<>();
        duLieu.put("TenLoaiVe", loaiVe.getTenLoaiVe());
        duLieu.put("MoTa", loaiVe.getMoTa());
        duLieu.put("TrangThai", loaiVe.getTrangThai());
        return duLieu;
    }

    private void xuLyKetQuaMotLoaiVe(Response<List<LoaiVe>> response, ApiCallback<LoaiVe> callback, String thongBaoLoi) {
        if (!response.isSuccessful()) {
            callback.onError(thongBaoLoi);
            return;
        }

        List<LoaiVe> danhSachLoaiVe = response.body();
        if (danhSachLoaiVe == null || danhSachLoaiVe.isEmpty()) {
            callback.onError("Không nhận được dữ liệu loại vé");
            return;
        }

        callback.onSuccess(danhSachLoaiVe.get(0));
    }
}
