package com.example.banve.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MaHoa {
    private MaHoa() {
    }

    public static String maHoaMD5(String chuoi) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = messageDigest.digest(chuoi.getBytes());
            StringBuilder ketQua = new StringBuilder();

            for (byte b : bytes) {
                ketQua.append(String.format("%02x", b & 0xff));
            }

            return ketQua.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Không thể mã hóa MD5.", e);
        }
    }
}
