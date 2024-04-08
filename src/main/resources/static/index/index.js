$(()=>{
    
    // 방 만들기 버튼 이벤트
    $(".make-room").click(()=>{
        $.ajax({
            type : "get",
            url : "/makeRoom",
            success: (res)=>{
                if(res.statusCode==="OK"){
                    $(".room-code").text(`방 코드: ` + res.resultData)

                    $("#room-code-modal").modal("show");
                }
            },
            error: (err)=>{
                alert(err.responseJSON.message)
            }
        })
    })

    // 방 참여하기 버튼 이벤트
    $(".join-room").click(()=>{
        const code = $(".code").val().trim();

        $.ajax({
            type : "get",
            url : `/checkRoom/${code}`,
            success: (res)=>{
                if(res.statusCode==="OK"){
                    $(".submit-room").submit();
                }
            },
            error: (err) =>{
                alert(err.responseJSON.message)
            }
        })


    })

    // 방 참가하기 모달창 꺼지면 input값 초기화
    $("#joinRoom").on("hidden.bs.modal", (e) => {
        $(".code").val("")
    })
})