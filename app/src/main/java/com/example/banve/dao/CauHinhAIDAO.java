package com.example.banve.dao;

import com.example.banve.models.CauHinhAI;
import com.example.banve.network.ApiCallback;
import com.example.banve.network.ApiService;
import com.example.banve.network.SupabaseClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CauHinhAIDAO {
    private final ApiService apiService;

    public CauHinhAIDAO() {
        apiService = SupabaseClient.layApiService();
    }

    public void layCauHinhHienTai(ApiCallback<CauHinhAI> callback) {
        Map<String, String> filter = new HashMap<>();
        filter.put("select", "*");
        filter.put("limit", "1");

        apiService.layCauHinhAI(filter).enqueue(new Callback<List<CauHinhAI>>() {
            @Override
            public void onResponse(Call<List<CauHinhAI>> call, Response<List<CauHinhAI>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể tải cấu hình AI");
                    return;
                }

                List<CauHinhAI> danhSachCauHinh = response.body();
                if (danhSachCauHinh == null || danhSachCauHinh.isEmpty()) {
                    callback.onError("Chưa có cấu hình AI");
                    return;
                }

                callback.onSuccess(danhSachCauHinh.get(0));
            }

            @Override
            public void onFailure(Call<List<CauHinhAI>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}
