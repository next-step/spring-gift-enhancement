# spring-gift-enhancement

## step 0 기본 코드 준비
- 기존 코드를 gift-enhancement 저장소로 이관
## step 1 엔티티 매핑
1. JPA 기반 엔티티 및 테이블 매핑
- Member 엔티티 생성 및 테이블 매핑
- Product 엔티티 생성 및 테이블 매핑
- Wish 엔티티 생성 및 테이블 매핑
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
