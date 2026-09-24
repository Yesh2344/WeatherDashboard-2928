package com.example.weather.service;

import com.example.weather.config.Config;
import com.example.weather.model.WeatherData;
import com.example.weather.util.HttpClientUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Service responsible for fetching and parsing weather data from
 * the external OpenWeatherMap API.
 */
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Retrieves current weather information for the supplied city.
     *
     * @param city name of the city (e.g., "London")
     * @return {@link WeatherData} populated with temperature, humidity, etc.
     * @throws IOException          if network or parsing errors occur
     * @throws InterruptedException if the HTTP request is interrupted
     */
    public WeatherData getCurrentWeather(String city) throws IOException, InterruptedException {
        String baseUrl = Config.getBaseUrl();
        String apiKey = Config.getApiKey();
        int timeout = Config.getTimeoutSeconds();

        // Build request URL safely
        String url = String.format("%s?q=%s&appid=%s&units=metric", baseUrl, encode(city), apiKey);
        logger.info("Requesting weather data: {}", url);

        String jsonResponse = HttpClientUtil.get(url, timeout);
        logger.debug("Received JSON: {}", jsonResponse);

        try {
            return mapper.readValue(jsonResponse, WeatherData.class);
        } catch (IOException e) {
            logger.error("Failed to parse weather data for city '{}'", city, e);
            throw e;
        }
    }

    /**
     * URL‑encodes a string using UTF‑8.
     *
     * @param value raw string
     * @return encoded string
     */
    private String encode(String value) {
        return java.net.URLEncoder.encode(value, java.nio.charset.StandardCharsets.UTF_8);
    }
}