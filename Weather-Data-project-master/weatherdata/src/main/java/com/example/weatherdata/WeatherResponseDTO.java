package com.example.weatherdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponseDTO {

	@JsonProperty("temperature")
	private String temperature;

	@JsonProperty("windspeed")
	private double windSpeed;

	@JsonProperty("weathercode")
	private int weatherCode;

	@JsonProperty("time")
	private String time;

	private String weatherDiscription;

	private String weatherIcon;
}
