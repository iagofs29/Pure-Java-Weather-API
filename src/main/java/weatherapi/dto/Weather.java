package weatherapi.dto;

public record Weather (
    String datetime,
    double tempmax,
    double tempmin,
    double temp,
    double humidity,
    double precipprob,
    double snow,
    double windspeed,
    String sunrise,
    String sunset,
    String conditions
    ){}
