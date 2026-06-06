-- =========================================================
-- Setup database PostgreSQL cho ứng dụng Quản lý Bán Vé Khu Du Lịch
-- Chạy được trên Supabase PostgreSQL
-- =========================================================

DROP TABLE IF EXISTS "LichSuChat", "CauHinhAI", "ChiTietHoaDon", "HoaDon", "ChiTietGioHang", "GioHang", "Voucher", "Ve", "LoaiVe", "NguoiDung" CASCADE;

-- =========================================================
-- Bảng NguoiDung
-- =========================================================
CREATE TABLE "NguoiDung" (
    "MaNguoiDung" BIGSERIAL PRIMARY KEY,
    "TaiKhoan" TEXT UNIQUE NOT NULL,
    "MatKhau" TEXT NOT NULL,
    "HoTen" TEXT NOT NULL,
    "Email" TEXT,
    "SoDienThoai" TEXT,
    "VaiTro" TEXT NOT NULL DEFAULT 'NguoiDung',
    "NgayDangKy" DATE DEFAULT CURRENT_DATE,
    "TrangThai" TEXT NOT NULL DEFAULT 'HoatDong'
);

ALTER TABLE "NguoiDung" DISABLE ROW LEVEL SECURITY;

-- =========================================================
-- Bảng LoaiVe
-- =========================================================
CREATE TABLE "LoaiVe" (
    "MaLoaiVe" BIGSERIAL PRIMARY KEY,
    "TenLoaiVe" TEXT NOT NULL,
    "MoTa" TEXT,
    "TrangThai" TEXT NOT NULL DEFAULT 'HoatDong'
);

ALTER TABLE "LoaiVe" DISABLE ROW LEVEL SECURITY;

-- =========================================================
-- Bảng Ve
-- =========================================================
CREATE TABLE "Ve" (
    "MaVe" BIGSERIAL PRIMARY KEY,
    "MaLoaiVe" BIGINT NOT NULL REFERENCES "LoaiVe"("MaLoaiVe") ON DELETE CASCADE,
    "TenVe" TEXT NOT NULL,
    "GiaVe" NUMERIC(18,2) DEFAULT 0,
    "GiaNguoiLon" NUMERIC(18,2) DEFAULT 0,
    "GiaTreEm" NUMERIC(18,2) DEFAULT 0,
    "GiaNguoiCaoTuoi" NUMERIC(18,2) DEFAULT 0,
    "SoLuong" INT DEFAULT 0,
    "MoTa" TEXT,
    "ThongTinVe" TEXT,
    "AnhVe" TEXT,
    "TrangThai" TEXT NOT NULL DEFAULT 'HoatDong'
);

ALTER TABLE "Ve" DISABLE ROW LEVEL SECURITY;

-- =========================================================
-- Bảng Voucher
-- =========================================================
CREATE TABLE "Voucher" (
    "MaVoucher" BIGSERIAL PRIMARY KEY,
    "MaGiamGia" TEXT UNIQUE NOT NULL,
    "TenVoucher" TEXT NOT NULL,
    "KieuGiamGia" TEXT NOT NULL,
    "GiaTriGiam" NUMERIC(18,2) NOT NULL,
    "NgayBatDau" DATE NOT NULL,
    "NgayKetThuc" DATE NOT NULL,
    "SoLuong" INT DEFAULT 0,
    "TrangThai" TEXT NOT NULL DEFAULT 'HoatDong'
);

ALTER TABLE "Voucher" DISABLE ROW LEVEL SECURITY;

-- =========================================================
-- Bảng GioHang
-- =========================================================
CREATE TABLE "GioHang" (
    "MaGioHang" BIGSERIAL PRIMARY KEY,
    "MaNguoiDung" BIGINT UNIQUE NOT NULL REFERENCES "NguoiDung"("MaNguoiDung") ON DELETE CASCADE,
    "NgayTao" TIMESTAMP DEFAULT NOW()
);

ALTER TABLE "GioHang" DISABLE ROW LEVEL SECURITY;

