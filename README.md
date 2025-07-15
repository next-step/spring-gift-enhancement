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
