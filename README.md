# spring-gift-enhancement

## [STEP 0] 기본 코드 준비
- 이전 미션(위시 리스트-요청과 응답 심화)의 스켈레톤 코드를 기반으로 본격적인 개발 시작 전 환경 세팅

## [STEP 1] 엔티티 매핑
JdbcTemplate 기반 코드를 JPA로 리팩토링 하여 객체와 테이블을 매핑하도록 한다.
- Member 엔티티 매핑 및 테스트 코드 작성
- Product 엔티티 매핑
- Wish 엔티티 매핑 (연관관계 포함)
- @DataJpaTest 기반 Repository test code 작성

## [STEP 2] 페이지네이션
상품과 위시 리스트 보기에 페이지네이션을 구현한다.
- Product Pagination
- Member Pagination
- Wish Pagination (상풍명 오름차순/내림차순 정렬 가능)

## [STEP 3] 상품 옵션
 상품 정보에 옵션을 추가한다. 상품과 옵션 모델 간의 관계를 고려하여 설계하고 구현한다.
- 상품에는 항상 하나 이상의 옵션이 있어야 한다.
    - 옵션 이름은 공백을 포함하여 최대 50자까지 입력할 수 있다.
    - 특수 문자
        - 허용: `(`, `)`, `[`, `]`, `+`, `-`, `&`, `/`, `_`
        - 그 외 특수 문자는 사용할 수 없다.
    - 옵션 수량은 최소 1개 이상, 1억 개 미만이다.
- 동일한 상품 내의 옵션 이름은 중복될 수 없다.
- 상품 옵션의 수량을 지정된 숫자만큼 차감하는 기능(서비스 또는 엔티티 레벨)을 구현한다. 별도의 HTTP API는 필요 없다.
- (선택) 관리자 화면에서 옵션을 추가할 수 있다.

### HTTP API 예시
- Request
```angular2html
GET /api/products/1/options HTTP/1.1
```
- Response
```angular2html
HTTP/1.1 200 
Content-Type: application/json

[
  {
    "id": 464946561,
    "name": "01. [Best] 시어버터 핸드 & 시어 스틱 립 밤",
    "quantity": 969
  }
]
```

### 옵션 목록 조회
