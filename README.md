# spring-gift-enhancement

# Step1

## 요구사항

지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.

- 엔티티 클래스와 리포지토리 클래스를 작성해 본다.
- 객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.
- @DataJpaTest를 사용하여 학습 테스트를 해 본다.

## 구현기능

- [x] Product 엔티티를 JPA로 리팩터링한다.
- [x] Member 엔티티를 JPA로 리팩터링한다.
- [x] Wish 엔티티를 JPA로 리팩터링한다.
- [x] Wish 엔티티에서 member 와 prouct의 id를 외래 키로 매핑한다.
- [x] Repository 를 리팩터링 한다.
- [x] @DataJpaTest 를 사용하여 테스트 코드를 작성한다.

## 테이블 설명

### Product

| 컬럼명      | 타입           | 제약 조건              | 설명              |
| ----------- | -------------- | ---------------------- | ----------------- |
| `id`        | `BIGINT`       | `PK`, `AUTO_INCREMENT` | 상품 ID (기본 키) |
| `name`      | `VARCHAR(15)`  | `NOT NULL`             | 상품 이름         |
| `price`     | `INT`          | `NOT NULL`             | 상품 가격         |
| `image_url` | `VARCHAR(255)` | `NOT NULL`             | 상품 이미지 URL   |

### Member

| 컬럼명     | 타입           | 제약 조건              | 설명                             |
| ---------- | -------------- | ---------------------- | -------------------------------- |
| `id`       | `BIGINT`       | `PK`, `AUTO_INCREMENT` | 회원 고유 ID                     |
| `email`    | `VARCHAR(255)` | `NOT NULL`, `UNIQUE`   | 회원 이메일 (로그인 ID로 사용)   |
| `password` | `VARCHAR(255)` | `NOT NULL`             | 회원 비밀번호                    |
| `role`     | `VARCHAR(255)` |                        | 사용자 역할 (예: USER, ADMIN 등) |

### Wish

| 컬럼명       | 타입     | 제약 조건              | 설명                            |
| ------------ | -------- | ---------------------- | ------------------------------- |
| `id`         | `BIGINT` | `PK`, `AUTO_INCREMENT` | 위시 항목의 고유 ID             |
| `member_id`  | `BIGINT` | `FK`, `NOT NULL`       | 회원 ID (`Member` 테이블 참조)  |
| `product_id` | `BIGINT` | `FK`, `NOT NULL`       | 상품 ID (`Product` 테이블 참조) |
| `quantity`   | `INT`    | `NOT NULL`             | 원하는 수량                     |

# Step2

상품과 위시 리스트 보기에 페이지네이션을 구현한다.

- 대부분의 게시판은 모든 게시글을 한 번에 표시하지 않고 여러 페이지로 나누어 표시한다. 정렬 방법을 설정하여 보고 싶은 정보의 우선 순위를 정할 수도 있다.
- 페이지네이션은 원하는 정렬 방법, 페이지 크기 및 페이지에 따라 정보를 전달하는 방법이다.

## 구현기능

- [x] Pageable 를 이용하여 쿼리로 size,page,sort 를 받아 페이지네이션을 구현.

요청
Method: GET

URL: /wishes/page

Headers:

Authorization: Bearer {accessToken} (필수)

## Wish 페이지네이션 API

회원의 위시리스트를 페이지 단위로 조회하는 API입니다.  
JWT 인증이 필요하며, 페이지네이션 및 정렬 기능을 지원합니다.

---

### [GET] /wishes/page

> 위시리스트를 페이지별로 조회합니다.

### 요청 헤더

| 헤더 이름     | 필수 | 설명                             |
| ------------- | ---- | -------------------------------- |
| Authorization | ✅   | `Bearer {JWT_ACCESS_TOKEN}` 형식 |

---

### 🔎 Query Parameters

| 파라미터 | 타입 | 설명                                   | 예시                      |
| -------- | ---- | -------------------------------------- | ------------------------- |
| `page`   | int  | 조회할 페이지 번호 (0부터 시작)        | `0`                       |
| `size`   | int  | 한 페이지당 결과 수                    | `5`                       |
| `sort`   | str  | 정렬 기준 필드 및 방향 (`asc`, `desc`) | `price,desc` / `name,asc` |

---

### 응답: 200 OK

```json
{
  "content": [
    {
      "wishId": 1,
      "productId": 10,
      "name": "초콜릿",
      "price": 3000,
      "imageUrl": "https://example.com/product1.jpg",
      "quantity": 2
    },
    ...
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 5,
    ...
  },
  "totalPages": 2,
  "totalElements": 10,
  "last": false,
  "first": true,

```

## Product 페이지네이션 API

상품 목록을 페이지 단위로 조회하는 API입니다.  
페이지네이션과 정렬을 지원합니다.

---

### [GET] /api/products/paged

> 전체 상품 목록을 페이징 및 정렬 조건과 함께 조회합니다.

---

### 🔎 Query Parameters

| 파라미터 | 타입 | 설명                                   | 예시                      |
| -------- | ---- | -------------------------------------- | ------------------------- |
| `page`   | int  | 조회할 페이지 번호 (0부터 시작)        | `0`                       |
| `size`   | int  | 한 페이지당 결과 수                    | `10`                      |
| `sort`   | str  | 정렬 기준 필드 및 방향 (`asc`, `desc`) | `price,desc` / `name,asc` |

---

### ✅ 응답: 200 OK

```json
{
  "content": [
    {
      "productId": 1,
      "name": "초콜릿",
      "price": 3000,
      "imageUrl": "https://example.com/product1.jpg"
    },
    {
      "productId": 2,
      "name": "꽃다발",
      "price": 15000,
      "imageUrl": "https://example.com/product2.jpg"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    ...
  },
  "totalPages": 1,
  "totalElements": 10,
  "last": true,
  "first": true,
  "sort": {...},
  "numberOfElements": 10
}
```
