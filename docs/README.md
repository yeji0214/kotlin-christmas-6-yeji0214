# 🎄 크리스마스 프로모션 🎄

## 1. 필요한 데이터 저장
- 음식의 메뉴와 가격 저장
- 요일 저장
## 2. 방문 예상 날짜 입력받기
- 예외처리
  - 1 이상 31 이하의 숫자가 아닌 경우
  - 숫자가 아닌 경우
  - 올바르게 입력할 때까지 반복
## 3. 주문할 메뉴와 개수 입력받기
- 각 메뉴들은 ,로 구분되며, 메뉴와 개수는 -로 구분된다
- 예외처리
  - 메뉴판에 없는 메뉴를 입력하는 경우
  - 메뉴의 개수가 1보다 작은 경우
  - 개수가 숫자가 아닌 경우
  - 메뉴 형식이 예시와 다른 경우
  - 중복 메뉴를 입력한 경우
  - 음료만 주문한 경우
  - 메뉴의 총 개수가 20을 초과한 경우
## 4. 주문 메뉴 출력
- 순서 무관
## 5. 할인 전 총 주문 금액 출력
## 6. 할인 계산
- 총 주문금액이 10000원 이상일 때 적제
- 날짜가 1 ~ 25 면
  - 크리스마스 디데이 할인 -> 1000 + (날짜 - 1) * 100 원
- 날짜 % 7 의 값이 1(금)이거나 2(토)면 (주말)
  - 메인 메뉴가 있으면
    - 주말 할인 -> 2023원 (메뉴 1개당)
- 아니면 (평일)
  - 디저트 메뉴가 있으면
    - 평일 할인 -> 2023원 (메뉴 1개당)
- 날짜 % 7 의 값이 3이거나 25면
  - 특별 할인 -> 1000원
## 7. 증정 메뉴 출력
- 할인 전 총 주문 금액이 12만원 이상일 때 샴페인 증정
## 8. 혜택 내역 출력
- 고객에게 적용된 이벤트 내역만 출력, 순서 무관
## 9. 총 혜택 금액 출력
- 할인 금액의 합계 + 증정 메뉴의 가격
## 10. 할인 후 예상 결제 금액 출력
- 할인 전 총 주문 금액 - 할인 금액 (샴페인 값은 포함 X)
## 11. 12월 이벤트 배지 출력 
- 혜택 금액에 따라 부여
  - 5천 원 이상: 별
  - 1만 원 이상: 트리
  - 2만 원 이상: 산타
## 12. 그 외 기능 구현
- 메서드 길이 조절
- 인텐트 depth 조절
- 클래스 분리
- inputView, OutputView 사용
- 테스트 코드 작성