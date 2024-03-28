package com.ourcenterhere.app.centerlocation.CenterLocation.service;

import com.ourcenterhere.app.centerlocation.CenterLocation.dto.SearchLocationDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CenterLocationService {

    /*
        유저가 입력한 주소들의 좌표를 가지고 centroid 알고리즘을 통해 중심점을 구하는 함수
     */
    public void centerLocation(List<SearchLocationDto> searchLocationDtoList){

        List<SearchLocationDto> arrayList = arrayLocationList(searchLocationDtoList);
        double centerLongitude = 0;
        double centerLatitude = 0;
        double area = 0;

        for(int i=0; i< searchLocationDtoList.size(); i++){

            int j = i+1;

            // 마지막 항은 다각형의 첫번째 점과 이어야 하므로 따로 처리
            if(j >= searchLocationDtoList.size()) j=0;

            double temp = (arrayList.get(i).getLatitude()*arrayList.get(j).getLongitude() - arrayList.get(j).getLatitude()*arrayList.get(i).getLongitude());

            area += temp;

            centerLongitude += (arrayList.get(i).getLongitude()+arrayList.get(j).getLongitude()) * temp;
            centerLatitude += (arrayList.get(i).getLatitude()+arrayList.get(j).getLatitude()) * temp;
        }

        centerLongitude = centerLongitude/(area*3);
        centerLatitude = centerLatitude/(area*3);


    }

    /*
        유저가 입력한 주소들의 좌표를 가지고 중심점을 구할 수 있게 정렬하는 함수
     */
    public List<SearchLocationDto> arrayLocationList(List<SearchLocationDto> searchLocationDtoList){

        double minLongitude = searchLocationDtoList
                                .stream()
                                .mapToDouble(SearchLocationDto::getLongitude)
                                .min()
                                .orElseThrow(NoSuchElementException::new);

        double maxLongitude = searchLocationDtoList
                                .stream()
                                .mapToDouble(SearchLocationDto::getLongitude)
                                .max()
                                .orElseThrow(NoSuchElementException::new);
        // 유저가 입력한 주소중에서 제일 큰 Longitude 와 제일 작은 Longitude 의 가운데 Longitude
        double centerLongitude = (maxLongitude+minLongitude)/2.0;

        // centerLongitude를 기준으로 왼쪽 좌표들의 Latitude가 작은 순서의 집합
        List<SearchLocationDto> leftArrayList = searchLocationDtoList.stream()
                                                .filter(x->x.getLongitude()<=centerLongitude)
                                                .sorted(Comparator.comparingDouble(SearchLocationDto::getLatitude))
                                                .toList();

        // centerLongitude를 기준으로 오른쪽 좌표들의 Latitude가 큰 순서의 집합
        List<SearchLocationDto> rightArrayList = searchLocationDtoList.stream()
                                                .filter(x->x.getLongitude()>centerLongitude)
                                                .sorted(Comparator.comparingDouble(SearchLocationDto::getLatitude).reversed())
                                                .toList();

        List<SearchLocationDto> arrayLocationDtoList = Stream.of(leftArrayList, rightArrayList).flatMap(Collection::stream).toList();

        return arrayLocationDtoList;
    }

}