-- =========================================================
-- Bảng ChiTietGioHang
-- =========================================================
CREATE TABLE "ChiTietGioHang" (
    "MaChiTietGioHang" BIGSERIAL PRIMARY KEY,
    "MaGioHang" BIGINT NOT NULL REFERENCES "GioHang"("MaGioHang") ON DELETE CASCADE,
    "MaVe" BIGINT NOT NULL REFERENCES "Ve"("MaVe") ON DELETE CASCADE,
    "NgaySuDung" DATE NOT NULL,
    "SoLuongNguoiLon" INT DEFAULT 0,
    "SoLuongTreEm" INT DEFAULT 0,
    "SoLuongNguoiCaoTuoi" INT DEFAULT 0,
    "DonGiaNguoiLon" NUMERIC(18,2) DEFAULT 0,
    "DonGiaTreEm" NUMERIC(18,2) DEFAULT 0,
    "DonGiaNguoiCaoTuoi" NUMERIC(18,2) DEFAULT 0
);

ALTER TABLE "ChiTietGioHang" DISABLE ROW LEVEL SECURITY;

-- =========================================================
-- Bảng HoaDon
-- =========================================================
CREATE TABLE "HoaDon" (
    "MaHoaDon" BIGSERIAL PRIMARY KEY,
    "MaNguoiDung" BIGINT NOT NULL REFERENCES "NguoiDung"("MaNguoiDung") ON DELETE CASCADE,
    "NgayLap" TIMESTAMP DEFAULT NOW(),
    "TongTien" NUMERIC(18,2) DEFAULT 0,
    "MaVoucher" BIGINT REFERENCES "Voucher"("MaVoucher") ON DELETE SET NULL,
    "TienGiam" NUMERIC(18,2) DEFAULT 0,
    "ThanhToan" TEXT,
    "TrangThai" TEXT NOT NULL DEFAULT 'ChuaThanhToan'
);

ALTER TABLE "HoaDon" DISABLE ROW LEVEL SECURITY;

-- =========================================================
-- Bảng ChiTietHoaDon
-- =========================================================
CREATE TABLE "ChiTietHoaDon" (
    "MaChiTietHoaDon" BIGSERIAL PRIMARY KEY,
    "MaHoaDon" BIGINT NOT NULL REFERENCES "HoaDon"("MaHoaDon") ON DELETE CASCADE,
    "MaVe" BIGINT NOT NULL REFERENCES "Ve"("MaVe") ON DELETE CASCADE,
    "NgaySuDung" DATE NOT NULL,
    "SoLuongNguoiLon" INT DEFAULT 0,
    "SoLuongTreEm" INT DEFAULT 0,
    "SoLuongNguoiCaoTuoi" INT DEFAULT 0,
    "DonGiaNguoiLon" NUMERIC(18,2) DEFAULT 0,
    "DonGiaTreEm" NUMERIC(18,2) DEFAULT 0,
    "DonGiaNguoiCaoTuoi" NUMERIC(18,2) DEFAULT 0,
    "ThanhTien" NUMERIC(18,2) DEFAULT 0
);

ALTER TABLE "ChiTietHoaDon" DISABLE ROW LEVEL SECURITY;

-- =========================================================
-- Bảng CauHinhAI
-- =========================================================
CREATE TABLE "CauHinhAI" (
    "MaCauHinhAI" BIGSERIAL PRIMARY KEY,
    "NhaCungCap" TEXT NOT NULL,
    "KhoaApi" TEXT NOT NULL,
    "MoHinh" TEXT NOT NULL,
    "NhacLenh" TEXT
);

ALTER TABLE "CauHinhAI" DISABLE ROW LEVEL SECURITY;

-- =========================================================
-- Bảng LichSuChat
-- =========================================================
CREATE TABLE "LichSuChat" (
    "MaLichSuChat" BIGSERIAL PRIMARY KEY,
    "MaNguoiDung" BIGINT NOT NULL REFERENCES "NguoiDung"("MaNguoiDung") ON DELETE CASCADE,
    "CauHoi" TEXT NOT NULL,
    "TraLoi" TEXT,
    "NgayTao" TIMESTAMP DEFAULT NOW()
);

ALTER TABLE "LichSuChat" DISABLE ROW LEVEL SECURITY;

-- =========================================================
-- Dữ liệu mẫu bảng NguoiDung
-- =========================================================
INSERT INTO "NguoiDung" ("TaiKhoan", "MatKhau", "HoTen", "Email", "SoDienThoai", "VaiTro", "TrangThai")
VALUES
    ('admin', '0192023a7bbd73250516f069df18b500', 'Quản trị viên', 'admin@example.com', '0900000000', 'QuanLy', 'HoatDong'),
    ('user1', 'e10adc3949ba59abbe56e057f20f883e', 'Người dùng 1', 'user1@example.com', '0911111111', 'NguoiDung', 'HoatDong'),
    ('user2', 'e10adc3949ba59abbe56e057f20f883e', 'Người dùng 2', 'user2@example.com', '0922222222', 'NguoiDung', 'HoatDong');

