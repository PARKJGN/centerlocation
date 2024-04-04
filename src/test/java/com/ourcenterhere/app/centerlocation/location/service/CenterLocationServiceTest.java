package com.ourcenterhere.app.centerlocation.location.service;

import com.ourcenterhere.app.centerlocation.location.dto.LocationDto;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class CenterLocationServiceTest {
    List<LocationDto> locationDtoList = new ArrayList<>();

    //
    public void centerLocation(List<LocationDto> locationDtoList){

        List<LocationDto> arrayList = arrayLocationList(locationDtoList);

        double centerLongitude = 0;
        double centerLatitude = 0;
        double area = 0;

        for(int i = 0; i< locationDtoList.size(); i++){

            int j = i+1;

            // 마지막 항은 다각형의 첫번째 점과 이어야 하므로 따로 처리
            if(j >= locationDtoList.size()) j=0;

            double temp = (arrayList.get(i).getLatitude()*arrayList.get(j).getLongitude() - arrayList.get(j).getLatitude()*arrayList.get(i).getLongitude());

            area += temp;

            centerLatitude += (arrayList.get(i).getLatitude()+arrayList.get(j).getLatitude()) * temp;
            centerLongitude += (arrayList.get(i).getLongitude()+arrayList.get(j).getLongitude()) * temp;
        }

        centerLongitude = centerLongitude/(area*3);
        centerLatitude = centerLatitude/(area*3);

        System.out.println(centerLongitude +"     " + centerLatitude);
    }

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

}