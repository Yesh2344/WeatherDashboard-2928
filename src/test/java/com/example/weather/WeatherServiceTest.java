package com.example.weather;

import com.example.weather.model.WeatherData;
import com.example.weather.service.WeatherService;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {

    private static MockWebServer mockWebServer;
    private WeatherService weatherService;

    @BeforeAll
    static void setUpServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        // Override configuration to point to the mock server
        System.setProperty("BASE_URL", mockWebServer.url("/data/2.5/weather").toString());
    }

    @AfterAll
    static void tearDownServer() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void init() {
        weatherService = new WeatherService();
    }

    @Test
    void testGetCurrentWeather_successfulResponse() throws Exception {
        String mockJson = """
                {
                  "main": {
                    "temp": 22.5,
                    "humidity": 55
                  },
                  "weather": [
                    {
                      "description": "clear sky"
                    }
                  ]
                }
                """;

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mockJson)
                .addHeader("Content-Type", "application/json"));

        // Use a dummy city; the URL is intercepted by MockWebServer
        WeatherData data = weatherService.getCurrentWeather("DummyCity");

        assertNotNull(data);
        assertEquals(22.5, data.getTemperature(), 0.001);
        assertEquals(55, data.getHumidity());
        assertEquals("clear sky", data.getDescription());
    }

    @Test
    void testGetCurrentWeather_httpError() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody("{\"cod\":\"404\",\"message\":\"city not found\"}"));

        Exception exception = assertThrows(IOException.class, () -> {
            weatherService.getCurrentWeather("UnknownCity");
        });

        assertTrue(exception.getMessage().contains("Unexpected HTTP status"));
    }
}