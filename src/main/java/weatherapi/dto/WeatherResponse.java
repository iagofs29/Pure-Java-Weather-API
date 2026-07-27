package weatherapi.dto;

import java.util.List;

public record WeatherResponse (
    String city,
    List<Weather> weatherOnDayList
    ){
        public WeatherResponse(String city, List<Weather> weatherOnDayList){
            this.city = city;
            this.weatherOnDayList = weatherOnDayList;
        }
    }

