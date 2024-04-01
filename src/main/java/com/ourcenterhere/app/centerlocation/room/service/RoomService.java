package com.ourcenterhere.app.centerlocation.room.service;

import com.ourcenterhere.app.centerlocation.location.dto.SearchLocationDto;
import com.ourcenterhere.app.centerlocation.location.dto.SearchLocationDtoList;
import com.ourcenterhere.app.centerlocation.room.entity.RoomEntity;
import com.ourcenterhere.app.centerlocation.room.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public void enrollRoom(List<SearchLocationDto> list){

        RoomEntity room = new RoomEntity();

        /*roomRepository.save();*/
    }

}
