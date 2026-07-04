---

name: android-ticket-ui
description: Sử dụng skill này khi tạo hoặc chỉnh sửa giao diện Android XML cho app quản lý bán vé khu du lịch trong project Android_doan.
------------------------------------------------------------------------------------------------------------------------------------------

# Skill giao diện Android cho project Android_doan

## Mục đích

Skill này dùng để hướng dẫn Antigravity tạo và chỉnh sửa giao diện cho project Android_doan.

Project là ứng dụng Android quản lý bán vé khu du lịch, viết bằng Java thuần, dùng Supabase qua REST API, Retrofit, DAO Pattern và Controller.

Skill này chỉ tập trung vào phần giao diện Android XML, resource, style, drawable và cách hiển thị nội dung cho người dùng.

## Quy tắc bắt buộc đầu tiên

Trước khi sửa bất kỳ giao diện nào, bắt buộc đọc các file sau:

1. `AGENTS.md`
2. `DESIGN.md`
3. File layout XML đang được sửa
4. Java Activity, Fragment hoặc Adapter đang dùng layout đó

Nếu quy tắc trong `AGENTS.md` và `DESIGN.md` khác nhau, ưu tiên theo thứ tự:

1. Không làm crash app
2. Không làm hỏng Java hiện có
3. Không làm hỏng encoding tiếng Việt
4. Tuân thủ `AGENTS.md`
5. Tuân thủ `DESIGN.md`

## Ràng buộc bắt buộc về tiếng Việt

Toàn bộ nội dung hiển thị trên giao diện phải dùng tiếng Việt có dấu.

Bắt buộc dùng tiếng Việt cho:

* TextView
* Button
* EditText hint
* Dialog
* Toast
* Menu
* Tab
* Badge trạng thái
* Empty state
* Loading message nếu có
* Nội dung card vé
* Nội dung card voucher
* Nội dung hóa đơn
* Nội dung báo cáo
* Nội dung AI hiển thị trên giao diện

Không dùng tiếng Anh cho giao diện người dùng.

Ví dụ sai:

* `Login`
* `Register`
* `Book now`
* `Payment`
* `Apply`
* `Cancel`
* `Submit`
* `No data`
* `Total amount`
* `Search ticket`

Ví dụ đúng:

* `Đăng nhập`
* `Đăng ký`
* `Đặt vé`
* `Thanh toán`
* `Áp dụng`
* `Hủy`
* `Xác nhận`
* `Không có dữ liệu`
* `Tổng tiền`
* `Tìm kiếm vé`

Ngoại lệ được phép giữ tiếng Anh:

* Tên class Java
* Tên method
* Tên biến
* Tên package
* Tên thư viện
* API endpoint
* Email
* Mã hóa đơn
* Mã voucher
* Tên model AI
* Dữ liệu gốc trả về từ API nếu không thể dịch

Nếu phát hiện layout cũ có text tiếng Anh, hãy đổi sang tiếng Việt khi đang chỉnh sửa layout đó.

## Ràng buộc về encoding

Bắt buộc giữ đúng Unicode UTF-8.

Không làm lỗi font tiếng Việt.

Không để chữ bị mất dấu.

Không dùng tiếng Việt không dấu cho text hiển thị trên giao diện.

Tên file, tên biến, tên id, tên class có thể dùng tiếng Việt không dấu theo quy ước project.

## Công nghệ được phép dùng

Project này dùng Android Java/XML.

Được dùng:

* Java
* XML layout
* Activity
* Fragment
* RecyclerView
* ListView nếu layout cũ đang dùng
* Adapter
* Toast
* AlertDialog
* SharedPreferences
* Retrofit callback
* ProgressBar
* Drawable XML
* `colors.xml`
* `dimens.xml`
* `styles.xml`
* `themes.xml`

Không tự ý thêm:

* Kotlin
* Jetpack Compose
* Room
* Hilt
* Dagger
* RxJava
* Coroutines
* Navigation Component phức tạp
* Thư viện UI mới
* Thư viện biểu đồ mới
* Material Components nếu project chưa cấu hình sẵn

Nếu cần biểu đồ cho báo cáo, ưu tiên hiển thị bằng card, danh sách hoặc bảng đơn giản. Chỉ thêm thư viện chart khi người dùng yêu cầu rõ.

## Cấu trúc project cần tôn trọng

Không đổi package chính.

Không đổi cấu trúc thư mục chính.

