package com.example.weatherdata;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

//	https://api.open-meteo.com/v1/forecast?latitude=13.08784&longitude=80.27847&current_weather=true
	@Bean
	public WebClient weatherWebClient(WebClient.Builder bulider) {
		return bulider.baseUrl("https://api.open-meteo.com").build();
	}

//	https://geocoding-api.open-meteo.com/v1/search?name=Chennai&count=1&language=en
	@Bean
	public WebClient geoWebClient(WebClient.Builder builder) {
		return builder.baseUrl("https://geocoding-api.open-meteo.com").build();
	}
}
