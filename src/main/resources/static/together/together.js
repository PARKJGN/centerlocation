$(()=>{

    // modal과 offcanvas의 백드롭이 겹치면서 발생하는 z-index문제 해결 이벤트
    $(".offcanvas").on("show.bs.offcanvas", (e) => {

        $(".offcanvas").css('z-index', 1060)
        $('.modal-backdrop').css('z-index', 1000)
        setTimeout(()=> {
            $(".offcanvas-backdrop").css('z-index', 1059)
        }, 0);
    })

    // 장소 추가 모달창에서 추가하기 이벤트
    $(".addLocation").click(()=>{
        regexAndSubmit()
    })

    // 주소 하나 제거
    $(document).on('click', '.remove-address', (e) => {
        removeAddress(e);
    })
    
    // 5초마다 변경된 정보를 가지고 화면에 변경

})

// 주소목록 중 하나 적용하는 이벤트
const setAddressInput =(e)=>{

    const roadName = $(e.currentTarget).find('.road-name').text().trim();
    const latitude = $(e.currentTarget).find('.latitude').val().trim();
    const longitude = $(e.currentTarget).find('.longitude').val().trim();

    addressinput.text(roadName)
    addressinput.next().val(roadName)

    //이미 주소를 추가했으면 값만 교체
    if(addressinput.parent().find('.latitude').length !== 0){
        $(".latitude").val(latitude)
        $(".longitude").val(longitude)
    } else{
        addressinput.parent().append(`<input class = "latitude" type="hidden" name="latitude" value=${latitude}>
                                <input class = "longitude" type="hidden" name="longitude" value=${longitude}>
                                `)
    }
}

// 정규식 검사 및 submit 호출
const regexAndSubmit = ()=>{

    if($('.latitude').length===0 || $('.latitude').val().trim()==="" || $('.longitude').length===0 || $('.longitude').val().trim()==="" || $('.user-name').val().trim()==="" || $('.road-name').val().trim()==="") {
        alert("빈칸을 채워주세요.")
        return false;
    }

    $(".submit-location").append(`<input type="hidden" name="roomId" value=${$(".uuid").val()}>`)
        .submit();
}

// 유저들의 주소 목록에서 주소 삭제 이벤트
const removeAddress = (e)=>{

    // 에이작스로 restapi 호출 -> success면 삭제 error면 커스텀 에러메세지 alter?
    const address = $(e.target).parent().parent()

    address.remove();
}

const selectLocation = ()=>{

}

