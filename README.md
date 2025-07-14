# spring-gift-enhancement

## STEP1 - 엔티티 매핑 (JPA)

- [ ] **의존성 추가 및 설정**:
    - `spring-boot-starter-data-jpa` 의존성을 추가한다.
    - `application.properties`에 JPA 및 Hibernate 관련 설정을 추가한다.
- [ ] **엔티티 리팩토링:** `@Entity` 어노테이션을 사용하여 모든 도메인 객체를 JPA 엔티티로 변환하고, 객체 간의 연관 관계(`@ManyToOne`, `@OneToMany`)를 설정한다.
- [ ] **Repository 리팩토링:** 기존 Repository 구현체를 삭제하고, `JpaRepository`를 상속받는 인터페이스로 변경한다.
- [ ] **서비스 계층 수정:** 변경된 Repository를 사용하도록 서비스 코드를 수정한다.
- [ ] **학습 테스트:** `@DataJpaTest`를 사용하여 JPA Repository가 올바르게 동작하는지 테스트한다.