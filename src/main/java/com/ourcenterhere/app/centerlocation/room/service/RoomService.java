package com.ourcenterhere.app.centerlocation.room.service;

import com.ourcenterhere.app.centerlocation.exception.ErrorCode;
import com.ourcenterhere.app.centerlocation.exception.RoomNotFoundException;
import com.ourcenterhere.app.centerlocation.location.dto.LocationDto;
import com.ourcenterhere.app.centerlocation.location.entity.LocationEntity;
import com.ourcenterhere.app.centerlocation.room.entity.RoomEntity;
import com.ourcenterhere.app.centerlocation.room.entity.RoomType;
import com.ourcenterhere.app.centerlocation.room.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.NoHandlerFoundException;

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
                loc.getUserName()==null || loc.getUserName().isEmpty() || loc.getRoadName()==null){
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

    public void checkRoomById(UUID id){
        RoomEntity roomEntity = roomRepository.findById(id).orElse(null);

        if(roomEntity == null){
            throw new RoomNotFoundException(ErrorCode.NOT_FOUND_ROOM);
        }
    }

    public void checkShareRoom(String id) throws NoHandlerFoundException {
        
        // 유저가 url의 id값에 이상한 uuid가 아닌 값을 넣었을 경우 404페이지로
        UUID uuid = null;

        try{
            uuid = UUID.fromString(id);
        }catch (IllegalArgumentException ex){
            throw new NoHandlerFoundException("GET", id, new HttpHeaders());
        }
        
        RoomEntity roomEntity = roomRepository.findById(uuid).orElse(null);

        if(roomEntity == null){
            throw new NoHandlerFoundException("GET", id, new HttpHeaders());
        }

    }

    @Transactional
    public String enrollTogetherRoom(){

        RoomEntity roomEntity = RoomEntity.builder()
                .type(RoomType.TOGETHER)
                .build();

        UUID id = roomRepository.save(roomEntity).getUuid();

        return id.toString();
    }
}
