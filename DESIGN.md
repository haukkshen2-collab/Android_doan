---

version: alpha
name: Android_doan - Quan ly ban ve khu du lich
description: He thong thiet ke giao dien cho project Android_doan, ung dung Android Java quan ly ban ve khu du lich (Tong the xanh ngoc dam).

colors:
  primary: "#0F766E"
  primary-dark: "#115E59"
  primary-light: "#CCFBF1"
  primary-soft: "#F0FDFA"

  accent: "#F97316"
  accent-dark: "#EA580C"
  accent-light: "#FFEDD5"

  positive: "#16A34A"
  positive-dark: "#15803D"
  positive-light: "#DCFCE7"

  highlight: "#F59E0B"
  highlight-dark: "#D97706"
  highlight-light: "#FEF3C7"

  info: "#0EA5E9"
  info-light: "#E0F2FE"

  background: "#F8FAFC"
  surface: "#FFFFFF"
  surface-card: "#FFFFFF"
  surface-soft: "#F0FDFA"

  text-primary: "#0F172A"
  text-secondary: "#475569"
  text-muted: "#94A3B8"
  text-on-primary: "#FFFFFF"
  text-on-gradient: "#FFFFFF"

  border: "#E2E8F0"
  border-light: "#F1F5F9"
  divider: "#E2E8F0"

  success: "#16A34A"
  success-light: "#DCFCE7"

  warning: "#F59E0B"
  warning-light: "#FEF3C7"

  danger: "#DC2626"
  danger-light: "#FEE2E2"

typography:
app-title:
fontFamily: sans-serif
fontSize: 24sp
fontWeight: 700
lineHeight: 1.25

screen-title:
fontFamily: sans-serif
fontSize: 22sp
fontWeight: 700
lineHeight: 1.25

section-title:
fontFamily: sans-serif
fontSize: 18sp
fontWeight: 700
lineHeight: 1.3

card-title:
fontFamily: sans-serif
fontSize: 16sp
fontWeight: 700
lineHeight: 1.35

price:
fontFamily: sans-serif
fontSize: 18sp
fontWeight: 700
lineHeight: 1.25

body:
fontFamily: sans-serif
fontSize: 14sp
fontWeight: 400
lineHeight: 1.5

body-small:
fontFamily: sans-serif
fontSize: 13sp
fontWeight: 400
lineHeight: 1.45

caption:
fontFamily: sans-serif
fontSize: 12sp
fontWeight: 400
lineHeight: 1.35

label:
fontFamily: sans-serif
fontSize: 13sp
fontWeight: 600
lineHeight: 1.3

button:
fontFamily: sans-serif
fontSize: 14sp
fontWeight: 700
lineHeight: 1.2

spacing:
xxs: 2dp
xs: 4dp
sm: 8dp
md: 12dp
lg: 16dp
xl: 20dp
xxl: 24dp
xxxl: 32dp
huge: 48dp

rounded:
xs: 4dp
sm: 8dp
md: 12dp
lg: 16dp
xl: 20dp
xxl: 24dp
xxxl: 32dp
pill: 100dp
circle: 999dp

elevation:
xs: 1dp
sm: 2dp
md: 4dp
lg: 8dp
xl: 12dp

components:
app-background:
backgroundColor: "{colors.background}"

card-default:
backgroundColor: "{colors.surface-card}"
textColor: "{colors.text-primary}"
borderColor: "{colors.border}"
rounded: "{rounded.lg}"
padding: "{spacing.lg}"
elevation: "{elevation.sm}"

card-ticket:
backgroundColor: "{colors.surface-card}"
textColor: "{colors.text-primary}"
borderColor: "{colors.border}"
rounded: "{rounded.xl}"
padding: "{spacing.lg}"
elevation: "{elevation.sm}"

card-voucher:
backgroundColor: "{colors.accent-light}"
textColor: "{colors.text-primary}"
borderColor: "{colors.accent}"
rounded: "{rounded.xl}"
padding: "{spacing.lg}"
elevation: "{elevation.sm}"

card-report:
backgroundColor: "{colors.surface-card}"
textColor: "{colors.text-primary}"
borderColor: "{colors.border}"
rounded: "{rounded.xl}"
padding: "{spacing.lg}"
elevation: "{elevation.sm}"

