package com.example.weatherdata;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherService {

	private final WebClient weatherWebClient;
	private final WebClient geoWebClient;

	public WeatherService(WebClient weatherWebClient, WebClient geoWebClient) {

		this.weatherWebClient = weatherWebClient;
		this.geoWebClient = geoWebClient;
	}

	// step 1 convert location name to lat and lon
	public GeocodingResponseDTO.Location getCoordinates(String location) {

		GeocodingResponseDTO response = geoWebClient.get()
				.uri(uriBuilder -> uriBuilder.path("/v1/search").queryParam("name", location).queryParam("count", 1)
						.queryParam("language", "en").build())
				.retrieve().bodyToMono(GeocodingResponseDTO.class).block();

//		return (response != null && !response.getResults().isEmpty()) ? response.getResults().get(0) : null;
		System.out.println("Geocoding API Response: " + (response != null ? response.toString() : "No response"));

		if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
			return response.getResults().get(0);
		} else {
			return null;
		}
	}

	// step 2 get weather data based on lat & lon
	public WeatherResponseDTO getWeather(String location) {

		GeocodingResponseDTO.Location coordinates = getCoordinates(location);
		if (coordinates == null) {
			System.out.println("No coordinates found for location: " + location);
			return null;
		}

		// map JSON to WeatherApiREsponseDTO
		WeatherApiResponseDTO response = weatherWebClient.get()
				.uri(uriBuilder -> uriBuilder.path("/v1/forecast").queryParam("latitude", coordinates.getLatitude())
						.queryParam("longitude", coordinates.getLongitude()).queryParam("current_weather", "true")
						.build())
				.retrieve().bodyToMono(WeatherApiResponseDTO.class).block();

		System.out.println("Weather API Response: " + (response != null ? response.toString() : "No response"));

		if (response == null || response.getCurrentWeather() == null) {
			System.out.println("No weather data available for coordinates.");
			return null;
		}

		WeatherResponseDTO weatherResponse = response.getCurrentWeather();

		// add weather description and Icon(emoji)
		weatherResponse.setWeatherDiscription(getWeatherDescription(weatherResponse.getWeatherCode()));

		weatherResponse.setWeatherIcon(getWeatherIcon(weatherResponse.getWeatherCode()));

		return weatherResponse;

	}

	// map weather code to weather description

	private String getWeatherDescription(int code) {

		Map<Integer, String> weatherDescription = Map.ofEntries(

				Map.entry(0, "Clear sky"), Map.entry(1, "Mainly clear"), Map.entry(2, "Partly cloudy"),
				Map.entry(3, "Overcast"), Map.entry(45, "Fog"), Map.entry(48, "Depositing rime fog"),
				Map.entry(51, "Light drizzle"), Map.entry(53, "Moderate drizzle"), Map.entry(55, "Heavy drizzle"),
				Map.entry(56, "Freezing drizzle"), Map.entry(57, "Heavy freezing drizzle"), Map.entry(61, "Light rain"),
				Map.entry(63, "Moderate rain"), Map.entry(65, "Heavy rain"), Map.entry(80, "Rain showers"),
				Map.entry(81, "Heavy rain showers"), Map.entry(82, "Violent rain showers"),
				Map.entry(95, "Thunderstorm"), Map.entry(96, "Thunderstorm with hail"),
				Map.entry(99, "Severe thunderstorm with hail")

		);

		return weatherDescription.getOrDefault(code, "UnKnown Weather");
	}

	// map weather code to weather icon

	private String getWeatherIcon(int code) {

		Map<Integer, String> weatherIcon = Map.ofEntries(Map.entry(0, "☀️"), // Clear sky
				Map.entry(1, "🌤️"), // Mainly clear
				Map.entry(2, "⛅"), // Partly cloudy
				Map.entry(3, "☁️"), // Overcast
				Map.entry(45, "🌫️"), // Fog
				Map.entry(48, "🌁"), // Depositing rime fog
				Map.entry(51, "🌦️"), // Light drizzle
				Map.entry(53, "🌧️"), // Moderate drizzle
				Map.entry(55, "🌧️"), // Heavy drizzle
				Map.entry(56, "🌨️"), // Freezing drizzle
				Map.entry(57, "❄️"), // Heavy freezing drizzle
				Map.entry(61, "🌧️"), // Light rain
				Map.entry(63, "🌧️"), // Moderate rain
				Map.entry(65, "🌧️"), // Heavy rain
				Map.entry(80, "🌦️"), // Rain showers
				Map.entry(81, "🌧️"), // Heavy rain showers
				Map.entry(82, "⛈️"), // Violent rain showers
				Map.entry(95, "🌩️"), // Thunderstorm
				Map.entry(96, "⛈️"), // Thunderstorm with hail
				Map.entry(99, "⛈️") // Severe thunderstorm with hail
		);

		return weatherIcon.getOrDefault(code, "❓");
	}
}
