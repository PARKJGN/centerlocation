$(()=>{
    // 주소입력칸 클릭시 주소입력칸 위치 기억
    $(document).on('click', '.input-location', (e) => {
        addressInput = $(e.target)
    })

    // 주소목록 사이드바 show할때 주소 입력창 focus
    $(".offcanvas").on("shown.bs.offcanvas", (e) => {
        $('.search-location').focus()
    })

    // 주소입력 사이드바 사라질때 안에 내용 초기화
    $(".offcanvas").on("hidden.bs.offcanvas", (e) => {
        $(".search-location").val("")
        $(".location-list").empty()
    })

    // 주소입력 Enter key 적용
    $(".search-location").on("keyup", (key) => {
        key.preventDefault()
        if (key.keyCode === 13) {
            $(".search").click()
        }
    })

    // 주소 검색
    $(".search").click(() => {
        const text = $(".search-location").val();

        if (text === "") return;

        searchAddress(text);
    })

    // 주소목록 중 하나 클릭했을때
    $(document).on('click', '.address', (e) => {

        setAddressInput(e)

        // 사이드바 hide
        $(".hidecanvas").click()
    })
})

// 키워드를 이용한 주소검색 api 호출
const searchAddress = (text) => {

    $.ajax({
        type: 'GET',
        url: `/search-location/${text}`,
        dataType: 'JSON',
        success: (res)=> {

            $(".location-list").empty();

            if(res.documents.length===0) {
                alert("검색결과가 없습니다.")
                return
            }

            res.documents.forEach((doc, idx)=>{
                // 도로명 안나와있으면 보여주지 않기
                if(doc.road_address_name==="") return

                $(".location-list").append(
                    `<a class="address list-group-item list-group-item-action py-3 lh-tight">
                        <div class="d-flex w-100 align-items-center justify-content-between placename">
                            <strong class="mb-1">${doc.place_name}</strong>
                        </div>
                        <div class="col-10 mb-1 small road-name">${doc.road_address_name}</div>
                        <input class="longitude" type="hidden" value=${doc.x}>
                        <input class="latitude" type="hidden" value=${doc.y}>
                    </a>
                    `
                )
            })
        },
        error: (err) => {
            console.log(err)
            alert("오류가 발생했습니다. 다시 시도해주세요.")
        }
    })
}