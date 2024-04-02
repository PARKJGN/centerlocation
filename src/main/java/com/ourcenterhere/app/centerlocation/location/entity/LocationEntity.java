package com.ourcenterhere.app.centerlocation.location.entity;

import com.ourcenterhere.app.centerlocation.location.dto.SearchLocationDto;
import com.ourcenterhere.app.centerlocation.room.entity.RoomEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationEntity {

    @Id @GeneratedValue
    private Long Id;

    @NotNull(message = "이름을 적어주세요.")
    @Size(min = 1)
    private String name;

    @NotNull(message = "주소를 입력해주세요.")
    private double longitude;

    @NotNull(message = "주소를 입력해주세요.")
    private double latitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @Builder
    public LocationEntity(String name, double longitude, double latitude, RoomEntity room){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.room = room;
    }

    //연관관계 메서드
    public void setRoomEntity(RoomEntity roomEntity){
        this.room = roomEntity;
        room.getLoc().add(this);
    }

    public SearchLocationDto toSearchLocationDto(){
        return SearchLocationDto.builder()
                .name(name)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
