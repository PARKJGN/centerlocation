package com.ourcenterhere.app.centerlocation;

import com.ourcenterhere.app.centerlocation.location.dto.LocationDto;
import com.ourcenterhere.app.centerlocation.location.service.CenterLocationServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Controller
public class CenterlocationApplicationTests {

	private CenterLocationServiceTest test;

	@Test
	void contextLoads() {
		List <LocationDto> locationDtoList = new ArrayList<>();
		/*locationDtoList.add(new LocationDto("1", 127.006481, 37.284213));
		locationDtoList.add(new LocationDto("2", 127.124356, 37.411124));
		locationDtoList.add(new LocationDto("3", 127.034809, 37.562045));
		locationDtoList.add(new LocationDto("4", 126.907483, 37.528694));*/
		test.centerLocation(locationDtoList);
	}

}
