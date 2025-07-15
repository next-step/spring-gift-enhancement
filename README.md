# spring-gift-enhancement

# Step1

## 요구사항

지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.

- 엔티티 클래스와 리포지토리 클래스를 작성해 본다.
- 객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.
- @DataJpaTest를 사용하여 학습 테스트를 해 본다.

## 구현기능

- [x] Product 엔티티를 JPA로 리팩터링한다.
- [x] Member 엔티티를 JPA로 리팩터링한다.
- [x] Wish 엔티티를 JPA로 리팩터링한다.
- [x] Wish 엔티티에서 member 와 prouct의 id를 외래 키로 매핑한다.
- [x] Repository 를 리팩터링 한다.
- [x] @DataJpaTest 를 사용하여 테스트 코드를 작성한다.
