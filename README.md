# 👥 회원 API

---

<details>
<summary>🎫 회원 가입</summary>

### Request

```json
POST /api/members/register HTTP/1.1
Content-Type: application/json
host: localhost:8080

{
    "email": "admin@email.com",
    "password": "password"
}
```

### Response

```json
HTTP/1.1 201 Created
Content-Type: application/json

{
    "token": ""
}
```
    
</details>
<details>
<summary>🔑 로그인</summary>

### Request

```json
POST /api/members/login HTTP/1.1
Content-Type: application/json
host: localhost:8080

{
    "email": "abc@gmail.com",
    "password": "123qwe"
}
```

### Response

```json
HTTP/1.1 200 OK
Content-Type: application/json

{
    "token": ""
}
```

</details>

# 📦 상품 API

---

<details>
<summary>🔎 상품 조회 (전체 상품)</summary>

### Request

```json
GET /api/products HTTP/1.1
```

### Response

```json
HTTP/1.1 200 OK
Content-Type: application/json

[
    {
        "id": 1,
        "name": "아이스 카페 아메리카노 T",
        "price": 4500,
        "imageUrl": "https://st.kakaocdn.net/product/api/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
    },
    {
        "id": 2,
        "name": "(ICE)아메리카노",
        "price": 2000,
        "imageUrl": "https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220622112804_d176787353ab48c690936557eefad11c.jpg"
    }
]
```

</details>
<details>
<summary>🔎 상품 조회 (특정 상품)</summary>

### Request

```json
GET /api/products/{productId} HTTP/1.1
```

### Response

```json
HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 1,
    "name": "아이스 카페 아메리카노 T",
    "price": 4500,
    "imageUrl": "https://st.kakaocdn.net/product/api/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
}
```

</details>
<details>
<summary>➕ 상품 추가</summary>

### Request

```json
POST /api/products HTTP/1.1
Content-Type: application/json

{
    "name": "(ICE)아메리카노",
    "price": 2000,
    "imageUrl": "https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220622112804_d176787353ab48c690936557eefad11c.jpg"
}
```

### Response

```json
HTTP/1.1 201 Created
Content-Type: application/json

{
    "id": 1,
    "name": "(ICE)아케리카노",
    "price": 2000,
    "imageUrl": "https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20220622112804_d176787353ab48c690936557eefad11c.jpg"
}
```

</details>
<details>
<summary>✏️ 상품 수정</summary>

### Request

```json
PUT /api/products/{productId} HTTP/1.1
Content-Type: application/json

{
    "name": "[EVENT](ICE)아메리카노",
    "price": 1600,
    "imageUrl": "https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20250515110714_9664acdff2b84e4e806c4d7d55dd8de0.jpg"
}
```

### Response

```json
HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 1,
    "name": "[EVENT](ICE)아메리카노",
    "price": 1600,
    "imageUrl": "https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20250515110714_9664acdff2b84e4e806c4d7d55dd8de0.jpg"
}
```

</details>
<details>
<summary>❌ 상품 삭제</summary>

### Request

```json
DELETE /api/products/{productId} HTTP/1.1
```

### Response

```json
HTTP/1.1 204 No Content
```

</details>

# 🎁 위시 리스트 API

---

<details>
<summary>🔎 위시 리스트 보기</summary>

### Request
- Header: Authorization: Bearer {JWT}

```json
GET /api/wishes?page=0&size=5&sort=createdDate,desc HTTP/1.1
Host: localhost:8080
```

### Response

```json
HTTP/1.1 200 OK
Content-Type: application/json

[
    {
        "id": 1,
        "product": {
            "id": 10,
            "name": "테스트 상품 1",
            "price": 15000,
            "imageUrl": "http://example.com/image.jpg"
        }
    },
    {
        "id": 2,
        "product": {
            "id": 12,
            "name": "테스트 상품 2",
            "price": 20000,
            "imageUrl": "http://example.com/image2.jpg"
        }
    }
]
```
    
</details>
<details>
<summary>➕ 위시 리스트 추가</summary>

### Request
- Header: Authorization: Bearer {JWT}

```json
POST /api/wishes HTTP/1.1
Content-Type: application/json
host: localhost:8080

{
    "productId": 1
}
```

### Response

```json
{
    "id": 1,
    "product": {
        "id": 10,
        "name": "테스트 상품 1",
        "price": 15000,
        "imageUrl": "[http://example.com/image.jpg](http://example.com/image.jpg)"
    }
}
```
    
</details>
<details>
<summary>❌ 위시 리스트 삭제</summary>

### Request
- Header: Authorization: Bearer {JWT}

```json
DELETE /api/wishes/{wishlistId} HTTP/1.1
host: localhost:8080

```

### Response

```json
HTTP/1.1 204 No Content
```
    
</details>

# 👤 유저 화면

---

<details>
<summary>✅ 로그인 및 회원가입</summary>

### 로그인

[GET] http://localhost:8080/members/login  
→ 로그인 화면으로 이동합니다.

### 특정 상품 조회

[GET] http://localhost:8080/members/register  
→ 회원 가입 화면으로 이동합니다.
</details>
<details>
<summary>🔎 상품 조회</summary>

### 전체 상품 목록

[GET] http://localhost:8080/members/products  
→ 등록된 모든 상품을 목록으로 확인할 수 있는 화면입니다.

### 특정 상품 조회

[GET] http://localhost:8080/members/products/{productId}  
→ 선택한 상품의 상세 정보를 확인할 수 있는 화면입니다.

### 위시 리스트 조회

