package weatherapi.dto;

public record WeatherRequest (
    String city,
    String unitGroup
    ){
        public WeatherRequest(String city, String unitGroup){
            this.city = city;
            this.unitGroup = unitGroup;
        }
    }
