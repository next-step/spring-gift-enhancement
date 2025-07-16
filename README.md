# spring-gift-enhancement

# 상품 고도화 과제 - step1 JPA 도입
각 엔티티의 연관관계를 매핑하고, 기존 코드에 JPA를 도입해 리팩토링합니다.

## 구현 기능
[x] 각 엔티티 매핑
[x] Member 도메인 Jpa 리팩토링
[x] Product 도메인 Jpa 리팩토링
[x] WishList 도메인 Jpa 리팩토링
[x] DataJpaTest 코드 작성

## 참고사항
- 이전에 사용하던 Jdbc기반의 repository들은 주석처리했습니다. Jpa 버전의 repository들의 이름은 {domain}JpaRepository입니다.
- schema.sql 파일에서 테이블 정의 및 생성을 모두 담당합니다.