[GET] http://localhost:8080/members/wishes  
→ 선택한 상품의 상세 정보를 확인할 수 있는 화면입니다.
</details>

# 🧑‍💻 관리자 화면

---

<details>
<summary>🔎 상품 조회</summary>

### 전체 상품 목록

[GET] http://localhost:8080/admin/products  
→ 등록된 모든 상품을 목록으로 확인할 수 있는 화면입니다.

### 특정 상품 조회

[GET] http://localhost:8080/admin/products/{productId}  
→ 선택한 상품의 상세 정보를 확인할 수 있는 화면입니다.
</details>
<details>
<summary>➕ 상품 추가</summary>

### 상품 추가 화면

[GET] http://localhost:8080/admin/products/new  
→ 새 상품을 입력하는 폼으로 이동합니다.

### 상품 추가 요청

[POST] http://localhost:8080/admin/products  
→ 폼에서 입력된 내용을 서버에 전송해 새 상품을 추가합니다.
</details>
<details>
<summary>✏️ 상품 수정</summary>

### 상품 수정 화면

[GET] http://localhost:8080/admin/products/edit/{productId}  
→ 선택한 상품의 정보를 수정할 수 있는 화면입니다.

### 상품 수정 요청

[PUT] http://localhost:8080/admin/products/{productId}  
→ HTML `<form>`에서 `_method=put`로 전송되는 요청입니다.  
→ 실제 HTTP 메서드는 `POST`이며,  
→ AdminController에서 `@PutMapping`으로 처리합니다.
</details>
<details>
<summary>❌ 상품 삭제</summary>

### 상품 삭제 요청

[DELETE] http://localhost:8080/admin/products/{productId}  
→ HTML `<form>`에서 `_method=delete`로 전송됩니다.  
→ 실제 HTTP 메서드는 `POST`이며,  
→ AdminController에서 `@DeleteMapping`으로 처리합니다.
</details>

# 💾 데이터베이스

---

<details>
<summary>🛠️ 사용 DB</summary>

### H2 Database (인메모리 DB)

- JDBC URL: `jdbc:h2:mem:spring-gift`
- Username: `sa`
- Password: ``

</details>
<details>
<summary>📌 DB 초기화</summary>

```sql
drop table if exists member
drop table if exists product
drop table if exists wish

create table member (
    id bigint not null auto_increment,
    email varchar(255) not null,
    password varchar(255) not null,
    role enum ('ADMIN','USER') not null,
    primary key (id)
)

create table product (
    id bigint not null auto_increment,
    price bigint not null,
    image_url varchar(255) not null,
    name varchar(255) not null,
    primary key (id)
)

create table wish (
    created_date datetime(6) not null,
    id bigint not null auto_increment,
    member_id bigint not null,
    product_id bigint not null,
    primary key (id)
)

alter table member 
   add constraint UKmbmcqelty0fbrvxp1q58dn57t unique (email);

alter table wish 
   add constraint UKimrh37c61jscdegh9fi3jbpix unique (member_id, product_id);

alter table wish 
   add constraint FK70nrc4a6uvljrtemsn80eq1gd 
   foreign key (member_id) 
   references member (id)
       
alter table wish 
   add constraint FKh3bvkvkslnehbxqma1x2eynqb 
   foreign key (product_id) 
   references product (id)
```

</details>

# ⚖️ 유효성 검사 및 예외 처리

---

<details>
<summary>🔍 유효성 검사</summary>

### 상품 이름

- 필수 입력
- 최소 1자, 최대 15자
- (), [], +, -, &, /, _ 외의 특수 문자를 사용할 수 없음
- RequiresApprovalWords 어노테이션을 사용하여 특정 단어가 포함되지 않도록 검사

### 상품 가격

- 0원 이상

### 상품 이미지 URL

- 필수 입력

</details>
<details>
<summary>🚨 예외 처리</summary>

### AuthenticationException
- 인증되지 않은 사용자 (로그인하지 않은 경우)
- 인증 토큰이 유효하지 않은 경우 (예: 만료된 토큰)

### AuthorizationException
- 인증된 사용자 (로그인한 경우) 권한이 없는 요청
  - 다른 사용자의 위시 리스트 삭제
  - 일반 사용자가 관리자 권한이 필요한 행위 요청

### LoginFailedException
- 로그인 실패 시 (잘못된 이메일 또는 비밀번호)

### ProductNotFoundException
- 상품이 존재하지 않을 경우 (조회, 수정, 삭제 시)

### WishException
- 위시 리스트가 존재하지 않을 경우
- 위시 리스트 중복 저장 시

</details>

# 🧪 테스트

---

<details>
<summary>API 테스트</summary>

- 상품 조회 (전체 상품)
- 상품 조회 (특정 상품)
- 상품 추가
- 상품 수정
- 상품 삭제

</details>
<details>
<summary>유효성 검사 테스트</summary>

- 상품 이름 (최대 15자 실패)
- 상품 이름 (특수 문자 성공)
- 상품 이름 (특수 문자 실패)
- 상품 이름 (MD 승인 글자)
- 상품 가격 (0원 이상 실패)
</details>
<details>
<summary>DataJpa 테스트</summary>

### 상품 CRUD 테스트
- 상품 저장 및 조회
- 상품 수정
- 상품 삭제

### 멤버 CRUD 테스트
- 멤버 저장 및 조회
- 멤버 조회 실패
- 멤버 회원가입 실패 (중복 이메일)

### 위시 리스트 CRUD 테스트
- 위시 리스트 저장
- 위시 리스트 조회
- 위시 중복 저장 실패
</details>