# SePay Webhook

Function này nhận webhook từ SePay, kiểm tra API key qua biến môi trường `SEPAY_API_KEY`, lấy mã hóa đơn từ nội dung `BANVE{MaHoaDon}` rồi cập nhật bảng `HoaDon`.

## Cấu hình secret

Không lưu API key SePay trong Android app hoặc trong Git. Chạy lệnh sau bằng Supabase CLI và thay giá trị bằng key thật:

```bash
supabase secrets set SEPAY_API_KEY="API_KEY_SEPAY_CUA_BAN"
```

`SUPABASE_URL` và `SUPABASE_SERVICE_ROLE_KEY` là secret mặc định của Supabase Edge Functions. Nếu project của bạn chưa có, hãy thêm trong Supabase Dashboard hoặc Supabase CLI.

## Deploy

```bash
supabase functions deploy sepay-webhook
```

Webhook URL:

```text
https://zouvtylxvdbadkpbrpdb.functions.supabase.co/sepay-webhook
```

Header cần cấu hình phía SePay:

```text
Authorization: Apikey API_KEY_SEPAY_CUA_BAN
```
