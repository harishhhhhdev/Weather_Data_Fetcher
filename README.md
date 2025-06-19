# 🌤️ Weather Data Fetcher - Spring Boot Backend API

This is a **pure backend Java project** built using **Spring Boot** that fetches current weather data for a given city using the **Open-Meteo API** and returns the result as a structured JSON response.

---

## 🔧 Technologies Used

- Java 17+
- Spring Boot 3
- Spring WebFlux (WebClient)
- Open-Meteo Weather API
- Open-Meteo Geocoding API

---

## 📦 Features

- Accepts a city name as input via query param
- Converts city name to latitude & longitude using the Geocoding API
- Fetches real-time weather info (temperature, wind, code, etc.)
- Maps weather codes to readable descriptions and emojis
- Fully backend — **REST API only** (no frontend/UI)

---

## 🛠️ How It Works

1. Call the endpoint:

1. `/api/weather?city=Chennai`
2.  You pass city as query param
3. Backend fetches:
   - Latitude & Longitude via Geocoding API
   - Weather data using the Weather API
4. API responds with:
```json
{
  "temperature": "36.4",
  "windSpeed": 9.4,
  "weatherCode": 2,
  "time": "2025-06-18T11:45",
  "weatherDescription": "Partly cloudy",
  "weatherIcon": "⛅"
}
