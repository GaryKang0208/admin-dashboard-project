# API 명세서

## 1. 관리자 회원가입

| 항목 | 내용 |
|---|---|
| Method / URL | `POST /admins/signup` |
| 설명 | 관리자 회원가입 |
| Request Body | name, email, password, phone, role |
| Response | 201 Created |
| Response Body | name, email |
| Error | 400 Bad Request |
| 비고 | 비밀번호 8자 이상, 형식 오류, 이메일 중복 불가, 필수값 누락 |

## 2. 관리자 인증

### 2-1. 로그인

| 항목 | 내용 |
|---|---|
| Method / URL | `POST /admins/login` |
| 설명 | 관리자 로그인 |
| Request Body | email, password |
| Response | 201 Created |
| Response Body | name, email |
| Error | 400 Bad Request |
| 비고 | 비밀번호 8자 이상, 형식 오류, 이메일 중복 불가, 필수값 누락 |

### 2-2. 로그아웃

| 항목 | 내용 |
|---|---|
| Method / URL | `POST /admins/logout` |
| 설명 | 관리자 로그아웃 |
| Response | 200 OK |
| Response Body | message ("로그아웃이 성공했습니다") |
| Error | 500 Server Error |
| 비고 | 500 Server Error |

## 3. 관리자 정보 관리

> 슈퍼 관리자만 수정 및 삭제를 할 수 있습니다.

### 3-1. 리스트 조회

| 항목 | 내용 |
|---|---|
| Method / URL | `GET /admins` |
| 설명 | 관리자 정보 조회 |
| Query Parameter | keyword(이름/이메일), page(기본 1), size(기본 10), sortBy(name, email, createdAt), sortOrder(asc, desc), role(SUPER_ADMIN, OPERATOR, CS_ADMIN), status(ACTIVE, INACTIVE, SUSPENDED, PENDING, REJECTED) |
| Response | 201 Created |
| Response Body | id, name, phone, role, status, createdAt, approvedAt, rejectedAt, 페이징 정보 |
| Error | 400 Bad Request |
| 비고 | 형식 오류, 필수값 누락 |

### 3-2. 상세 조회

| 항목 | 내용 |
|---|---|
| Method / URL | `GET /admins/{adminId}` |
| 설명 | 관리자 정보 상세 조회 |
| Response | 200 OK |
| Response Body | id, name, email, phone_number, role, status, createdAt, approvedAt, rejectedAt |
| Error | 400 Bad Request |
| 비고 | 형식 오류, 필수값 누락 |

### 3-3. 정보 수정

| 항목 | 내용 |
|---|---|
| Method / URL | `PUT /admins/{adminId}` |
| 설명 | 정보 수정 |
| Request Body | name, email, phone |
| Response | 200 OK |
| Response Body | id, adminname, email, phone_number, role, status, createdAt, approvedAt, rejectedAt |
| Error | 400 Bad Request |
| 비고 | 슈퍼관리자 id만 삭제 가능 |

### 3-4. 역할 변경

| 항목 | 내용 |
|---|---|
| Method / URL | `PATCH /admins/{adminId}` |
| Request Body | role |
| Response | 200 OK |
| Response Body | id, name, email, phone, role, status, createdAt, approvedAt, rejectedAt |
| Error | 400 Bad Request |
| 비고 | 슈퍼관리자 id만 역할 변경 가능 |

### 3-5. 상태 변경

| 항목 | 내용 |
|---|---|
| Method / URL | `PATCH /admins/{adminId}` |
| Request Body | status |
| Response | 200 OK |
| Response Body | id, name, email, phone, role, status, createdAt, approvedAt, rejectedAt |
| Error | 400 Bad Request |
| 비고 | 슈퍼관리자 id만 변경 가능 |

### 3-6. 삭제

| 항목 | 내용 |
|---|---|
| Method / URL | `DELETE /admins/{adminId}` |
| 설명 | 특정 관리자 삭제 |
| Response | 204 No Content |
| Error | 400 Bad Request |
| 비고 | 슈퍼관리자 id만 삭제 가능 |

### 3-7. 승인/거부

| 항목 | 내용 |
|---|---|
| Method / URL | `PATCH /admins/{adminId}` |
| 설명 | 승인/거부 상태 변경 |
| Response | 200 OK |
| Response Body | id, name, email, phone, role, status, createdAt, approvedAt, rejectedAt |
| Error | 400 Bad Request |
| 비고 | 슈퍼관리자 id만 승인/거부 가능 |

### 3-8. 내 프로필 조회

