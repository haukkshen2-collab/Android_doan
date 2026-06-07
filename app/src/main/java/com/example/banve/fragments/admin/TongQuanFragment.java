package com.example.banve.fragments.admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banve.R;
import com.example.banve.adapters.ThongKeLoaiVeAdapter;
import com.example.banve.adapters.ThongKeNgayAdapter;
import com.example.banve.adapters.ThongKeThangAdapter;
import com.example.banve.controllers.ThongKeController;
import com.example.banve.models.KetQuaThongKe;
import com.example.banve.models.ThongKeTongQuan;
import com.example.banve.network.ApiCallback;
import com.example.banve.utils.DinhDangTien;

import java.util.Calendar;
import java.util.Date;

public class TongQuanFragment extends Fragment {
    private DatePicker dtpTuNgay;
    private DatePicker dtpDenNgay;
    private Button btnLoc;
    private ProgressBar pgbDangTai;
    private TextView lblTongHoaDon;
    private TextView lblTongDoanhThu;
    private TextView lblTongTienGiam;
    private TextView lblTongThanhTien;
    private TextView lblTongVeBan;
    private RecyclerView rcvThongKeLoaiVe;
    private RecyclerView rcvDoanhThuNgay;
    private RecyclerView rcvDoanhThuThang;
    private ThongKeLoaiVeAdapter thongKeLoaiVeAdapter;
    private ThongKeNgayAdapter thongKeNgayAdapter;
    private ThongKeThangAdapter thongKeThangAdapter;
    private ThongKeController thongKeController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_thong_ke, container, false);
        anhXa(view);
        thongKeController = new ThongKeController();
        khoiTaoNgayMacDinh();
        khoiTaoRecyclerView();
        batSuKien();
        taiThongKe();
        return view;
    }

    private void anhXa(View view) {
        dtpTuNgay = view.findViewById(R.id.dtpTuNgay);
        dtpDenNgay = view.findViewById(R.id.dtpDenNgay);
        btnLoc = view.findViewById(R.id.btnLoc);
        pgbDangTai = view.findViewById(R.id.pgbDangTai);
        lblTongHoaDon = view.findViewById(R.id.lblTongHoaDon);
        lblTongDoanhThu = view.findViewById(R.id.lblTongDoanhThu);
        lblTongTienGiam = view.findViewById(R.id.lblTongTienGiam);
        lblTongThanhTien = view.findViewById(R.id.lblTongThanhTien);
        lblTongVeBan = view.findViewById(R.id.lblTongVeBan);
        rcvThongKeLoaiVe = view.findViewById(R.id.rcvThongKeLoaiVe);
        rcvDoanhThuNgay = view.findViewById(R.id.rcvDoanhThuNgay);
        rcvDoanhThuThang = view.findViewById(R.id.rcvDoanhThuThang);
    }

    private void khoiTaoNgayMacDinh() {
        Calendar tuNgay = Calendar.getInstance();
        tuNgay.add(Calendar.DAY_OF_MONTH, -30);
        dtpTuNgay.updateDate(tuNgay.get(Calendar.YEAR), tuNgay.get(Calendar.MONTH), tuNgay.get(Calendar.DAY_OF_MONTH));

        Calendar denNgay = Calendar.getInstance();
        dtpDenNgay.updateDate(denNgay.get(Calendar.YEAR), denNgay.get(Calendar.MONTH), denNgay.get(Calendar.DAY_OF_MONTH));
    }

    private void khoiTaoRecyclerView() {
        thongKeLoaiVeAdapter = new ThongKeLoaiVeAdapter();
        thongKeNgayAdapter = new ThongKeNgayAdapter();
        thongKeThangAdapter = new ThongKeThangAdapter();

        rcvThongKeLoaiVe.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvThongKeLoaiVe.setAdapter(thongKeLoaiVeAdapter);
        rcvDoanhThuNgay.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvDoanhThuNgay.setAdapter(thongKeNgayAdapter);
        rcvDoanhThuThang.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvDoanhThuThang.setAdapter(thongKeThangAdapter);
    }

    private void batSuKien() {
        btnLoc.setOnClickListener(v -> taiThongKe());
    }

    private void taiThongKe() {
        Date tuNgay = layNgayTuDatePicker(dtpTuNgay);
        Date denNgay = layNgayTuDatePicker(dtpDenNgay);

        if (tuNgay.after(denNgay)) {
            baoLoi("Lỗi thống kê", "Từ ngày không được lớn hơn đến ngày");
            return;
        }

        hienDangTai(true);
        thongKeController.layThongKeTongQuan(tuNgay, denNgay, new ApiCallback<KetQuaThongKe>() {
            @Override
            public void onSuccess(KetQuaThongKe data) {
                hienDangTai(false);
                hienThongKe(data);
            }

            @Override
            public void onError(String thongBao) {
                hienDangTai(false);
                baoLoi("Lỗi thống kê", thongBao);
            }
        });
    }

    private Date layNgayTuDatePicker(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private void hienThongKe(KetQuaThongKe ketQua) {
        if (ketQua == null || ketQua.getThongKeTongQuan() == null) {
            return;
        }

        ThongKeTongQuan tongQuan = ketQua.getThongKeTongQuan();
        lblTongHoaDon.setText("Tổng hóa đơn: " + tongQuan.getTongHoaDon());
        lblTongDoanhThu.setText("Tổng doanh thu: " + DinhDangTien.dinhDang(tongQuan.getTongDoanhThu()));
        lblTongTienGiam.setText("Tổng tiền giảm: " + DinhDangTien.dinhDang(tongQuan.getTongTienGiam()));
        lblTongThanhTien.setText("Tổng thành tiền: " + DinhDangTien.dinhDang(tongQuan.getTongThanhTien()));
        lblTongVeBan.setText("Tổng vé bán: " + tongQuan.getTongVeBan());

        thongKeLoaiVeAdapter.capNhatDuLieu(ketQua.getDanhSachTheoLoaiVe());
        thongKeNgayAdapter.capNhatDuLieu(ketQua.getDanhSachTheoNgay());
        thongKeThangAdapter.capNhatDuLieu(ketQua.getDanhSachTheoThang());
    }

    private void hienDangTai(boolean dangTai) {
        pgbDangTai.setVisibility(dangTai ? View.VISIBLE : View.GONE);
        btnLoc.setEnabled(!dangTai);
    }

    private void baoLoi(String tieuDe, String noiDung) {
        if (getContext() == null) {
            return;
        }

        new AlertDialog.Builder(requireContext())
                .setTitle(tieuDe)
                .setMessage(noiDung)
                .setPositiveButton("Đồng ý", null)
                .show();
    }
}
