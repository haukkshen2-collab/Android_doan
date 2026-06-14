package com.example.banve.controllers;

import android.util.Patterns;

import com.example.banve.dao.NguoiDungDAO;
import com.example.banve.models.NguoiDung;
import com.example.banve.network.ApiCallback;
import com.example.banve.utils.MaHoa;

import java.util.List;

public class NguoiDungController {
    private final NguoiDungDAO nguoiDungDAO;

    public NguoiDungController() {
        nguoiDungDAO = new NguoiDungDAO();
    }

    public void dangKy(
            String taiKhoan,
            String matKhau,
            String nhapLaiMatKhau,
            String hoTen,
            String email,
            String soDienThoai,
            ApiCallback<NguoiDung> callback
    ) {
        String loi = kiemTraDangKy(taiKhoan, matKhau, nhapLaiMatKhau, hoTen, email, soDienThoai);
        if (loi != null) {
            callback.onError(loi);
            return;
        }

        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setTaiKhoan(taiKhoan.trim());
        nguoiDung.setMatKhau(matKhau);
        nguoiDung.setHoTen(hoTen.trim());
        nguoiDung.setEmail(email.trim());
        nguoiDung.setSoDienThoai(soDienThoai.trim());

        nguoiDungDAO.dangKy(nguoiDung, callback);
    }

    public void kiemTraDangNhap(String taiKhoan, String matKhau, ApiCallback<NguoiDung> callback) {
        if (rong(taiKhoan)) {
            callback.onError("Tài khoản không được để trống");
            return;
        }
        if (rong(matKhau)) {
            callback.onError("Mật khẩu không được để trống");
            return;
        }

        String matKhauMD5 = MaHoa.maHoaMD5(matKhau);
        nguoiDungDAO.dangNhap(taiKhoan.trim(), matKhauMD5, new ApiCallback<NguoiDung>() {
            @Override
            public void onSuccess(NguoiDung data) {
                if (!"HoatDong".equals(data.getTrangThai())) {
                    callback.onError("Tài khoản đã bị khóa");
                    return;
                }

                callback.onSuccess(data);
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
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
        String loi = kiemTraThongTinCaNhan(hoTen, email, soDienThoai);
        if (loi != null) {
            callback.onError(loi);
            return;
        }

        nguoiDungDAO.capNhatThongTin(
                maNguoiDung,
                hoTen.trim(),
                email.trim(),
                soDienThoai.trim(),
                callback
        );
    }

    public void layTheoTaiKhoan(String taiKhoan, ApiCallback<NguoiDung> callback) {
        if (rong(taiKhoan)) {
            callback.onError("Tài khoản không hợp lệ");
            return;
        }

        nguoiDungDAO.layTheoTaiKhoan(taiKhoan.trim(), callback);
    }

    public void doiMatKhau(
            int maNguoiDung,
            String matKhauCu,
            String matKhauMoi,
            String nhapLaiMatKhauMoi,
            ApiCallback<NguoiDung> callback
    ) {
        String loi = kiemTraDoiMatKhau(matKhauCu, matKhauMoi, nhapLaiMatKhauMoi);
        if (loi != null) {
            callback.onError(loi);
            return;
        }

        nguoiDungDAO.doiMatKhau(
                maNguoiDung,
                MaHoa.maHoaMD5(matKhauCu),
                MaHoa.maHoaMD5(matKhauMoi),
                callback
        );
    }

    public void layDanhSachNguoiDung(ApiCallback<List<NguoiDung>> callback) {
        nguoiDungDAO.layDanhSachNguoiDung(callback);
    }

    public void datLaiMatKhau(
            int maNguoiDung,
            String matKhauMoi,
            String nhapLaiMatKhau,
            ApiCallback<NguoiDung> callback
    ) {
        if (maNguoiDung <= 0) {
            callback.onError("Người dùng không hợp lệ");
            return;
        }
        if (rong(matKhauMoi)) {
            callback.onError("Mật khẩu mới không được để trống");
            return;
        }
        if (matKhauMoi.length() < 6) {
            callback.onError("Mật khẩu mới phải có ít nhất 6 ký tự");
            return;
        }
        if (!matKhauMoi.equals(nhapLaiMatKhau)) {
            callback.onError("Hai mật khẩu không trùng nhau");
            return;
        }

        nguoiDungDAO.datLaiMatKhau(maNguoiDung, MaHoa.maHoaMD5(matKhauMoi), callback);
    }

    public void datLaiMatKhau(
            int maNguoiDung,
            String matKhauMoi,
            ApiCallback<NguoiDung> callback
    ) {
        if (maNguoiDung <= 0) {
            callback.onError("Người dùng không hợp lệ");
            return;
        }
        if (rong(matKhauMoi)) {
            callback.onError("Mật khẩu mới không được để trống");
            return;
        }
        if (matKhauMoi.length() < 6) {
            callback.onError("Mật khẩu mới phải có ít nhất 6 ký tự");
            return;
        }

        nguoiDungDAO.datLaiMatKhau(maNguoiDung, MaHoa.maHoaMD5(matKhauMoi), callback);
    }

    public void capNhatTrangThai(int maNguoiDung, String trangThai, ApiCallback<NguoiDung> callback) {
        if (maNguoiDung <= 0) {
            callback.onError("Người dùng không hợp lệ");
            return;
        }
        if (!"HoatDong".equals(trangThai) && !"Khoa".equals(trangThai)) {
            callback.onError("Trạng thái không hợp lệ");
            return;
        }

        nguoiDungDAO.capNhatTrangThai(maNguoiDung, trangThai, callback);
    }

    private String kiemTraThongTinCaNhan(String hoTen, String email, String soDienThoai) {
        if (rong(hoTen)) {
            return "Họ tên không được để trống";
        }
        if (rong(email) || !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            return "Email không hợp lệ";
        }
        if (rong(soDienThoai) || !soDienThoai.trim().matches("\\d{10}")) {
            return "Số điện thoại phải gồm 10 số";
        }
        return null;
    }

    private String kiemTraDoiMatKhau(String matKhauCu, String matKhauMoi, String nhapLaiMatKhauMoi) {
        if (rong(matKhauCu)) {
            return "Mật khẩu cũ không được để trống";
        }
        if (rong(matKhauMoi)) {
            return "Mật khẩu mới không được để trống";
        }
        if (matKhauMoi.length() < 6) {
            return "Mật khẩu mới phải có ít nhất 6 ký tự";
        }
        if (!matKhauMoi.equals(nhapLaiMatKhauMoi)) {
            return "Hai mật khẩu mới không trùng nhau";
        }
        return null;
    }

    private String kiemTraDangKy(
            String taiKhoan,
            String matKhau,
            String nhapLaiMatKhau,
            String hoTen,
            String email,
            String soDienThoai
    ) {
        if (rong(taiKhoan)) {
            return "Tài khoản không được để trống";
        }
        if (rong(matKhau)) {
            return "Mật khẩu không được để trống";
        }
        if (matKhau.length() < 6) {
            return "Mật khẩu phải có ít nhất 6 ký tự";
        }
        if (!matKhau.equals(nhapLaiMatKhau)) {
            return "Hai mật khẩu không trùng nhau";
        }
        if (rong(hoTen)) {
            return "Họ tên không được để trống";
        }
        if (rong(email) || !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            return "Email không hợp lệ";
        }
        if (rong(soDienThoai) || !soDienThoai.trim().matches("\\d{10}")) {
            return "Số điện thoại phải gồm 10 số";
        }
        return null;
    }

    private boolean rong(String giaTri) {
        return giaTri == null || giaTri.trim().isEmpty();
    }
}
