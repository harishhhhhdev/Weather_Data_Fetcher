package com.example.weatherdata;

import java.util.List;

import lombok.Data;

@Data
public class GeocodingResponseDTO {

	private List<Location> results;

	@Data
	public static class Location {

		private double latitude;
		private double longitude;
	}
}
