$(()=>{
    // 지도의 마커
    const markers = []

    // 마커의 오버레이
    const markerOverlays = []

    const container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
    const options = { //지도를 생성할 때 필요한 기본 옵션
        center: new kakao.maps.LatLng(37.498008, 127.028027), //지도의 중심좌표.
        level: 3 //지도의 레벨(확대, 축소 정도)
    };

    const map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

    // 페이지가 이동되자마자 데이터 가져오기
    selectLocations($('.uuid').val(), map, markers, markerOverlays)

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
        regexAndSave(map, markers, markerOverlays)
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
        removeLocation(e, map, markers, markerOverlays);
    })
    
    // 5초마다 변경된 정보를 가지고 화면에 변경
    setInterval(()=>{
        selectLocations($('.uuid').val(), map, markers, markerOverlays)
    }, 5000)

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
const regexAndSave = (map, markers, markerOverlays)=>{

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
                selectLocations(roomId, map, markers, markerOverlays)

            }
        },
        error: (err)=>{
            alert(err.responseJSON.message)
        }
    })

    $('#addLocationModal').modal("hide")

}

// 유저들의 주소 목록에서 주소 삭제 이벤트
const removeLocation = (e, map, markers, markerOverlays)=>{

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
                selectLocations(roomId, map, markers, markerOverlays);
                alert("삭제가 완료되었습니다.")
            }

        },
        error: (err)=>{
            alert(err.responseJSON.message)
        }
    })
}

// 해당 방의 유저들의 장소 select
const selectLocations = (id, map, markers, markerOverlays)=>{

    $(".user-location-list").empty()

    $.ajax({
        type: "post",
        url: "/together/selectLocations",
        data: {"id": id},
        success: (res)=>{
            if(res.statusCode==="OK"){

                const locations = JSON.parse(res.resultData)
                let center = null

                // 가져온 장소가 없으면 카카오지점 map, 1개면 해당 지점, 2개 이상부터 가운데 지점
                if(locations.length>=2){
                    center = JSON.parse(res.resultMsg)
                }

                // 장소 목록보기 채워넣기
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
                changeMap(locations, center, map, markers, markerOverlays)
            }
        },
        error: (err)=>{
            alert(err.responseJSON.message)
        }
    })
}

const changeMap = (locations, center, map, markers, markerOverlays)=>{

    // 보여줄 맵의 경졔
    const bounds = new kakao.maps.LatLngBounds();

    // 기존에 마커가 찍혀있었으면 마커와 마커오버레이 제거
    if(markers.length!==0){
        markers.forEach((marker, idx)=>{
            marker.setMap(null)
            markerOverlays[idx].setMap(null)
        })
    }

    // 중심값이 null -> 장소가 0개 아니면 1개
    if(center===null){
        // 장소가 1개이면
        if(locations.length===1){

            // 보여줄 맵의 가운데 지점
            map.setCenter(new kakao.maps.LatLng(locations[0].latitude, locations[0].longitude))

            const marker = new kakao.maps.Marker({
                position: new kakao.maps.LatLng(locations[0].latitude, locations[0].longitude),
                image: new kakao.maps.MarkerImage(`/static/icon/number/1.png`,new kakao.maps.Size(64,64))
            })

            const markerOverlay = new kakao.maps.CustomOverlay({
                position: new kakao.maps.LatLng(locations[0].latitude, locations[0].longitude),
                content: `<div class ="label"><span class="left"></span><span class="center">${locations[0].userName}</span><span class="right"></span></div>`
            })
            markers.push(marker)
            markerOverlays.push(markerOverlay)
        }
    }
    // 중심값이 있다 -> 장소가 2개 이상
    else {
        map.setCenter(new kakao.maps.LatLng(center.Latitude, center.Longitude))

        locations.forEach((loc, idx)=>{
            const marker = new kakao.maps.Marker({
                position: new kakao.maps.LatLng(loc.latitude, loc.longitude),
                image: new kakao.maps.MarkerImage(`/static/icon/number/${idx+1}.png`,new kakao.maps.Size(64,64))
            })
            bounds.extend(new kakao.maps.LatLng(loc.latitude, loc.longitude))
            markers.push(marker)

            const markerOverlay = new kakao.maps.CustomOverlay({
                position: new kakao.maps.LatLng(loc.latitude, loc.longitude),
                content: `<div class ="label"><span class="left"></span><span class="center">${loc.userName}</span><span class="right"></span></div>`
            })
            markerOverlays.push(markerOverlay)
            map.setBounds(bounds)
        })
    }
    
    // 맵에 마커와 마커오버레이 찍기
    markers.forEach((marker, idx)=>{
        marker.setMap(map)
        markerOverlays[idx].setMap(map)
    })
}


