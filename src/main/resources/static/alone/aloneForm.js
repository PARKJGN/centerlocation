$(()=> {

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

    // 중간 지점 구하기 버튼
    $(".get-center").click(()=>{
        regexAndSubmit()
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
        `                        <div class="col-sm-2 themed-grid-col align-content-center sequence">${lastSequence}</div>\n` +
        '                        <div class="col-sm-4 themed-grid-col">\n' +
        '                            <label>\n' +
        `                                <input class="form-control user-name" type="text" placeholder="이름을 입력해 주세요." name="LocationDtoList[${lastSequence-1}].userName">\n` +
        '                            </label>\n' +
        '                        </div>\n' +
        '                        <div class="col-sm-4 themed-grid-col">\n' +
        '                            <span href="#offcanvasExample" data-bs-toggle="offcanvas" class="form-control inputaddress">주소를 입력해 주세요.</span>\n' +
        `                            <input class = "road-name" type="hidden" name="LocationDtoList[${lastSequence}].roadName">`+
        '                        </div>\n' +
        '                        <div class="col-2 themed-grid-col">\n' +
        '                            <button class="btn-close btn removeperson" type="button"></button>\n' +
        '                        </div>\n' +
        '                    </div>')
}

// 순서 초기화 이벤트
const resetSequence = () =>{
    $(".person").each((idx, p)=>{
        $(p).find(".sequence").text(idx+1)
        $(p).find(".user-name").attr("name", `LocationDtoList[${idx}].userName`)
        $(p).find(".road-name").attr("name", `LocationDtoList[${idx}].roadName`)

        if($(p).find(".longitude").length !== 0){
            $(p).find(".longitude").attr("name", `LocationDtoList[${idx}].longitude`)
            $(p).find(".latitude").attr("name", `LocationDtoList[${idx}].latitude`)
        }
    })
}

// 주소목록 중 하나 적용하는 이벤트
const setAddressInput = (e)=>{

    const roadName = $(e.currentTarget).find('.road-name').text().trim();
    const latitude = $(e.currentTarget).find('.latitude').val().trim();
    const longitude = $(e.currentTarget).find('.longitude').val().trim();

    addressinput.text(roadName)
    addressinput.next().val(roadName)

    const index = addressinput.parent().parent().index();

    //이미 주소를 추가했으면 값만 교체
    if(addressinput.parent().find('.latitude').length!==0){
        $(".latitude").val(latitude)
        $(".longitude").val(longitude)
    }else{
        addressinput.parent().append(`<input class = "latitude" type="hidden" name="LocationDtoList[${index}].latitude" value=${latitude}>
                                <input class = "longitude" type="hidden" name="LocationDtoList[${index}].longitude" value=${longitude}>
                                `)
    }


}

// 정규식 검사 및 submit 호출
const regexAndSubmit = ()=>{

    const data = {};

    let regex = false

    $.each($(`.person`),(idx,el)=>{

        let lati = $(el).find(".latitude");
        let logi = $(el).find(".longitude");
        let name = $(el).find(".user-name");

        if(lati.length===0 || logi.length===0 || lati.val().trim()==="" || logi.val().trim()==="" || name.val().trim()==="" || $(el).find(".inputaddress").text()==="주소를 입력해 주세요." || $(el).find(".road-name").val().trim()===""){
            regex = true
            return false;
        }

        data[lati.attr("name")] = lati.val();
        data[logi.attr("name")] = logi.val();
        data[name.attr("name")] = name.val();
        data[$(el).find(".road-name").attr("name")] = $(el).find(".road-name").val();
    })

    if(regex) {
        alert("빈칸을 채워주세요.")
        return false;
    }
    
    // db에 insert 후 페이지 이동
    $.ajax({
        type: "post",
        url: "/alone/save",
        data: data,
        dataType: "json",
        success: (res)=>{
            if(res.statusCode==="OK"){
                // 동적 form태그 생성
                const form = $('<form hidden="hidden" method="post" action="/alone"></form>')

                // 동적 input태그 생성
                form.append($(`<input type="hidden" name="id" value=${res.resultData}>`))

                form.appendTo('body')
                form.submit()
            } else{
                alert("다시 시도해 주세요.")
            }
        },
        error: (err)=>{
            alert(err.responseJSON.message)

        }
    })

}