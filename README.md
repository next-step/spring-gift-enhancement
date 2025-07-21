# spring-gift-enhancement

| method | url                        | 기능       |
|--------|----------------------------|----------|
| GET    | /api/products              | 상품 전체 조회 |
| POST   | /api/products              | 상품 생성    |
| PATCH  | /api/products/{id}         | 상품 수정    |
| DELETE | /api/products/{id}         | 상품 삭제    |
| GET    | /api/products/{id}/options | 상품 옵션 조회 |
| GET    | /admin                     | 관리자 페이지  |
| POST   | /api/members/register      | 회원가입     |
| POST   | /api/members/login         | 로그인      |

step 1. 엔티티 매핑

- 기능요구 사항
  + 지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.
    - 아래의 DDL(Data Definition Language)을 보고 유추하여 엔티티 클래스와 리포지토리 클래스를 작성해 본다.
    - 객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.
    - @DataJpaTest를 사용하여 학습 테스트를 해 본다.

1. Member entity,repository 클래스를 JPA로 리팩토링
2. Product entity,repository 클래스를 JPA로 리팩토링
3. WishList entity,repository 클래스를 JPA로 리팩토링
4. @DataJpaTest를 사용하여 WishListRepositoryTest

step 2. 페이지네이션

1. 위시리스트를 페이지네이션을 사용하여 조회
2. 상품리스트를 페이지네이션을 사용하여 조회

step 1. 피드백
1. ResponseStatusException의 status code를 항상404으로 반환한걸 수정
2. Optional 타입의 null 타입확인시 중복되는 코드 메소드화

step 3. 상품 옵션

- 기능요구 사항
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
  - (선택) 관리자 화면에서 옵션을 추가할 수 있다.

1. product 테이블에 option을 추가하기
2. 상품 옵션의 수량을 지정된 숫자만큼 빼는 기능을 구현
3. 상품의 옵션을 조회하는 API 구현

step 2. 피드백
1. dto, entity 간의 변환 로직 추가
