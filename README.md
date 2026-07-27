# Weather API

A lightweight RESTful Weather API developed in pure Java without using Spring Boot.

The project consumes the **Visual Crossing Weather API**, exposes custom REST endpoints and follows a layered architecture (Controller → Service → Repository) to keep responsibilities separated.

This project was developed as a learning exercise to understand how HTTP servers, REST APIs and backend architectures work before moving to Spring Boot.

User must sign up on Visual Crossing (https://www.visualcrossing.com)and create env. variable with your API KEY, which is detailed further down.

---

## Features

- Current weather endpoint
- Weather forecast endpoint
- Query parameter parsing
- JSON serialization/deserialization using Gson
- HTTP client implementation using Java HttpClient
- Environment variable configuration
- Proper HTTP status codes
- Layered architecture
- Maven project

---

## Tech Stack

- Java 21+
- Maven
- Gson
- Java HttpServer
- Java HttpClient
- Visual Crossing Weather API

---

## Architecture

```
Client
   │
   ▼
WeatherController
   │
   ▼
WeatherService
   │
   ▼
WeatherRepository
   │
   ▼
VisualCrossingRepository
   │
   ▼
Visual Crossing API
```

Project structure:

```
src
└── main
    └── java
        └── weatherapi
            ├── controller
            ├── dto
            ├── repository
            ├── service
            ├── http
            └── Main.java
```

---

## Endpoints

### Get current weather

```
GET /weather/current
```

Example

```
GET /weather/current?city=Vigo&unitGroup=metric
```

Query Parameters

| Parameter | Required | Description |
|-----------|----------|-------------|
| city | Yes | City name |
| unitGroup | No | metric, us or uk |

Response

```json
{
  "city": "Madrid",
  "weatherOnDayList": [
    {
      "datetime": "2026-07-28",
      "tempmax": 25.9,
      "tempmin": 16.7,
      "temp": 21.2,
      "humidity": 62.7,
      "precipprob": 0.0,
      "snow": 0.0,
      "windspeed": 12.6,
      "sunrise": "07:23:42",
      "sunset": "21:58:36",
      "uvindex": 9,
      "conditions": "Clear"
    }
  ]
}
```

---

### Get weather forecast

```
GET /weather/forecast
```

Example

```
GET /weather/forecast?city=Vigo&unitGroup=metric&days=5
```

Query Parameters

| Parameter | Required | Description |
|-----------|----------|-------------|
| city | Yes | City name |
| unitGroup | No | metric or us |
| days | No | Number of forecast days |

---

## HTTP Status Codes

| Code | Meaning |
|------|---------|
| 200 | OK |
| 400 | Bad Request |
| 404 | City not found |
| 405 | Method Not Allowed |
| 500 | Internal Server Error |
| 502 | External Weather Service Error |
| 504 | Gateway Timeout |

---

## Running the project

### Clone the repository

```bash
git clone https://github.com/<your-user>/<repository>.git
```

### Set the API key

Create the following environment variable:

```
VISUAL_CROSSING_API_KEY
```

Example

Linux / macOS

```bash
export VISUAL_CROSSING_API_KEY=your_api_key
```

Windows

```cmd
set VISUAL_CROSSING_API_KEY=your_api_key
```

---

### Build

```bash
mvn clean package
```

---

### Run

```bash
mvn exec:java
```

or directly from your IDE.

The server starts on:

```
http://localhost:8080
```

---

## Example Requests

Current weather

```
GET http://localhost:8080/weather/current?city=Madrid
```

Forecast

```
GET http://localhost:8080/weather/forecast?city=Madrid&days=3
```

---

## Design Decisions

- Layered architecture
- Repository abstraction
- Environment variables for API keys
- DTO-based communication
- Separation between external API and business logic
- Manual HTTP handling to better understand REST internals

---

## What I learned

- REST API design
- HTTP protocol fundamentals
- Java HttpServer
- Java HttpClient
- Layered architecture
- DTOs and Records
- Gson serialization/deserialization
- Environment variables
- Error handling
- Maven project structure
- Git workflow

## License

This project is intended for educational purposes.