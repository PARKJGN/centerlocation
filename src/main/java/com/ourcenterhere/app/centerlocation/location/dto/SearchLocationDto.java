package com.ourcenterhere.app.centerlocation.location.dto;

import com.ourcenterhere.app.centerlocation.location.entity.LocationEntity;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@ToString
public class SearchLocationDto {

    private String name;

    private double longitude;

    private double latitude;

    @Builder
    public SearchLocationDto(String name, double longitude, double latitude){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public LocationEntity toEntity(){
        return LocationEntity.builder()
                .name(name)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

}
