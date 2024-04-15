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

    - ajax를 통해 error가 나면 ExceptionHandler를 통해 오류 내용을 alert로 사용자에게 보여준다.

    - RestController, Controller 구분 - ResponseEntity를 통해 restapi 호출 시 명확한 데이터 상세정보 넘긴다.
 
2024-04-08

- address -> location으로 명칭 통일

- 부트스트랩 SourceMap인 .map파일 추가

- RestController, Controller, Room, Location 구분

- 모달창, 오프캔버스 창 사라질때 내용 초기화되는 기능

- HttpRequestMethodNotSupportedException 예외 추가
-> post주소를 get으로 들어갈때 에러페이지 표시

- gson 라이브러리 추가
-> ajax success로 json을 전달하기 위해 (같이 구하기에서 save Or remove할때 db에서 select)

2024-04-10

- setInterval함수를 이용해서 5초마다 페이지가 동적으로 업데이트
    - 처음 구상은 컨트롤러에서 Model에 값을 넘겨 내부 스크립트에서 KakaoMap과 marker를 적용시켰다.
    - 하지만 페이지를 동적으로 업데이트 시키기 위해서는 지도도 동적으로 움직일 필요가 있었고 내부 스크립트로 선언하게 되면 외부 스크립트에서 지도를 지우고 다시 만드는 방법밖에 없었다.
    - 지도를 5초마다 지우고 다시 만들면 그 사이에 깜빡임이 존재
    - 그래서 Model로 값을 넘기기 보다는 페이지가 띄워질때 외부 js에서  ajax로 값을 가지고와 KakaoMap을 사용
    - 화면에 깜빡임 없이 5초마다 업데이트

- JPA의 deleteById를 select 후 delete로 교체
    - 1번방에 A 유저와 B유저가 있을때 A유저와 B유저가 같은 장소를 삭제 했을때 이미 삭제되어 해당 id가 없으면 deleteById가 emptyresultdataaccessexception 를 던진다고 알고 있었다.
    - 하지만 처음에는 select와 delete가 발생하고 한번 더 삭제를 누르면 select가 발생 후 아무런 반응이 없었다.
    - 찾아보니 JPA 3.0.0 버전 이후로 deleteById에서 오류가 발생되지 않게 업데이트 되었다.
    - 그래서 오류는 명확히 알려줘야 한다고 생각
    - findId 후 delete를 실행
    - findId에서 없으면 오류 발생

- delete할때 findId에서 ErrorCode : Not_Found_Location 추가 (같이 구하기 방에서 이미 삭제한 장소를 다시 삭제할때 에러를 띄어주기 위해)

- 유저가 브라우저창에 이상한 주소를 쳤을경우 오류가 발생하면서 ResponseEntity가 JSON형태가 그대로 들어남 ex) 방 참여하기 할때 띄워주는 '없는 방 코드입니다'
    - 내가 원하는건 유저와 상호작용에서는 JSON형태로 alert로 보여주고 url에 이상한 endpoint를 치면 에러페이지를 보여주기를 원했다
    - 해당 상황이 JSON형태의 오류를 던지는 하나의 service 함수를 RestController와 Controller에서 같이 사용하여 벌어진 일이라고 판단
    - Controller에서 해당 service를 호출하기 전 오류가 나면 에러페이지로 보내는 service함수를 만들어서 적용

- index페이지 방 참여하기 모달에서 focus된 상태로 enter를 누르면 submit되는 현상 발견 (button type="button")
    - form태그 안에 input태그가 하나면 enter시 submit이 된다는 것을 발견
    - 처음에 input태그 옆에 ipunt type="hidden" 태그를 넣고 다시 enter
    - 그래도 sumbit이 날라갔다
    - input hidden="hidden" 태그를 넣으니 오류가 사라졌다

2024-04-11

- 같이 구하기시 센터 마커안찍어 주던 오류 해결
- favicon 추가
- aws ec2 생성 후 배포 
    - 배포 후 어떤 page를 가던 template might not exist or might not be accessible by any of the configured Template Resolvers 오류 발생
    - 원인 : html을 리턴해주는 컨트롤러에서 return값을 /부터 시작해서 - IDE에서는 //를 / 하나로 처리를 해주지만 jar 배포시에는 처리를 해주지 못한다.
- rds mysql 연결
- clipboard api -> execCommand로 변경
    - clipboard는 https와 localhost에서만 적용되므로 도메인을 구매하지 않으면 적용할 수 없어서

 2024-04-12

- 같이 구하기시 center 마커가 여러개 찍히던 현상 해결

- 
