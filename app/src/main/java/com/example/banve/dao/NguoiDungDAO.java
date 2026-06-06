package com.example.banve.dao;

import com.example.banve.models.NguoiDung;
import com.example.banve.network.ApiCallback;
import com.example.banve.network.ApiService;
import com.example.banve.network.SupabaseClient;
import com.example.banve.utils.MaHoa;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NguoiDungDAO {
    private final ApiService apiService;

    public NguoiDungDAO() {
        apiService = SupabaseClient.layApiService();
    }

    public void dangKy(NguoiDung nguoiDung, ApiCallback<NguoiDung> callback) {
        kiemTraTaiKhoanTonTai(nguoiDung.getTaiKhoan(), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean daTonTai) {
                if (daTonTai) {
                    callback.onError("Tài khoản đã tồn tại");
                    return;
                }

                chuanBiDuLieuDangKy(nguoiDung);
                themNguoiDung(nguoiDung, callback);
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
            }
        });
    }

    public void dangNhap(String taiKhoan, String matKhauMD5, ApiCallback<NguoiDung> callback) {
        Map<String, String> filter = new HashMap<>();
        filter.put("TaiKhoan", "eq." + taiKhoan);
        filter.put("MatKhau", "eq." + matKhauMD5);
        filter.put("select", "*");

        apiService.timNguoiDung(filter).enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể đăng nhập");
                    return;
                }

                List<NguoiDung> danhSachNguoiDung = response.body();
                if (danhSachNguoiDung == null || danhSachNguoiDung.isEmpty()) {
                    callback.onError("Tài khoản hoặc mật khẩu không đúng");
                    return;
                }

                callback.onSuccess(danhSachNguoiDung.get(0));
            }

            @Override
            public void onFailure(Call<List<NguoiDung>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void layTheoTaiKhoan(String taiKhoan, ApiCallback<NguoiDung> callback) {
        Map<String, String> filter = new HashMap<>();
        filter.put("TaiKhoan", "eq." + taiKhoan);
        filter.put("select", "*");

        apiService.timNguoiDung(filter).enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể tải thông tin người dùng");
                    return;
                }

                List<NguoiDung> danhSachNguoiDung = response.body();
                if (danhSachNguoiDung == null || danhSachNguoiDung.isEmpty()) {
                    callback.onError("Không tìm thấy người dùng");
                    return;
                }

                callback.onSuccess(danhSachNguoiDung.get(0));
            }

            @Override
            public void onFailure(Call<List<NguoiDung>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void layDanhSachNguoiDung(ApiCallback<List<NguoiDung>> callback) {
        Map<String, String> filter = new HashMap<>();
        filter.put("select", "*");
        filter.put("order", "MaNguoiDung.desc");

        apiService.timNguoiDung(filter).enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể tải danh sách người dùng");
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<NguoiDung>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void capNhatThongTin(
            int maNguoiDung,
            String hoTen,
            String email,
            String soDienThoai,
            ApiCallback<NguoiDung> callback
    ) {
        Map<String, String> duLieuCapNhat = new HashMap<>();
        duLieuCapNhat.put("HoTen", hoTen);
        duLieuCapNhat.put("Email", email);
        duLieuCapNhat.put("SoDienThoai", soDienThoai);

        apiService.capNhatNguoiDung("eq." + maNguoiDung, duLieuCapNhat).enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                xuLyKetQuaCapNhat(response, callback, "Cập nhật thông tin thất bại");
            }

            @Override
            public void onFailure(Call<List<NguoiDung>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void doiMatKhau(
            int maNguoiDung,
            String matKhauCuMD5,
            String matKhauMoiMD5,
            ApiCallback<NguoiDung> callback
    ) {
        Map<String, String> filter = new HashMap<>();
        filter.put("MaNguoiDung", "eq." + maNguoiDung);
        filter.put("MatKhau", "eq." + matKhauCuMD5);
        filter.put("select", "*");

        apiService.timNguoiDung(filter).enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể kiểm tra mật khẩu cũ");
                    return;
                }

                List<NguoiDung> danhSachNguoiDung = response.body();
                if (danhSachNguoiDung == null || danhSachNguoiDung.isEmpty()) {
                    callback.onError("Mật khẩu cũ không đúng");
                    return;
                }

                capNhatMatKhau(maNguoiDung, matKhauMoiMD5, callback);
            }

            @Override
            public void onFailure(Call<List<NguoiDung>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private void capNhatMatKhau(int maNguoiDung, String matKhauMoiMD5, ApiCallback<NguoiDung> callback) {
        Map<String, String> duLieuCapNhat = new HashMap<>();
        duLieuCapNhat.put("MatKhau", matKhauMoiMD5);

        apiService.capNhatNguoiDung("eq." + maNguoiDung, duLieuCapNhat).enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                xuLyKetQuaCapNhat(response, callback, "Đổi mật khẩu thất bại");
            }

            @Override
            public void onFailure(Call<List<NguoiDung>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void datLaiMatKhau(int maNguoiDung, String matKhauMoiMD5, ApiCallback<NguoiDung> callback) {
        Map<String, String> duLieuCapNhat = new HashMap<>();
        duLieuCapNhat.put("MatKhau", matKhauMoiMD5);

        apiService.capNhatNguoiDung("eq." + maNguoiDung, duLieuCapNhat).enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                xuLyKetQuaCapNhat(response, callback, "Đặt lại mật khẩu thất bại");
            }

            @Override
            public void onFailure(Call<List<NguoiDung>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void capNhatTrangThai(int maNguoiDung, String trangThai, ApiCallback<NguoiDung> callback) {
        Map<String, String> duLieuCapNhat = new HashMap<>();
        duLieuCapNhat.put("TrangThai", trangThai);

        apiService.capNhatNguoiDung("eq." + maNguoiDung, duLieuCapNhat).enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                xuLyKetQuaCapNhat(response, callback, "Cập nhật trạng thái thất bại");
            }

            @Override
            public void onFailure(Call<List<NguoiDung>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }


    private void xuLyKetQuaCapNhat(
            Response<List<NguoiDung>> response,
            ApiCallback<NguoiDung> callback,
            String thongBaoLoi
    ) {
        if (!response.isSuccessful()) {
            callback.onError(thongBaoLoi);
            return;
        }

        List<NguoiDung> danhSachNguoiDung = response.body();
        if (danhSachNguoiDung == null || danhSachNguoiDung.isEmpty()) {
            callback.onError("Không nhận được dữ liệu người dùng");
            return;
        }

        callback.onSuccess(danhSachNguoiDung.get(0));
    }

    private void kiemTraTaiKhoanTonTai(String taiKhoan, ApiCallback<Boolean> callback) {
        apiService.layNguoiDungTheoTaiKhoan("eq." + taiKhoan).enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Không thể kiểm tra tài khoản");
                    return;
                }

                List<NguoiDung> danhSachNguoiDung = response.body();
                callback.onSuccess(danhSachNguoiDung != null && !danhSachNguoiDung.isEmpty());
            }

            @Override
            public void onFailure(Call<List<NguoiDung>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private void themNguoiDung(NguoiDung nguoiDung, ApiCallback<NguoiDung> callback) {
        apiService.themNguoiDung(taoDuLieuDangKy(nguoiDung)).enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                if (!response.isSuccessful()) {
                    callback.onError("Đăng ký thất bại");
                    return;
                }

                List<NguoiDung> danhSachNguoiDung = response.body();
                if (danhSachNguoiDung == null || danhSachNguoiDung.isEmpty()) {
                    callback.onError("Không nhận được dữ liệu người dùng");
                    return;
                }

                callback.onSuccess(danhSachNguoiDung.get(0));
            }

            @Override
            public void onFailure(Call<List<NguoiDung>> call, Throwable t) {
                callback.onError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private Map<String, Object> taoDuLieuDangKy(NguoiDung nguoiDung) {
        Map<String, Object> duLieu = new HashMap<>();
        duLieu.put("TaiKhoan", nguoiDung.getTaiKhoan());
        duLieu.put("MatKhau", nguoiDung.getMatKhau());
        duLieu.put("HoTen", nguoiDung.getHoTen());
        duLieu.put("Email", nguoiDung.getEmail());
        duLieu.put("SoDienThoai", nguoiDung.getSoDienThoai());
        duLieu.put("VaiTro", nguoiDung.getVaiTro());
        duLieu.put("NgayDangKy", nguoiDung.getNgayDangKy());
        duLieu.put("TrangThai", nguoiDung.getTrangThai());
        return duLieu;
    }

    private void chuanBiDuLieuDangKy(NguoiDung nguoiDung) {
        nguoiDung.setMatKhau(MaHoa.maHoaMD5(nguoiDung.getMatKhau()));
        nguoiDung.setVaiTro("NguoiDung");
        nguoiDung.setTrangThai("HoatDong");
        nguoiDung.setNgayDangKy(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
    }
}