-- =========================================================
-- Dữ liệu mẫu bảng LoaiVe
-- =========================================================
INSERT INTO "LoaiVe" ("TenLoaiVe", "MoTa", "TrangThai")
VALUES
    ('Vé tham quan', 'Vé dành cho khách tham quan khu du lịch', 'HoatDong'),
    ('Vé vui chơi', 'Vé dành cho các khu trò chơi giải trí', 'HoatDong'),
    ('Vé Combo', 'Vé kết hợp tham quan và vui chơi', 'HoatDong');

-- =========================================================
-- Dữ liệu mẫu bảng Ve
-- =========================================================
INSERT INTO "Ve" (
    "MaLoaiVe",
    "TenVe",
    "GiaVe",
    "GiaNguoiLon",
    "GiaTreEm",
    "GiaNguoiCaoTuoi",
    "SoLuong",
    "MoTa",
    "ThongTinVe",
    "AnhVe",
    "TrangThai"
)
VALUES
    (1, 'Vé tham quan trong ngày', 120000, 120000, 80000, 90000, 100, 'Tham quan toàn khu trong ngày', 'Áp dụng cho một lượt vào cổng trong ngày sử dụng.', 'https://images.unsplash.com/photo-1500530855697-b586d89ba3ee', 'HoatDong'),
    (1, 'Vé tham quan cuối tuần', 150000, 150000, 100000, 110000, 100, 'Tham quan khu du lịch vào cuối tuần', 'Áp dụng thứ Bảy, Chủ Nhật và ngày lễ.', 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e', 'HoatDong'),
    (2, 'Vé khu trò chơi ngoài trời', 180000, 180000, 120000, 140000, 100, 'Vui chơi tại khu trò chơi ngoài trời', 'Bao gồm các trò chơi phổ thông trong khu ngoài trời.', 'https://images.unsplash.com/photo-1513889961551-628c1e5e2ee9', 'HoatDong'),
    (2, 'Vé khu trò chơi nước', 220000, 220000, 150000, 170000, 100, 'Vui chơi tại công viên nước', 'Áp dụng cho các trò chơi nước trong ngày.', 'https://images.unsplash.com/photo-1530549387789-4c1017266635', 'HoatDong'),
    (3, 'Vé combo tham quan và vui chơi', 300000, 300000, 210000, 240000, 100, 'Combo tham quan và vui chơi cơ bản', 'Bao gồm vé vào cổng và khu trò chơi phổ thông.', 'https://images.unsplash.com/photo-1526772662000-3f88f10405ff', 'HoatDong'),
    (3, 'Vé combo gia đình', 500000, 500000, 350000, 400000, 100, 'Combo phù hợp cho gia đình', 'Bao gồm nhiều quyền lợi tham quan, vui chơi và ưu đãi dịch vụ.', 'https://images.unsplash.com/photo-1501785888041-af3ef285b470', 'HoatDong');

-- =========================================================
-- Dữ liệu mẫu bảng Voucher
-- =========================================================
INSERT INTO "Voucher" ("MaGiamGia", "TenVoucher", "KieuGiamGia", "GiaTriGiam", "NgayBatDau", "NgayKetThuc", "SoLuong", "TrangThai")
VALUES
    ('GIAM10', 'Giảm 10% tổng hóa đơn', 'PhanTram', 10, CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days', 50, 'HoatDong'),
    ('TANG50K', 'Giảm 50,000 VNĐ', 'TienMat', 50000, CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days', 50, 'HoatDong');

-- =========================================================
-- Dữ liệu mẫu bảng CauHinhAI
-- =========================================================
INSERT INTO "CauHinhAI" ("NhaCungCap", "KhoaApi", "MoHinh", "NhacLenh")
VALUES
    ('Gemini', '', 'gemini-3.5-flash', 'Bạn là trợ lý chăm sóc khách hàng của khu du lịch. Trả lời ngắn gọn, lịch sự và đúng chính sách.');

SELECT 'Tao database thanh cong!' AS "ThongBao";
