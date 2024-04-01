package com.ourcenterhere.app.centerlocation.location.repository;

import com.ourcenterhere.app.centerlocation.location.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

}
