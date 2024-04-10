package com.ourcenterhere.app.centerlocation.location.service;

import com.ourcenterhere.app.centerlocation.exception.ErrorCode;
import com.ourcenterhere.app.centerlocation.exception.LocationNotFoundException;
import com.ourcenterhere.app.centerlocation.exception.RoomNotFoundException;
import com.ourcenterhere.app.centerlocation.exception.RoomNotMatchTypeException;
import com.ourcenterhere.app.centerlocation.location.dto.LocationDto;
import com.ourcenterhere.app.centerlocation.location.entity.LocationEntity;
import com.ourcenterhere.app.centerlocation.location.repository.LocationRepository;
import com.ourcenterhere.app.centerlocation.room.entity.RoomEntity;
import com.ourcenterhere.app.centerlocation.room.entity.RoomType;
import com.ourcenterhere.app.centerlocation.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final RoomRepository roomRepository;

    /*
        유저가 입력한 주소들의 좌표를 가지고 중심점을 구하는 함수
     */
    public  Map<String, Double> centerLocation(List<LocationDto> locationDtoList){

        double centerLongitude = 0;
        double centerLatitude = 0;
        double area = 0;

        if(locationDtoList.size()==2){
            centerLongitude = (locationDtoList.get(0).getLongitude() + locationDtoList.get(1).getLongitude()) / 2.0;
            centerLatitude = (locationDtoList.get(0).getLatitude() + locationDtoList.get(1).getLatitude()) / 2.0;

        }else{
            List<LocationDto> locList = arrayLocationList(locationDtoList);

            for(int i = 0; i< locationDtoList.size(); i++){

                int j = i+1;

                // 마지막 항은 다각형의 첫번째 점과 이어야 하므로 따로 처리
                if(j >= locationDtoList.size()) j=0;

                double temp = (locList.get(i).getLatitude()*locList.get(j).getLongitude() - locList.get(j).getLatitude()*locList.get(i).getLongitude());

                area += temp;

                centerLongitude += (locList.get(i).getLongitude()+locList.get(j).getLongitude()) * temp;
                centerLatitude += (locList.get(i).getLatitude()+locList.get(j).getLatitude()) * temp;
            }

            centerLongitude = centerLongitude/(area*3);
            centerLatitude = centerLatitude/(area*3);
        }


        Map<String, Double> center = new HashMap<>();

        center.put("Longitude", centerLongitude);
        center.put("Latitude", centerLatitude);

        return center;

    }

    /*
        유저가 입력한 주소들의 좌표를 가지고 중심점을 구할 수 있게 정렬하는 함수
     */
    public List<LocationDto> arrayLocationList(List<LocationDto> locationDtoList){

        double minLongitude = locationDtoList
                                .stream()
                                .mapToDouble(LocationDto::getLongitude)
                                .min()
                                .orElseThrow(NoSuchElementException::new);

        double maxLongitude = locationDtoList
                                .stream()
                                .mapToDouble(LocationDto::getLongitude)
                                .max()
                                .orElseThrow(NoSuchElementException::new);
        // 유저가 입력한 주소중에서 제일 큰 Longitude 와 제일 작은 Longitude 의 가운데 Longitude
        double centerLongitude = (maxLongitude+minLongitude)/2.0;

        // centerLongitude를 기준으로 왼쪽 좌표들의 Latitude가 작은 순서의 집합
        List<LocationDto> leftArrayList = locationDtoList.stream()
                                                .filter(x->x.getLongitude()<=centerLongitude)
                                                .sorted(Comparator.comparingDouble(LocationDto::getLatitude))
                                                .toList();

        // centerLongitude를 기준으로 오른쪽 좌표들의 Latitude가 큰 순서의 집합
        List<LocationDto> rightArrayList = locationDtoList.stream()
                                                .filter(x->x.getLongitude()>centerLongitude)
                                                .sorted(Comparator.comparingDouble(LocationDto::getLatitude).reversed())
                                                .toList();

        List<LocationDto> arrayLocationDtoList = Stream.of(leftArrayList, rightArrayList).flatMap(Collection::stream).toList();

        return arrayLocationDtoList;
    }

    public void removeLocation(Long id){
        // deleteById - JPA 3.0.2 버전부터 EmptyResultDataAccessException 를 던지지 않는다.
        /*locationRepository.deleteById(id);*/
        LocationEntity locationEntity = locationRepository.findById(id).orElse(null);

        if(locationEntity==null){
            throw new LocationNotFoundException(ErrorCode.NOT_FOUND_LOCATION);
        }

        locationRepository.delete(locationEntity);
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

        List<LocationDto> locationList = roomEntity.getLoc().stream()
                .map(a->{
                    if(roomEntity.getType()==RoomType.ALONE)
                        return a.toAloneLocationDto();
                    else{
                        return a.toTogetherLocationDto();
                    }
                })
                .toList();

        return locationList;
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
