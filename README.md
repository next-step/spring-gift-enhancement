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
- [x] ProductService 코드 가독성을 높이기 위한 리팩토링
- [x] 스네이크 표기법 사용된 패키지명 변경

## 3단계 - 상품옵션
- [x] 상품의 옵션(Option) entity 구현
- [x] Option에 상품 옵션의 수량을 지정된 숫자만큼 빼는 기능을 하는 메서드 추가
- [x] Product와 Option의 양방향 연관관계 설정
  - [x] Product에 Option 객체들을 저장하는 options 필드 추가
  - [x] Product에 연관관계 편의 메서드 추가
- [x] Option CRUD 기능 구현
  - [x] controller, service, repository 구현
  - [x] 요청을 위한 OptionReqeustDto, 응답을 위한 OptionResponseDto 구현
    - OptionRequestDto에 Valid 적용
      - 옵션 이름은 공백을 포함하여 최대 50자까지 입력 가능
      - ( ), [ ], +, -, &, /, _ 그 외 특수 문자 사용 불가
      - 옵션 수량은 최소 1개 이상 1억 개 미만
  - [x] 상품에 Option 추가 기능 구현
  - [x] 상품의 전체 Option 조회 기능 구현
  - [x] 옵션 수정 기능 구현
  - [x] 옵션 삭제 기능 구현
- [x] 상품의 옵션명 중복 검사 상품이 하도록 구현
- [x] 상품에 최소한 하나의 옵션이 존재하도록 구현
- [x] 테스트 코드 추가 작성
  - [x] OptionRepository 테스트 코드 작성
  - [x] Option 도메인 테스트 코드 작성
- [x] 미사용 코드 제거