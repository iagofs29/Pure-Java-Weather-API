package weatherapi.dto;


public record Weather (
    String datetime,
    Double tempmax,
    Double tempmin,
    Double temp,
    Double humidity,
    Double precipprob,
    Double snow,
    Double windspeed,
    String sunrise,
    String sunset,
    int uvindex,
    String conditions
    ){}
