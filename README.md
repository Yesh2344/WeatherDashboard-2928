# WeatherDashboard 🌤️

[![Build Status](https://github.com/yourusername/WeatherDashboard/workflows/CI/badge.svg)](https://github.com/yourusername/WeatherDashboard/actions)
[![Java Version](https://img.shields.io/badge/java-17%2B-blue)](https://adoptium.net)

## Overview

WeatherDashboard is a lightweight, production‑ready Java console application that fetches and displays the current weather for a given city. 
It demonstrates:

* Clean architecture with separate concerns (configuration, HTTP client, service, model)
* Modern Java (Java 17) features and best practices
* Robust error handling & logging (SLF4J + Logback)
* External API integration (OpenWeatherMap)
* Unit testing with JUnit 5 and OkHttp MockWebServer
* Environment‑based configuration using **.env** files

## Architecture