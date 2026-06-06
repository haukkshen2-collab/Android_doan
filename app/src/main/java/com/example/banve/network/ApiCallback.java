package com.example.banve.network;

public interface ApiCallback<T> {
    void onSuccess(T data);

    void onError(String thongBao);
}