card-ai:
backgroundColor: "{colors.primary-light}"
textColor: "{colors.text-primary}"
borderColor: "{colors.primary}"
rounded: "{rounded.xl}"
padding: "{spacing.lg}"
elevation: "{elevation.sm}"

button-primary:
backgroundColor: "{colors.primary}"
textColor: "{colors.text-on-primary}"
rounded: "{rounded.md}"
paddingHorizontal: "{spacing.xl}"
paddingVertical: "{spacing.sm}"
minHeight: 48dp

button-cta:
backgroundColor: "{colors.accent}"
textColor: "{colors.text-on-primary}"
rounded: "{rounded.md}"
paddingHorizontal: "{spacing.xl}"
paddingVertical: "{spacing.sm}"
minHeight: 48dp

button-secondary:
backgroundColor: "{colors.surface}"
textColor: "{colors.primary-dark}"
borderColor: "{colors.primary}"
rounded: "{rounded.md}"
paddingHorizontal: "{spacing.xl}"
paddingVertical: "{spacing.sm}"
minHeight: 48dp

button-danger:
backgroundColor: "{colors.danger}"
textColor: "{colors.text-on-primary}"
rounded: "{rounded.md}"
paddingHorizontal: "{spacing.xl}"
paddingVertical: "{spacing.sm}"
minHeight: 48dp

input-default:
backgroundColor: "{colors.surface}"
textColor: "{colors.text-primary}"
hintColor: "{colors.text-muted}"
borderColor: "{colors.border}"
rounded: "{rounded.md}"
padding: "{spacing.lg}"
minHeight: 48dp

chip-category:
backgroundColor: "{colors.primary-light}"
textColor: "{colors.primary-dark}"
rounded: "{rounded.pill}"
paddingHorizontal: "{spacing.lg}"
paddingVertical: "{spacing.xs}"

badge-hot:
backgroundColor: "{colors.accent}"
textColor: "{colors.text-on-primary}"
rounded: "{rounded.pill}"
paddingHorizontal: "{spacing.sm}"
paddingVertical: "{spacing.xs}"

badge-success:
backgroundColor: "{colors.success-light}"
textColor: "{colors.success}"
rounded: "{rounded.pill}"
paddingHorizontal: "{spacing.sm}"
paddingVertical: "{spacing.xs}"

badge-warning:
backgroundColor: "{colors.warning-light}"
textColor: "{colors.warning}"
rounded: "{rounded.pill}"
paddingHorizontal: "{spacing.sm}"
paddingVertical: "{spacing.xs}"

badge-danger:
backgroundColor: "{colors.danger-light}"
textColor: "{colors.danger}"
rounded: "{rounded.pill}"
paddingHorizontal: "{spacing.sm}"
paddingVertical: "{spacing.xs}"

android_resources:
colors:
primary: "@color/mauTimChinh"
primary-dark: "@color/mauTimDam"
primary-light: "@color/mauTimNhat"
primary-soft: "@color/mauTimSieuNhat"
accent: "@color/mauCamChinh"
accent-dark: "@color/mauCamDam"
accent-light: "@color/mauCamNhat"
positive: "@color/mauXanhNgoc"
positive-dark: "@color/mauXanhNgocDam"
positive-light: "@color/mauXanhNgocNhat"
highlight: "@color/mauVangChinh"
highlight-light: "@color/mauVangNhat"
background: "@color/mauNenChinh"
surface: "@color/mauNenPhu"
card: "@color/mauNenCard"
text-primary: "@color/mauChuChinh"
text-secondary: "@color/mauChuPhu"
text-muted: "@color/mauChuMo"
border: "@color/mauVien"
divider: "@color/mauDivider"
success: "@color/mauThanhCong"
success-light: "@color/mauThanhCongNhat"
warning: "@color/mauCanhBao"
warning-light: "@color/mauCanhBaoNhat"
danger: "@color/mauNguyHiem"
danger-light: "@color/mauNguyHiemNhat"

