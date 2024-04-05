package com.ourcenterhere.app.centerlocation.room.controller;

import com.ourcenterhere.app.centerlocation.ResponseDto;
import com.ourcenterhere.app.centerlocation.location.dto.LocationDto;
import com.ourcenterhere.app.centerlocation.location.dto.LocationDtoList;
import com.ourcenterhere.app.centerlocation.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RoomRestController {

    private final RoomService roomService;

    // 혼자 구하기 방 생성 and 데이터 insert
    @PostMapping("/alone/save")
    public ResponseEntity<ResponseDto> aloneSaveLocation(@ModelAttribute LocationDtoList list, Model model){
        List<LocationDto> locationDtoList = list.getLocationDtoList();

        UUID uuid = roomService.enrollAloneRoom(locationDtoList);

        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, HttpStatus.OK.toString(), uuid.toString()));
    }

    // 혼자 구하기 url 공유할때 url 만드는 api
    @GetMapping("/roomUrl/{id}")
    public ResponseEntity<ResponseDto> RoomUrl(@PathVariable UUID id){
        UriComponents uriComponents = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost:8080")
                .path("alone/share/")
                .path(String.valueOf(id))
                .build();

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, HttpStatus.OK.toString(), uriComponents.toString()));
    }

    // 같이 구하기 방 만들기
    @GetMapping("/makeRoom")
    public String makeRoom(){
        String id = roomService.enrollTogetherRoom();
        return id;
    }

}
