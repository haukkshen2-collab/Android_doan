package com.example.banve.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banve.R;
import com.example.banve.adapters.LoaiVeAdapter;
import com.example.banve.adapters.VeAdapter;
import com.example.banve.controllers.LoaiVeController;
import com.example.banve.controllers.VeController;
import com.example.banve.models.LoaiVe;
import com.example.banve.models.Ve;
import com.example.banve.network.ApiCallback;
import com.example.banve.utils.Session;
import com.example.banve.utils.TienIch;

import java.util.List;

public class DashboardNguoiDungActivity extends AppCompatActivity {
    private RecyclerView rcvLoaiVe;
    private RecyclerView rcvDanhSachVe;
    private TextView lblXinChao;
    private Button btnHoSo;
    private Button btnDangXuat;
    private LoaiVeAdapter loaiVeAdapter;
    private VeAdapter veAdapter;
    private LoaiVeController loaiVeController;
    private VeController veController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session.khoiPhuc(this);
        setContentView(R.layout.user_activity_dashboard_nguoi_dung);

        anhXa();
        khoiTaoRecyclerView();
        loaiVeController = new LoaiVeController();
        veController = new VeController();
        batSuKien();
        taiLoaiVe();
        taiDanhSachVe(null);
    }

    private void anhXa() {
        rcvLoaiVe = findViewById(R.id.rcvLoaiVe);
        rcvDanhSachVe = findViewById(R.id.rcvDanhSachVe);
        lblXinChao = findViewById(R.id.lblXinChao);
        btnHoSo = findViewById(R.id.btnHoSo);
        btnDangXuat = findViewById(R.id.btnDangXuat);

        if (Session.nguoiDungHienTai != null) {
            lblXinChao.setText("Xin chào, " + Session.nguoiDungHienTai.getHoTen());
        }
    }

    private void khoiTaoRecyclerView() {
        loaiVeAdapter = new LoaiVeAdapter(this::taiDanhSachVe);
        rcvLoaiVe.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcvLoaiVe.setAdapter(loaiVeAdapter);

        veAdapter = new VeAdapter(maVe -> {
            Intent intent = new Intent(this, ThongTinVeActivity.class);
            intent.putExtra("maVe", maVe);
            startActivity(intent);
        });
        rcvDanhSachVe.setLayoutManager(new LinearLayoutManager(this));
        rcvDanhSachVe.setAdapter(veAdapter);
    }

    private void batSuKien() {
        btnHoSo.setOnClickListener(v -> startActivity(new Intent(this, ThongTinNguoiDungActivity.class)));
        btnDangXuat.setOnClickListener(v -> TienIch.dangXuat(this));
        findViewById(R.id.btnGioHang).setOnClickListener(v -> startActivity(new Intent(this, GioHangActivity.class)));
        findViewById(R.id.btnLichSu).setOnClickListener(v -> startActivity(new Intent(this, LichSuDonHangActivity.class)));
        findViewById(R.id.btnChatAI).setOnClickListener(v -> startActivity(new Intent(this, ChatAIActivity.class)));
    }

    private void taiLoaiVe() {
        loaiVeController.layDanhSachLoaiVe(new ApiCallback<List<LoaiVe>>() {
            @Override
            public void onSuccess(List<LoaiVe> data) {
                loaiVeAdapter.capNhatDuLieu(data);
            }

            @Override
            public void onError(String thongBao) {
                TienIch.hienAlert(DashboardNguoiDungActivity.this, "Lỗi tải loại vé", thongBao);
            }
        });
    }

    private void taiDanhSachVe(Integer maLoaiVe) {
        veController.layDanhSachVe(maLoaiVe, new ApiCallback<List<Ve>>() {
            @Override
            public void onSuccess(List<Ve> data) {
                veAdapter.capNhatDuLieu(data);
            }

            @Override
            public void onError(String thongBao) {
                TienIch.hienAlert(DashboardNguoiDungActivity.this, "Lỗi tải vé", thongBao);
            }
        });
    }
}