dimens:
space-xs: "@dimen/space_xs"
space-sm: "@dimen/space_sm"
space-md: "@dimen/space_md"
space-lg: "@dimen/space_lg"
space-xl: "@dimen/space_xl"
radius-md: "@dimen/radius_md"
radius-lg: "@dimen/radius_lg"
radius-xl: "@dimen/radius_xl"
radius-pill: "@dimen/radius_pill"
text-body: "@dimen/text_body"
text-body-sm: "@dimen/text_body_sm"
button-height: "@dimen/btn_height"
----------------------------------

# Hệ thống thiết kế cho project Android_doan

## Tổng quan

File này định nghĩa phong cách giao diện cho project Android_doan.

Ứng dụng là app Android Java quản lý bán vé khu du lịch, gồm phần người dùng và phần quản trị.

Thiết kế phải bám theo resource hiện có của project, đặc biệt là:

* `colors.xml`
* `dimens.xml`
* `styles.xml`
* `themes.xml`
* Các drawable XML hiện có
* Các layout đã có trong `res/layout`

Không tự ý đổi toàn bộ phong cách giao diện nếu không có yêu cầu rõ.

## Quy tắc bắt buộc về tiếng Việt

Toàn bộ chữ hiển thị trên giao diện phải dùng tiếng Việt có dấu.

Bắt buộc dùng tiếng Việt cho:

* Tiêu đề màn hình
* Nút bấm
* Gợi ý nhập liệu
* Thông báo lỗi
* Thông báo thành công
* Dialog
* Toast
* Menu
* Tab
* Badge trạng thái
* Empty state
* Nội dung card
* Nội dung AI
* Nội dung báo cáo

Không dùng tiếng Anh trên giao diện người dùng.

Sai:

```text
Login
Register
Book now
Payment
Apply
Cancel
Submit
Search
No data
Total amount
```

Đúng:

```text
Đăng nhập
Đăng ký
Đặt vé
Thanh toán
Áp dụng
Hủy
Xác nhận
Tìm kiếm
Không có dữ liệu
Tổng tiền
```

Ngoại lệ được phép giữ tiếng Anh:

* Tên class
* Tên biến
* Tên method
* Tên package
* Tên thư viện
* Email
* API endpoint
* Mã hóa đơn
* Mã voucher
* Tên model AI

Nếu tạo dữ liệu mẫu hiển thị trên giao diện, dữ liệu mẫu cũng phải là tiếng Việt có dấu.

## Phong cách thị giác

Phong cách chính của app là xanh ngọc đậm hiện đại, cam san hô nổi bật và xanh lá tích cực.

Tổng thể giao diện phải có cảm giác:

* Sạch
* Hiện đại
* Thân thiện
* Dễ đọc
* Phù hợp app du lịch
* Phù hợp app bán vé
* Dễ giải thích trong báo cáo đồ án
* Không quá phức tạp

Không đổi sang theme khác nếu không được yêu cầu.

## Màu sắc

### Màu xanh ngọc đậm (Primary)

Màu xanh ngọc đậm là màu thương hiệu chính.

Dùng cho:

* Header
* Dashboard
* Tiêu đề nổi bật
* Navigation
* Tab đang chọn
* Nút chính
* Icon chính
* Giao diện quản trị

Resource tương ứng:

```text
@color/mauTimChinh      → #0F766E (xanh ngọc đậm)
@color/mauTimDam        → #115E59 (xanh ngọc rất đậm)
@color/mauTimNhat       → #CCFBF1 (xanh ngọc nhạt)
@color/mauTimSieuNhat   → #F0FDFA (xanh ngọc rất nhạt)
@color/mauTimTrung      → #14B8A6 (teal sáng)
```

### Màu cam

Màu cam dùng cho hành động nổi bật và nội dung khuyến mãi.

Dùng cho:

* Đặt vé
* Thanh toán
* Voucher
* Khuyến mãi
* Nhãn HOT
* Tổng tiền cần thanh toán
* CTA quan trọng

Resource tương ứng:

```text
@color/mauCamChinh
@color/mauCamDam
@color/mauCamNhat
```

Không dùng cam cho mọi nút trên màn hình, chỉ dùng cho hành động cần nhấn mạnh.

### Màu xanh lá (Success)

Màu xanh lá dùng cho trạng thái tích cực.

Dùng cho:

* Thành công
* Đã thanh toán
* Đang hoạt động
* Gợi ý tốt
* AI đề xuất phù hợp

