# spring-gift-enhancement
## 1단계 - 엔티티 매핑
### 기능 목록
- [x] JPA 의존성 추가 및 application.properties에 관련 설정 추가
- [x] Product 리팩토링
  - [x] Product를 JPA 엔티티로 변환
  - [x] ProductRepository를 Jdbc 기반에서 JPA 기반으로 변경 
  - [x] ProductRepository에 의존하는 WishService 및 WishServiceTest 리팩토링
  - [x] JPA 적용으로 인한 ProductService 로직 변경
  - [x] DataJpaTest를 이용한 ProductRepository 테스트 코드 추가
- [x] Member 리펙토링
  - [x] Member를 JPA 엔티티로 변환
  - [x] MemberRepository를 Jdbc 기반에서 JPA 기반으로 변경
  - [x] JPA 적용으로 인한 MemberService 로직 변경
  - [x] DataJpaTest를 이용한 MemberRepository 테스트 코드 추가
- [x] Wishlist 리펙토링
  - [x] Wishlist를 JPA 엔티티로 변환
  - [x] WishlistRepository를 Jdbc 기반에서 JPA 기반으로 변경
  - [x] JPA 적용으로 인한 MemberService 로직 변경 및 관련 예외 처리 추가
  - [x] MemberService 로직 변경으로 인한 WishlistController 리팩토링
  - [x] DataJpaTest를 이용한 WishlistRepository 테스트 코드 추가
- [x] JPA의 데이터베이스 스키마 자동 생성을 위한 설정 추가 및 data.sql 사용을 위한 설정 추가

## 2단계 - 페이지네이션
- [x] 상품에 페이지네이션 구현
  - [x] ProductService의 getProducts 메서드 로직 변경(페이지 반환)
  - [x] ProductController의 getProducts 메서드 로직 변경
  
- [x] 위시리스트에 페이지네이션 구현
  - [x] WishlistRepository의 findAllByMember 메서드 반환 타입 변경
  - [x] WishlistService의 getWishesByMember 메서드 로직 변경
  - [x] WishlistController의 getWishlist 메서드 로직 변경
  
- [x] 기존 테스트 코드 복구 및 리팩토링

## 리뷰 반영
- [x] WishlistServiceTest 실패하는 테스트 성공하도록 수정  
- [x] entity의 기본 생성자의 접근 제어자를 protected로 변경
- [x] MemberService 에서 반복되는 회원 조회 로직을 private 메서드로 추출
- [x] WishNotFoundByMemberIdAndWishId 예외클래스 이름을 WishlistAccessDeniedException으로 변경
- [x] Wishlist에게 위시리스트를 가진 소유자가 맞는지 확인하는 로직 위임

## 3단계 - 상품옵션