Tôn trọng cấu trúc hiện có:

```text
app/src/main/java/com/example/banve/
├── activities/
│   ├── admin/
│   └── user/
├── adapters/
├── config/
├── controllers/
├── dao/
├── fragments/
│   └── admin/
├── models/
├── network/
├── utils/
└── MainActivity.java
```

Tất cả layout XML phải nằm trực tiếp trong:

```text
app/src/main/res/layout/
```

Không tạo thư mục con trong `res/layout`.

## Quy tắc đặt tên layout

Giữ đúng quy ước hiện tại của project.

Layout cho người dùng:

```text
user_activity_*.xml
user_dialog_*.xml
user_item_*.xml
user_fragment_*.xml
```

Layout cho quản trị:

```text
admin_activity_*.xml
admin_dialog_*.xml
admin_item_*.xml
admin_fragment_*.xml
```

Layout chung:

```text
activity_main.xml
```

Không tự ý đổi tên file layout nếu không cập nhật toàn bộ Java liên quan.

## Quy tắc đặt ID view

Dùng camelCase và tiền tố theo loại View.

Ví dụ:

```text
btnDangNhap
btnThanhToan
btnDatVe
edtTaiKhoan
edtMatKhau
lblTieuDe
lblGiaVe
lblThongBao
spnLoaiVe
rcvDanhSachVe
lsvHoaDon
imgAnhVe
pgbDangTai
layHeader
layNoiDung
```

Không đổi ID cũ nếu Java đang gọi bằng `findViewById`.

Nếu bắt buộc đổi ID, phải sửa Java tương ứng.

## Quy tắc resource

Bắt buộc ưu tiên dùng resource có sẵn.

Không hardcode màu nếu `colors.xml` đã có màu phù hợp.

Không hardcode kích thước nếu `dimens.xml` đã có dimen phù hợp.

Không tạo drawable trùng chức năng nếu đã có drawable tương đương.

Ưu tiên dùng:

```xml
@color/mauTimChinh
@color/mauTimDam
@color/mauCamChinh
@color/mauXanhNgoc
@color/mauNenChinh
@color/mauNenCard
@color/mauChuChinh
@color/mauChuPhu
@color/mauVien
@color/mauThanhCong
@color/mauCanhBao
@color/mauNguyHiem
```

Ưu tiên dùng:

```xml
@dimen/space_sm
@dimen/space_md
@dimen/space_lg
@dimen/space_xl
@dimen/radius_md
@dimen/radius_lg
@dimen/radius_xl
@dimen/text_body
@dimen/text_heading
@dimen/btn_height
```

Nếu thiếu resource, thêm vào đúng file:

```text
res/values/colors.xml
res/values/dimens.xml
res/values/styles.xml
res/drawable/
```

## Phong cách giao diện chính

Phong cách giao diện của project là:

* Xanh ngọc đậm làm màu thương hiệu chính
* Cam dùng cho nút hành động nổi bật và khuyến mãi
* Xanh lá dùng cho trạng thái tích cực
* Nền sáng, card trắng
* Bo góc mềm
* Card rõ ràng
* Giao diện thân thiện với khu du lịch
* Đơn giản, dễ hiểu, phù hợp đồ án Android Java

Không đổi style sang màu khác nếu không có yêu cầu.

## Quy tắc dùng màu

Dùng xanh ngọc đậm cho:

* Header
* Tiêu đề nổi bật
* Tab đang chọn
* Navigation chính
* Nút chính nếu màn hình không phải thanh toán/khuyến mãi
* Giao diện quản trị

Dùng cam cho:

* Nút đặt vé
* Nút thanh toán
* Voucher
* Khuyến mãi
* Nhãn HOT
* Nhãn siêu hời
* Tổng tiền cần thanh toán

Dùng xanh lá cho:

* Thành công
* Đã thanh toán
* Đang hoạt động
* Gợi ý tích cực
* AI gợi ý tốt

Dùng đỏ cho:

* Xóa
* Hủy
* Lỗi
* Hết hạn
* Thanh toán thất bại

Dùng vàng/cam nhạt cho:

* Chờ thanh toán
* Cảnh báo
* Nhắc nhở

Không dùng quá nhiều màu trên một màn hình.

## Quy tắc bố cục

Màn hình phải dễ đọc trên điện thoại Android.

Dùng `ScrollView` hoặc `NestedScrollView` cho form dài.

