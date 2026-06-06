package com.example.banve.dao;

import com.example.banve.models.ChiTietHoaDon;
import com.example.banve.models.HoaDon;
import com.example.banve.network.ApiCallback;
import com.example.banve.network.ApiService;
import com.example.banve.network.SupabaseClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HoaDonDAO {
    private final ApiService apiService;

    public HoaDonDAO() {
        apiService = SupabaseClient.layApiService();
    }

    public void taoHoaDon(HoaDon hoaDon, ApiCallback<HoaDon> callback) {
        apiService.taoHoaDon(taoDuLieuHoaDon(hoaDon)).enqueue(new Callback<List<HoaDon>>() {
            @Override
            public void onResponse(Call<List<HoaDon>> call, Response<List<HoaDon>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể tạo hóa đơn");
                    return;
                }

                List<HoaDon> danhSachHoaDon = response.body();
                if (danhSachHoaDon == null || danhSachHoaDon.isEmpty()) {
                    callback.onError("Không nhận được dữ liệu hóa đơn");
                    return;
                }

                callback.onSuccess(danhSachHoaDon.get(0));
            }

            @Override
            public void onFailure(Call<List<HoaDon>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void layDanhSachDaThanhToan(int maNguoiDung, ApiCallback<List<HoaDon>> callback) {
        Map<String, String> filter = new HashMap<>();
        filter.put("MaNguoiDung", "eq." + maNguoiDung);
        filter.put("TrangThai", "eq.DaThanhToan");
        filter.put("select", "*");
        filter.put("order", "NgayLap.desc");

        apiService.timHoaDon(filter).enqueue(new Callback<List<HoaDon>>() {
            @Override
            public void onResponse(Call<List<HoaDon>> call, Response<List<HoaDon>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể tải lịch sử đơn hàng");
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<HoaDon>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void layChiTietHoaDon(int maHoaDon, ApiCallback<List<ChiTietHoaDon>> callback) {
        Map<String, String> filter = new HashMap<>();
        filter.put("MaHoaDon", "eq." + maHoaDon);
        filter.put("select", "*,Ve(*)");

        apiService.timChiTietHoaDon(filter).enqueue(new Callback<List<ChiTietHoaDon>>() {
            @Override
            public void onResponse(Call<List<ChiTietHoaDon>> call, Response<List<ChiTietHoaDon>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể tải chi tiết hóa đơn");
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<ChiTietHoaDon>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private Map<String, Object> taoDuLieuHoaDon(HoaDon hoaDon) {
        Map<String, Object> duLieu = new HashMap<>();
        duLieu.put("MaNguoiDung", hoaDon.getMaNguoiDung());
        duLieu.put("TongTien", hoaDon.getTongTien());
        duLieu.put("MaVoucher", hoaDon.getMaVoucher());
        duLieu.put("TienGiam", hoaDon.getTienGiam());
        duLieu.put("ThanhToan", hoaDon.getThanhToan());
        duLieu.put("TrangThai", hoaDon.getTrangThai());
        return duLieu;
    }
}