| 항목 | 내용 |
|---|---|
| Method / URL | `GET /admins/{adminId}/profile` |
| 설명 | 개인 프로필 조회 |
| Response | 200 OK |
| Response Body | id, name, email, phone, role, status, createdAt, approvedAt, rejectedAt |
| Error | 400 Bad Request |

### 3-9. 내 프로필 수정

| 항목 | 내용 |
|---|---|
| Method / URL | `PUT /admins/{adminId}/profile` |
| 설명 | 로그인한 유저 프로필 정보 수정 |
| Request Body | name, email, phone |
| Response | 200 OK |
| Response Body | id, name, email, phone, role, status, createdAt, approvedAt, rejectedAt |
| Error | 400 Bad Request |
| 비고 | 슈퍼관리자 id만 삭제 가능 |

### 3-10. 비밀번호 변경

| 항목 | 내용 |
|---|---|
| Method / URL | `PUT /admins/{adminId}/password` |
| 설명 | 현재 비밀번호를 새 비밀번호로 변경 |
| Request Body | password |
| Response | 200 OK |
| Response Body | password |
| Error | 400 Bad Request |
| 비고 | 8자 이상, 이전 비밀번호와 동일하면 안 됨 |

## 4. 고객정보 관리

### 4-1. 고객 리스트 조회

| 항목 | 내용 |
|---|---|
| Method / URL | `GET /admins/{admin}/customers` |
| 설명 | 고객 목록을 검색 조건과 함께 조회 |
| Query Parameter | keyword(이름/이메일), page(기본 1), size(기본 10), sortBy(name, email, createdAt), sortOrder(desc) |
| Request Body | id, name, email, phone, status, createdAt |
| Response Body | pagination, currentPage, limit, totalCount, totalPages |
| Response | 200 OK |
| Error | 400 Bad Request |

### 4-2. 고객 상세 조회

| 항목 | 내용 |
|---|---|
| Method / URL | `GET /admins/customers/{customerId}` |
| 설명 | 고객 이름, 이메일, 전화번호를 통해 조회 |
| Request Body | name, email, phone, status, createdAt |
| Response Body | name, email, phone, status, totalOrders, totalPurchaseAmount, createdAt |
| Response | 200 OK |
| Error | 400 Bad Request |

### 4-3. 고객 정보 수정

| 항목 | 내용 |
|---|---|
| Method / URL | `PATCH /admins/customers/{customerId}` |
| 설명 | 고객의 이름, 이메일, 전화번호 수정 |
| Response Body | name, email, phone, status, createdAt |
| Response | 200 OK |
| Error | 400 Bad Request |

### 4-4. 고객 상태 변경

| 항목 | 내용 |
|---|---|
| Method / URL | `PATCH /admins/customers/{customerId}/status` |
| 설명 | 고객의 상태(활성/비활성/정지) 변경 |
| Request Body | status |
| Response Body | id, name, status, updatedAt |
| Response | 200 OK |
| Error | 400 Bad Request / 404 Not Found |

### 4-5. 고객 삭제

| 항목 | 내용 |
|---|---|
| Method / URL | `DELETE /admins/customers/{customerId}` |
| 설명 | 특정 고객을 탈퇴(삭제) 처리 |
| Response Body | message |
| Response | 200 OK |
| Error | 404 Not Found |

## 5. 상품 정보 관리

### 5-1. 상품 등록

| 항목 | 내용 |
|---|---|
| Method / URL | `POST /admins/products` |
| 설명 | 새로운 상품 등록 |
| Request Body | name, category, price, stock, status |
| Response Body | id, name, category, price, stock, status, createdAt, createdBy |
| Response | 201 Created |
| Error | 400 Bad Request |

### 5-2. 상품 리스트 조회

| 항목 | 내용 |
|---|---|
| Method / URL | `GET /admins/products` |
| 설명 | 상품 목록을 검색 조건과 함께 조회 |
| Query Parameter | keyword(상품명), page(기본 1), size(기본 10), sortBy(createdAt), sortOrder(desc), role(전자기기, 패션의류, 식품, 생활용품, 스포츠 레져, 뷰티화장품, 도서, 완구/취미), status(Onsale, soldout, discontinued) |
| Response Body | products, id, name, category, price, stock, createdAt, createdBy |
| Response | 200 OK |
| Error | 400 Bad Request |

### 5-3. 상품 상세 조회

| 항목 | 내용 |
|---|---|
| Method / URL | `GET /admins/products/{productId}` |
| 설명 | 특정 상품의 상세 정보 조회 |
| Response Body | id, name, category, price, stock, status, createdAt, createdBy, createdByEmail |
| Response | 200 OK |
| Error | 404 Not Found |

