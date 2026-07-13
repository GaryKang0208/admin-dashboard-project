# API 명세서

---

# 1. 관리자 회원가입

## 1. 관리자 회원가입

| 항목 | 내용 |
|------|------|
| URL | `POST /admins/signup` |
| 설명 | 새로운 관리자 계정을 생성합니다. |
| Request Body | `name`, `email`, `password`, `phone`, `role` |
| Response | `201 Created` |
| Response Body | `id`, `name`, `email`, `phone`, `role`, `status`, `createdAt` |
| Error | `400 Bad Request`, `409 Conflict` |
| 비고 | 비밀번호는 8자 이상, 이메일 중복 불가, 필수값 누락, 가입 시 상태는 `PENDING`입니다. |

---

# 2. 관리자 인증

## 1. 로그인

| 항목 | 내용 |
|------|------|
| URL | `POST /admins/login` |
| 설명 | 관리자 로그인 |
| Request Body | `email`, `password` |
| Response | `200 OK` |
| Response Body | `id`, `name`, `email`, `role`, `status` |
| Error | `400 Bad Request`, `401 Unauthorized` |
| 비고 | 승인된 관리자만 로그인할 수 있습니다. |

---

## 2. 로그아웃

| 항목 | 내용 |
|------|------|
| URL | `POST /admins/logout` |
| 설명 | 관리자 로그아웃 |
| Request Body | 없음 |
| Response | `200 OK` |
| Response Body | `message` |
| Error | `401 Unauthorized`, `500 Internal Server Error` |
| 비고 | 로그인 세션을 종료합니다. |

---

# 3. 관리자 정보 관리

## 1. 리스트 조회

| 항목 | 내용 |
|------|------|
| URL | `GET /admins` |
| 설명 | 관리자 목록을 조회합니다. |
| Query Parameter | `keyword`, `page`, `size`, `sortBy`, `sortOrder`, `role`, `status` |
| Response | `200 OK` |
| Response Body | `admins`, `pagination` |
| Error | `400 Bad Request`, `401 Unauthorized`, `403 Forbidden` |
| 비고 | `SUPER_ADMIN`만 조회할 수 있습니다. |

---

## 2. 상세 조회

| 항목 | 내용 |
|------|------|
| URL | `GET /admins/{adminId}` |
| 설명 | 관리자 상세 정보를 조회합니다. |
| Response | `200 OK` |
| Response Body | `id`, `name`, `email`, `phone`, `role`, `status`, `createdAt`, `approvedAt`, `rejectedAt` |
| Error | `400 Bad Request`, `404 Not Found` |
| 비고 | `SUPER_ADMIN`만 조회할 수 있습니다. |

---

## 3. 정보 수정

| 항목 | 내용                                                            |
|------|---------------------------------------------------------------|
| URL | `PUT /admins/{adminId}`                                       |
| 설명 | 관리자 정보를 수정합니다.                                                |
| Request Body | `name`, `email`, `phone`                                      |
| Response | `200 OK`                                                      |
| Response Body | `id`, `name`, `email`, `phone`, `role`, `status`, `updatedAt` |
| Error | `400 Bad Request`, `404 Not Found`, `409 Conflict`            |
| 비고 | `SUPER_ADMIN`만 수정할 수 있습니다.                                    |

---
## 4. 역할 변경

| 항목 | 내용 |
|------|------|
| URL | `PATCH /admins/{adminId}/role` |
| 설명 | 관리자의 역할을 변경합니다. |
| Request Body | `role` |
| Response | `200 OK` |
| Response Body | `id`, `name`, `email`, `phone`, `role`, `status`, `updatedAt` |
| Error | `400 Bad Request`, `404 Not Found` |
| 비고 | `SUPER_ADMIN`만 역할을 변경할 수 있습니다. |

---

## 5. 상태 변경

