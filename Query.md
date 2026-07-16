
# Dummy Data

```sql
use ecommerce;

-- ========== Admins ==========
INSERT INTO admins (name, email, password, phone, role, status, created_at, approved_at, rejected_at, reject_reason) VALUES
('최고관리자', 'superadmin@example.com', '$2a$04$JdjUbxUUiB1osaLbFi4Y..SmyTxJkWe0ZtwXR//7nz.1YhmPXmOA2', '010-9999-9999', 'SUPER_ADMIN', 'ACTIVE', NOW(), NOW(), NULL, NULL),
('CS관리자', 'csadmin@example.com', '$2a$04$JdjUbxUUiB1osaLbFi4Y..SmyTxJkWe0ZtwXR//7nz.1YhmPXmOA2', '010-8888-8888', 'CS_ADMIN', 'ACTIVE', NOW(), NOW(), NULL, NULL);

-- ========== Customers ==========
INSERT INTO customers (name, email, phone, role, status, created_at, updated_at, total_orders, total_purchase_amount) VALUES
('김민수', 'user01@example.com', '010-1001-1001', 'CUSTOMER', 'ACTIVE', NOW(),NOW(), 1, 1200000),
('이지은', 'user02@example.com', '010-1002-1002', 'CUSTOMER', 'ACTIVE', NOW(),NOW(), 1, 278000),
('박지호', 'user03@example.com', '010-1003-1003', 'CUSTOMER', 'ACTIVE', NOW(),NOW(), 1, 30000),
('최유리', 'user04@example.com', '010-1004-1004', 'CUSTOMER', 'INACTIVE', NOW(),NOW(), 1, 1200000),
('정현우', 'user05@example.com', '010-1005-1005', 'CUSTOMER', 'ACTIVE', NOW(),NOW(),1, 45000);
-- ========== Products ==========
INSERT INTO products (admin_id, name, category, price, stock, status, created_at) VALUES
(1, '삼성 갤럭시 S24', 'ELECTRONICS', 1200000, 50, 'SELLING', NOW()),
(1, '나이키 에어포스 1', 'FASHION', 139000, 100, 'SELLING', NOW()),
(2, '신라면 40봉', 'FOOD', 30000, 200, 'SELLING', NOW()),
(1, 'LG 그램 16인치', 'ELECTRONICS', 1800000, 0, 'SOLD_OUT', NOW()),
(2, '단종된 구형 노트북', 'ELECTRONICS', 900000, 0, 'DISCONTINUED', NOW());

-- ========== Orders ==========
-- admin_id NULL = 고객 직접 주문, 값 있음 = CS 대리주문
-- cancel_at, cancle_reason은 스키마상 NULL 허용이라 취소 안 된 건은 NULL로 둠
INSERT INTO orders (order_number, customer_id, product_id, admin_id, quantity, total_price, status, created_at, cancel_at, cancel_reason) VALUES
('ORD-20260713-0001', 1, 1, 2, 1, 1200000, 'DELIVERED', NOW(), NULL, NULL),
('ORD-20260713-0002', 2, 2, NULL, 2, 278000, 'SHIPPING', NOW(), NULL, NULL),
('ORD-20260713-0003', 3, 3, NULL, 1, 30000, 'PREPARING', NOW(), NULL, NULL),
('ORD-20260713-0004', 4, 1, 2, 1, 1200000, 'CANCELED', NOW(), NOW(), '고객 요청으로 주문 취소'),
('ORD-20260713-0005', 5, 5, NULL, 3, 45000, 'PREPARING', NOW(), NULL, NULL);



# Table Query

use ecommerce;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS admins;

CREATE TABLE admins (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(50) NOT NULL,
                        email VARCHAR(50) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL,
                        phone VARCHAR(255) NOT NULL,
                        role VARCHAR(50) NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        created_at DATETIME NOT NULL,
                        updated_at DATETIME NULL,
                        approved_at DATETIME,
                        rejected_at DATETIME,
                        reject_reason TEXT NULL
);

CREATE TABLE customers (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(50) NOT NULL,
                           email VARCHAR(50) NOT NULL UNIQUE,
                           phone VARCHAR(255) NOT NULL,
                           role VARCHAR(50) NOT NULL,
                           status VARCHAR(50) NOT NULL,
                           created_at DATETIME NOT NULL,
                           updated_at DATETIME NULL,
                           total_orders INT NOT NULL,
                           total_purchase_amount INT NOT NULL
);

CREATE TABLE products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          admin_id BIGINT NOT NULL,
                          name VARCHAR(50) NOT NULL,
                          category VARCHAR(255) NOT NULL,
                          price INT NOT NULL,
                          stock INT NOT NULL,
                          status VARCHAR(50) NOT NULL,
                          created_at DATETIME NOT NULL,
                          updated_at DATETIME NULL,
                          FOREIGN KEY (admin_id) REFERENCES admins(id)
);

CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        order_number VARCHAR(50) NOT NULL,
                        customer_id BIGINT NOT NULL,
                        product_id BIGINT NULL,
                        admin_id BIGINT NULL,
                        quantity INT NOT NULL,
                        total_price INT NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        created_at DATETIME NOT NULL,
                        cancel_at DATETIME NULL,
                        cancel_reason TEXT NULL,
                        FOREIGN KEY (customer_id) REFERENCES customers(id),
                        FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE SET NULL,
                        FOREIGN KEY (admin_id) REFERENCES admins(id)
);

