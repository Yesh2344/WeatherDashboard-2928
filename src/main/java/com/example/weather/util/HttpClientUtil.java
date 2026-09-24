package com.example.weather.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Simple wrapper around {@link java.net.http.HttpClient} that provides
 * a reusable, thread‑safe client and a convenient GET method.
 */
public final class HttpClientUtil {

    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private HttpClientUtil() {
        // Utility class – prevent instantiation
    }

    /**
     * Executes an HTTP GET request and returns the response body as a string.
     *
     * @param url full request URL
     * @param timeoutSeconds request timeout in seconds
     * @return response body
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    public static String get(String url, int timeoutSeconds) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(timeoutSeconds))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        if (status >= 200 && status < 300) {
            return response.body();
        } else {
            throw new IOException("Unexpected HTTP status: " + status + " for URL: " + url);
        }
    }
}