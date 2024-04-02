package com.ourcenterhere.app.centerlocation.room.controller;

import com.ourcenterhere.app.centerlocation.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @ResponseBody
    @GetMapping("/getRoomUrl/{id}")
    public String RoomUrl(@PathVariable UUID id){
        UriComponents uriComponents = UriComponentsBuilder
                    .newInstance()
                    .scheme("http")
                    .host("localhost:8080")
                    .path("search-center/")
                    .path(String.valueOf(id))
                    .build();
        return uriComponents.toString();
    }
}
