$(()=>{
    $(document).on('click','.btn-close',(e)=>{
        removePerson(e);
        resetSequence();
    })

    $(".add-person").on('click',()=>{
        addPerson();
        resetSequence();

    })

    $(".searchAddress").on("input keypress click",(e)=>{
        if(e.keyCode===13){
            e.preventDefault()
        }

        let text = $(".searchAddress").val()

        if(text.trim() !== ""){
            searchAddress(text)
        }
    })



})

// 인원 삭제 이벤트
const removePerson = (e) =>{
    const person = $(e.target).parent().parent()
    if(person.siblings().length<=2){
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
        '                    <input class="form-control" type="text" placeholder="이름을 입력해 주세요.">\n' +
        '                </label>\n' +
        '            </div>\n' +
        '            <div class="col-sm-4 themed-grid-col">\n' +
        '                <label>\n' +
        '                    <input class="form-control" type="text" placeholder="주소를 입력해 주세요">\n' +
        '                </label>\n' +
        '            </div>\n' +
        '            <div class="col-2 themed-grid-col">\n' +
        '                <button class="btn-close btn" type="button"></button>\n' +
        '            </div>\n' +
        '        </div>')
}

// 순서 초기화 이벤트
const resetSequence = () =>{
    $(".person").each((idx, p)=>{
        $(p).find(".sequence").text(idx+1)
    })
}

// 주소 자동완성
const searchAddress = (text) => {

    $.ajax({
        type: 'GET',
        url: '/searchAddress',
        dataType: 'JSON',
        success: (res)=> {
            $(`.resultAddress`).empty();
            console.log(res)
            for (let i = 0; i < res.items.length; i++) {
                let value = res.items[i].substring(3, res.items[i].title.length - 4)
                console.log(value)
                $(".resultAddress").append(
                    `<a class="list-group-item list-group-item-action">${value}</a>`)
            }
        },
        error: (err) => {
            console.log(err)
        }
    })
}