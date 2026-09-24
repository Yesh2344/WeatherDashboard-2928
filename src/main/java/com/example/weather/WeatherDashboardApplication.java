package com.example.weather;

import com.example.weather.model.WeatherData;
import com.example.weather.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point of the Weather Dashboard application.
 * It reads a city name from command‑line arguments (or defaults to "London"),
 * fetches weather data via {@link WeatherService}, and prints a human‑readable
 * summary to the console.
 */
public class WeatherDashboardApplication {

    private static final Logger logger = LoggerFactory.getLogger(WeatherDashboardApplication.class);
    private static final String DEFAULT_CITY = "London";

    public static void main(String[] args) {
        String city = (args.length > 0 && !args[0].isBlank()) ? args[0] : DEFAULT_CITY;
        logger.info("Fetching weather for {}", city);

        WeatherService service = new WeatherService();
        try {
            WeatherData data = service.getCurrentWeather(city);
            System.out.println("Current weather in " + city + ":");
            System.out.println("  Temperature: " + data.getTemperature() + " °C");
            System.out.println("  Humidity: " + data.getHumidity() + " %");
            System.out.println("  Description: " + data.getDescription());
        } catch (Exception e) {
            logger.error("Unable to retrieve weather data.", e);
            System.err.println("Failed to fetch weather data. See logs for details.");
            System.exit(1);
        }
    }
}