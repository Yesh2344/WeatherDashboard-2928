package com.example.weather.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads configuration values from a .env file located at the project root.
 * The Dotenv library automatically reads the file and makes the values
 * accessible via {@code get(String)}.
 */
public final class Config {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);
    private static final Dotenv dotenv = Dotenv.configure()
            .directory("./")          // project root
            .ignoreIfMissing()
            .load();

    private Config() {
        // Utility class – prevent instantiation
    }

    /**
     * Retrieves the OpenWeatherMap API key.
     *
     * @return API key string; throws IllegalStateException if missing.
     */
    public static String getApiKey() {
        String key = dotenv.get("API_KEY");
        if (key == null || key.isBlank()) {
            logger.error("API_KEY is not set in .env file.");
            throw new IllegalStateException("Missing API_KEY in configuration.");
        }
        return key;
    }

    /**
     * Retrieves the base URL for the weather API.
     *
     * @return base URL string; defaults to OpenWeatherMap endpoint if not set.
     */
    public static String getBaseUrl() {
        String url = dotenv.get("BASE_URL");
        if (url == null || url.isBlank()) {
            logger.warn("BASE_URL not set – falling back to default OpenWeatherMap URL.");
            return "https://api.openweathermap.org/data/2.5/weather";
        }
        return url;
    }

    /**
     * Retrieves the request timeout in seconds.
     *
     * @return timeout in seconds; defaults to 10.
     */
    public static int getTimeoutSeconds() {
        String value = dotenv.get("TIMEOUT_SECONDS");
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException | NullPointerException e) {
            return 10; // sensible default
        }
    }
}