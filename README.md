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
- [ ] `WishRepository`의 `findByMember` 메소드가 `Pageable`을 인자로 받도록 수정
- [ ] `WishService`의 `getWishes` 메소드가 `Pageable`을 인자로 받도록 수정
- [ ] `WishController`의 위시리스트 조회 API가 페이지네이션을 지원하도록 수정