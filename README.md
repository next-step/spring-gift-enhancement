# spring-gift-enhancement
> 상품 옵션
## 기능 요구 사항
* 상품에 옵션이 존재
  * 옵션
    * 이름
      * 50자까지
      * (), [], +, -, &, /, _ 특수문자 가능
      * 동일한 상품, 옵션 이름 중복 x -> UNIQUE (productId, name)
    * 수량 - 최소 1개에서 1억개 미만
      * 지정한 숫자 만큼 뺄 수 있다. - Service에서 메서드만 구현
  * 옵션은 한개 이상 존재 해야함
    * 상품에 Status 조건에 option 수 반영 - 근데 Approve 된 것들 기억하고 있어야하기에 다른 방식으로 해결해야할 것
* 관리자 화면에서 옵션 추가

* 리뷰 반영
  * findAllByStatus로 메서드 명 변경