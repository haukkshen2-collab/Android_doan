# AGENTS.md — Ứng dụng Quản lý Bán Vé Khu Du Lịch (Android)

## Tổng quan dự án
- Ứng dụng Android viết bằng **Java thuần** (không dùng Kotlin).
- Kết nối CSDL **Supabase** (PostgreSQL) qua **REST API** với Retrofit2 + OkHttp3 + Gson.
- Kiến trúc **DAO Pattern + Controller** xử lý logic nghiệp vụ.
- Hỗ trợ 2 vai trò: **NguoiDung** (user) và **QuanLy** (admin).
- Toàn bộ giao tiếp người dùng, comment, message dùng **Tiếng Việt có dấu**.

## Phạm vi kiến thức được phép sử dụng
Chỉ sử dụng các kiến thức Android sau, KHÔNG dùng thư viện/khái niệm ngoài danh sách:
- **Ứng dụng**: Application, Activity, Activity Stack, Tasks, Life Cycle States.
- **Giao diện**: XML Layout (View/ViewGroup, Common Layouts), Toast, AlertDialog.
- **Common controls**: TextView, EditText, Button, CheckBox, RadioButton, ImageView, ScrollView.
- **Advanced controls**: ListView, Spinner, AutoComplete, GridView, Time/DatePicker, Tab selector, Menu, RecyclerView (cho danh sách phức tạp).
- **Custom layout, Webkit, Intent** (Explicit, Implicit, getResult).
- **Sự kiện**: onClick XML, inline, Activity bắt sự kiện, biến/lớp bắt sự kiện, View subclassing.
- **Lưu trữ**: File, XML Parser, **SharedPreferences** (lưu session), SQLite (cache cục bộ nếu cần), ContentProvider, lưu/khôi phục trạng thái.
- **Multi-thread, Broadcast Receiver, Service, Intent filter**.
- **Networking**: HTTP (StrictMode), gọi REST API Supabase.
- **Multimedia, Telephony, Location, Sensor, Battery, Security** (chỉ dùng khi chức năng yêu cầu).

## Cấu trúc thư mục bắt buộc
```text
app/src/main/
├── java/com/example/banve/
│   ├── models/          # Entity class (POJO + @SerializedName)
│   ├── network/         # SupabaseClient, ApiService, ApiCallback
│   ├── dao/             # CRUD qua Supabase REST API
│   ├── controllers/     # Logic nghiệp vụ
│   ├── activities/
│   │   ├── admin/
│   │   └── user/
│   ├── fragments/
│   │   ├── admin/
│   │   └── user/
│   ├── adapters/        # Adapter cho RecyclerView/ListView
│   ├── utils/           # Session, TienIch, DinhDangTien, MaHoa
│   ├── config/          # SupabaseConfig (URL, KEY)
│   └── MainActivity.java
└── res/
    ├── layout/          # Tất cả XML layout nằm trực tiếp tại đây
    ├── drawable/
    ├── values/
    ├── menu/
    └── mipmap/
```

## Quy tắc đặt tên (BẮT BUỘC)

### Database (Supabase – PostgreSQL)
- Tên bảng/cột: **PascalCase** tiếng Việt không dấu. VD: `KhachHang`, `MaKhachHang`, `GiaVe`, `NgayBan`.
- Khóa chính: `Ma` + tên bảng. VD: `MaKhachHang`, `MaHoaDon`, `MaVe`.

### Java class
| Loại | Quy tắc | Ví dụ |
|------|---------|-------|
| Model | PascalCase, có getter/setter | `KhachHang`, `Ve`, `HoaDon` |
| DAO | `[TenBang]DAO` | `KhachHangDAO`, `VeDAO`, `HoaDonDAO` |
| Controller | `[Ten]Controller` | `KhachHangController`, `ThanhToanController` |
| Activity | `[Ten]Activity` | `DangNhapActivity`, `QuanLyVeActivity` |
| Fragment | `[Ten]Fragment` | `DanhSachVeFragment`, `ThongKeFragment` |
| Adapter | `[Ten]Adapter` | `VeAdapter`, `HoaDonAdapter` |
| Utils | PascalCase tiếng Việt | `Session`, `TienIch`, `DinhDangTien`, `MaHoa` |

### Method & biến
- Method: **camelCase** tiếng Việt không dấu. VD: `layDanhSachKhachHang()`, `themKhachHang()`, `kiemTraDangNhap()`, `tinhTongTien()`, `layTheoMa()`.
- Biến/tham số: camelCase tiếng Việt không dấu. VD: `maKhachHang`, `tongTien`, `danhSachVe`.

### Layout XML — Tên file
Android chỉ compile XML nằm trực tiếp trong `res/layout`, vì vậy **KHÔNG tạo thư mục con** như `res/layout/user/activity` hoặc `res/layout/admin/activity`.

Dùng tiền tố vai trò để dễ quản lý:

| Nhóm | Tiền tố | Ví dụ |
|------|---------|-------|
| User Activity | `user_activity_` | `user_activity_dang_nhap.xml`, `user_activity_chat_ai.xml` |
| User Fragment | `user_fragment_` | `user_fragment_lich_su.xml` |
| User Dialog | `user_dialog_` | `user_dialog_doi_mat_khau.xml` |
| User Item | `user_item_` | `user_item_ve.xml`, `user_item_hoa_don.xml` |
| Admin Activity | `admin_activity_` | `admin_activity_dashboard_quan_ly.xml` |
| Admin Fragment | `admin_fragment_` | `admin_fragment_tong_quan.xml` |
| Admin Dialog | `admin_dialog_` | `admin_dialog_them_ve.xml` |
| Admin Item | `admin_item_` | `admin_item_thong_ke.xml` |
| Chung | giữ tên mô tả chung | `activity_main.xml` |

