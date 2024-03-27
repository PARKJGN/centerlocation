$(()=> {
    // 주소적용시킬 input
    let input;

    // input박스 클릭시 input박스 위치 기억
    $(document).on('click', '.inputaddress', (e) => {
        input = $(e.target)
    })

    // 주소목록 사이드바 show할때 주소 입력창 focus
    $(".offcanvas").on("shown.bs.offcanvas", (e) => {
        $('.searchaddress').focus()
    })

    // 인원 제거
    $(document).on('click', '.removeperson', (e) => {
        removePerson(e);
        resetSequence();
    })

    // 인원 추가
    $(".add-person").on('click', () => {
        addPerson();
        resetSequence();

    })

    // 주소 검색
    $(".search").click(() => {
        const text = $(".searchaddress").val();

        if (text === "") return;

        searchAddress(text);
    })

    // 주소입력 사이드바 사라질때 안에 내용 초기화
    $(".offcanvas").on("hidden.bs.offcanvas", (e) => {
        $(".searchaddress").val("")
        $(".addresslist").empty()
    })

    // 주소입력 Enter key 적용
    $(".searchaddress").on("keyup", (key) => {
        key.preventDefault()
        if (key.keyCode === 13) {
            $(".search").click()
        }
    })

    // 주소목록 중 하나 클릭했을때
    $(document).on('click', '.address', (e) => {

        const roadName = $(e.currentTarget).find('.roadname').text().trim();
        const latitude = $(e.currentTarget).find('.latitude').val().trim();
        const longitude = $(e.currentTarget).find('.longitude').val().trim();


        input.val(roadName)
        input.parent().append(`<input type="hidden" name="latitude" value=${latitude}>
                                <input type="hidden" name="longitude" value=${longitude}>`)

        // 사이드바 hide
        $(".hidecanvas").click()
    })

    $(document).on('keydown keypress paste focus mousedown', '.readonly',(e)=>{
        $(e.target).readOnly=true
        if(e.keyCode !== 9) // ignore tab
            e.preventDefault();
    })

})

// 인원 삭제 이벤트
const removePerson = (e) =>{
    const person = $(e.target).parent().parent()

    if(person.siblings().length<=1){
        alert("최소 2명의 인원이 있어야 합니다.")
        return;
    }

    person.remove();
}

// 인원 추가 이벤트
const addPerson = () =>{

    const lastSequence = Number($('.sequence').last().text()) + 1

    if(lastSequence>=11){
        alert("최대 10명까지 가능합니다.")
        return;
    }

    $(".people").append('<div class="row mb-3 text-center justify-content-evenly person">\n' +
        `            <div class="col-sm-2 themed-grid-col align-content-center sequence">${lastSequence}</div>\n` +
        '            <div class="col-sm-4 themed-grid-col">\n' +
        '                <label>\n' +
        '                    <input class="form-control" type="text" placeholder="이름을 입력해 주세요." required>\n' +
        '                </label>\n' +
        '            </div>\n' +
        '            <div class="col-sm-4 themed-grid-col">\n' +
        '                <label>\n' +
        '                     <input readonly href="#offcanvasExample" data-bs-toggle="offcanvas" class="form-control inputaddress" type="text" placeholder="주소를 입력해 주세요" required>\n' +
        '                </label>\n' +
        '            </div>\n' +
        '            <div class="col-2 themed-grid-col">\n' +
        '                <button class="btn-close btn removeperson" type="button"></button>\n' +
        '            </div>\n' +
        '        </div>')
}

// 순서 초기화 이벤트
const resetSequence = () =>{
    $(".person").each((idx, p)=>{
        $(p).find(".sequence").text(idx+1)
    })
}

// 키워드를 이용한 주소검색 api 호출
const searchAddress = (text) => {

    $.ajax({
        type: 'GET',
        url: `/searchAddress/${text}`,
        dataType: 'JSON',
        success: (res)=> {

            $(".addresslist").empty();

            if(res.documents.length===0) {
                alert("검색결과가 없습니다.")
                return
            }

            res.documents.forEach((doc, idx)=>{
                // 도로명 안나와있으면 패스
                if(doc.road_address_name==="") return

                console.log(doc)

                $(".addresslist").append(
                    `<a class="address list-group-item list-group-item-action py-3 lh-tight">
                        <div class="d-flex w-100 align-items-center justify-content-between placename">
                            <strong class="mb-1">${doc.place_name}</strong>
                        </div>
                        <div class="col-10 mb-1 small roadname">${doc.road_address_name}</div>
                        <input class="longitude" type="hidden" value=${doc.x}>
                        <input class="latitude" type="hidden" value=${doc.y}>
                    </a>
                    `
                )
            })
        },
        error: (err) => {
            console.log(err)
        }
    })
}