package com.ourcenterhere.app.centerlocation.room.entity;

import com.ourcenterhere.app.centerlocation.location.entity.LocationEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomEntity {

    @Id
    @Column(name = "room_id")
    @UuidGenerator
    private UUID uuid;

    private LocalDateTime enrollDate;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<LocationEntity> loc = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private RoomType type;

    @Builder
    public RoomEntity(RoomType type){
        this.type = type;
        this.enrollDate = LocalDateTime.now();
    }

}
