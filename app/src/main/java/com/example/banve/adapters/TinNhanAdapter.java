package com.example.banve.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banve.R;
import com.example.banve.models.LichSuChat;

import java.util.ArrayList;
import java.util.List;

public class TinNhanAdapter extends RecyclerView.Adapter<TinNhanAdapter.TinNhanViewHolder> {
    private static final int LOAI_USER = 1;
    private static final int LOAI_AI = 2;
    private final List<TinNhan> danhSachTinNhan = new ArrayList<>();

    public void capNhatTuLichSu(List<LichSuChat> danhSachLichSu) {
        danhSachTinNhan.clear();
        if (danhSachLichSu != null) {
            for (LichSuChat lichSuChat : danhSachLichSu) {
                if (!rong(lichSuChat.getCauHoi())) {
                    danhSachTinNhan.add(new TinNhan(lichSuChat.getCauHoi(), LOAI_USER));
                }
                if (!rong(lichSuChat.getTraLoi())) {
                    danhSachTinNhan.add(new TinNhan(lichSuChat.getTraLoi(), LOAI_AI));
                }
            }
        }
        notifyDataSetChanged();
    }

    public void themTinNhanUser(String noiDung) {
        themTinNhan(noiDung, LOAI_USER);
    }

    public void themTinNhanAI(String noiDung) {
        themTinNhan(noiDung, LOAI_AI);
    }

    private void themTinNhan(String noiDung, int loaiTinNhan) {
        danhSachTinNhan.add(new TinNhan(noiDung, loaiTinNhan));
        notifyItemInserted(danhSachTinNhan.size() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        return danhSachTinNhan.get(position).loaiTinNhan;
    }

    @NonNull
    @Override
    public TinNhanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = viewType == LOAI_USER ? R.layout.user_item_tin_nhan_user : R.layout.user_item_tin_nhan_ai;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new TinNhanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TinNhanViewHolder holder, int position) {
        holder.lblNoiDungTinNhan.setText(danhSachTinNhan.get(position).noiDung);
    }

    @Override
    public int getItemCount() {
        return danhSachTinNhan.size();
    }

    private boolean rong(String chuoi) {
        return chuoi == null || chuoi.trim().isEmpty();
    }

    static class TinNhanViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblNoiDungTinNhan;

        public TinNhanViewHolder(@NonNull View itemView) {
            super(itemView);
            lblNoiDungTinNhan = itemView.findViewById(R.id.lblNoiDungTinNhan);
        }
    }

    static class TinNhan {
        private final String noiDung;
        private final int loaiTinNhan;

        public TinNhan(String noiDung, int loaiTinNhan) {
            this.noiDung = noiDung;
            this.loaiTinNhan = loaiTinNhan;
        }
    }
}

