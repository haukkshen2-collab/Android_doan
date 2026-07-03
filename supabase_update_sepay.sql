-- Chuẩn hóa thanh toán SePay: chỉ dùng một cột TrangThai cho hóa đơn.
ALTER TABLE "HoaDon"
ADD COLUMN IF NOT EXISTS "NoiDungChuyenKhoan" TEXT;

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = 'public'
          AND table_name = 'HoaDon'
          AND column_name = 'TrangThaiThanhToan'
    ) THEN
        UPDATE "HoaDon"
        SET "TrangThai" = 'DaThanhToan'
        WHERE "TrangThaiThanhToan" = 'DaThanhToan';

        UPDATE "HoaDon"
        SET "TrangThai" = 'ChoThanhToan'
        WHERE "TrangThaiThanhToan" = 'ChoThanhToan'
          AND "TrangThai" <> 'DaThanhToan';

        DROP INDEX IF EXISTS "idx_HoaDon_TrangThaiThanhToan";

        ALTER TABLE "HoaDon"
        DROP COLUMN "TrangThaiThanhToan";
    END IF;
END $$;

CREATE INDEX IF NOT EXISTS "idx_HoaDon_NoiDungChuyenKhoan"
ON "HoaDon"("NoiDungChuyenKhoan");

SELECT 'Da gop trang thai thanh toan SePay thanh cong!' AS "ThongBao";
