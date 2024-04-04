package com.ourcenterhere.app.centerlocation.location.dto;

import com.ourcenterhere.app.centerlocation.location.entity.LocationEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@ToString
public class LocationDto {

    private Long id;

    @NotEmpty
    private String userName;

    @NotEmpty
    private String roadName;

    @NotNull
    private double longitude;

    @NotNull
    private double latitude;

    private String roomId;

    @Builder
    public LocationDto(Long id,String userName, double longitude, double latitude, String roadName){
        this.userName = userName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.roadName = roadName;
        this.id = id;
    }

    public LocationEntity toEntity(){
        return LocationEntity.builder()
                .userName(userName)
                .latitude(latitude)
                .longitude(longitude)
                .roadName(roadName)
                .build();
    }

}
