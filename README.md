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
