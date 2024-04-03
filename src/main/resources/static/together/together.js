$(()=>{

    // modal과 offcanvas의 백드롭이 겹치면서 발생하는 z-index문제 해결 이벤트
    $(".offcanvas").on("show.bs.offcanvas", (e) => {

        $(".offcanvas").css('z-index', 1060)
        $('.modal-backdrop').css('z-index', 1000)
        setTimeout(()=> {
            $(".offcanvas-backdrop").css('z-index', 1059)
        }, 0);
    })

    $(".addLocation").click(()=>{
        regexAndSubmit()
    })

})
const setAddressInput =(e)=>{

    const roadName = $(e.currentTarget).find('.roadname').text().trim();
    const latitude = $(e.currentTarget).find('.latitude').val().trim();
    const longitude = $(e.currentTarget).find('.longitude').val().trim();

    addressinput.text(roadName)

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

    if($('.latitude').length===0 || $('.latitude').val().trim()==="" || $('.longitude').length===0 || $('.longitude').val().trim()==="" || $('.name').val().trim()===""){
        alert("빈칸을 채워주세요.")
        return false;
    }

    $(".submit-location").append(`<input type="hidden" name="roomId" value=${$(".uuid").val()}>`)
        .submit();
}