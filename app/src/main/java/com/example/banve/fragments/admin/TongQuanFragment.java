package com.example.banve.fragments.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.banve.R;
import com.example.banve.models.HoaDon;
import com.example.banve.models.NguoiDung;
import com.example.banve.models.Ve;
import com.example.banve.models.Voucher;
import com.example.banve.network.ApiService;
import com.example.banve.network.SupabaseClient;
import com.example.banve.utils.DinhDangTien;
import com.example.banve.utils.TienIch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TongQuanFragment extends Fragment {
    private TextView lblNguoiDung;
    private TextView lblVe;
    private TextView lblVoucher;
    private TextView lblHoaDon;
    private TextView lblDoanhThu;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_tong_quan, container, false);
        anhXa(view);
        apiService = SupabaseClient.layApiService();
        taiThongKe();
        return view;
    }

    private void anhXa(View view) {
        lblNguoiDung = view.findViewById(R.id.lblNguoiDung);
        lblVe = view.findViewById(R.id.lblVe);
        lblVoucher = view.findViewById(R.id.lblVoucher);
        lblHoaDon = view.findViewById(R.id.lblHoaDon);
        lblDoanhThu = view.findViewById(R.id.lblDoanhThu);
    }

    private void taiThongKe() {
        taiSoNguoiDung();
        taiSoVe();
        taiSoVoucher();
        taiHoaDonVaDoanhThu();
    }

    private void taiSoNguoiDung() {
        Map<String, String> filter = new HashMap<>();
        filter.put("select", "MaNguoiDung");

        apiService.timNguoiDung(filter).enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                if (!kiemTraResponse(response, "người dùng")) {
                    return;
                }

                List<NguoiDung> data = response.body();
                lblNguoiDung.setText("Người dùng: " + dem(data));
            }

            @Override
            public void onFailure(Call<List<NguoiDung>> call, Throwable t) {
                baoLoi("Không thể tải số người dùng: " + t.getMessage());
            }
        });
    }

    private void taiSoVe() {
        Map<String, String> filter = new HashMap<>();
        filter.put("select", "MaVe,SoLuong");

        apiService.layDanhSachVe(filter).enqueue(new Callback<List<Ve>>() {
            @Override
            public void onResponse(Call<List<Ve>> call, Response<List<Ve>> response) {
                if (!kiemTraResponse(response, "vé")) {
                    return;
                }

                List<Ve> data = response.body();
                int tongTonKho = 0;
                if (data != null) {
                    for (Ve ve : data) {
                        tongTonKho += ve.getSoLuong();
                    }
                }
                lblVe.setText("Vé: " + dem(data) + " loại | Tồn kho: " + tongTonKho);
            }

            @Override
            public void onFailure(Call<List<Ve>> call, Throwable t) {
                baoLoi("Không thể tải số vé: " + t.getMessage());
            }
        });
    }

    private void taiSoVoucher() {
        Map<String, String> filter = new HashMap<>();
        filter.put("select", "MaVoucher");

        apiService.timVoucher(filter).enqueue(new Callback<List<Voucher>>() {
            @Override
            public void onResponse(Call<List<Voucher>> call, Response<List<Voucher>> response) {
                if (!kiemTraResponse(response, "voucher")) {
                    return;
                }

                lblVoucher.setText("Voucher: " + dem(response.body()));
            }

            @Override
            public void onFailure(Call<List<Voucher>> call, Throwable t) {
                baoLoi("Không thể tải số voucher: " + t.getMessage());
            }
        });
    }

    private void taiHoaDonVaDoanhThu() {
        Map<String, String> filter = new HashMap<>();
        filter.put("TrangThai", "eq.DaThanhToan");
        filter.put("select", "MaHoaDon,TongTien,TienGiam");

        apiService.timHoaDon(filter).enqueue(new Callback<List<HoaDon>>() {
            @Override
            public void onResponse(Call<List<HoaDon>> call, Response<List<HoaDon>> response) {
                if (!kiemTraResponse(response, "hóa đơn")) {
                    return;
                }

                List<HoaDon> data = response.body();
                double doanhThu = 0;
                if (data != null) {
                    for (HoaDon hoaDon : data) {
                        doanhThu += Math.max(0, hoaDon.getTongTien() - hoaDon.getTienGiam());
                    }
                }

                lblHoaDon.setText("Hóa đơn đã thanh toán: " + dem(data));
                lblDoanhThu.setText("Doanh thu: " + DinhDangTien.dinhDang(doanhThu));
            }

            @Override
            public void onFailure(Call<List<HoaDon>> call, Throwable t) {
                baoLoi("Không thể tải hóa đơn: " + t.getMessage());
            }
        });
    }

    private int dem(List<?> data) {
        return data == null ? 0 : data.size();
    }

    private boolean kiemTraResponse(Response<?> response, String tenDuLieu) {
        if (!response.isSuccessful()) {
            baoLoi("Không thể tải dữ liệu " + tenDuLieu);
            return false;
        }
        return true;
    }

    private void baoLoi(String thongBao) {
        if (getContext() != null) {
            TienIch.hienToast(getContext(), thongBao);
        }
    }
}

