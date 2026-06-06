package com.example.banve.models;

import com.google.gson.annotations.SerializedName;

public class LichSuChat {
    @SerializedName("MaLichSuChat")
    private int maLichSuChat;

    @SerializedName("MaNguoiDung")
    private int maNguoiDung;

    @SerializedName("CauHoi")
    private String cauHoi;

    @SerializedName("TraLoi")
    private String traLoi;

    @SerializedName("NgayTao")
    private String ngayTao;

    public int getMaLichSuChat() {
        return maLichSuChat;
    }

    public void setMaLichSuChat(int maLichSuChat) {
        this.maLichSuChat = maLichSuChat;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getCauHoi() {
        return cauHoi;
    }

    public void setCauHoi(String cauHoi) {
        this.cauHoi = cauHoi;
    }

    public String getTraLoi() {
        return traLoi;
    }

    public void setTraLoi(String traLoi) {
        this.traLoi = traLoi;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }
}