Resource tương ứng:

```text
@color/mauXanhNgoc      → #16A34A (xanh lá)
@color/mauXanhNgocDam   → #15803D (xanh lá đậm)
@color/mauXanhNgocNhat  → #DCFCE7 (xanh lá nhạt)
```

### Màu trạng thái

Dùng màu trạng thái rõ ràng:

```text
@color/mauThanhCong      -> Thành công, đã thanh toán
@color/mauCanhBao        -> Chờ xử lý, cảnh báo
@color/mauNguyHiem       -> Lỗi, xóa, hủy, hết hạn
@color/mauXanhDuong      -> Thông tin
```

Không chỉ dùng màu để báo trạng thái. Luôn phải có chữ đi kèm.

Ví dụ:

```text
Đã thanh toán
Chờ thanh toán
Đã hủy
Hết hạn
Đang hoạt động
```

## Kiểu chữ

Dùng font mặc định Android `sans-serif`.

Không thêm font ngoài nếu không cần.

Text phải dễ đọc trên điện thoại.

Gợi ý phân cấp:

* Tiêu đề app: 24sp, đậm
* Tiêu đề màn hình: 22sp, đậm
* Tiêu đề section: 18sp, đậm
* Tiêu đề card: 16sp, đậm
* Giá tiền: 18sp, đậm, màu cam hoặc xanh ngọc đậm
* Nội dung thường: 14sp
* Ghi chú nhỏ: 12sp hoặc 13sp

Không làm chữ quá nhỏ.

Không để text bị cắt.

Không để text chồng lên nhau.

## Khoảng cách

Dùng spacing từ `dimens.xml`.

Gợi ý:

```text
space_xs   -> khoảng cách rất nhỏ
space_sm   -> khoảng cách nhỏ
space_md   -> khoảng cách vừa
space_lg   -> padding cơ bản trong card
space_xl   -> khoảng cách giữa nhóm nội dung
space_xxl  -> khoảng cách giữa section lớn
```

Không hardcode nhiều giá trị như `16dp`, `24dp`, `12dp` trực tiếp trong layout nếu đã có dimen tương ứng.

## Bo góc

Dùng bo góc mềm.

Gợi ý:

```text
radius_md   -> ô nhập liệu, nút nhỏ
radius_lg   -> card thường
radius_xl   -> card nổi bật
radius_pill -> badge, chip, nhãn trạng thái
```

Không trộn quá nhiều kiểu bo góc trên cùng một màn hình.

## Đổ bóng và chiều sâu

Dùng elevation nhẹ.

Không dùng shadow quá mạnh.

Gợi ý:

* Card thường: elevation nhỏ
* Card báo cáo: elevation nhỏ hoặc vừa
* Dialog: elevation vừa
* Nút chính: có thể dùng selector/drawable sẵn

## Layout

Layout phải đơn giản, dễ sửa.

Dùng:

* `LinearLayout`
* `ConstraintLayout`
* `ScrollView`
* `NestedScrollView`
* `RecyclerView`
* `CardView` nếu project đang dùng
* `TextView`
* `EditText`
* `Button`
* `ImageView`
* `Spinner`
* `ProgressBar`

Không dùng Compose.

Không thêm thư viện UI mới nếu không cần.

Mọi file layout phải nằm trực tiếp trong:

```text
app/src/main/res/layout/
```

Không tạo thư mục con trong `res/layout`.

## Quy ước đặt tên layout

Giữ đúng tiền tố hiện tại.

Người dùng:

```text
user_activity_*.xml
user_dialog_*.xml
user_item_*.xml
user_fragment_*.xml
```

Quản trị:

```text
admin_activity_*.xml
admin_dialog_*.xml
admin_item_*.xml
admin_fragment_*.xml
```

Chung:

```text
activity_main.xml
```

Không đổi tên layout nếu Java đang dùng.

## Quy ước đặt ID

Dùng ID camelCase, có tiền tố theo View.

Ví dụ:

```text
btnDangNhap
btnDatVe
btnThanhToan
btnApDungVoucher
edtTaiKhoan
edtMatKhau
edtMaVoucher
lblTieuDe
lblGiaVe
lblTongTien
lblTrangThai
imgAnhVe
rcvDanhSachVe
rcvDanhSachHoaDon
spnLoaiVe
pgbDangTai
layHeader
layNoiDung
```

