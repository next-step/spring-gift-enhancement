# spring-gift-enhancement

## 구현할 기능 목록 (0단계 - 코드복사)

- [x] 이전 과정 코드 복사

## 구현할 기능 목록 (1단계 - 엔티티 매핑)

- [x] `build.gradle`에 Spring Data JPA 의존성 추가
- [ ] `application.properties`에 JPA 및 H2 데이터베이스 설정 추가
- [ ] `Product` 클래스를 JPA 엔티티로 매핑
- [ ] `Member` 클래스를 JPA 엔티티로 매핑
- [ ] `Wish` 클래스를 JPA 엔티티로 매핑하고 `Member`, `Product`와 연관관계 설정
- [ ] Spring Data JPA를 사용하도록 리포지토리 계층 리팩터링 (`JdbcTemplate` 구현체 삭제)
- [ ] 리포지토리 변경에 따른 서비스 계층 코드 수정
- [ ] `@DataJpaTest`를 이용한 JPA 리포지토리 학습 테스트 작성
