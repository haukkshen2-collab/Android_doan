import { createClient } from "https://esm.sh/@supabase/supabase-js@2";

type WebhookPayload = Record<string, unknown>;

type KetQuaThanhToan = {
  thanh_cong?: boolean;
  ma_hoa_don?: number;
  thong_bao?: string;
};

const jsonHeaders = {
  "Content-Type": "application/json; charset=utf-8",
};

Deno.serve(async (request) => {
  if (request.method !== "POST") {
    return traVeJson({ loi: "Phương thức không hợp lệ" }, 405);
  }

  const sepayApiKey = Deno.env.get("SEPAY_API_KEY") ?? "";
  if (!kiemTraApiKey(request, sepayApiKey)) {
    return traVeJson({ loi: "Không có quyền gọi webhook" }, 401);
  }

  const supabaseUrl = Deno.env.get("SUPABASE_URL") ?? "";
  const serviceRoleKey = Deno.env.get("SUPABASE_SERVICE_ROLE_KEY") ?? "";
  if (!supabaseUrl || !serviceRoleKey) {
    return traVeJson({ loi: "Thiếu cấu hình Supabase Edge Function" }, 500);
  }

  let payload: WebhookPayload;
  try {
    payload = await request.json();
  } catch (_error) {
    return traVeJson({ loi: "Dữ liệu webhook không hợp lệ" }, 400);
  }

  const noiDungChuyenKhoan = layNoiDungChuyenKhoan(payload);
  const maHoaDon = layMaHoaDon(noiDungChuyenKhoan);
  if (!maHoaDon) {
    return traVeJson({ loi: "Không tìm thấy mã hóa đơn trong nội dung chuyển khoản" }, 400);
  }

  const supabase = createClient(supabaseUrl, serviceRoleKey, {
    auth: {
      persistSession: false,
      autoRefreshToken: false,
    },
  });

  const { data: hoaDonDaThanhToan, error: loiKiemTraHoaDon } = await supabase
    .from("HoaDon")
    .select("MaHoaDon,NoiDungChuyenKhoan")
    .eq("MaHoaDon", maHoaDon)
    .eq("TrangThai", "DaThanhToan")
    .maybeSingle();

  if (loiKiemTraHoaDon) {
    return traVeJson({ loi: loiKiemTraHoaDon.message }, 500);
  }

  if (hoaDonDaThanhToan) {
    return traVeJson({
      success: true,
      thanhCong: true,
      maHoaDon,
      noiDungChuyenKhoan: hoaDonDaThanhToan.NoiDungChuyenKhoan ?? `BANVE${maHoaDon}`,
      thongBao: "Hóa đơn đã được xử lý trước đó",
    });
  }

  const soTienNhan = laySoTien(payload);
  const { data: ketQuaRpc, error: loiHoanTat } = await supabase.rpc(
    "hoan_tat_thanh_toan_sepay",
    {
      p_ma_hoa_don: maHoaDon,
      p_so_tien_nhan: soTienNhan > 0 ? soTienNhan : null,
    },
  );

  if (loiHoanTat) {
    return traVeJson({ loi: loiHoanTat.message }, 500);
  }

  const ketQua = layDongKetQua(ketQuaRpc);
  if (!ketQua?.thanh_cong) {
    return traVeJson({
      success: true,
      thanhCong: false,
      maHoaDon,
      thongBao: ketQua?.thong_bao ?? "Thanh toán tạm không còn tồn tại",
    });
  }

  return traVeJson({
    success: true,
    thanhCong: true,
    maHoaDon,
    noiDungChuyenKhoan: `BANVE${maHoaDon}`,
    thongBao: ketQua.thong_bao ?? "Thanh toán thành công",
  });
});

function kiemTraApiKey(request: Request, sepayApiKey: string): boolean {
  if (!sepayApiKey) {
    return false;
  }

  const authorization = request.headers.get("Authorization") ?? "";
  const apiKey = request.headers.get("apikey") ?? request.headers.get("x-api-key") ?? "";

  return authorization === `Apikey ${sepayApiKey}`
    || authorization === `Bearer ${sepayApiKey}`
    || apiKey === sepayApiKey;
}

function layNoiDungChuyenKhoan(payload: WebhookPayload): string {
  const danhSachKhoa = [
    "content",
    "description",
    "transfer_content",
    "transaction_content",
    "payment_content",
    "data.content",
    "data.description",
    "data.transfer_content",
    "data.transaction_content",
  ];

  for (const khoa of danhSachKhoa) {
    const giaTri = layGiaTriSau(payload, khoa);
    if (typeof giaTri === "string" && giaTri.trim()) {
      return giaTri;
    }
  }

  return JSON.stringify(payload);
}

function layMaHoaDon(noiDungChuyenKhoan: string): number | null {
  const ketQua = /BANVE\s*(\d+)/i.exec(noiDungChuyenKhoan);
  if (!ketQua) {
    return null;
  }

  return Number(ketQua[1]);
}

function laySoTien(payload: WebhookPayload): number {
  const danhSachKhoa = [
    "transferAmount",
    "amount",
    "money",
    "data.transferAmount",
    "data.amount",
    "data.money",
  ];

  for (const khoa of danhSachKhoa) {
    const giaTri = layGiaTriSau(payload, khoa);
    const soTien = chuyenThanhSo(giaTri);
    if (soTien > 0) {
      return soTien;
    }
  }

  return 0;
}

function layGiaTriSau(payload: WebhookPayload, duongDan: string): unknown {
  return duongDan.split(".").reduce<unknown>((ketQua, khoa) => {
    if (ketQua && typeof ketQua === "object" && khoa in ketQua) {
      return (ketQua as Record<string, unknown>)[khoa];
    }
    return undefined;
  }, payload);
}

function chuyenThanhSo(giaTri: unknown): number {
  if (typeof giaTri === "number") {
    return giaTri;
  }

  if (typeof giaTri === "string") {
    return Number(giaTri.replace(/[^\d.-]/g, ""));
  }

  return 0;
}

function layDongKetQua(ketQuaRpc: unknown): KetQuaThanhToan | null {
  if (Array.isArray(ketQuaRpc)) {
    return (ketQuaRpc[0] ?? null) as KetQuaThanhToan | null;
  }

  return ketQuaRpc as KetQuaThanhToan | null;
}

function traVeJson(body: unknown, status = 200): Response {
  return new Response(JSON.stringify(body), {
    status,
    headers: jsonHeaders,
  });
}
