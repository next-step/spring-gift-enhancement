# spring-gift-enhancement

# step1 구현 사항

- 엔티티 매핑
- jdbc template 기반 코드를 jpa로 리팩터링
- @DataJpaTest 사용해 테스트 코드 작성

# step2 구현 사항

- 전체 상품 조회에 페이지네이션 구현
- 전체 위시리스트 조회에 페이지네이션 구현

# step3 구현 사항

- 상품 정보에 옵션을 추가

- 옵션 API
    - 옵션 생성: POST /api/products/{productId}/options
    - 옵션 조회: GET /api/products/{productId}/options
    - 옵션 삭제(전체) DELETE /api/products/{productId}/options
    - 옵션 삭제(단건) DELETE /api/products/{productId}/options/{optionId}

- 옵션 이름 요구사항 -> OptionRequestDto에 validation 어노테이션을 통해 구현
    - 공백을 포함하여 최대 50자
    - 특수문자는 (),[],+,-,&,/,_ 만 가능
- 동일한 상품 내의 옵션 이름은 중복불가능
    - product의 옵션 리스트를 조회하여 중복 체크
- 상품 옵션의 수량을 지정된 숫자만큼 빼는 기능 구현
    - option 엔티티에 subtractQuantity() 메서드 