# BaiTap03

Hệ thống **thanh toán** dùng các Design Pattern:

- **Strategy**: Chiến lược thanh toán (Thẻ tín dụng, PayPal, Chuyển khoản,...).
- **Decorator**: Thêm phí xử lý, mã giảm giá,... chồng lên phương thức thanh toán.
- **State**: Trạng thái giao dịch (Khởi tạo, Đang xử lý, Hoàn tất, Thất bại).

## Cấu trúc

- `src/main/java/model` – Thông tin `PaymentRequest`, `PaymentResult`, loại phương thức.
- `src/main/java/strategy` – Các chiến lược thanh toán (credit card, PayPal, bank).
- `src/main/java/decorator` – Decorator cho phí xử lý, mã giảm giá.
- `src/main/java/state` – Trạng thái giao dịch và `PaymentContext`.
- `src/main/java/Main.java` – Demo mô phỏng.

## Chạy

Trong thư mục `BaiTap03`:

```bash
mvn --% -q compile exec:java -Dexec.mainClass=Main
```

