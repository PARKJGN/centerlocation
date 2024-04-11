package com.ourcenterhere.app.centerlocation;

import com.ourcenterhere.app.centerlocation.location.dto.LocationDto;
import com.ourcenterhere.app.centerlocation.location.service.CenterLocationServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Controller
public class CenterlocationApplicationTests {

	@Autowired
	private CenterLocationServiceTest test;

	@Test
	void contextLoads() {
		List <LocationDto> locationDtoList = new ArrayList<>();
		locationDtoList.add(LocationDto.builder().userName("1")
				.longitude(127.006481)
				.latitude(37.284213)
				.build());
		locationDtoList.add(LocationDto.builder().userName("2")
				.longitude(127.124356)
				.latitude(37.411124)
				.build());
		locationDtoList.add(LocationDto.builder().userName("3")
				.longitude(127.034809)
				.latitude(37.562045)
				.build());

		test.centerLocation(locationDtoList);
	}

}
