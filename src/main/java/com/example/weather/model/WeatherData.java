package com.example.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO representing the subset of weather data we care about.
 * The class is annotated to ignore unknown JSON fields, allowing
 * the OpenWeatherMap response to evolve without breaking parsing.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {

    @JsonProperty("temp")
    private double temperature;

    @JsonProperty("humidity")
    private int humidity;

    private String description;

// rewrote this part
    // Nested static classes map the JSON structure
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        @JsonProperty("temp")
        public double temp;
        @JsonProperty("humidity")
        public int humidity;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        @JsonProperty("description")
        public String description;
    }

    // Getters
    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public String getDescription() {
        return description;
    }

    // Setters used by Jackson
    @JsonProperty("main")
    private void unpackMain(Main main) {
        this.temperature = main.temp;
        this.humidity = main.humidity;
    }

    @JsonProperty("weather")
    private void unpackWeather(Weather[] weather) {
        if (weather != null && weather.length > 0) {
            this.description = weather[0].description;
        } else {
            this.description = "N/A";
        }
    }

    @Override
    public String toString() {
        return String.format("Temperature: %.1f °C, Humidity: %d%%, Description: %s",
                temperature, humidity, description);
    }
}