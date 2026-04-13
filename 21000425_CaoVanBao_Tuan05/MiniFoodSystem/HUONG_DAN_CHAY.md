# HUONG DAN CHAY MINI FOOD SYSTEM

## 1) Mô tả nhanh

Hệ thống gồm 5 phần:
- `user-service` (Spring Boot, cong `8081`)
- `food-service` (Spring Boot, cong `8082`)
- `order-service` (Spring Boot, cong `8083`)
- `payment-service` (Spring Boot, cong `8084`)
- `frontend-react` (React + Vite, mặc định cổng `5173`)

Kiến trúc: service-based, frontend gọi trực tiếp các REST API của từng service.

---

## 2) Yêu cầu môi trường

Cần cài đặt:
- Java `17`
- Maven `3.9+`
- Node.js `18+`
- npm `9+`

Kiểm tra nhanh:

```bash
java -version
mvn -v
node -v
npm -v
```

---

## 3) Chạy backend (4 service)

Mở **4 terminal** riêng, chạy lần lượt:

### Terminal 1 - User Service
```bash
cd user-service
mvn spring-boot:run
```

### Terminal 2 - Food Service
```bash
cd food-service
mvn spring-boot:run
```

### Terminal 3 - Order Service
```bash
cd order-service
mvn spring-boot:run
```

### Terminal 4 - Payment Service
```bash
cd payment-service
mvn spring-boot:run
```

Nếu chạy thành công, bạn sẽ có:
- User API: `http://localhost:8081/users`
- Food API: `http://localhost:8082/foods`
- Order API: `http://localhost:8083/orders`
- Payment API: `http://localhost:8084/payments`

---

## 4) Chạy frontend

Mở thêm 1 terminal:

```bash
cd frontend-react
npm install
npm run dev
```

Mở trình duyệt:
- `http://localhost:5173`

---

## 5) Tài khoản test nhanh

Tài khoản đã dùng để demo:
- Username: `usera`
- Password: `123456`

Hoặc bạn có thể đăng ký mới trong giao diện.

---

## 6) Kịch bản demo bắt buộc

Thực hiện đúng thứ tự:
1. Đăng ký hoặc đăng nhập
2. Vào trang món ăn, xem danh sách (`Phở`, `Cơm tấm`, ...)
3. Thêm món ăn vào giỏ
4. Tạo đơn hàng
5. Vào thanh toán, chọn `COD` hoặc `Banking`
6. Kiểm tra trang đơn hàng: trạng thái đơn thanh `PAID`
7. Kiểm tra log `payment-service`: có dòng notification thành công

---

## 7) Cấu hình chạy LAN (nhiều máy)

Frontend đọc URL từ biến môi trường:
- `frontend-react/.env.example`

Tao file `frontend-react/.env` va sửa IP thật:

```env
VITE_USER_SERVICE_URL=http://192.168.x.x:8081/users
VITE_FOOD_SERVICE_URL=http://192.168.x.x:8082/foods
VITE_ORDER_SERVICE_URL=http://192.168.x.x:8083/orders
VITE_PAYMENT_SERVICE_URL=http://192.168.x.x:8084/payments
```

Sau khi sửa `.env`, chạy lại:

```bash
npm run dev
```

---

## 8) Lỗi thường gặp

- Port đã được sử dụng:
  - Dùng service đang chiếm port hoặc đổi port trong `application.properties`.
- Frontend goi API bị CORS:
  - Đã bắt `@CrossOrigin(origins = "*")` trong controller.
- Tên món hiện thị sai dấu tiếng Việt:
  - Dùng service, chạy lại `mvn clean package` + `mvn spring-boot:run`.
  - Sau đó Ctrl+F5 trên trình duyệt.

---

## 9) Đóng chương trình

Tại mỗi terminal đang chạy service/frontend, bấm:
- `Ctrl + C`