Không đổi ID nếu Java đang dùng `findViewById`.

## Button

### Nút chính

Dùng cho hành động chính.

Ví dụ:

```text
Đăng nhập
Đặt vé
Thanh toán
Xác nhận
Lưu
Cập nhật
```

Ưu tiên màu xanh ngọc đậm hoặc drawable nút chính hiện có.

### Nút CTA

Dùng cho hành động cần nổi bật mạnh.

Ví dụ:

```text
Đặt vé ngay
Thanh toán
Áp dụng voucher
Chọn voucher tốt nhất
```

Ưu tiên màu cam.

### Nút phụ

Dùng cho hành động ít quan trọng.

Ví dụ:

```text
Quay lại
Hủy
Xem chi tiết
Chọn lại
```

Có thể dùng nền trắng, viền xanh ngọc, chữ xanh ngọc đậm.

### Nút nguy hiểm

Dùng cho:

```text
Xóa
Hủy đơn
Khóa tài khoản
```

Dùng màu đỏ hoặc style danger hiện có.

## Card vé

Card vé phải hiển thị rõ:

* Tên vé
* Mô tả ngắn
* Loại vé
* Giá vé
* Trạng thái
* Nút đặt vé hoặc xem chi tiết

Giá vé phải nổi bật.

Ví dụ nội dung:

```text
Vé tham quan trọn gói
Tham quan toàn bộ khu du lịch trong ngày
250.000 VNĐ
Đang hoạt động
[Đặt vé]
```

## Card loại vé

Card loại vé nên hiển thị:

* Tên loại vé
* Mô tả ngắn
* Số lượng vé nếu có
* Trạng thái nếu có

## Card voucher

Card voucher dùng màu cam nhạt hoặc điểm nhấn cam.

Nên hiển thị:

* Mã voucher
* Mức giảm
* Điều kiện áp dụng
* Ngày hết hạn
* Trạng thái
* Nút áp dụng

Ví dụ:

```text
SUMMER20
Giảm 20%
Áp dụng cho đơn từ 500.000 VNĐ
Hết hạn: 30/07/2026
[Áp dụng]
```

## Card giỏ hàng

Nên hiển thị:

* Tên vé
* Ngày sử dụng
* Số lượng
* Đơn giá
* Thành tiền
* Nút sửa
* Nút xóa

Tổng tiền phải nằm ở cuối màn hình hoặc khu vực dễ thấy.

## Màn hình thanh toán

Màn hình thanh toán phải rõ ràng và đáng tin cậy.

Nên hiển thị:

* Tóm tắt hóa đơn
* Danh sách vé
* Tổng tiền ban đầu
* Voucher đang dùng
* Số tiền giảm
* Tổng tiền cần thanh toán
* Phương thức thanh toán
* Nút xác nhận thanh toán

Tổng tiền cần thanh toán phải nổi bật nhất.

Nếu là thanh toán SePay, hiển thị rõ:

* Số tiền cần chuyển
* Nội dung chuyển khoản
* QR nếu có
* Trạng thái chờ xác nhận
* Hướng dẫn ngắn bằng tiếng Việt

## Card hóa đơn

Nên hiển thị:

* Mã hóa đơn
* Ngày đặt
* Trạng thái thanh toán
* Tổng tiền
* Nút xem chi tiết

Trạng thái phải có cả màu và chữ.

Ví dụ:

```text
Đã thanh toán
Chưa thanh toán
Đang chờ xác nhận
Đã hủy
```

## Màn hình chat AI

Chat AI phải đơn giản.

Nên có:

* Danh sách tin nhắn
* Bong bóng tin nhắn người dùng
* Bong bóng tin nhắn AI
* Ô nhập nội dung
* Nút gửi
* Trạng thái đang tải nếu AI đang phản hồi

AI phải được định vị là trợ lý tư vấn vé khu du lịch.

Ví dụ placeholder:

```text
Nhập câu hỏi về vé, giá hoặc voucher...
```

Ví dụ nút:

```text
Gửi
```

Không dùng:

```text
Ask AI
Send
Type a message
```

## AI gợi ý voucher

