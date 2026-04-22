# Movie Ticket System (Event-Driven Architecture)

Theo đề bài: các service **không gọi trực tiếp nhau**, giao tiếp qua **Message Broker (RabbitMQ)**.

## Services + Port

- Gateway Service (Spring Cloud Gateway): `8080` (FE chỉ gọi service này)
- User Service: `8081`
- Movie Service: `8082`
- Booking Service (CORE): `8083`
- Payment Service: `8084` (chỉ consumer/producer event; có health endpoint)
- Notification Service: `8086` (consumer event; có health endpoint)
- Frontend (React): `8085` (đúng theo bảng trong đề)

## Event

- `USER_REGISTERED`
- `BOOKING_CREATED`
- `PAYMENT_COMPLETED`
- `BOOKING_FAILED`

## Chạy RabbitMQ

```bash
docker compose up -d
```

RabbitMQ UI: `http://localhost:15672` (guest/guest)

## Chạy các service (mở 5 terminal)

Yêu cầu: cài **Java 17** và **Maven** (để dùng lệnh `mvn`). Nếu chưa cài Maven, bạn có thể mở từng service bằng **IntelliJ** và bấm Run.

```bash
mvn -q -DskipTests spring-boot:run -pl gateway-service
```

```bash
mvn -q -DskipTests spring-boot:run -pl user-service
```

```bash
mvn -q -DskipTests spring-boot:run -pl movie-service
```

```bash
mvn -q -DskipTests spring-boot:run -pl booking-service
```

```bash
mvn -q -DskipTests spring-boot:run -pl payment-service
```

```bash
mvn -q -DskipTests spring-boot:run -pl notification-service
```

## Chạy Frontend (React)

```bash
cd frontend
copy .env.example .env
npm install
npm run dev
```

## Demo bắt buộc (đúng kịch bản đề)

1) **Đăng ký** (User Service publish `USER_REGISTERED`)

```bash
curl -X POST http://localhost:8080/api/users/register -H "Content-Type: application/json" -d "{\"username\":\"userA\",\"password\":\"123\"}"
```

2) **Thêm phim** rồi xem danh sách (Movie Service)

```bash
curl -X POST http://localhost:8080/api/movies -H "Content-Type: application/json" -d "{\"title\":\"Dune 2\"}"
curl http://localhost:8080/api/movies
```

3) **Tạo booking** (Booking Service publish `BOOKING_CREATED`)

```bash
curl -X POST http://localhost:8080/api/bookings -H "Content-Type: application/json" -d "{\"user\":\"userA\",\"movieId\":1,\"seats\":[\"A1\",\"A2\"]}"
```

4) Payment Service **random success/fail**:
- Success: publish `PAYMENT_COMPLETED`
- Fail: publish `BOOKING_FAILED`

5) Notification Service nhận event và in ra console:
- `Booking #123 thành công!`

6) Kiểm tra trạng thái booking:

```bash
curl http://localhost:8080/api/bookings
```

## Chạy trên LAN (đúng ý đề)

- **RabbitMQ**: set host bằng env `RABBITMQ_HOST` cho các service: `user-service`, `booking-service`, `payment-service`, `notification-service`.
- **Gateway**: set URL các service bằng env:
  - `USER_SERVICE_URL` (vd `http://192.168.2.x:8081`)
  - `MOVIE_SERVICE_URL` (vd `http://192.168.2.x:8082`)
  - `BOOKING_SERVICE_URL` (vd `http://192.168.2.x:8083`)
- **Frontend**: chỉnh `.env` (`VITE_API=http://192.168.2.x:8080`) để FE chỉ gọi Gateway.


