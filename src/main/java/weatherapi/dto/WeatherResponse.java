package weatherapi.dto;

import java.util.List;

// NOTA IMPORTANTE: ESTE FICHERO QUEDARÁ INUTILIZADO DEBIDO A QUE VISUALCROSSINGREPOSITORY SE ENCARGARÁ DE PARSEAR
// CORRECTAMENTE LOS DATOS EXTRAÍDOS DE LA WEB

public class WeatherResponse {
    private String address;
    private List<Weather> days;

    public WeatherResponse(){}

    public String getAddress(){
        return this.address;
    }

    public List<Weather> getDays(){
        return days;
    }
}
