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

2024-04-01
- kakao mapMarker
- JPA
- 전체적인 프로젝트 구상도 변경
   - 1명이서 장소들을 다 입력해야 했다.
   - 9개의 장소를 입력하려면 시간이 오래걸린다.
   - 그래서 방개념을 도입하여 url을 하나 생성
   - url에 들어가면 여러명이서 url의 내용을 공유

2024-04-02
- cover_layout 추가
- h2연결 후 비지니스 로직 테스트
  - 혼자입력 (insert, select, uri share)
- ExceptionHandler
    - RoomNotFound (친구 방을 못찾을 때)
    - Validation (Valid 에서 오류났을때)
    - Exception (통합 오류)
    - 추가해야될 것 : 현재 return이 json형태로 타임스탬프, 내용이 찍히는데 오류페이지를 만들어서 관리를 해야될 것 같다.

 2024-04-03
- together page (같이 입력하기)
   - save location
   - select location
- ExceptionHandler
    - RoomNotMathType (방 타입이 다른 id를 입력했을때 )
- offcanvnas와 modal을 같이 쓸때 index가 겹치는 문제가 발생한다. -> bootstrap으로 backdrop이 자동 생성 그래서 show.bs.offcanvas 이벤트가 실행이 될때 index를 수정했다.

2024-04-04
- together page (같이 입력하기)
   - remove location
- setInterval 함수를 통해 5초마다 together page에 비동기로 데이터를 받아서 보여주기를 원했고,
  그러기 위해서는 나의 로직들이 rest api 인지 아닌지 리팩토링이 필요하다고 느꼈다.

- Exception Handler -> json
- 404page, 500page

2024-04-05

- type : refactor

    - ExceptionHandler를 통해 NoHandlerException 발생시 에러페이지로 이동
(WAS까지 가지 않고 서블릿에서 처리)

    - ajax를 통해 error가 나면 ExceptionHandler를 통해 오류 내용을 alert로 사용자에게 보여줌

    - RestController, Controller 구분 - ResponseEntity를 통해 restapi 호출 시 명확한 데이터 상세정보 넘김
