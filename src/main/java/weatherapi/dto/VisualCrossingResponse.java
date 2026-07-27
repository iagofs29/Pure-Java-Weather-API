package weatherapi.dto;

import java.util.List;

public record VisualCrossingResponse (
    String address,
    List<Weather> days,
    Weather currentConditions
){}