Dùng `RecyclerView` cho danh sách vé, voucher, hóa đơn, giỏ hàng, người dùng và báo cáo.

Không đặt chiều cao cố định quá nhiều làm cắt nội dung.

Ưu tiên `layout_marginStart` và `layout_marginEnd`, không dùng `left` và `right`.

Không để text chồng lên nhau.

Không để button quá nhỏ. Vùng chạm nên từ 48dp trở lên.

Mỗi màn hình nên có một hành động chính nổi bật.

## Quy tắc card

Dùng card cho:

* Vé
* Loại vé
* Voucher
* Hóa đơn
* Chi tiết hóa đơn
* Giỏ hàng
* Người dùng
* Báo cáo thống kê
* AI tư vấn
* AI phân tích doanh thu

Card nên có:

* Nền trắng
* Bo góc lớn
* Padding rõ ràng
* Viền hoặc shadow nhẹ
* Text chính rõ
* Text phụ màu nhẹ hơn

Không dùng cùng một drawable cho các loại card có ý nghĩa khác nhau nếu gây nhầm. Ví dụ không dùng drawable của item vé cho bong bóng chat AI.

## Màn hình người dùng

### Đăng nhập và đăng ký

Giữ giao diện đơn giản.

Phải có:

* Tên app
* Ô tài khoản/email
* Ô mật khẩu
* Nút đăng nhập hoặc đăng ký
* Link chuyển màn hình nếu có

Text phải là tiếng Việt.

Thông báo lỗi phải rõ:

```text
Vui lòng nhập tài khoản
Vui lòng nhập mật khẩu
Tài khoản hoặc mật khẩu không đúng
Đăng nhập thành công
```

### Dashboard người dùng

Nên hiển thị:

* Lời chào
* Danh mục vé
* Vé nổi bật
* Voucher nổi bật nếu có
* Lối tắt giỏ hàng
* Lối tắt lịch sử đơn hàng
* Lối tắt chat AI

Không nhồi quá nhiều dữ liệu trên một màn hình.

### Danh sách vé

Mỗi vé hiển thị dạng card.

Nên có:

* Tên vé
* Mô tả ngắn
* Giá vé
* Loại vé
* Trạng thái
* Nút `Đặt vé` hoặc `Xem chi tiết`

Giá vé phải nổi bật.

### Chi tiết vé

Nên có:

* Ảnh vé hoặc placeholder
* Tên vé
* Mô tả
* Giá người lớn
* Giá trẻ em
* Giá người cao tuổi nếu có
* Ngày sử dụng
* Số lượng
* Nút thêm vào giỏ hàng hoặc đặt vé

### Giỏ hàng

Nên có:

* Danh sách vé đã chọn
* Ngày sử dụng
* Số lượng
* Thành tiền
* Nút sửa
* Nút xóa
* Tổng tiền
* Nút thanh toán

Tổng tiền phải dễ thấy.

### Thanh toán

Nên có:

* Tóm tắt hóa đơn
* Danh sách vé
* Tổng tiền ban đầu
* Voucher
* Số tiền giảm
* Tổng tiền thanh toán
* Phương thức thanh toán
* Nút xác nhận

Nếu có SePay, màn hình phải hiển thị rõ:

* Số tiền cần chuyển
* Nội dung chuyển khoản
* Trạng thái chờ xác nhận
* Hướng dẫn ngắn bằng tiếng Việt

Không để người dùng mơ hồ ở bước thanh toán.

### Hóa đơn và lịch sử đơn hàng

Mỗi hóa đơn nên có:

* Mã hóa đơn
* Ngày đặt
* Trạng thái
* Tổng tiền
* Nút xem chi tiết

Trạng thái phải có chữ rõ:

```text
Đã thanh toán
Chưa thanh toán
Đã hủy
Đang chờ xác nhận
```

### Chat AI

Chat AI phải có giao diện đơn giản.

Nên có:

* Danh sách tin nhắn
* Bong bóng tin nhắn người dùng
* Bong bóng tin nhắn AI
* Ô nhập tin nhắn
* Nút gửi
* ProgressBar hoặc trạng thái khi AI đang trả lời

AI phải được thể hiện là trợ lý tư vấn vé khu du lịch.

Không viết text kiểu chatbot chung chung.

## Màn hình quản trị

### Dashboard quản lý

Nên có card thống kê:

* Tổng doanh thu
* Số vé đã bán
* Số hóa đơn
* Số người dùng
* Vé bán chạy
* Voucher đang hoạt động

