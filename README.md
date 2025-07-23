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
* 이름 검색 - findByNameContaining가 escape을 자동으로 추가하여 오류를 일으킴. @Query로 searchByName 을 만들어 해결
* 가격, 이름 기준 정렬

## step3 상품옵션
- [X] 상품에는 항상 하나 이상의 옵션이 있어야 한다.
- [x] 옵션 이름은 공백을 포함하여 최대 50자까지 입력할 수 있다.
- [x]  특수 문자
* 가능: ( ), [ ], +, -, &, /, _
* 그 외 특수 문자 사용 불가
- [x] 옵션 수량은 최소 1개 이상 1억 개 미만이다. 
- [x] 중복된 옵션은 구매 시 고객에게 불편을 줄 수 있다. 동일한 상품 내의 옵션 이름은 중복될 수 없다.
- [x] 상품 옵션의 수량을 지정된 숫자만큼 빼는 기능을 구현한다. (별도의 HTTP API를 만들 필요는 없다.)
- [x] 서비스 클래스 또는 엔티티 클래스에서 기능을 구현하고 나중에 사용할 수 있도록 한다.
- [x] (선택) 관리자 화면에서 옵션을 추가할 수 있다.=>관리자 화면에서 이어지는 상품상세페이지에서 가능하도록 만듦