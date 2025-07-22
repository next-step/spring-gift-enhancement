# spring-gift-enhancement
# 기본 코드 준비
1. 미션2 위시 리스트에서 작성했던 소스코드 로딩
2. 코드 피드백 반영

# 엔티티 매핑
1. products, users 테이블 매핑을 위한 entity 변경 및 이에 따른 service, repository 레이어 수정
2. wishes 테이블 매핑을 위한 entity 변경 및 이에 따른 3레이어 수정 및 예외 수정
3. JPA 테스트 작성

# 페이지네이션
1. Products 조회 페이지네이션 적용
2. wishes 유저별 조회 페이지네이션 적용
3. Products 관리 화면 페이지 이동 버튼 추가
4. 테스트 코드 수정

# 상품 옵션
1. options, product_options 테이블 정의 및 엔티티 매핑
2. ProductOption에 value 필드 추가
3. 테스트 케이스 작성 및 디버깅(데이터베이스 자체 설정 삭제 후 JPA에게 위탁)
4. 코드 피드백 반영

테이블명 단수형으로 수정

product_option 테이블의 option_value를 value로 네이밍 변경

ProductOptionRepository가 상속받는 인터페이스를 CrudRepository에서 JpaRepository로 변경

비즈니스 코드에 영향을 주던 테스트 코드 수정

Option 관련 API 추가