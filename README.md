# spring-gift-enhancement

## Step1 - 엔티티 매핑
- 기존의 JDBC Template 기반의 코드를 JPA로 리팩토링한다.
  - 기존 Entity와 Repository에 JPA 적용
  - JPA를 적용한 Repository의 메서드 명에 맞게 코드 수정 
  - @DataJpaTest를 사용한 테스트 코드 작성

## Step2 - 페이지네이션
- 상품과 위시 리스트 보기에 페이지네이션을 구현한다.
  - 상품, 위시 Entity에 생성시간, 수정시간 필드 추가
  - 상품, 위시 리스트를 응답하기 위해 `Pageable`를 사용한 메서드 추가
    - 페이지 크기: 4
    - 정렬 기준: 생성 시간을 기준으로 내림차순