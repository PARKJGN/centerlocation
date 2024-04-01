package com.ourcenterhere.app.centerlocation.room.repository;

import com.ourcenterhere.app.centerlocation.room.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {

}
