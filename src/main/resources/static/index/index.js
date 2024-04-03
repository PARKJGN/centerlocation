$(()=>{
    
    // 방 만들기 버튼 이벤트
    $(".make-room").click(()=>{
        $.ajax({
            type : "get",
            url : "/makeRoom",
            success: (data)=>{
                $(".room-code").text(`방 코드: ` + data)

                $("#room-code-modal").modal("show");

            },
            error: (err)=>{
                alert("예기치 못한 오류가 발생했습니다. 다시 시도해 주세요.")
            }
        })
    })

    // 방 참여하기 버튼 이벤트
    $(".join-room").click(()=>{
        const code = $(".code").val().trim();

        $(".submit-room").attr("action",`/together/search-center/${code}`)
            .submit();
    })
})