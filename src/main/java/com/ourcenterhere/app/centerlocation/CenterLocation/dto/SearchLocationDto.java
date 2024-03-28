package com.ourcenterhere.app.centerlocation.CenterLocation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchLocationDto {

    private String name;

    private double longitude;

    private double latitude;
}
