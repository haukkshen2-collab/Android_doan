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

    public void luuCauHinh(CauHinhAI cauHinhAI, ApiCallback<CauHinhAI> callback) {
        if (cauHinhAI.getMaCauHinhAI() > 0) {
            capNhatCauHinh(cauHinhAI, callback);
            return;
        }

        layCauHinhHienTai(new ApiCallback<CauHinhAI>() {
            @Override
            public void onSuccess(CauHinhAI cauHinhCu) {
                cauHinhAI.setMaCauHinhAI(cauHinhCu.getMaCauHinhAI());
                capNhatCauHinh(cauHinhAI, callback);
            }

            @Override
            public void onError(String thongBao) {
                if ("Chưa có cấu hình AI".equals(thongBao)) {
                    themCauHinh(cauHinhAI, callback);
                    return;
                }
                callback.onError(thongBao);
            }
        });
    }

    private void themCauHinh(CauHinhAI cauHinhAI, ApiCallback<CauHinhAI> callback) {
        apiService.themCauHinhAI(taoDuLieuCauHinh(cauHinhAI)).enqueue(new Callback<List<CauHinhAI>>() {
            @Override
            public void onResponse(Call<List<CauHinhAI>> call, Response<List<CauHinhAI>> response) {
                xuLyKetQuaLuu(response, callback, "Không thể thêm cấu hình AI");
            }

            @Override
            public void onFailure(Call<List<CauHinhAI>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private void capNhatCauHinh(CauHinhAI cauHinhAI, ApiCallback<CauHinhAI> callback) {
        apiService.capNhatCauHinhAI(
                "eq." + cauHinhAI.getMaCauHinhAI(),
                taoDuLieuCauHinh(cauHinhAI)
        ).enqueue(new Callback<List<CauHinhAI>>() {
            @Override
            public void onResponse(Call<List<CauHinhAI>> call, Response<List<CauHinhAI>> response) {
                xuLyKetQuaLuu(response, callback, "Không thể cập nhật cấu hình AI");
            }

            @Override
            public void onFailure(Call<List<CauHinhAI>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private void xuLyKetQuaLuu(Response<List<CauHinhAI>> response, ApiCallback<CauHinhAI> callback, String thongBaoLoi) {
        if (!response.isSuccessful()) {
            callback.onError(thongBaoLoi);
            return;
        }

        List<CauHinhAI> danhSachCauHinh = response.body();
        if (danhSachCauHinh == null || danhSachCauHinh.isEmpty()) {
            callback.onError("Không nhận được dữ liệu cấu hình AI");
            return;
        }

        callback.onSuccess(danhSachCauHinh.get(0));
    }

    private Map<String, Object> taoDuLieuCauHinh(CauHinhAI cauHinhAI) {
        Map<String, Object> duLieu = new HashMap<>();
        duLieu.put("NhaCungCap", cauHinhAI.getNhaCungCap());
        duLieu.put("KhoaApi", cauHinhAI.getKhoaApi());
        duLieu.put("MoHinh", cauHinhAI.getMoHinh());
        duLieu.put("NhacLenh", cauHinhAI.getNhacLenh());
        return duLieu;
    }
}