Ưu tiên dễ đọc dữ liệu hơn là trang trí.

### Quản lý vé

Mỗi item vé nên có:

* Tên vé
* Loại vé
* Giá
* Trạng thái
* Nút sửa
* Nút xóa

Nút xóa dùng màu nguy hiểm.

### Quản lý loại vé

Mỗi item loại vé nên có:

* Tên loại vé
* Mô tả
* Trạng thái
* Nút sửa
* Nút xóa

### Quản lý voucher

Mỗi voucher nên có:

* Mã voucher
* Mức giảm
* Điều kiện áp dụng
* Ngày hết hạn
* Trạng thái
* Nút sửa
* Nút xóa

### Quản lý hóa đơn

Mỗi hóa đơn nên có:

* Mã hóa đơn
* Người dùng
* Ngày đặt
* Tổng tiền
* Trạng thái thanh toán
* Nút xem chi tiết

### Quản lý người dùng

Mỗi người dùng nên có:

* Họ tên
* Email hoặc số điện thoại
* Vai trò
* Trạng thái
* Nút đặt lại mật khẩu nếu có
* Nút khóa/mở khóa nếu có

### Thống kê và báo cáo

Màn hình thống kê phải rõ ràng.

Nên có:

* Bộ lọc ngày/tháng
* Tổng doanh thu
* Tổng số vé bán
* Vé bán chạy
* Loại vé bán chạy
* Danh sách thống kê theo ngày
* Danh sách thống kê theo tháng
* Danh sách thống kê theo loại vé

Không thêm biểu đồ phức tạp nếu project chưa có thư viện.

### Quản lý AI

Màn hình quản lý AI nên hiển thị:

* Trạng thái cấu hình AI
* Model đang dùng nếu có
* Prompt hệ thống nếu có
* Nút lưu cấu hình
* Khu vực test AI nếu project đã có

Không để lộ API key trên giao diện nếu không cần.

## Quy tắc AI trên giao diện

AI trong app chỉ phục vụ các mục đích:

* Tư vấn vé theo dữ liệu thật
* Gợi ý voucher tốt nhất
* Phân tích doanh thu cho quản lý

Không thiết kế AI như chatbot tán gẫu ngoài phạm vi app.

Nội dung AI hiển thị phải ngắn, rõ, tiếng Việt và có ích.

## Quy tắc an toàn khi sửa UI

Không sửa logic DAO nếu task chỉ yêu cầu giao diện.

Không sửa Supabase config nếu task chỉ yêu cầu giao diện.

Không sửa database nếu task chỉ yêu cầu giao diện.

Không xóa Activity, Fragment, Adapter hiện có.

Không đổi tên class nếu không cần.

Không đổi tên layout nếu Java đang dùng.

Không đổi id nếu Java đang `findViewById`.

Sau khi sửa XML, kiểm tra Java liên quan để chắc chắn không crash.

## Khi tạo layout mới

Khi tạo layout mới, phải:

1. Đặt tên đúng tiền tố `user_` hoặc `admin_`
2. Dùng tiếng Việt có dấu cho toàn bộ text hiển thị
3. Dùng màu từ `colors.xml`
4. Dùng kích thước từ `dimens.xml`
5. Dùng style từ `styles.xml` nếu có
6. Dùng drawable riêng có tên rõ nghĩa nếu cần
7. Không tạo thư mục con trong `res/layout`
8. Không thêm thư viện UI mới
9. Không làm phức tạp quá mức

## Khi sửa layout cũ

Khi sửa layout cũ, phải:

1. Giữ lại id quan trọng
2. Giữ lại dữ liệu binding với Adapter
3. Giữ lại luồng Activity/Fragment
4. Chỉ đổi giao diện, không đổi nghiệp vụ
5. Chuyển text tiếng Anh sang tiếng Việt nếu thấy
6. Giảm hardcode nếu có thể
7. Không phá style hiện có

## Kết quả mong muốn

Giao diện sau khi sửa phải:

* Đẹp hơn nhưng vẫn đơn giản
* Đồng bộ xanh ngọc đậm / cam / xanh lá
* Dùng tiếng Việt có dấu hoàn toàn
* Không crash
* Không làm hỏng Java
* Không làm hỏng Supabase/API
* Dễ giải thích trong báo cáo đồ án
* Phù hợp ứng dụng quản lý bán vé khu du lịch
