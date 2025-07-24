# spring-gift-enhancement

## Step-00 코드 준비
위시리스트 코드 복사 완료.

## Step-01 엔티티 매핑
- model 및 repository 수정
- controller와 service 계층 전부 주석 처리


## Step-02 페이지네이션
- page 매개변수 추가를 통한 구현
- repository 인터페이스에서 필요 함수들 추가

## Step-03 상품옵션
- 상품 옵션에 대한 controller, entity, repository, service 구현
- 유효성 검사 전부 완료
- 옵션 상품 전체 보기와 새로운 옵션 등록 API 구현
- *(문제)* 응답 DTO를 따로 만들지 않아서 옵션을 등록했을 때 Response로 Product에 대한 정보가 튀어나옴.

