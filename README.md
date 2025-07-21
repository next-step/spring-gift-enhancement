# spring-gift-enhancement

### 1단계 구현사항
-[x] application.properties, build.gradle 수정
-[x] [Domain] 기존 domain 클래스인 Member, Product, WishList를 JPA 어노테이션을 적용
-[x] [Repository] 기존 Repository 인터페이스(총 3개 - Member, Product, WishList) 내용 삭제후, Spring Data JPA의 JpaRepository를 상속 및 필요에 따라 커스텀 쿼리 메서드 작성
-[x] [Service] 기존 Service 클래스(총 3개 - Member, Product, WishList)에서 Repository 호출시 사용되는 함수 이름 변경 및, Repository 함수 파라미터에 맞게 전처리 작업 추가
-[x] [Dto] WishListRepository 에서 반환하는 WishList 를 편리하게 바꿀 수 있도록 WishListResponseDto에 생성자 추가
-[x] [Test] WishListRepository에 대한 테스트 추가

### 2단계 구현사항
-[x] [Repository] WishListRepository에 findAllByMember 이름을 갖지만, 파라미터로 Pageable을 받는 메소드 오버로딩 (단, ProductRepository에는 findAll 을 사용하고, 이는 JPA에서 기본 제공하므로 선언하지 않음)
-[x] [Service] ProductService 인터페이스와 이에 대한 구현체 ProductServiceImpl에서 기존 List를 반환하는 searchAllProducts 에 Page 반환 및 Pageable 파라미터로 요구하도록 변경
-[x] [Service] WishListService 인터페이스와 이에 대한 구현체 WishListServiceImpl에서 기존 List를 반환하는 getWishList 에 Page 반환 및 Pageable 파라미터로 요구하도록 변경
-[x] [Controller] ProductController의 searchAllProducts와 ProductAdminController의 productList에서 페이지네이션 사용하도록 변경
-[x] [Controller] WishListController의 getWishList에서 페이지네이션 사용하도록 변경
-[x] [Resource/admin] 상품 관리자 페이지에서 페이지네이션 기반 html, css 수정 
-[x] [Test] 기존 WishListTest에서 WishListController 사용하는데 List -> Page 변경에 따른 테스트 코드 원활히 동작하도록 변경

### 3단계 구현사항
-[x] [Domain] Option domain 설계 및 기존 Product domain과의 연관 관계 설정 (Option, Product)
-[x] [resources] 기존 schema.sql, data.sql를 Option domain 설계에 따라 수정
-[x] [Dto] 상품 옵션을 추가하기 위한 OptionRequestDto, 상품 옵션 남은 수량 감소하기 위한 OptionSubtractRequestDto, 옵션의 정보를 담고 있는 OptionInfoResponseDto의 총 3개의 Dto 생성
-[x] [Repository] 2가지 검색기능(상품 id로만 또는 상품id와 옵션 이름 같이 고려한)을 제공하는 JPA 활용한 OptionRepository 구현
-[x] [Service] 상품 옵션을 추가, 조회, 개수 차감 및 초기 상품에 대한 기본 옵션 추가를 DB에 요청하고 이 과정에서 발생할 수 있는 예외 처리를 담당하는 OptionService 인터페이스 및 이에 대한 구현체인 OptionServiceImpl 구현
-[x] [Service] 기존 ProductServiceImpl 에서 "상품에는 항상 하나 이상의 옵션이 있어야 한다" 조건을 만족시키기 위한, 상품 처음 생성시 "상품 이름 + 단품" 으로 구성된 옵션 생성하는 기능 사용토록 수정
-[x] [Controller] 옵션 생성, 조회, 개수 차감에 대한 API 선언 (OptionController)
-[x] [Exception] "동일한 상품 내의 옵션 이름은 중복될 수 없다" 에 대한 커스텀 예외 DuplicateOptionNameException 생성
-[x] [Exception] 상품 옵션 개수 차감시 음수가 되는 요구 발생시에 대한 커스텀 예외 InvalidOptionQuantityException 생성
-[x] [Exception] 해당 상품의 해당 옵션이 존재하는 않는 경우에 대한 커스텀 예외 OptionNotFoundException 생성
-[x] [Exception] GlobalExceptionHandler에 위 3개의 커스텀 예외 반영
-[x] [Test] Option 도메인에 대한 Controller&Service, Repository 테스트 추가 

### 2단계 피드백 반영 사항 (깜빡해서 3단계 구현후 진행)
