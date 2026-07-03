package com.example.banve.activities.user;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.banve.R;
import com.example.banve.dao.HoaDonDAO;
import com.example.banve.dao.ThanhToanTamDAO;
import com.example.banve.models.HoaDon;
import com.example.banve.models.ThanhToanTam;
import com.example.banve.network.ApiCallback;
import com.example.banve.utils.DinhDangTien;
import com.example.banve.utils.SePayUtil;
import com.example.banve.utils.TienIch;

public class ThanhToanSePayActivity extends AppCompatActivity {
    private ImageView imgQrSePay;
    private TextView lblMaHoaDon;
    private TextView lblSoTien;
    private TextView lblNoiDungChuyenKhoan;
    private TextView lblTrangThaiThanhToan;
    private Button btnKiemTraThanhToan;
    private Button btnQuayLai;
    private HoaDonDAO hoaDonDAO;
    private ThanhToanTamDAO thanhToanTamDAO;
    private Handler handler;
    private Runnable pollingThanhToanRunnable;
    private int maHoaDon;
    private String noiDungChuyenKhoan;
    private double soTienCanTra;
    private boolean dangPollingThanhToan;
    private boolean daThanhToan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_thanh_toan_sepay);

        hoaDonDAO = new HoaDonDAO();
        thanhToanTamDAO = new ThanhToanTamDAO();
        handler = new Handler(Looper.getMainLooper());
        anhXa();
        layDuLieuIntent();
        hienThiThongTinQr();
        batSuKien();
        batDauPollingThanhToan();
    }

    @Override
    protected void onDestroy() {
        dungPollingThanhToan();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        xacNhanHuyThanhToan();
    }

    private void anhXa() {
        imgQrSePay = findViewById(R.id.imgQrSePay);
        lblMaHoaDon = findViewById(R.id.lblMaHoaDon);
        lblSoTien = findViewById(R.id.lblSoTien);
        lblNoiDungChuyenKhoan = findViewById(R.id.lblNoiDungChuyenKhoan);
        lblTrangThaiThanhToan = findViewById(R.id.lblTrangThaiThanhToan);
        btnKiemTraThanhToan = findViewById(R.id.btnKiemTraThanhToan);
        btnQuayLai = findViewById(R.id.btnQuayLai);
    }

    private void layDuLieuIntent() {
        maHoaDon = getIntent().getIntExtra("maHoaDon", 0);
        double tongTien = getIntent().getDoubleExtra("tongTien", 0);
        double tienGiam = getIntent().getDoubleExtra("tienGiam", 0);
        soTienCanTra = Math.max(0, tongTien - tienGiam);
        noiDungChuyenKhoan = getIntent().getStringExtra("noiDungChuyenKhoan");

        if (noiDungChuyenKhoan == null || noiDungChuyenKhoan.trim().isEmpty()) {
            noiDungChuyenKhoan = SePayUtil.taoNoiDungChuyenKhoan(maHoaDon);
        }
    }

    private void hienThiThongTinQr() {
        if (maHoaDon <= 0) {
            TienIch.hienAlert(this, "Lỗi thanh toán", "Không nhận được mã hóa đơn");
            return;
        }

        String linkQr = SePayUtil.taoLinkQr(soTienCanTra, noiDungChuyenKhoan);
        lblMaHoaDon.setText("Mã thanh toán: " + maHoaDon);
        lblSoTien.setText("Số tiền: " + DinhDangTien.dinhDang(soTienCanTra));
        lblNoiDungChuyenKhoan.setText("Nội dung chuyển khoản: " + noiDungChuyenKhoan);
        lblTrangThaiThanhToan.setText("Đang chờ thanh toán. Hóa đơn thật chỉ được tạo sau khi SePay xác nhận đã nhận tiền.");

        Glide.with(this)
                .load(linkQr)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.bg_card_glass)
                .error(R.drawable.bg_badge_warning)
                .into(imgQrSePay);
    }

    private void batSuKien() {
        btnKiemTraThanhToan.setOnClickListener(v -> kiemTraTrangThaiThanhToan(true));
        btnQuayLai.setOnClickListener(v -> xacNhanHuyThanhToan());
    }

    private void batDauPollingThanhToan() {
        dungPollingThanhToan();
        dangPollingThanhToan = true;
        pollingThanhToanRunnable = new Runnable() {
            @Override
            public void run() {
                kiemTraTrangThaiThanhToan(false);
                if (dangPollingThanhToan) {
                    handler.postDelayed(this, 5000);
                }
            }
        };
        handler.postDelayed(pollingThanhToanRunnable, 5000);
    }

    private void dungPollingThanhToan() {
        dangPollingThanhToan = false;
        if (handler != null && pollingThanhToanRunnable != null) {
            handler.removeCallbacks(pollingThanhToanRunnable);
            pollingThanhToanRunnable = null;
        }
    }

    private void kiemTraTrangThaiThanhToan(boolean hienThongBaoCho) {
        if (maHoaDon <= 0) {
            return;
        }

        hoaDonDAO.layTheoMa(maHoaDon, new ApiCallback<HoaDon>() {
            @Override
            public void onSuccess(HoaDon data) {
                if ("DaThanhToan".equals(data.getTrangThai())) {
                    daThanhToan = true;
                    dungPollingThanhToan();
                    lblTrangThaiThanhToan.setText("Đã thanh toán thành công.");
                    moThanhToanThanhCong(data);
                    return;
                }

                kiemTraThanhToanTam(hienThongBaoCho);
            }

            @Override
            public void onError(String thongBao) {
                kiemTraThanhToanTam(hienThongBaoCho);
            }
        });
    }

    private void kiemTraThanhToanTam(boolean hienThongBaoCho) {
        thanhToanTamDAO.layTheoMa(maHoaDon, new ApiCallback<ThanhToanTam>() {
            @Override
            public void onSuccess(ThanhToanTam data) {
                if ("LoiThanhToan".equals(data.getTrangThai())) {
                    dungPollingThanhToan();
                    String thongBao = data.getThongBaoLoi() == null ? "Thanh toán SePay gặp lỗi, vui lòng liên hệ quản lý." : data.getThongBaoLoi();
                    lblTrangThaiThanhToan.setText(thongBao);
                    TienIch.hienAlert(ThanhToanSePayActivity.this, "Lỗi thanh toán", thongBao);
                    return;
                }

                lblTrangThaiThanhToan.setText("Chưa nhận được thanh toán. Ứng dụng sẽ tiếp tục kiểm tra.");
                if (hienThongBaoCho) {
                    Toast.makeText(ThanhToanSePayActivity.this, "Chưa nhận được thanh toán", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String thongBao) {
                lblTrangThaiThanhToan.setText("Thanh toán đã bị hủy hoặc không còn tồn tại.");
                if (hienThongBaoCho) {
                    TienIch.hienAlert(ThanhToanSePayActivity.this, "Thông báo", "Thanh toán này không còn tồn tại.");
                }
            }
        });
    }

    private void xacNhanHuyThanhToan() {
        if (daThanhToan) {
            finish();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Hủy thanh toán?")
                .setMessage("Nếu bạn chưa chuyển khoản, hệ thống sẽ xóa thanh toán tạm và giữ nguyên giỏ hàng.")
                .setPositiveButton("Hủy thanh toán", (dialog, which) -> huyThanhToanTam())
                .setNegativeButton("Tiếp tục chờ", null)
                .show();
    }

    private void huyThanhToanTam() {
        dungPollingThanhToan();
        thanhToanTamDAO.xoaTheoMa(maHoaDon, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                Toast.makeText(ThanhToanSePayActivity.this, "Đã hủy thanh toán SePay", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(String thongBao) {
                TienIch.hienAlert(ThanhToanSePayActivity.this, "Lỗi hủy thanh toán", thongBao);
            }
        });
    }

    private void moThanhToanThanhCong(HoaDon hoaDon) {
        Intent intent = new Intent(this, ThanhToanThanhCongActivity.class);
        intent.putExtra("maHoaDon", hoaDon.getMaHoaDon());
        intent.putExtra("noiDungChuyenKhoan", hoaDon.getNoiDungChuyenKhoan());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