| 항목 | 내용 |
|------|------|
| URL | `PATCH /admins/{adminId}/status` |
| 설명 | 관리자의 상태를 변경합니다. |
| Request Body | `status` |
| Response | `200 OK` |
| Response Body | `id`, `name`, `email`, `phone`, `role`, `status`, `updatedAt` |
| Error | `400 Bad Request`, `404 Not Found` |
| 비고 | `SUPER_ADMIN`만 상태를 변경할 수 있습니다. 상태는 `ACTIVE`, `INACTIVE`, `SUSPENDED`로 변경 가능합니다. |

---

## 6. 관리자 삭제

| 항목 | 내용 |
|------|------|
| URL | `DELETE /admins/{adminId}` |
| 설명 | 특정 관리자를 삭제합니다. |
| Response | `204 No Content` |
| Response Body | 없음 |
| Error | `404 Not Found` |
| 비고 | `SUPER_ADMIN`만 삭제할 수 있습니다. |

---

## 7. 관리자 승인

| 항목 | 내용 |
|------|------|
| URL | `PATCH /admins/{adminId}/approve` |
| 설명 | 가입 대기 중인 관리자를 승인합니다. |
| Request Body | 없음 |
| Response | `200 OK` |
| Response Body | `id`, `name`, `email`, `phone`, `role`, `status`, `approvedAt` |
| Error | `400 Bad Request`, `404 Not Found` |
| 비고 | `SUPER_ADMIN`만 승인할 수 있으며 승인 후 상태는 `ACTIVE`로 변경됩니다. |

---

## 8. 관리자 거절

| 항목 | 내용 |
|------|------|
| URL | `PATCH /admins/{adminId}/reject` |
| 설명 | 가입 대기 중인 관리자를 거절합니다. |
| Request Body | `reason` |
| Response | `200 OK` |
| Response Body | `id`, `name`, `email`, `phone`, `role`, `status`, `rejectedAt` |
| Error | `400 Bad Request`, `404 Not Found` |
| 비고 | `SUPER_ADMIN`만 거절할 수 있으며 상태는 `REJECTED`로 변경됩니다. |

---

## 9. 내 프로필 조회

| 항목 | 내용 |
|------|------|
| URL | `GET /admins/me` |
| 설명 | 로그인한 관리자의 프로필 정보를 조회합니다. |
| Response | `200 OK` |
| Response Body | `id`, `name`, `email`, `phone`, `role`, `status`, `createdAt`, `approvedAt` |
| Error | `401 Unauthorized`, `404 Not Found` |
| 비고 | 로그인한 관리자 본인의 정보만 조회할 수 있습니다. |

---

## 10. 내 프로필 수정

| 항목 | 내용 |
|------|------|
| URL | `PUT /admins/me` |
| 설명 | 로그인한 관리자의 프로필 정보를 수정합니다. |
| Request Body | `name`, `email`, `phone` |
| Response | `200 OK` |
| Response Body | `id`, `name`, `email`, `phone`, `role`, `status`, `updatedAt` |
| Error | `400 Bad Request`, `409 Conflict` |
| 비고 | 본인의 이름, 이메일, 전화번호만 수정할 수 있습니다. |

---

## 11. 비밀번호 변경

| 항목 | 내용 |
|------|------|
| URL | `PUT /admins/me/password` |
| 설명 | 로그인한 관리자의 비밀번호를 변경합니다. |
| Request Body | `currentPassword`, `newPassword`, `confirmPassword` |
| Response | `200 OK` |
| Response Body | `message` |
| Error | `400 Bad Request`, `401 Unauthorized` |
| 비고 | 새 비밀번호는 8자 이상이어야 하며 기존 비밀번호와 동일할 수 없습니다. |

---

# 4. 고객 정보 관리

## 1. 고객 리스트 조회

| 항목 | 내용 |
|------|------|
| URL | `GET /customers` |
| 설명 | 고객 목록을 검색 조건과 함께 조회합니다. |
| Query Parameter | `keyword`, `page`, `size`, `sortBy`, `sortOrder`, `status` |
| Response | `200 OK` |
| Response Body | `customers`, `pagination` |
| Error | `400 Bad Request` |
| 비고 | 검색, 정렬, 페이징을 지원합니다. |

---

## 2. 고객 상세 조회

