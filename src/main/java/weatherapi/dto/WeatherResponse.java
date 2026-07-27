package weatherapi.dto;

import java.util.List;

public record WeatherResponse (
    String city,
    List<Weather> weatherOnDayList,
    Weather current
    ){
        public WeatherResponse(String city, List<Weather> weatherOnDayList, Weather current){
            this.city = city;
            this.weatherOnDayList = weatherOnDayList;
            this.current = current;
        }
    }

