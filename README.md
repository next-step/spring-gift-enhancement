# spring-gift-enhancement

## step0 기본 코드 준비

## step1 엔티티 매핑
* db의 wish 테이블명 wishes로 변경(위에 members, products와 형식을 맞추기 위함)
* entity 리팩토링(Member, Product, Wish)
* controller-repository로 되어있던 product를 controller-service-repository로 분리
* repository를 JPA방식으로 수정
* 제대로 작동하지 않는 상품수정, 멤버수정 로직 변경
* 사용하지 않는 메서드 삭제

## step2 페이지네이션
* 예외던지기를 repository로 옮김
* global-exception에서 자세한 예외메세지 처리하게 수정
* 