### 5-4. 상품 정보 수정

| 항목 | 내용 |
|---|---|
| Method / URL | `PATCH /admins/products/{productId}` |
| 설명 | 상품명, 카테고리, 가격 수정 |
| Request Body | name, category, price |
| Response Body | id, name, category, price, stock, status, createdAt, createdBy |
| Response | 200 OK |
| Error | 400 Bad Request / 404 Not Found |

### 5-5. 상품 재고 변경

| 항목 | 내용 |
|---|---|
| Method / URL | `PATCH /admins/products/{productId}/stock` |
| 설명 | 상품 재고 변경 (재고에 따라 상태 자동 갱신) |
| Request Body | stock |
| Response Body | id, name, stock, status, updatedAt |
| Response | 200 OK |
| Error | 400 Bad Request / 404 Not Found |
| 비고 | 재고 0 이하 → soldOut(품절) 자동 전환 / 재고 1 이상 → selling(판매중) 자동 전환 / 단, discontinued(단종) 상태는 재고와 관계없이 상태 유지 |

### 5-6. 상품 상태 변경

| 항목 | 내용 |
|---|---|
| Method / URL | `PATCH /admins/products/{productId}/status` |
| 설명 | 상품 상태(판매중/품절/단종) 변경 |
| Request Body | status |
| Response Body | id, name, status, updatedAt |
| Response | 200 OK |
| Error | 400 Bad Request / 404 Not Found |

### 5-7. 상품 삭제

| 항목 | 내용 |
|---|---|
| Method / URL | `DELETE /admins/products/{productId}` |
| 설명 | 특정 상품 삭제 |
| Response Body | message |
| Response | 200 OK |
| Error | 404 Not Found |

## 6. 주문 정보 관리

### 6-1. CS 주문 생성

| 항목 | 내용 |
|---|---|
| Method / URL | `POST /admins/orders` |
| 설명 | 관리자가 고객을 대신하여 주문을 생성 (CS 대리 주문) |
| Request Body | customerId, productId, quantity |
| Response Body | id, orderNumber, customerName, productName, quantity, totalPrice, status, orderedAt, createdBy |
| Response | 201 Created |
| Error | 400 Bad Request |

### 6-2. 주문 리스트 조회

| 항목 | 내용 |
|---|---|
| Method / URL | `GET /admins/orders` |
| 설명 | 주문 목록을 검색 조건과 함께 조회 |
| Query Parameter | keyword(주문번호/고객명), page(기본 1), size(기본 10), sortBy(createdAt), sortOrder(desc), status(Preparing, On delivery, delivered, canceled) |
| Response Body | id, orderNumber, customerName, productName, quantity, totalPrice, orderedAt, status, createdBy, pagination(currentPage, limit, totalCount, totalPages) |
| Response | 200 OK |
| Error | 400 Bad Request |

### 6-3. 주문 상세 조회

| 항목 | 내용 |
|---|---|
| Method / URL | `GET /admins/orders/{orderId}` |
| 설명 | 특정 주문의 상세 정보 조회 |
| Response Body | id, orderNumber, customerName, customerEmail, productName, quantity, totalPrice, orderedAt, status, createdBy, createdByEmail, createdByRole |
| Response | 200 OK |
| Error | 404 Not Found |
| 비고 | CS 주문이 아닌 고객 직접 주문인 경우, createdBy, createdByEmail, createdByRole은 null 반환 |

### 6-4. 주문 상태 수정

| 항목 | 내용 |
|---|---|
| Method / URL | `PATCH /admins/orders/{orderId}/status` |
| 설명 | 주문 상태 변경 (준비중 → 배송중 → 배송완료) |
| Request Body | status |
| Response Body | id, orderNumber, status, updatedAt |
| Response | 200 OK |
| Error | 400 Bad Request |
| 비고 | 준비중 → 배송중 → 배송완료 순서로만 변경 가능 (건너뛰기/역순 변경 불가) |

### 6-5. 주문 취소

| 항목 | 내용 |
|---|---|
| Method / URL | `PATCH /admins/orders/{orderId}/cancel` |
| 설명 | 주문을 취소됨 상태로 변경하고 재고를 복구 처리 |
| Request Body | cancelReason |
| Response Body | id, orderNumber, status, cancelReason, canceledAt |
| Response | 200 OK |
| Error | 400 Bad Request |
| 비고 | 준비중(preparing) 상태에서만 취소 가능. 배송중/배송완료는 취소 불가 