| 항목 | 내용 |
|------|------|
| URL | `GET /customers/{customerId}` |
| 설명 | 특정 고객의 상세 정보를 조회합니다. |
| Response | `200 OK` |
| Response Body | `id`, `name`, `email`, `phone`, `status`, `totalOrders`, `totalPurchaseAmount`, `createdAt` |
| Error | `404 Not Found` |
| 비고 | 존재하지 않는 고객은 조회할 수 없습니다. |

---

## 3. 고객 정보 수정

| 항목 | 내용 |
|------|------|
| URL | `PATCH /customers/{customerId}` |
| 설명 | 고객 정보를 수정합니다. |
| Request Body | `name`, `email`, `phone` |
| Response | `200 OK` |
| Response Body | `id`, `name`, `email`, `phone`, `status`, `updatedAt` |
| Error | `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| 비고 | 변경할 정보만 전달하여 수정할 수 있습니다. |

---

## 4. 고객 상태 변경

| 항목 | 내용 |
|------|------|
| URL | `PATCH /customers/{customerId}/status` |
| 설명 | 고객의 상태를 변경합니다. |
| Request Body | `status` |
| Response | `200 OK` |
| Response Body | `id`, `name`, `status`, `updatedAt` |
| Error | `400 Bad Request`, `404 Not Found` |
| 비고 | 상태는 `ACTIVE`, `INACTIVE`, `SUSPENDED`로 변경할 수 있습니다. |

---

## 5. 고객 삭제

| 항목 | 내용 |
|------|------|
| URL | `DELETE /customers/{customerId}` |
| 설명 | 특정 고객을 삭제합니다. |
| Response | `200 OK` |
| Response Body | `message` |
| Error | `404 Not Found` |
| 비고 | 삭제 완료 메시지를 반환합니다. |

---

# 5. 상품 정보 관리

## 1. 상품 등록

| 항목 | 내용 |
|------|------|
| URL | `POST /products` |
| 설명 | 새로운 상품을 등록합니다. |
| Request Body | `name`, `category`, `price`, `stock`, `status` |
| Response | `201 Created` |
| Response Body | `id`, `name`, `category`, `price`, `stock`, `status`, `createdAt`, `createdBy` |
| Error | `400 Bad Request` |
| 비고 | 가격과 재고는 0 이상이어야 합니다. |

---

## 2. 상품 리스트 조회

| 항목 | 내용 |
|------|------|
| URL | `GET /products` |
| 설명 | 상품 목록을 검색 조건과 함께 조회합니다. |
| Query Parameter | `keyword`, `page`, `size`, `sortBy`, `sortOrder`, `category`, `status` |
| Response | `200 OK` |
| Response Body | `products`, `pagination` |
| Error | `400 Bad Request` |
| 비고 | 검색, 정렬, 페이징을 지원합니다. |

---

## 3. 상품 상세 조회

| 항목 | 내용 |
|------|------|
| URL | `GET /products/{productId}` |
| 설명 | 특정 상품의 상세 정보를 조회합니다. |
| Response | `200 OK` |
| Response Body | `id`, `name`, `category`, `price`, `stock`, `status`, `createdAt`, `createdBy`, `createdByEmail` |
| Error | `404 Not Found` |
| 비고 | 존재하지 않는 상품은 조회할 수 없습니다. |

---

## 4. 상품 정보 수정

| 항목 | 내용 |
|------|------|
| URL | `PATCH /products/{productId}` |
| 설명 | 상품 정보를 수정합니다. |
| Request Body | `name`, `category`, `price` |
| Response | `200 OK` |
| Response Body | `id`, `name`, `category`, `price`, `stock`, `status`, `updatedAt`, `createdBy` |
| Error | `400 Bad Request`, `404 Not Found` |
| 비고 | 변경할 정보만 수정할 수 있습니다. |

---

## 5. 상품 재고 변경

| 항목 | 내용 |
|------|------|
| URL | `PATCH /products/{productId}/stock` |
| 설명 | 상품의 재고를 변경합니다. |
| Request Body | `stock` |
| Response | `200 OK` |
| Response Body | `id`, `name`, `stock`, `status`, `updatedAt` |
| Error | `400 Bad Request`, `404 Not Found` |
| 비고 | 재고가 0이면 `SOLD_OUT`, 1 이상이면 `SELLING`으로 자동 변경됩니다. 단, `DISCONTINUED` 상태는 변경되지 않습니다. |

---

## 6. 상품 상태 변경

| 항목 | 내용 |
|------|------|
| URL | `PATCH /products/{productId}/status` |
| 설명 | 상품의 판매 상태를 변경합니다. |
| Request Body | `status` |
| Response | `200 OK` |
| Response Body | `id`, `name`, `status`, `updatedAt` |
| Error | `400 Bad Request`, `404 Not Found` |
| 비고 | 변경 가능한 상태는 `SELLING`, `SOLD_OUT`, `DISCONTINUED`입니다. |

---

## 7. 상품 삭제

| 항목 | 내용 |
|------|------|
| URL | `DELETE /products/{productId}` |
| 설명 | 특정 상품을 삭제합니다. |
| Response | `200 OK` |
| Response Body | `message` |
| Error | `404 Not Found` |
| 비고 | 삭제 완료 메시지를 반환합니다. |

---

# 6. 주문 정보 관리

## 1. 주문 생성

| 항목 | 내용 |
|------|------|
| URL | `POST /orders` |
| 설명 | 새로운 주문을 생성합니다. |
| Request Body | `customerId`, `productId`, `quantity` |
| Response | `201 Created` |
| Response Body | `id`, `orderNumber`, `customerName`, `productName`, `quantity`, `totalPrice`, `status`, `orderedAt`, `createdBy` |
| Error | `400 Bad Request`, `404 Not Found` |
| 비고 | 상품 재고보다 많은 수량은 주문할 수 없습니다. |

---

## 2. 주문 리스트 조회

| 항목 | 내용 |
|------|------|
| URL | `GET /orders` |
| 설명 | 주문 목록을 검색 조건과 함께 조회합니다. |
| Query Parameter | `keyword`, `page`, `size`, `sortBy`, `sortOrder`, `status` |
| Response | `200 OK` |
| Response Body | `orders`, `pagination` |
| Error | `400 Bad Request` |
| 비고 | 검색, 정렬, 페이징을 지원합니다. |

---

## 3. 주문 상세 조회

| 항목 | 내용 |
|------|------|
| URL | `GET /orders/{orderId}` |
| 설명 | 특정 주문의 상세 정보를 조회합니다. |
| Response | `200 OK` |
| Response Body | `id`, `orderNumber`, `customerName`, `customerEmail`, `productName`, `quantity`, `totalPrice`, `orderedAt`, `status`, `createdBy`, `createdByEmail`, `createdByRole` |
| Error | `404 Not Found` |
| 비고 | 관리자가 생성한 주문인 경우 생성 관리자 정보가 함께 조회됩니다. |

---

## 4. 주문 상태 변경

| 항목 | 내용 |
|------|------|
| URL | `PATCH /orders/{orderId}/status` |
| 설명 | 주문 상태를 변경합니다. |
| Request Body | `status` |
| Response | `200 OK` |
| Response Body | `id`, `orderNumber`, `status`, `updatedAt` |
| Error | `400 Bad Request`, `404 Not Found` |
| 비고 | 상태는 `PREPARING → SHIPPING → DELIVERED` 순서로만 변경할 수 있습니다. |

---

## 5. 주문 취소

| 항목 | 내용 |
|------|------|
| URL | `PATCH /orders/{orderId}/cancel` |
| 설명 | 주문을 취소합니다. |
| Request Body | `cancelReason` |
| Response | `200 OK` |
| Response Body | `id`, `orderNumber`, `status`, `cancelReason`, `canceledAt` |
| Error | `400 Bad Request`, `404 Not Found` |
| 비고 | `PREPARING` 상태의 주문만 취소할 수 있으며, 취소 시 상품 재고가 자동 복구됩니다. |