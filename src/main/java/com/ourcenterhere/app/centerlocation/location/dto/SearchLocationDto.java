package com.ourcenterhere.app.centerlocation.location.dto;

import com.ourcenterhere.app.centerlocation.location.entity.LocationEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@ToString
public class SearchLocationDto {

    @NotEmpty
    private String name;

    @NotNull
    private double longitude;

    @NotNull
    private double latitude;

    private String roomId;

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
