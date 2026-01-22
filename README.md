# Cloud2FA

> Trình quản lý xác thực hai yếu tố (2FA) an toàn và thân thiện với nhà phát triển — backend Spring Boot, frontend React (Vite), và client di động tùy chọn.

## Giới thiệu

Cloud2FA cung cấp giải pháp mã hóa đầu-cuối để quản lý TOTP (Time-based One-Time Password) và hỗ trợ xác thực hai yếu tố cho người dùng cá nhân và đội nhỏ. Dự án bao gồm backend Spring Boot, frontend Vite + React và scaffold client di động để phát triển tiếp.

## Tính năng chính

- Quản lý bí mật TOTP (tạo, đọc, xóa)
- Lưu trữ bí mật được mã hoá, hỗ trợ mật khẩu chính (master password)
- Sinh và xác thực mã OTP theo thời gian (TOTP)
- Đăng ký người dùng, đăng nhập và quản lý phiên
- Hỗ trợ nội địa hoá thông báo (i18n)
- Sẵn sàng chạy bằng Docker để triển khai nhanh

## Kiến trúc tổng quan

Hệ thống được chia thành ba thành phần chính:

- Backend: REST API bằng Spring Boot chịu trách nhiệm xác thực, logic TOTP, lưu trữ và bảo mật.
- Frontend: SPA Vite + React tương tác với API backend để hiển thị và quản lý các mục TOTP.
- Mobile (tùy chọn): scaffold client di động để mở rộng về sau.

Sơ đồ tổng quan (Mermaid):

```mermaid
flowchart LR
  subgraph FE [Frontend]
    A[React (Vite) SPA]
  end

  subgraph BE [Backend]
    B[Spring Boot REST API]
    DB[(Database)]
  end

  subgraph MISC
    D[Docker / Docker Compose]
    Auth[Auth (JWT / Session)]
  end

  A -- HTTPS --> B
  B -- JDBC --> DB
  B -- Auth --> Auth
  D -. manages .-> B
  D -. manages .-> A

  style FE fill:#f9f,stroke:#333,stroke-width:1px
  style BE fill:#ff9,stroke:#333,stroke-width:1px
```

## Cài đặt

Yêu cầu trước khi cài đặt

- Java 17+ (JDK)
- Maven (hoặc dùng Maven Wrapper kèm theo)
- Node 18+ (cho frontend)
- Docker & Docker Compose (tuỳ chọn)

Clone repo:

```bash
git clone https://your-repo-url/example-cloud-2fa.git
cd example-cloud-2fa
```

Chạy nhanh backend

```bash
# Unix/macOS
./backend/mvnw spring-boot:run

# Windows (PowerShell)
.\backend\mvnw.cmd spring-boot:run
```

Frontend

```bash
cd frontend
npm install
npm run dev
```

Chạy bằng Docker (tuỳ chọn)

```bash
# Từ thư mục gốc của repo
docker-compose -f backend/docker-compose.yml up --build
```

## Chạy dự án

1. Khởi động backend (xem phần Cài đặt) — mặc định `http://localhost:8080`.
2. Khởi động frontend (`npm run dev`) — Vite dev server thường chạy ở `http://localhost:5173`.
3. Mở URL frontend trong trình duyệt, đăng ký người dùng và bắt đầu thêm bí mật TOTP.

Ví dụ

- Tạo người dùng (curl):

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","password":"S3cur3P@ss"}'
```

- Đăng nhập (curl):

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","password":"S3cur3P@ss"}'
```

## Cấu hình môi trường

Backend

- Backend đọc cấu hình từ `src/main/resources/application.properties` và các file theo profile như `application-dev.properties`.
- Một số thiết lập thường cần cấu hình (ví dụ):

```properties
# application-dev.properties
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:5432/cloud2fa_db
spring.datasource.username=cloud2fa
spring.datasource.password=secret
cloud2fa.masterPasswordSalt=change-me
jwt.secret=replace_with_a_secure_random_value
```

Frontend

- Tạo file `.env` hoặc dùng biến môi trường có tiền tố `VITE_` cho Vite. Ví dụ `.env` trong `frontend/`:

```env
VITE_API_BASE_URL=http://localhost:8080/api

# Tuỳ chọn: vô hiệu hoá analytics hoặc bật logging chi tiết
VITE_DEBUG=true
```

Bảo mật

- Không commit bí mật production vào hệ thống quản lý mã nguồn. Sử dụng biến môi trường hoặc dịch vụ quản lý bí mật cho môi trường production.

## Cấu trúc thư mục

Cấu trúc cấp cao (rút gọn):

```
backend/
  ├─ src/main/java/org/example/cloud2fa/   # Ứng dụng Spring Boot và mã nguồn
  ├─ src/main/resources/                  # properties, i18n
  ├─ docker-compose.yml
  └─ pom.xml

frontend/
  ├─ src/                                # Component React, trang
  ├─ apis/                               # Client gọi API từ frontend
  ├─ package.json
  └─ vite.config.js

mobile/                                   # scaffold client di động (tùy chọn)
```

Chi tiết hơn:

- `backend/src/main/java/org/example/cloud2fa/controller` - các API controller
- `backend/src/main/java/org/example/cloud2fa/service` - logic nghiệp vụ
- `backend/src/main/java/org/example/cloud2fa/repository` - lớp lưu trữ/persistence
- `frontend/src/components` - component React tái sử dụng

## Hướng dẫn đóng góp

Chúng tôi hoan nghênh các đóng góp. Các bước đề xuất:

1. Fork repository.
2. Tạo nhánh tính năng: `git checkout -b feat/short-description`.
3. Viết tests cho hành vi mới khi cần thiết.
4. Chạy test và linter cục bộ.
5. Mở Pull Request với tiêu đề và mô tả rõ ràng, tham chiếu issue nếu có.

Quy ước mã nguồn

- Java: tuân thủ phong cách code hiện có, đặt tên rõ ràng và giữ các phương thức theo nguyên tắc single-responsibility.
- JavaScript/React: tuân thủ `eslint` trong `frontend/`, ưu tiên component hàm và hooks.

Quy trình review

- PRs sẽ được review về tính đúng đắn, rõ ràng, bảo mật và phạm vi test. Ưu tiên các PR nhỏ, tập trung.

## Giấy phép

Dự án này được phát hành theo giấy phép MIT — xem `backend/LICENSE` để biết chi tiết.

## Lộ trình

- [ ] Client di động (UX quản lý TOTP hoàn chỉnh)
- [ ] Sao lưu & phục hồi bí mật được mã hoá
- [ ] Luồng phục hồi đa-yếu tố (email / mã dự phòng)
- [ ] Tính năng doanh nghiệp: teams, audit và SSO
- [ ] Đăng tải Docker image và CI/CD bằng GitHub Actions

Bạn muốn đóng góp ý tưởng hoặc tính năng? Mở issue hoặc tham gia thảo luận trong repository.

---

Nếu bạn muốn, tôi có thể dịch và đồng bộ hóa `backend/README.md` và `frontend/README.md` theo nội dung top-level này — cho tôi biết bạn muốn tự động áp dụng cho file nào.

