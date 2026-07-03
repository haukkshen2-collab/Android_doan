-- =========================================================
-- Fix bảo mật Supabase cho database đang có sẵn
-- Chạy file này trong Supabase SQL Editor để bật RLS cho các bảng public.
-- File này KHÔNG drop bảng và KHÔNG xóa dữ liệu.
-- =========================================================

ALTER TABLE "NguoiDung" ENABLE ROW LEVEL SECURITY;
ALTER TABLE "LoaiVe" ENABLE ROW LEVEL SECURITY;
ALTER TABLE "Ve" ENABLE ROW LEVEL SECURITY;
ALTER TABLE "Voucher" ENABLE ROW LEVEL SECURITY;
ALTER TABLE "GioHang" ENABLE ROW LEVEL SECURITY;
ALTER TABLE "ChiTietGioHang" ENABLE ROW LEVEL SECURITY;
ALTER TABLE "HoaDon" ENABLE ROW LEVEL SECURITY;
ALTER TABLE "ChiTietHoaDon" ENABLE ROW LEVEL SECURITY;
ALTER TABLE "CauHinhAI" ENABLE ROW LEVEL SECURITY;
ALTER TABLE "LichSuChat" ENABLE ROW LEVEL SECURITY;

GRANT USAGE ON SCHEMA public TO anon, authenticated;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE "NguoiDung", "LoaiVe", "Ve", "Voucher", "GioHang", "ChiTietGioHang", "HoaDon", "ChiTietHoaDon", "CauHinhAI", "LichSuChat" TO anon, authenticated;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO anon, authenticated;

DROP POLICY IF EXISTS "NguoiDung_doc" ON "NguoiDung";
DROP POLICY IF EXISTS "NguoiDung_them" ON "NguoiDung";
DROP POLICY IF EXISTS "NguoiDung_sua" ON "NguoiDung";
DROP POLICY IF EXISTS "NguoiDung_xoa" ON "NguoiDung";

CREATE POLICY "NguoiDung_doc" ON "NguoiDung"
    FOR SELECT TO anon, authenticated
    USING (true);

CREATE POLICY "NguoiDung_them" ON "NguoiDung"
    FOR INSERT TO anon, authenticated
    WITH CHECK (true);

CREATE POLICY "NguoiDung_sua" ON "NguoiDung"
    FOR UPDATE TO anon, authenticated
    USING (true)
    WITH CHECK (true);

CREATE POLICY "NguoiDung_xoa" ON "NguoiDung"
    FOR DELETE TO anon, authenticated
    USING (true);

DROP POLICY IF EXISTS "LoaiVe_doc" ON "LoaiVe";
DROP POLICY IF EXISTS "LoaiVe_them" ON "LoaiVe";
DROP POLICY IF EXISTS "LoaiVe_sua" ON "LoaiVe";
DROP POLICY IF EXISTS "LoaiVe_xoa" ON "LoaiVe";

CREATE POLICY "LoaiVe_doc" ON "LoaiVe"
    FOR SELECT TO anon, authenticated
    USING (true);

CREATE POLICY "LoaiVe_them" ON "LoaiVe"
    FOR INSERT TO anon, authenticated
    WITH CHECK (true);

CREATE POLICY "LoaiVe_sua" ON "LoaiVe"
    FOR UPDATE TO anon, authenticated
    USING (true)
    WITH CHECK (true);

CREATE POLICY "LoaiVe_xoa" ON "LoaiVe"
    FOR DELETE TO anon, authenticated
    USING (true);

DROP POLICY IF EXISTS "Ve_doc" ON "Ve";
DROP POLICY IF EXISTS "Ve_them" ON "Ve";
DROP POLICY IF EXISTS "Ve_sua" ON "Ve";
DROP POLICY IF EXISTS "Ve_xoa" ON "Ve";

CREATE POLICY "Ve_doc" ON "Ve"
    FOR SELECT TO anon, authenticated
    USING (true);

CREATE POLICY "Ve_them" ON "Ve"
    FOR INSERT TO anon, authenticated
    WITH CHECK (true);

CREATE POLICY "Ve_sua" ON "Ve"
    FOR UPDATE TO anon, authenticated
    USING (true)
    WITH CHECK (true);

CREATE POLICY "Ve_xoa" ON "Ve"
    FOR DELETE TO anon, authenticated
    USING (true);

DROP POLICY IF EXISTS "Voucher_doc" ON "Voucher";
DROP POLICY IF EXISTS "Voucher_them" ON "Voucher";
DROP POLICY IF EXISTS "Voucher_sua" ON "Voucher";
DROP POLICY IF EXISTS "Voucher_xoa" ON "Voucher";

CREATE POLICY "Voucher_doc" ON "Voucher"
    FOR SELECT TO anon, authenticated
    USING (true);

CREATE POLICY "Voucher_them" ON "Voucher"
    FOR INSERT TO anon, authenticated
    WITH CHECK (true);

CREATE POLICY "Voucher_sua" ON "Voucher"
    FOR UPDATE TO anon, authenticated
    USING (true)
    WITH CHECK (true);

CREATE POLICY "Voucher_xoa" ON "Voucher"
    FOR DELETE TO anon, authenticated
    USING (true);

DROP POLICY IF EXISTS "GioHang_doc" ON "GioHang";
DROP POLICY IF EXISTS "GioHang_them" ON "GioHang";
DROP POLICY IF EXISTS "GioHang_sua" ON "GioHang";
DROP POLICY IF EXISTS "GioHang_xoa" ON "GioHang";

CREATE POLICY "GioHang_doc" ON "GioHang"
    FOR SELECT TO anon, authenticated
    USING (true);

