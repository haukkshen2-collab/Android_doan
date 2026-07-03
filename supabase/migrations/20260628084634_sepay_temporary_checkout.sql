-- =========================================================
-- Thanh toán SePay bằng dữ liệu tạm
-- Chỉ tạo hóa đơn thật sau khi webhook xác nhận đã nhận tiền.
-- =========================================================

ALTER TABLE "HoaDon"
ADD COLUMN IF NOT EXISTS "NoiDungChuyenKhoan" TEXT;

CREATE TABLE IF NOT EXISTS "ThanhToanTam" (
    "MaHoaDon" BIGINT PRIMARY KEY DEFAULT nextval('"HoaDon_MaHoaDon_seq"'::regclass),
    "MaNguoiDung" BIGINT NOT NULL REFERENCES "NguoiDung"("MaNguoiDung") ON DELETE CASCADE,
    "NgayTao" TIMESTAMP DEFAULT NOW(),
    "TongTien" NUMERIC(18,2) DEFAULT 0,
    "MaVoucher" BIGINT REFERENCES "Voucher"("MaVoucher") ON DELETE SET NULL,
    "TienGiam" NUMERIC(18,2) DEFAULT 0,
    "ThanhToan" TEXT,
    "TrangThai" TEXT NOT NULL DEFAULT 'ChoThanhToan',
    "NoiDungChuyenKhoan" TEXT UNIQUE,
    "ThongBaoLoi" TEXT
);

