# spring-gift-enhancement

## STEP2 - 페이지네이션

### STEP1 관련 피드백 코드 리팩토링

### STEP2 구현 목록
- [ ] **Repository 수정:** `JpaRepository`의 페이지네이션 기능을 사용하도록 `ProductRepository`와 `WishRepository`를 수정한다.
- [ ] **Service 수정:** `Pageable` 객체를 파라미터로 받고, `Page` 객체를 반환하도록 서비스 로직을 수정한다.
- [ ] **API Controller 수정:** 상품 목록 API가 페이지네이션 파라미터(`page`, `size`, `sort`)를 받아 페이징된 결과를 JSON으로 반환하도록 수정한다.
- [ ] **View Controller 수정:** 관리자 화면 컨트롤러가 페이지 정보를 모델에 담아 뷰로 전달하도록 수정한다.
- [ ] **Thymeleaf 템플릿 수정:** 상품 목록 페이지에 페이지 번호, 이전/다음 버튼 등 페이지네이션 UI를 구현한다.paTest`를 사용하여 JPA Repository가 올바르게 동작하는지 테스트한다.