CREATE POLICY "GioHang_them" ON "GioHang"
    FOR INSERT TO anon, authenticated
    WITH CHECK (true);

CREATE POLICY "GioHang_sua" ON "GioHang"
    FOR UPDATE TO anon, authenticated
    USING (true)
    WITH CHECK (true);

CREATE POLICY "GioHang_xoa" ON "GioHang"
    FOR DELETE TO anon, authenticated
    USING (true);

DROP POLICY IF EXISTS "ChiTietGioHang_doc" ON "ChiTietGioHang";
DROP POLICY IF EXISTS "ChiTietGioHang_them" ON "ChiTietGioHang";
DROP POLICY IF EXISTS "ChiTietGioHang_sua" ON "ChiTietGioHang";
DROP POLICY IF EXISTS "ChiTietGioHang_xoa" ON "ChiTietGioHang";

CREATE POLICY "ChiTietGioHang_doc" ON "ChiTietGioHang"
    FOR SELECT TO anon, authenticated
    USING (true);

CREATE POLICY "ChiTietGioHang_them" ON "ChiTietGioHang"
    FOR INSERT TO anon, authenticated
    WITH CHECK (true);

CREATE POLICY "ChiTietGioHang_sua" ON "ChiTietGioHang"
    FOR UPDATE TO anon, authenticated
    USING (true)
    WITH CHECK (true);

CREATE POLICY "ChiTietGioHang_xoa" ON "ChiTietGioHang"
    FOR DELETE TO anon, authenticated
    USING (true);

DROP POLICY IF EXISTS "HoaDon_doc" ON "HoaDon";
DROP POLICY IF EXISTS "HoaDon_them" ON "HoaDon";
DROP POLICY IF EXISTS "HoaDon_sua" ON "HoaDon";
DROP POLICY IF EXISTS "HoaDon_xoa" ON "HoaDon";

CREATE POLICY "HoaDon_doc" ON "HoaDon"
    FOR SELECT TO anon, authenticated
    USING (true);

CREATE POLICY "HoaDon_them" ON "HoaDon"
    FOR INSERT TO anon, authenticated
    WITH CHECK (true);

CREATE POLICY "HoaDon_sua" ON "HoaDon"
    FOR UPDATE TO anon, authenticated
    USING (true)
    WITH CHECK (true);

CREATE POLICY "HoaDon_xoa" ON "HoaDon"
    FOR DELETE TO anon, authenticated
    USING (true);

DROP POLICY IF EXISTS "ChiTietHoaDon_doc" ON "ChiTietHoaDon";
DROP POLICY IF EXISTS "ChiTietHoaDon_them" ON "ChiTietHoaDon";
DROP POLICY IF EXISTS "ChiTietHoaDon_sua" ON "ChiTietHoaDon";
DROP POLICY IF EXISTS "ChiTietHoaDon_xoa" ON "ChiTietHoaDon";

CREATE POLICY "ChiTietHoaDon_doc" ON "ChiTietHoaDon"
    FOR SELECT TO anon, authenticated
    USING (true);

CREATE POLICY "ChiTietHoaDon_them" ON "ChiTietHoaDon"
    FOR INSERT TO anon, authenticated
    WITH CHECK (true);

CREATE POLICY "ChiTietHoaDon_sua" ON "ChiTietHoaDon"
    FOR UPDATE TO anon, authenticated
    USING (true)
    WITH CHECK (true);

CREATE POLICY "ChiTietHoaDon_xoa" ON "ChiTietHoaDon"
    FOR DELETE TO anon, authenticated
    USING (true);

DROP POLICY IF EXISTS "CauHinhAI_doc" ON "CauHinhAI";
DROP POLICY IF EXISTS "CauHinhAI_them" ON "CauHinhAI";
DROP POLICY IF EXISTS "CauHinhAI_sua" ON "CauHinhAI";
DROP POLICY IF EXISTS "CauHinhAI_xoa" ON "CauHinhAI";

CREATE POLICY "CauHinhAI_doc" ON "CauHinhAI"
    FOR SELECT TO anon, authenticated
    USING (true);

CREATE POLICY "CauHinhAI_them" ON "CauHinhAI"
    FOR INSERT TO anon, authenticated
    WITH CHECK (true);

CREATE POLICY "CauHinhAI_sua" ON "CauHinhAI"
    FOR UPDATE TO anon, authenticated
    USING (true)
    WITH CHECK (true);

CREATE POLICY "CauHinhAI_xoa" ON "CauHinhAI"
    FOR DELETE TO anon, authenticated
    USING (true);

DROP POLICY IF EXISTS "LichSuChat_doc" ON "LichSuChat";
DROP POLICY IF EXISTS "LichSuChat_them" ON "LichSuChat";
DROP POLICY IF EXISTS "LichSuChat_sua" ON "LichSuChat";
DROP POLICY IF EXISTS "LichSuChat_xoa" ON "LichSuChat";

CREATE POLICY "LichSuChat_doc" ON "LichSuChat"
    FOR SELECT TO anon, authenticated
    USING (true);

CREATE POLICY "LichSuChat_them" ON "LichSuChat"
    FOR INSERT TO anon, authenticated
    WITH CHECK (true);

CREATE POLICY "LichSuChat_sua" ON "LichSuChat"
    FOR UPDATE TO anon, authenticated
    USING (true)
    WITH CHECK (true);

CREATE POLICY "LichSuChat_xoa" ON "LichSuChat"
    FOR DELETE TO anon, authenticated
    USING (true);

SELECT 'Đã bật RLS và tạo policy cho Supabase!' AS "ThongBao";
