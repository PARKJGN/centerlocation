package com.ourcenterhere.app.centerlocation.room.service;

import com.ourcenterhere.app.centerlocation.exception.RoomNotFoundException;
import com.ourcenterhere.app.centerlocation.location.dto.SearchLocationDto;
import com.ourcenterhere.app.centerlocation.location.entity.LocationEntity;
import com.ourcenterhere.app.centerlocation.room.entity.RoomEntity;
import com.ourcenterhere.app.centerlocation.room.entity.RoomType;
import com.ourcenterhere.app.centerlocation.room.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    @Transactional
    public UUID enrollRoom(List<SearchLocationDto> list){

        System.out.println(list.toString());

        RoomEntity room = RoomEntity.builder()
                .type(RoomType.ALONE)
                .build();

        List<LocationEntity> locationEntityList = list.stream()
                .map(a->{
                    LocationEntity loc = a.toEntity();
                    loc.setRoomEntity(room);
                    return loc;
                })
                .toList();

        roomRepository.save(room);
        return room.getUuid();
    }

    public List<SearchLocationDto> findLocListByRoomId(String id){
        RoomEntity roomEntity = roomRepository.findById(UUID.fromString(id)).orElse(null);

        if(roomEntity == null){
            throw new RoomNotFoundException("id : " + id);
        }

        List<SearchLocationDto> locationEntityList = roomEntity.getLoc().stream()
                .map(LocationEntity::toSearchLocationDto)
                .toList();

        return locationEntityList;
    }

    public UUID checkRoom(UUID id){
        RoomEntity roomEntity = roomRepository.findById(id).orElse(null);

        if(roomEntity == null){
            throw new RoomNotFoundException("id : " + id);
        }

        return roomEntity.getUuid();
    }

}