Khi gọi layout trong Java phải dùng đúng tên mới. VD: `R.layout.user_activity_dang_nhap`, `R.layout.admin_fragment_tong_quan`.

### View ID trong XML (camelCase, có tiền tố)
| Tiền tố | View | Ví dụ |
|---------|------|-------|
| `btn` | Button | `btnLuu`, `btnDangNhap`, `btnThanhToan` |
| `edt` | EditText | `edtTaiKhoan`, `edtMatKhau` |
| `lbl` | TextView | `lblTieuDe`, `lblGiaVe`, `lblThongBao` |
| `spn` | Spinner | `spnLoaiVe` |
| `rcv` | RecyclerView | `rcvDanhSachVe` |
| `lsv` | ListView | `lsvHoaDon` |
| `dtp` | DatePicker | `dtpNgaySuDung` |
| `rad` | RadioButton | `radNam`, `radNu` |
| `chk` | CheckBox | `chkGhiNhoMatKhau` |
| `img` | ImageView | `imgAnhVe` |
| `pgb` | ProgressBar | `pgbDangTai` |
| `swt` | Switch | `swtTrangThai` |
| `lay` | Layout container | `layHeader`, `layThanhToan` |

## Quy ước kết nối Supabase
- File `config/SupabaseConfig.java` chứa hằng số:
  - `SUPABASE_URL` (ví dụ: `https://xxx.supabase.co`)
  - `SUPABASE_KEY` (anon key)
- `network/SupabaseClient.java`: singleton Retrofit, tự gắn header:
  - `apikey: <SUPABASE_KEY>`
  - `Authorization: Bearer <SUPABASE_KEY>`
  - `Content-Type: application/json`
  - `Prefer: return=representation` (cho POST/PATCH)
- `network/ApiService.java`: interface định nghĩa endpoint `rest/v1/<TenBang>` với annotation Retrofit (`@GET`, `@POST`, `@PATCH`, `@DELETE`).
- `network/ApiCallback.java`: interface chung `onSuccess(T data)` / `onError(String thongBao)`.
- DAO **KHÔNG** trả về trực tiếp – luôn dùng callback bất đồng bộ.

## Quy ước Model
- POJO + getter/setter Java.
- Mỗi field map sang cột Supabase bằng `@SerializedName("TenCot")`.
- Không đặt logic trong model.

## Format dữ liệu hiển thị
- **Số tiền**: dùng `DinhDangTien.dinhDang(tongTien)` → `String.format("%,.0f VNĐ", tongTien)`. VD: `120000` → `120,000 VNĐ`.
- **Ngày**: dùng `SimpleDateFormat("dd/MM/yyyy")` hoặc `"dd/MM/yyyy HH:mm"`.
- **Mật khẩu**: mã hóa **MD5** trước khi gửi lên Supabase (class `utils/MaHoa.java`).

## Quy ước thông báo
- Dùng `Toast` cho thông báo ngắn (thành công, lỗi nhập liệu).
- Dùng `AlertDialog` cho xác nhận, lỗi nghiêm trọng, thông tin chi tiết.
- Nội dung **Tiếng Việt có dấu**. VD: "Đăng nhập thành công", "Mật khẩu không đúng", "Bạn có chắc muốn xoá vé này?".

## Quy ước Session
- Class `utils/Session.java` (static):
  - `NguoiDung nguoiDungHienTai`
  - `void dangNhap(NguoiDung nd)`
  - `void dangXuat()`
  - `boolean laQuanLy()` → kiểm tra `VaiTro.equals("QuanLy")`
  - `boolean dangDangNhap()`
- Lưu session bền vững bằng **SharedPreferences** (`Session.luuLocal()` / `Session.khoiPhuc()`).

## Quy ước phân quyền điều hướng
- `MainActivity` kiểm tra session:
  - Chưa đăng nhập → `DangNhapActivity`.
  - Là `NguoiDung` → `DashboardNguoiDungActivity`.
  - Là `QuanLy` → `DashboardQuanLyActivity`.
- Mỗi Activity admin phải kiểm tra `Session.laQuanLy()` ở `onCreate()`, nếu không thì `finish()` và Toast cảnh báo.

## Quy ước Networking
- Bật `StrictMode` ở `MainActivity` (nếu cần debug).
- Mọi request HTTP chạy trên **background thread** (Retrofit `enqueue` đã async sẵn).
- Cập nhật UI luôn trong main thread (callback của Retrofit đã chạy main thread).
- Hiển thị `ProgressBar` (`pgbDangTai`) khi đang gọi API.

## Quy ước commit & PR
- Commit message tiếng Việt, ngắn gọn, có dấu.
- Một PR/commit nên gói trọn 1 chức năng (1 Activity hoặc 1 controller).

## Cấm
- KHÔNG dùng Kotlin.
- KHÔNG dùng thư viện ngoài phạm vi: Room, Hilt, Dagger, Coroutines, RxJava, Jetpack Compose.
- KHÔNG viết SQL trực tiếp – mọi truy vấn đi qua Supabase REST API.
- KHÔNG hard-code URL/Key trong DAO – luôn lấy từ `SupabaseConfig`.
- KHÔNG để mật khẩu plaintext – luôn MD5 trước khi lưu/so sánh.
- Không đặt tên Tiếng Việt có dấu trong code.
- Không trả lời ngôn ngữ khác ngoài tiếng Việt và phải có dấu.
