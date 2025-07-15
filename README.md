# spring-gift-enhancement

## step 0 기본 코드 준비
- 기존 코드를 gift-enhancement 저장소로 이관
## step 1 엔티티 매핑
1. JPA 기반 엔티티 및 테이블 매핑
- Member 엔티티 생성 및 테이블 매핑 (id, email, password)
- Product 엔티티 생성 및 테이블 매핑 (id, name, price, imageUrl)
- Wish 엔티티 생성 및 테이블 매핑 (id, member, product)
- Wish 엔티티의 Member, Product를 외래키가 아닌 객체 참조로 매핑
2. Repository 구현
- MemberRepository JPA 리포지토리 생성
- ProductRepository JPA 리포지토리 생성
- WishRepository JPA 리포지토리 생성
3. 학습 테스트(@DataJpaTest)
- MemberRepository 저장, 조회 테스트
- ProductRepository 저장, 조회 테스트
- WishRepository 저장, 조회 테스트
- Wish 저장 시 Member, Product와의 연관관계 매핑 테스트
4. JPA 설정 및 H2 MySQL 모드
- H2 DB를 MySQL 모드로 설정
- Hibernate SQL 출력 설정 (pretty print + show sql)
- schema.sql, data.sql 초기 데이터 스크립트 작성
