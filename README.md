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


# Step3

## 요구사항

상품 정보에 옵션을 추가한다. 상품과 옵션 모델 간의 관계를 고려하여 설계하고 구현한다.

- 상품에는 항상 하나 이상의 옵션이 있어야 한다.
  - 옵션 이름은 공백을 포함하여 최대 50자까지 입력할 수 있다.
  - 특수 문자
  - 가능: ( ), [ ], +, -, &, /, _
  - 그 외 특수 문자 사용 불가
  - 옵션 수량은 최소 1개 이상 1억 개 미만이다.
- 중복된 옵션은 구매 시 고객에게 불편을 줄 수 있다. 동일한 상품 내의 옵션 이름은 중복될 수 없다.
- 상품 옵션의 수량을 지정된 숫자만큼 빼는 기능을 구현한다.
  - 별도의 HTTP API를 만들 필요는 없다.
  - 서비스 클래스 또는 엔티티 클래스에서 기능을 구현하고 나중에 사용할 수 있도록 한다.


## 구현 기능

- [x] 상품 옵션을 저장할 ProductOption Entity 생성
- [x] Product 엔티티와 연관관계를 설정
- [x] Vaildation을 사용하여 입력을 검증
- [x] 상품의 옵션을 추가
- [x] 상품의 옵션을 모두 조회
- [x] 옵션 이름 중복 체크
- [x] 엔티티 클래스에 옵션 수량 조절 메서드 구현
- [x] JPA를 활용한 조회 저장 테스트 코드 작성

### ProductOption 테이블
| 컬럼명          | 타입          | 제약 조건                        | 설명                       |
| ------------ | ----------- | ---------------------------- | ------------------------ |
| `id`         | BIGINT      | PK, AUTO\_INCREMENT          | 옵션 ID (기본 키)             |
| `name`       | VARCHAR(50) | NOT NULL                     | 옵션 이름                    |
| `quantity`   | INTEGER     | NOT NULL, 1 이상 1억 미만         | 재고 수량                    |
| `product_id` | BIGINT      | NOT NULL, FK → `product(id)` | 이 옵션이 속한 상품 ID (외래 키 참조) |


##  Product Option API 명세

###  상품 옵션 추가

- **URL**: `/api/products/{id}/options`
- **Method**: `POST`
- **설명**: 특정 상품에 옵션을 추가합니다.


#### 요청 예시

```http
POST /api/products/1/options HTTP/1.1
Content-Type: application/json
```

```json
{
  "name": "01. [Best] 시어버터 핸드 & 시어 스틱 립 밤",
  "quantity": 1000
}
```

#### 응답: `201 Created`

- 바디 없음


---

###  상품 옵션 조회

- **URL**: `/api/products/{id}/options`
- **Method**: `GET`
- **설명**: 특정 상품의 옵션 목록을 조회합니다.

#### 응답: `200 OK`

```json
[
  {
    "id": 101,
    "name": "01. [Best] 시어버터 핸드 & 시어 스틱 립 밤",
    "quantity": 1000
  },
  {
    "id": 102,
    "name": "02. 시어버터 핸드 크림",
    "quantity": 500
  }
]
```


### ⚙️ 유효성 검사 규칙

- **옵션 이름**
    - 최대 50자
    - 허용 특수문자: `( ) [ ] + - & / _`
- **옵션 수량**
    - 최소 1개 이상
    - 최대 99,999,999 (1억 미만)