Nếu có khu vực AI gợi ý voucher, hiển thị bằng card nhỏ.

Nên có:

* Mã voucher được gợi ý
* Mức giảm
* Lý do gợi ý
* Nút áp dụng

Ví dụ:

```text
AI gợi ý voucher tốt nhất
Voucher SUMMER20 giúp bạn giảm 50.000 VNĐ cho đơn hàng này.
[Áp dụng voucher]
```

## AI phân tích doanh thu

Hiển thị bằng card phân tích ngắn.

Nên có:

* Tóm tắt doanh thu
* Vé bán chạy
* Xu hướng
* Gợi ý hành động

Không viết đoạn quá dài trong giao diện.

Ví dụ:

```text
Doanh thu tháng này tăng 12% so với tháng trước. Vé tham quan trọn gói đang bán tốt nhất. Nên ưu tiên hiển thị vé này ở trang chủ.
```

## Dashboard quản lý

Dashboard quản lý nên dùng các card thống kê.

Nên có:

* Tổng doanh thu
* Số vé đã bán
* Số hóa đơn
* Số người dùng
* Vé bán chạy
* Voucher đang hoạt động

Có thể dùng gradient xanh ngọc hoặc gradient xanh ngọc-teal cho card nổi bật nếu project đã có drawable tương ứng.

Không làm dashboard quá màu mè.

## Màn hình thống kê

Màn hình thống kê phải ưu tiên đọc dữ liệu.

Nên có:

* Bộ lọc ngày
* Bộ lọc tháng
* Tổng doanh thu
* Tổng số vé bán
* Danh sách doanh thu theo ngày
* Danh sách doanh thu theo tháng
* Danh sách vé bán chạy theo loại vé

Không thêm biểu đồ mới nếu project chưa có thư viện biểu đồ.

## Dialog

Dialog phải ngắn gọn.

Tiêu đề rõ.

Nút rõ.

Ví dụ:

```text
Xác nhận xóa
Bạn có chắc muốn xóa vé này không?
[Hủy] [Xóa]
```

Không dùng tiếng Anh trong dialog.

## Empty state

Khi không có dữ liệu, hiển thị thông báo tiếng Việt.

Ví dụ:

```text
Chưa có vé nào
Không tìm thấy hóa đơn phù hợp
Chưa có voucher khả dụng
Giỏ hàng của bạn đang trống
```

Có thể thêm icon nếu project có sẵn vector phù hợp.

## Loading state

Khi đang tải dữ liệu, dùng ProgressBar.

Text nếu có phải là tiếng Việt.

Ví dụ:

```text
Đang tải dữ liệu...
Đang xử lý thanh toán...
AI đang phân tích...
```

## Lỗi và thông báo

Thông báo lỗi phải dễ hiểu.

Ví dụ:

```text
Không thể tải danh sách vé
Vui lòng kiểm tra kết nối mạng
Mã voucher không hợp lệ
Thanh toán chưa được xác nhận
Đã xảy ra lỗi, vui lòng thử lại
```

Không hiển thị lỗi kỹ thuật quá dài cho người dùng cuối.

## Không được làm

Không đổi theme chính sang màu khác nếu không được yêu cầu.

Không dùng tiếng Anh trên giao diện.

Không hardcode màu nếu đã có màu trong `colors.xml`.

Không hardcode kích thước nếu đã có trong `dimens.xml`.

Không tạo layout trong thư mục con.

Không đổi ID nếu Java đang dùng.

Không đổi tên layout nếu Java đang dùng.

Không thêm Compose.

Không thêm thư viện UI mới nếu không được yêu cầu.

Không sửa DAO, Controller, Supabase config nếu task chỉ là giao diện.

Không làm app crash.

## Nên làm

Dùng lại resource hiện có.

Giữ phong cách xanh ngọc đậm / cam / xanh lá.

Giữ layout đơn giản.

Giữ tiếng Việt có dấu.

Giá vé và tổng tiền phải nổi bật.

Trạng thái phải rõ chữ.

Mỗi màn hình nên có một hành động chính.

Admin tập trung vào dữ liệu.

User tập trung vào đặt vé, thanh toán và hóa đơn.

AI hiển thị ngắn gọn, đúng ngữ cảnh bán vé khu du lịch.
