$(()=>{
    $(".share").click(()=>{

        const id = $(".shareMap").val()

        $.ajax({
            type : "get",
            url : `/roomUrl/${id}`,
            success: (res)=>{
                if(res.statusCode==="OK")
                navigator.clipboard.writeText(res.resultData)
                    .then(()=>{
                        alert("주소가 복사되었습니다.")
                    })
            },
            error: (err)=>{
                alert("오류가 발생했습니다. 다시 시도해 주세요.")
            }

        })
    })
})