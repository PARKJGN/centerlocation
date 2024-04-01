package com.ourcenterhere.app.centerlocation.room.entity;

import com.ourcenterhere.app.centerlocation.location.dto.SearchLocationDto;
import com.ourcenterhere.app.centerlocation.location.entity.LocationEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class RoomEntity {

    @Id
    @Column(name = "room_id")
    @UuidGenerator
    private UUID uuid;

    @CreationTimestamp
    private LocalDateTime enrollDate;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<LocationEntity> loc = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private RoomType type;

    //생성자 함수
    public static RoomEntity createRoom(RoomType type, List<LocationEntity> loc){
        RoomEntity room = new RoomEntity();
        room.type = type;
        for(LocationEntity locationEntity: loc){
        }
        return room;
    }

}
