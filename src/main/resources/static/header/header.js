$(()=>{

    // 주소 공유하기 버튼 눌렀을 경우
    $(".share").click(()=>{

        const id = $(".shareMap").val()

        $.ajax({
            type : "get",
            url : `/roomUrl/${id}`,
            success: (res)=>{
                if(res.statusCode==="OK"){
                    const value = $(`<input type='text' value=${res.resultData}>`);

                    value.appendTo('body')
                    value.select();

                    const copy = document.execCommand('copy')
                    
                    if(copy){
                        alert("주소가 복사되었습니다.")
                    } else {
                        alert("다시 시도해주세요")
                    }
                    value.remove();
                }
            },
            error: (err)=>{
                alert("오류가 발생했습니다. 다시 시도해 주세요.")
            }

        })
    })
})