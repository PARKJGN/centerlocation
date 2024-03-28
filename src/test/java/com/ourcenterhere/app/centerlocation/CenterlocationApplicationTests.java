package com.ourcenterhere.app.centerlocation;

import com.ourcenterhere.app.centerlocation.CenterLocation.dto.SearchLocationDto;
import com.ourcenterhere.app.centerlocation.CenterLocation.dto.SearchLocationDtoList;
import com.ourcenterhere.app.centerlocation.CenterLocation.service.CenterLocationService;
import com.ourcenterhere.app.centerlocation.CenterLocation.service.CenterLocationServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Controller
class CenterlocationApplicationTests {

	@Autowired
	private CenterLocationServiceTest centerLocationService;

	@Test
	void contextLoads() {
		List <SearchLocationDto> searchLocationDtoList = new ArrayList<>();
		searchLocationDtoList.add(new SearchLocationDto("1", 127.006481, 37.284213));
		searchLocationDtoList.add(new SearchLocationDto("2", 127.124356, 37.411124));
		searchLocationDtoList.add(new SearchLocationDto("3", 127.034809, 37.562045));
		searchLocationDtoList.add(new SearchLocationDto("4", 126.907483, 37.528694));
		centerLocationService.centerLocation(searchLocationDtoList);
	}

}
