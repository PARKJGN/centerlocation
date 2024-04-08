package com.ourcenterhere.app.centerlocation.room.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.ourcenterhere.app.centerlocation.ResponseDto;
import com.ourcenterhere.app.centerlocation.location.dto.LocationDto;
import com.ourcenterhere.app.centerlocation.location.dto.LocationDtoList;
import com.ourcenterhere.app.centerlocation.location.service.LocationService;
import com.ourcenterhere.app.centerlocation.room.entity.RoomType;
import com.ourcenterhere.app.centerlocation.room.service.RoomService;
import jakarta.validation.Valid;
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

    // 혼자 구하기 url 공유할때 url 만드는 api
    @GetMapping("/roomUrl/{id}")
    public ResponseEntity<ResponseDto> RoomUrl(@PathVariable UUID id){

        roomService.checkRoom(id);

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
    public ResponseEntity<ResponseDto> makeRoom(){
        String id = roomService.enrollTogetherRoom();
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, HttpStatus.OK.toString(), id));
    }

    // 같이 구하기 방 참여하기 방 존재 여부
    @GetMapping("/checkRoom/{id}")
    public ResponseEntity<ResponseDto> checkRoom(@PathVariable String id){
        roomService.checkRoom(UUID.fromString(id));
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, HttpStatus.OK.toString(), id));
    }
}
