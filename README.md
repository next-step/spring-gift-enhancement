# spring-gift-enhancement

## 구현할 기능 목록 (0단계 - 코드복사)

- [x] 이전 과정 코드 복사

## 구현할 기능 목록 (1단계 - 엔티티 매핑)

- [x] `build.gradle`에 Spring Data JPA 의존성 추가
- [x] `application.properties`에 JPA 및 H2 데이터베이스 설정 추가
- [x] `Product` 클래스를 JPA 엔티티로 매핑
- [x] `Member` 클래스를 JPA 엔티티로 매핑
- [x] `Wish` 클래스를 JPA 엔티티로 매핑하고 `Member`, `Product`와 연관관계 설정
- [x] Spring Data JPA를 사용하도록 리포지토리 계층 리팩터링 (`JdbcTemplate` 구현체 삭제)
- [x] 리포지토리 변경에 따른 서비스 계층 코드 수정
- [x] `@DataJpaTest`를 이용한 JPA 리포지토리 학습 테스트 작성

## 구현할 기능 목록 (2단계 - 페이지네이션)

- [x] `ProductRepository`에 페이지네이션 기능 적용 (수정 필요 없음, JpaRepository 기본 기능 활용 가능!)
- [x] `ProductService`의 `getAllProducts` 메소드가 `Pageable`을 인자로 받도록 수정
- [x] `ProductController`의 상품 목록 조회 API가 페이지네이션을 지원하도록 수정
- [x] `AdminProductController`의 상품 관리 페이지가 페이지네이션을 지원하도록 수정
- [x] `admin/product/list.html` 뷰에 페이지네이션 UI(이전, 다음, 페이지 번호) 추가
- [x] `WishRepository`의 `findByMember` 메소드가 `Pageable`을 인자로 받도록 수정
- [x] `WishService`의 `getWishes` 메소드가 `Pageable`을 인자로 받도록 수정
- [x] `WishController`의 위시리스트 조회 API가 페이지네이션을 지원하도록 수정

## 개별 개선 작업 (위시리스트 조회 페이지 추가)

- [x] 위시리스트 페이지 뷰 (`wishlist.html`) 추가
- [x] 위시리스트 페이지에서 현재 로그인한 사용자의 위시리스트 상품 목록을 비동기(AJAX)로 조회
- [x] 조회된 상품 목록을 화면에 동적으로 렌더링
- [x] 위시리스트 상품 삭제 기능 구현

## 개별 개선 작업 (사용자 인증)

- [x] 일반 사용자용 회원가입/로그인 페이지 (`register.html`, `login.html`) 추가
- [x] 로그아웃 기능 구현
- [x] `MemberController`를 사용자 인증 전용 컨트롤러로 리팩터링
- [x] 홈페이지(`home.html`) 및 네비게이션에 회원가입, 로그인, 로그아웃 링크 추가

## 구현할 기능 목록 (3단계 - 상품 옵션)

- [x] `Option` 엔티티 클래스 생성 (name, quantity 필드 포함)
- [x] `Product`와 `Option` 간의 1:N 연관관계 매핑 (상품은 최소 1개 이상의 옵션을 가져야 함)
- [x] `OptionRepository` 생성
- [ ] 옵션 이름, 수량에 대한 유효성 검사 로직 추가
- [ ] 상품 내 옵션 이름 중복 검사 로직 추가
- [x] `Option` 엔티티에 수량 차감(`subtract`) 비즈니스 로직 구현
- [ ] `ProductService` 또는 별도의 `OptionService`에 특정 상품의 옵션 목록을 조회하는 기능 추가
- [ ] 특정 상품의 모든 옵션을 조회하는 API 엔드포인트 구현 (`GET /api/products/{productId}/options`)
- [ ] 옵션 기능 관련 단위 테스트 및 통합 테스트 작성