CREATE TABLE IF NOT EXISTS "ChiTietThanhToanTam" (
    "MaChiTietThanhToanTam" BIGSERIAL PRIMARY KEY,
    "MaHoaDon" BIGINT NOT NULL REFERENCES "ThanhToanTam"("MaHoaDon") ON DELETE CASCADE,
    "MaChiTietGioHang" BIGINT NOT NULL,
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

ALTER TABLE "ThanhToanTam" ENABLE ROW LEVEL SECURITY;
ALTER TABLE "ChiTietThanhToanTam" ENABLE ROW LEVEL SECURITY;

GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE "ThanhToanTam", "ChiTietThanhToanTam" TO anon, authenticated;
GRANT USAGE, SELECT ON SEQUENCE "HoaDon_MaHoaDon_seq" TO anon, authenticated;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO anon, authenticated;

DROP POLICY IF EXISTS "ThanhToanTam_doc" ON "ThanhToanTam";
DROP POLICY IF EXISTS "ThanhToanTam_them" ON "ThanhToanTam";
DROP POLICY IF EXISTS "ThanhToanTam_sua" ON "ThanhToanTam";
DROP POLICY IF EXISTS "ThanhToanTam_xoa" ON "ThanhToanTam";

CREATE POLICY "ThanhToanTam_doc" ON "ThanhToanTam"
    FOR SELECT TO anon, authenticated
    USING (true);

CREATE POLICY "ThanhToanTam_them" ON "ThanhToanTam"
    FOR INSERT TO anon, authenticated
    WITH CHECK (true);

CREATE POLICY "ThanhToanTam_sua" ON "ThanhToanTam"
    FOR UPDATE TO anon, authenticated
    USING (true)
    WITH CHECK (true);

CREATE POLICY "ThanhToanTam_xoa" ON "ThanhToanTam"
    FOR DELETE TO anon, authenticated
    USING (true);

DROP POLICY IF EXISTS "ChiTietThanhToanTam_doc" ON "ChiTietThanhToanTam";
DROP POLICY IF EXISTS "ChiTietThanhToanTam_them" ON "ChiTietThanhToanTam";
DROP POLICY IF EXISTS "ChiTietThanhToanTam_sua" ON "ChiTietThanhToanTam";
DROP POLICY IF EXISTS "ChiTietThanhToanTam_xoa" ON "ChiTietThanhToanTam";

CREATE POLICY "ChiTietThanhToanTam_doc" ON "ChiTietThanhToanTam"
    FOR SELECT TO anon, authenticated
    USING (true);

CREATE POLICY "ChiTietThanhToanTam_them" ON "ChiTietThanhToanTam"
    FOR INSERT TO anon, authenticated
    WITH CHECK (true);

CREATE POLICY "ChiTietThanhToanTam_sua" ON "ChiTietThanhToanTam"
    FOR UPDATE TO anon, authenticated
    USING (true)
    WITH CHECK (true);

CREATE POLICY "ChiTietThanhToanTam_xoa" ON "ChiTietThanhToanTam"
    FOR DELETE TO anon, authenticated
    USING (true);

CREATE INDEX IF NOT EXISTS "idx_ThanhToanTam_MaNguoiDung"
ON "ThanhToanTam"("MaNguoiDung");

CREATE INDEX IF NOT EXISTS "idx_ThanhToanTam_NoiDungChuyenKhoan"
ON "ThanhToanTam"("NoiDungChuyenKhoan");

CREATE INDEX IF NOT EXISTS "idx_ChiTietThanhToanTam_MaHoaDon"
ON "ChiTietThanhToanTam"("MaHoaDon");

CREATE INDEX IF NOT EXISTS "idx_HoaDon_NoiDungChuyenKhoan"
ON "HoaDon"("NoiDungChuyenKhoan");

-- Xóa hóa đơn chờ thanh toán cũ do luồng QR trước đã tạo sớm.
DELETE FROM "HoaDon"
WHERE "TrangThai" = 'ChoThanhToan'
  AND "ThanhToan" = 'ChuyenKhoan';

CREATE OR REPLACE FUNCTION public.hoan_tat_thanh_toan_sepay(
    p_ma_hoa_don BIGINT,
    p_so_tien_nhan NUMERIC DEFAULT NULL
)
RETURNS TABLE (
    thanh_cong BOOLEAN,
    ma_hoa_don BIGINT,
    thong_bao TEXT
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_thanh_toan_tam RECORD;
    v_chi_tiet RECORD;
    v_so_tien_can_tra NUMERIC(18,2);
    v_so_luong_goc INT;
    v_so_luong_da_ban INT;
    v_so_luong_mua INT;
    v_da_co_hoa_don BOOLEAN;
BEGIN
    SELECT EXISTS (
        SELECT 1
        FROM "HoaDon"
        WHERE "MaHoaDon" = p_ma_hoa_don
          AND "TrangThai" = 'DaThanhToan'
    )
    INTO v_da_co_hoa_don;

    IF v_da_co_hoa_don THEN
        RETURN QUERY SELECT true, p_ma_hoa_don, 'Hóa đơn đã được thanh toán';
        RETURN;
    END IF;

    SELECT *
    INTO v_thanh_toan_tam
    FROM "ThanhToanTam"
    WHERE "MaHoaDon" = p_ma_hoa_don
    FOR UPDATE;

    IF NOT FOUND THEN
        RETURN QUERY SELECT false, p_ma_hoa_don, 'Không tìm thấy thanh toán tạm';
        RETURN;
    END IF;

    v_so_tien_can_tra := GREATEST(0, COALESCE(v_thanh_toan_tam."TongTien", 0) - COALESCE(v_thanh_toan_tam."TienGiam", 0));
    IF p_so_tien_nhan IS NOT NULL AND p_so_tien_nhan > 0 AND p_so_tien_nhan < v_so_tien_can_tra THEN
        UPDATE "ThanhToanTam"
        SET "TrangThai" = 'LoiThanhToan',
            "ThongBaoLoi" = 'Số tiền chuyển khoản chưa đủ'
        WHERE "MaHoaDon" = p_ma_hoa_don;

        RETURN QUERY SELECT false, p_ma_hoa_don, 'Số tiền chuyển khoản chưa đủ';
        RETURN;
    END IF;

    FOR v_chi_tiet IN
        SELECT *
        FROM "ChiTietThanhToanTam"
        WHERE "MaHoaDon" = p_ma_hoa_don
    LOOP
        SELECT COALESCE("SoLuong", 0)
        INTO v_so_luong_goc
        FROM "Ve"
        WHERE "MaVe" = v_chi_tiet."MaVe";

        SELECT COALESCE(SUM(
            COALESCE("SoLuongNguoiLon", 0)
            + COALESCE("SoLuongTreEm", 0)
            + COALESCE("SoLuongNguoiCaoTuoi", 0)
        ), 0)
        INTO v_so_luong_da_ban
        FROM "ChiTietHoaDon"
        WHERE "MaVe" = v_chi_tiet."MaVe"
          AND "NgaySuDung" = v_chi_tiet."NgaySuDung";

        v_so_luong_mua :=
            COALESCE(v_chi_tiet."SoLuongNguoiLon", 0)
            + COALESCE(v_chi_tiet."SoLuongTreEm", 0)
            + COALESCE(v_chi_tiet."SoLuongNguoiCaoTuoi", 0);

        IF v_so_luong_da_ban + v_so_luong_mua > COALESCE(v_so_luong_goc, 0) THEN
            UPDATE "ThanhToanTam"
            SET "TrangThai" = 'LoiThanhToan',
                "ThongBaoLoi" = 'Vé không đủ số lượng'
            WHERE "MaHoaDon" = p_ma_hoa_don;

            RETURN QUERY SELECT false, p_ma_hoa_don, 'Vé không đủ số lượng';
            RETURN;
        END IF;
    END LOOP;

    IF v_thanh_toan_tam."MaVoucher" IS NOT NULL THEN
        UPDATE "Voucher"
        SET "SoLuong" = GREATEST(0, COALESCE("SoLuong", 0) - 1)
        WHERE "MaVoucher" = v_thanh_toan_tam."MaVoucher"
          AND COALESCE("SoLuong", 0) > 0;
    END IF;

    INSERT INTO "HoaDon" (
        "MaHoaDon",
        "MaNguoiDung",
        "TongTien",
        "MaVoucher",
        "TienGiam",
        "ThanhToan",
        "TrangThai",
        "NoiDungChuyenKhoan"
    )
    VALUES (
        v_thanh_toan_tam."MaHoaDon",
        v_thanh_toan_tam."MaNguoiDung",
        v_thanh_toan_tam."TongTien",
        v_thanh_toan_tam."MaVoucher",
        v_thanh_toan_tam."TienGiam",
        v_thanh_toan_tam."ThanhToan",
        'DaThanhToan',
        v_thanh_toan_tam."NoiDungChuyenKhoan"
    );

    INSERT INTO "ChiTietHoaDon" (
        "MaHoaDon",
        "MaVe",
        "NgaySuDung",
        "SoLuongNguoiLon",
        "SoLuongTreEm",
        "SoLuongNguoiCaoTuoi",
        "DonGiaNguoiLon",
        "DonGiaTreEm",
        "DonGiaNguoiCaoTuoi",
        "ThanhTien"
    )
    SELECT
        "MaHoaDon",
        "MaVe",
        "NgaySuDung",
        "SoLuongNguoiLon",
        "SoLuongTreEm",
        "SoLuongNguoiCaoTuoi",
        "DonGiaNguoiLon",
        "DonGiaTreEm",
        "DonGiaNguoiCaoTuoi",
        "ThanhTien"
    FROM "ChiTietThanhToanTam"
    WHERE "MaHoaDon" = p_ma_hoa_don;

    DELETE FROM "ChiTietGioHang"
    WHERE "MaChiTietGioHang" IN (
        SELECT "MaChiTietGioHang"
        FROM "ChiTietThanhToanTam"
        WHERE "MaHoaDon" = p_ma_hoa_don
    );

    DELETE FROM "ThanhToanTam"
    WHERE "MaHoaDon" = p_ma_hoa_don;

    RETURN QUERY SELECT true, p_ma_hoa_don, 'Thanh toán thành công';
END;
$$;

REVOKE ALL ON FUNCTION public.hoan_tat_thanh_toan_sepay(BIGINT, NUMERIC) FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hoan_tat_thanh_toan_sepay(BIGINT, NUMERIC) FROM anon;
REVOKE ALL ON FUNCTION public.hoan_tat_thanh_toan_sepay(BIGINT, NUMERIC) FROM authenticated;
GRANT EXECUTE ON FUNCTION public.hoan_tat_thanh_toan_sepay(BIGINT, NUMERIC) TO service_role;
