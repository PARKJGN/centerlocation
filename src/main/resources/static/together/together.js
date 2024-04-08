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
        regexAndSave()
    })

    // 장소 추가 모달창 사라질때 안에 내용 초기화
    $("#addLocationModal").on("hidden.bs.modal", (e) => {
        $(".user-name").val("")
        $(".road-name").val("")
        $(".latitude").remove()
        $(".longitude").remove()
        $(".input-location").text("주소를 입력해 주세요.")
    })

    // 주소 하나 제거
    $(document).on('click', '.remove-location', (e) => {
        removeLocation(e);
    })
    
    // 5초마다 변경된 정보를 가지고 화면에 변경
})

// 주소목록 중 하나 적용하는 이벤트
const setAddressInput =(e)=>{

    const roadName = $(e.currentTarget).find('.road-name').text().trim();
    const latitude = $(e.currentTarget).find('.latitude').val().trim();
    const longitude = $(e.currentTarget).find('.longitude').val().trim();

    addressInput.text(roadName)
    addressInput.next().val(roadName)

    //이미 주소를 추가했으면 값만 교체
    if(addressInput.parent().find('.latitude').length !== 0){
        $(".latitude").val(latitude)
        $(".longitude").val(longitude)
    } else{
        addressInput.parent().append(`<input class = "latitude" type="hidden" name="latitude" value=${latitude}>
                                <input class = "longitude" type="hidden" name="longitude" value=${longitude}>
                                `)
    }
}

// 정규식 검사 및 장소 save
const regexAndSave = ()=>{

    const latitude = $('.latitude').val()
    const longitude = $('.longitude').val()
    const userName = $('.user-name').val()
    const roadName = $('.road-name').val()
    const roomId = $('.uuid').val()

    if($('.latitude').length===0 ||latitude.trim()==="" || $('.longitude').length===0 || longitude.trim()==="" || userName.trim()==="" || roadName.trim()==="") {
        alert("빈칸을 채워주세요.")
        return false;
    }

    const data = {"latitude": latitude,
                        "longitude": longitude,
                        "userName": userName,
                        "roadName": roadName,
                        "roomId": roomId}

    $.ajax({
        type: "post",
        url: "/together/saveLocation",
        data : data,
        dataType: "json",
        success: (res)=>{
            if(res.statusCode==="OK"){
                selectLocations(roomId)

            }
        },
        error: (err)=>{
            alert(err.responseJSON.message)
        }
    })

    $('#addLocationModal').modal("hide")

}

// 유저들의 주소 목록에서 주소 삭제 이벤트
const removeLocation = (e)=>{

    // 삭제버튼을 누른 주소칸
    const location = $(e.target).parent().parent()
    // 삭제하려는 주소의 id
    const id = location.find(".id").val()

    const roomId = $('.uuid').val()

    $.ajax({
        type: "post",
        url: "/together/removeLocation",
        data: {"id": id},
        success: (res)=>{
            if(res.statusCode==="OK"){
                alert("삭제가 완료되었습니다.")
                location.remove();
                selectLocations(roomId);
            }

        },
        error: (err)=>{
            alert(err.responseJSON.message)
        }
    })
}

// 해당 방의 유저들의 장소 select
const selectLocations = (id)=>{

    $(".user-location-list").empty()

    $.ajax({
        type: "post",
        url: "/together/selectLocations",
        data: {"id": id},
        success: (res)=>{
            if(res.statusCode==="OK"){
                const locations = JSON.parse(res.resultData)

                locations.forEach((loc, idx)=>{
                    $('.user-location-list').append(`
                    <a class="list-group-item list-group-item-action py-3 lh-tight">
                        <div class="d-flex w-100 align-items-center justify-content-between placename">
                            <strong class="mb-1">${loc.userName}</strong>
                            <button class="btn-close btn remove-location" type="button"></button>
                        </div>
                        <div class="col-10 mb-1 small road-name">${loc.roadName}</div>
                        <input class="id" type="hidden" value=${loc.id}>
                    </a>`)
                })
            }
        },
        error: (err)=>{
            alert(err.responseJSON.message)
        }
    })
}

const changeMap = ()=>{

}


