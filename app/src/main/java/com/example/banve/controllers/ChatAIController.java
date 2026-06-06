package com.example.banve.controllers;

import android.os.Handler;
import android.os.Looper;

import com.example.banve.dao.CauHinhAIDAO;
import com.example.banve.dao.LichSuChatDAO;
import com.example.banve.models.CauHinhAI;
import com.example.banve.models.LichSuChat;
import com.example.banve.network.ApiCallback;
import com.example.banve.utils.Session;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class ChatAIController {
    private final CauHinhAIDAO cauHinhAIDAO;
    private final LichSuChatDAO lichSuChatDAO;
    private final Handler mainHandler;

    public ChatAIController() {
        cauHinhAIDAO = new CauHinhAIDAO();
        lichSuChatDAO = new LichSuChatDAO();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public void guiCauHoi(String cauHoi, ApiCallback<String> callback) {
        if (cauHoi == null || cauHoi.trim().isEmpty()) {
            callback.onError("Vui lòng nhập câu hỏi");
            return;
        }

        if (!Session.dangDangNhap()) {
            callback.onError("Phiên đăng nhập không hợp lệ, vui lòng đăng nhập lại");
            return;
        }

        cauHinhAIDAO.layCauHinhHienTai(new ApiCallback<CauHinhAI>() {
            @Override
            public void onSuccess(CauHinhAI cauHinhAI) {
                goiApiAI(cauHinhAI, cauHoi.trim(), callback);
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
            }
        });
    }

    private void goiApiAI(CauHinhAI cauHinhAI, String cauHoi, ApiCallback<String> callback) {
        String loi = kiemTraCauHinh(cauHinhAI);
        if (loi != null) {
            callback.onError(loi);
            return;
        }

        new Thread(() -> {
            try {
                String traLoi = guiYeuCauHttp(cauHinhAI, cauHoi);
                mainHandler.post(() -> luuLichSu(cauHoi, traLoi, callback));
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError("Lỗi gọi AI: " + e.getMessage()));
            }
        }).start();
    }

    private String kiemTraCauHinh(CauHinhAI cauHinhAI) {
        if (cauHinhAI == null) {
            return "Chưa có cấu hình AI";
        }
        if (rong(cauHinhAI.getNhaCungCap())) {
            return "Chưa cấu hình nhà cung cấp AI";
        }
        if (rong(cauHinhAI.getKhoaApi())) {
            return "Chưa cấu hình khóa API AI";
        }
        if (rong(cauHinhAI.getMoHinh())) {
            return "Chưa cấu hình mô hình AI";
        }
        if (!laCauHinhGemini(cauHinhAI.getNhaCungCap())) {
            return "Chỉ hỗ trợ Gemini, vui lòng kiểm tra lại cấu hình AI";
        }
        return null;
    }

    private String guiYeuCauHttp(CauHinhAI cauHinhAI, String cauHoi) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(taoUrlGemini(cauHinhAI)).openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(60000);
        connection.setDoOutput(true);
        connection.setRequestProperty("x-goog-api-key", cauHinhAI.getKhoaApi());
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        byte[] body = taoBody(cauHinhAI, cauHoi).toString().getBytes(StandardCharsets.UTF_8);
        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(body);
        }

        int statusCode = connection.getResponseCode();
        String responseBody = docResponse(connection, statusCode);
        connection.disconnect();

        if (statusCode < 200 || statusCode >= 300) {
            throw new Exception("HTTP " + statusCode + " - " + responseBody);
        }

        return parseTraLoi(responseBody);
    }

    private String taoUrlGemini(CauHinhAI cauHinhAI) {
        String nhaCungCap = cauHinhAI.getNhaCungCap().trim();
        String moHinh = chuanHoaMoHinhGemini(cauHinhAI.getMoHinh());

        if (nhaCungCap.startsWith("http://") || nhaCungCap.startsWith("https://")) {
            if (nhaCungCap.contains("{model}")) {
                return nhaCungCap.replace("{model}", moHinh);
            }
            if (nhaCungCap.contains(":generateContent")) {
                return nhaCungCap;
            }

            String url = nhaCungCap.endsWith("/")
                    ? nhaCungCap.substring(0, nhaCungCap.length() - 1)
                    : nhaCungCap;
            if (url.endsWith("/models")) {
                return url + "/" + moHinh + ":generateContent";
            }
            if (url.endsWith("/v1beta")) {
                return url + "/models/" + moHinh + ":generateContent";
            }
            return url + "/v1beta/models/" + moHinh + ":generateContent";
        }

        return "https://generativelanguage.googleapis.com/v1beta/models/"
                + moHinh
                + ":generateContent";
    }

    private String chuanHoaMoHinhGemini(String moHinh) {
        String giaTri = moHinh.trim();
        if (giaTri.startsWith("models/")) {
            giaTri = giaTri.substring("models/".length());
        }
        return giaTri.toLowerCase(Locale.US);
    }

    private JSONObject taoBody(CauHinhAI cauHinhAI, String cauHoi) throws Exception {
        JSONObject body = new JSONObject();

        if (!rong(cauHinhAI.getNhacLenh())) {
            body.put("system_instruction", new JSONObject()
                    .put("parts", new JSONArray()
                            .put(new JSONObject().put("text", cauHinhAI.getNhacLenh().trim()))));
        }

        body.put("contents", new JSONArray()
                .put(new JSONObject()
                        .put("parts", new JSONArray()
                                .put(new JSONObject().put("text", cauHoi)))));

        return body;
    }

    private String docResponse(HttpURLConnection connection, int statusCode) throws Exception {
        InputStream inputStream = statusCode >= 200 && statusCode < 300
                ? connection.getInputStream()
                : connection.getErrorStream();

        if (inputStream == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
        return builder.toString();
    }

    private String parseTraLoi(String responseBody) throws Exception {
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray candidates = jsonObject.optJSONArray("candidates");
        if (candidates == null || candidates.length() == 0) {
            throw new Exception("AI không trả về nội dung");
        }

        JSONObject content = candidates.getJSONObject(0).optJSONObject("content");
        if (content == null) {
            String lyDo = candidates.getJSONObject(0).optString("finishReason", "");
            throw new Exception(lyDo.isEmpty() ? "AI không trả về nội dung" : "AI dừng trả lời: " + lyDo);
        }

        JSONArray parts = content.optJSONArray("parts");
        if (parts == null || parts.length() == 0) {
            throw new Exception("AI không trả về nội dung");
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < parts.length(); i++) {
            String text = parts.getJSONObject(i).optString("text", "");
            if (!text.trim().isEmpty()) {
                builder.append(text);
            }
        }

        String traLoi = builder.toString().trim();
        if (traLoi.isEmpty()) {
            throw new Exception("AI trả lời rỗng");
        }

        return traLoi;
    }

    private void luuLichSu(String cauHoi, String traLoi, ApiCallback<String> callback) {
        LichSuChat lichSuChat = new LichSuChat();
        lichSuChat.setMaNguoiDung(Session.nguoiDungHienTai.getMaNguoiDung());
        lichSuChat.setCauHoi(cauHoi);
        lichSuChat.setTraLoi(traLoi);

        lichSuChatDAO.themLichSu(lichSuChat, new ApiCallback<LichSuChat>() {
            @Override
            public void onSuccess(LichSuChat data) {
                callback.onSuccess(traLoi);
            }

            @Override
            public void onError(String thongBao) {
                callback.onError(thongBao);
            }
        });
    }

    private boolean rong(String chuoi) {
        return chuoi == null || chuoi.trim().isEmpty();
    }

    private boolean laCauHinhGemini(String nhaCungCap) {
        if (rong(nhaCungCap)) {
            return false;
        }
        String giaTri = nhaCungCap.toLowerCase(Locale.US);
        return giaTri.contains("gemini") || giaTri.contains("generativelanguage.googleapis.com");
    }
}
