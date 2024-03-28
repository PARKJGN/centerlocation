2024-03-26
- fragment (header, layout)
- Naver Search Api
- autocomplete Address (Proceeding...)

2024-03-27
- Naver -> Kakao 교체

  why? 네이버의 search api는 네이버의 연관검색어. 내가 원하는 기능이 아니었고, kakao는 키워드를 치면 해당 키워드에 연관된 장소를 보여줘서 원하는 기능이었다.
  
- autocomplete Address -> list Address

  why? 키보드 하나하나 칠때마다 연관된 주소가 input 아래에 바로 보여지길 바랬는데 공간도 한정적이고 사용자 입장에서는 nav bar가 나오는게 더 편할 것이라고 판단했다.
  
- index page (complete)

2024-03-28
- 유저가 입력한 주소들의 좌표 우선순위
- 다각형의 중심 좌표
- 중심좌표 Service 단위테스트 코드 작성
  - 단위테스트 코드작성을 귀찮아서 왜 하지 생각을 했었는데 막상 써보니 매번 view단에서 이벤트를 걸지 않고 서버단의 코드를 확인 할 수 있어서 오히려 귀찮음이 줄어드는 것 같다.
