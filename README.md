# spring-gift-enhancement

## Step1 - 엔티티 매핑
- 기존의 JDBC Template 기반의 코드를 JPA로 리팩토링한다.
  - 기존 Entity와 Repository에 JPA 적용
  - JPA를 적용한 Repository의 메서드 명에 맞게 코드 수정 
  - @DataJpaTest를 사용한 테스트 코드 작성