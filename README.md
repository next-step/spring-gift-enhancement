# Step3 - 상품 옵션

## 기능 요구 사항
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

- 아래 예시와 같이 HTTP 메시지를 주고받도록 구현한다.

Request
``` 
GET /api/products/1/options HTTP/1.1 
```
Response
``` 
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

---
## 체크 리스트 

1. 요구사항 
- [ ] 상품에는 최소 1개의 옵션이 있어야 한다.
- [ ] 옵션 이름은 최대 50자, 특정 특수문자만 허용 (( ), [ ], +, -, &, /, _)
- [ ] 옵션 수량은 1 이상 100,000,000 미만
- [ ] 동일 상품 내 옵션 이름 중복 불가
- [ ] 옵션 수량을 감소시키는 기능 구현 (메서드 기반)
- [ ] 옵션 목록 조회 API 구현 (GET /api/products/{productId}/options)

2. DB 설계 & 엔티티 생성
- [ ] ProductOption 엔티티 생성
- [ ] 필드: id, product(연관관계), name, quantity
- [ ] 검증 로직 추가 (이름 길이, 특수문자 허용, 수량 범위)
- [ ] subtract() 메서드 구현 (수량 감소)
- [ ] Product 엔티티에 @OneToMany 매핑 추가
- [ ] ProductOptionRequestDto, ProductOptionResponseDto 등 옵션 관련 DTO 설계 및 구현

3. Repository 생성
- [ ] ProductOptionRepository 생성 (JpaRepository 상속)
- [ ] List<ProductOption> findByProductId(Long productId)
- [ ] boolean existsByProductIdAndName(Long productId, String name)

4. Service 구현
- [ ] 옵션 추가 로직 (addOption)
- [ ] 동일 상품 내 이름 중복 검증
- [ ] 유효성 검증 후 저장
- [ ] 옵션 목록 조회 로직 (getOptionsByProductId)
- [ ] 옵션 수량 차감 기능 (subtractOptionQuantity)

5. Controller 구현
- [ ] GET /api/products/{productId}/options → 옵션 목록 반환
- [ ] 옵션 추가 API → 관리자 전용
- [ ] 옵션 수량 차감 기능 (비공개 API or 내부 로직에서 호출)

6. 테스트
- [ ] 옵션 이름 제약 검증 테스트
- [ ] 옵션 수량 제약 검증 테스트
- [ ] 동일 상품 내 옵션 이름 중복 테스트
- [ ] 옵션 수량 차감 테스트
- [ ] 옵션 목록 조회 API 테스트 (정상 동작 + 빈 목록)