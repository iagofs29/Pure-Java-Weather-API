package weatherapi.dto;

public record WeatherRequest (
    String city,
    String unitGroup,
    int days
    ){
        public WeatherRequest(String city, String unitGroup, int days){
            this.city = city;
            this.unitGroup = unitGroup;
            this.days = days;
        }
    }
