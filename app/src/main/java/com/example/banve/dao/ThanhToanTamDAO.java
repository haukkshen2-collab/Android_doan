package com.example.banve.dao;

import com.example.banve.models.ThanhToanTam;
import com.example.banve.network.ApiCallback;
import com.example.banve.network.ApiService;
import com.example.banve.network.SupabaseClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThanhToanTamDAO {
    private final ApiService apiService;

    public ThanhToanTamDAO() {
        apiService = SupabaseClient.layApiService();
    }

    public void taoThanhToanTam(ThanhToanTam thanhToanTam, ApiCallback<ThanhToanTam> callback) {
        apiService.taoThanhToanTam(taoDuLieuThanhToanTam(thanhToanTam)).enqueue(new Callback<List<ThanhToanTam>>() {
            @Override
            public void onResponse(Call<List<ThanhToanTam>> call, Response<List<ThanhToanTam>> response) {
                xuLyMotThanhToanTam(response, callback, "Không thể tạo thanh toán SePay");
            }

            @Override
            public void onFailure(Call<List<ThanhToanTam>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void capNhatNoiDungChuyenKhoan(int maHoaDon, String noiDungChuyenKhoan, ApiCallback<ThanhToanTam> callback) {
        Map<String, Object> duLieuCapNhat = new HashMap<>();
        duLieuCapNhat.put("NoiDungChuyenKhoan", noiDungChuyenKhoan);

        apiService.capNhatThanhToanTam("eq." + maHoaDon, duLieuCapNhat).enqueue(new Callback<List<ThanhToanTam>>() {
            @Override
            public void onResponse(Call<List<ThanhToanTam>> call, Response<List<ThanhToanTam>> response) {
                xuLyMotThanhToanTam(response, callback, "Không thể cập nhật nội dung chuyển khoản");
            }

            @Override
            public void onFailure(Call<List<ThanhToanTam>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void layTheoMa(int maHoaDon, ApiCallback<ThanhToanTam> callback) {
        Map<String, String> filter = new HashMap<>();
        filter.put("MaHoaDon", "eq." + maHoaDon);
        filter.put("select", "*");
        filter.put("limit", "1");

        apiService.timThanhToanTam(filter).enqueue(new Callback<List<ThanhToanTam>>() {
            @Override
            public void onResponse(Call<List<ThanhToanTam>> call, Response<List<ThanhToanTam>> response) {
                xuLyMotThanhToanTam(response, callback, "Không thể kiểm tra thanh toán SePay");
            }

            @Override
            public void onFailure(Call<List<ThanhToanTam>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void xoaTheoMa(int maHoaDon, ApiCallback<Boolean> callback) {
        Map<String, String> filter = new HashMap<>();
        filter.put("MaHoaDon", "eq." + maHoaDon);

        xoaThanhToanTam(filter, callback);
    }

    public void xoaDangChoTheoNguoiDung(int maNguoiDung, ApiCallback<Boolean> callback) {
        Map<String, String> filter = new HashMap<>();
        filter.put("MaNguoiDung", "eq." + maNguoiDung);
        filter.put("TrangThai", "eq.ChoThanhToan");

        xoaThanhToanTam(filter, callback);
    }

    private void xoaThanhToanTam(Map<String, String> filter, ApiCallback<Boolean> callback) {
        apiService.xoaThanhToanTam(filter).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể xóa thanh toán SePay");
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

    private void xuLyMotThanhToanTam(Response<List<ThanhToanTam>> response, ApiCallback<ThanhToanTam> callback, String thongBaoLoi) {
        if (!response.isSuccessful()) {
            callback.onError(thongBaoLoi);
            return;
        }

        List<ThanhToanTam> danhSachThanhToan = response.body();
        if (danhSachThanhToan == null || danhSachThanhToan.isEmpty()) {
            callback.onError("Không nhận được dữ liệu thanh toán SePay");
            return;
        }

        callback.onSuccess(danhSachThanhToan.get(0));
    }

    private Map<String, Object> taoDuLieuThanhToanTam(ThanhToanTam thanhToanTam) {
        Map<String, Object> duLieu = new HashMap<>();
        duLieu.put("MaNguoiDung", thanhToanTam.getMaNguoiDung());
        duLieu.put("TongTien", thanhToanTam.getTongTien());
        duLieu.put("MaVoucher", thanhToanTam.getMaVoucher());
        duLieu.put("TienGiam", thanhToanTam.getTienGiam());
        duLieu.put("ThanhToan", thanhToanTam.getThanhToan());
        duLieu.put("TrangThai", thanhToanTam.getTrangThai());
        duLieu.put("NoiDungChuyenKhoan", thanhToanTam.getNoiDungChuyenKhoan());
        return duLieu;
    }
}
