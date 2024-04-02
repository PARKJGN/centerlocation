$(()=>{
    $(".share").click(()=>{

        const id = $(".shareMap").val()

        $.ajax({
            type : "get",
            url : `/getRoomUrl/${id}`,
            success: (data)=>{
                navigator.clipboard.writeText(data)
                    .then(()=>{
                        alert("주소가 복사되었습니다.")
                    })
            },
            error: (err)=>{
                alert("오류가 발생했습니다. 다시 실행해 주세요.")
            }

        })
    })
})