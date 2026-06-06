package com.example.banve.dao;

import com.example.banve.models.LichSuChat;
import com.example.banve.network.ApiCallback;
import com.example.banve.network.ApiService;
import com.example.banve.network.SupabaseClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LichSuChatDAO {
    private final ApiService apiService;

    public LichSuChatDAO() {
        apiService = SupabaseClient.layApiService();
    }

    public void layTheoNguoiDung(int maNguoiDung, ApiCallback<List<LichSuChat>> callback) {
        Map<String, String> filter = new HashMap<>();
        filter.put("MaNguoiDung", "eq." + maNguoiDung);
        filter.put("select", "*");
        filter.put("order", "NgayTao.asc");

        apiService.timLichSuChat(filter).enqueue(new Callback<List<LichSuChat>>() {
            @Override
            public void onResponse(Call<List<LichSuChat>> call, Response<List<LichSuChat>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể tải lịch sử chat");
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<LichSuChat>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void themLichSu(LichSuChat lichSuChat, ApiCallback<LichSuChat> callback) {
        apiService.themLichSuChat(taoDuLieuLichSu(lichSuChat)).enqueue(new Callback<List<LichSuChat>>() {
            @Override
            public void onResponse(Call<List<LichSuChat>> call, Response<List<LichSuChat>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể lưu lịch sử chat");
                    return;
                }

                List<LichSuChat> danhSachLichSu = response.body();
                if (danhSachLichSu == null || danhSachLichSu.isEmpty()) {
                    callback.onError("Không nhận được dữ liệu lịch sử chat");
                    return;
                }

                callback.onSuccess(danhSachLichSu.get(0));
            }

            @Override
            public void onFailure(Call<List<LichSuChat>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private Map<String, Object> taoDuLieuLichSu(LichSuChat lichSuChat) {
        Map<String, Object> duLieu = new HashMap<>();
        duLieu.put("MaNguoiDung", lichSuChat.getMaNguoiDung());
        duLieu.put("CauHoi", lichSuChat.getCauHoi());
        duLieu.put("TraLoi", lichSuChat.getTraLoi());
        return duLieu;
    }
}
