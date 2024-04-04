package com.ourcenterhere.app.centerlocation.room.service;

import com.ourcenterhere.app.centerlocation.exception.ErrorCode;
import com.ourcenterhere.app.centerlocation.exception.RoomNotFoundException;
import com.ourcenterhere.app.centerlocation.exception.RoomNotMatchTypeException;
import com.ourcenterhere.app.centerlocation.location.dto.LocationDto;
import com.ourcenterhere.app.centerlocation.location.entity.LocationEntity;
import com.ourcenterhere.app.centerlocation.room.entity.RoomEntity;
import com.ourcenterhere.app.centerlocation.room.entity.RoomType;
import com.ourcenterhere.app.centerlocation.room.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public UUID enrollAloneRoom(List<LocationDto> list){

        // 유저가 입력한 값들이 클라이언트단에서 조작되어 정규식을 거치지 않고 넘어왔을때
        // 추후에 오류 커스텀
        for(LocationDto loc : list){
            if(Double.isNaN(loc.getLatitude()) || loc.getLatitude()==0.0 ||
                Double.isNaN(loc.getLongitude()) || loc.getLongitude()==0.0 ||
                loc.getUserName()==null || loc.getUserName().isEmpty() || loc.getRoadName().isEmpty()){
                throw new IllegalStateException();
            }
        }

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

    public List<LocationDto> findLocListByRoomId(String id, RoomType type){
        RoomEntity roomEntity = roomRepository.findById(UUID.fromString(id)).orElse(null);

        // db에 일치하는 방의 데이터가 없을때
        if(roomEntity == null){
            throw new RoomNotFoundException(ErrorCode.NOT_FOUND_ROOM);
        }
        // 유저가 입력한 방 코드의 타입(alone, together)과 view단에서 입력한 페이지의 기능과 다를때
        if(type!=roomEntity.getType()){
            throw new RoomNotMatchTypeException(ErrorCode.NOT_MATCH_ROOM_TYPE);
        }

        List<LocationDto> locationEntityList = roomEntity.getLoc().stream()
                .map(a->{
                    if(roomEntity.getType()==RoomType.ALONE)
                        return a.toAloneLocationDto();
                    else{
                        return a.toTogetherLocationDto();
                    }
                })
                .toList();

        return locationEntityList;
    }

    public UUID checkRoom(UUID id){
        RoomEntity roomEntity = roomRepository.findById(id).orElse(null);

        if(roomEntity == null){
            throw new RoomNotFoundException(ErrorCode.NOT_FOUND_ROOM);
        }

        return roomEntity.getUuid();
    }

    @Transactional
    public String enrollTogetherRoom(){

        RoomEntity roomEntity = RoomEntity.builder()
                .type(RoomType.TOGETHER)
                .build();

        UUID id = roomRepository.save(roomEntity).getUuid();

        return id.toString();
    }

    @Transactional
    public void saveLocation(LocationDto locationDto) {
        RoomEntity roomEntity = roomRepository.findById(UUID.fromString(locationDto.getRoomId())).orElse(null);

        if(roomEntity==null){
            throw new RoomNotFoundException(ErrorCode.NOT_FOUND_ROOM);
        }
        LocationEntity locationEntity = locationDto.toEntity();
        locationEntity.setRoomEntity(roomEntity);
    }

}
