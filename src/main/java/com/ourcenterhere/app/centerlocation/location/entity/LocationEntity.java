package com.ourcenterhere.app.centerlocation.location.entity;

import com.ourcenterhere.app.centerlocation.room.entity.RoomEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
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

    public void setRoomEntity(RoomEntity roomEntity){
        this.room = roomEntity;
        room.getLoc().add(this);
    }
}
