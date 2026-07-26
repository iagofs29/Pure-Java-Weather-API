package weatherapi.models;

public class Weather {  // Variables names correspond to Json response format so the method gson.fromJson can parse data correctly.

    private String datetime;
    private double tempmax;
    private double tempmin;
    private double temp;
    private double humidity;
    private double precipprob;
    private double snow;
    private double windspeed; 
    private String sunrise;
    private String sunset;
    private String conditions;

    public Weather(){}

    public String getDateTime(){
        return this.datetime;
    }

    public double getTempMax(){
        return this.tempmax;
    }

    public double getTempMin(){
        return this.tempmin;
    }

    public double getTemp(){
        return this.temp;
    }

    public double getHumidity(){
        return this.humidity;
    }

    public double getPrecipProb(){
        return this.precipprob;
    }

    public double getSnow(){
        return this.snow;
    }

    public double getWindSpeed(){
        return this.windspeed;
    }

    public String getSunrise(){
        return this.sunrise;
    }

    public String getSunset(){
        return this.sunset;
    }

    public String getConditions(){
        return this.conditions;
    }

    @Override
    public String toString(){
        return "The weather at " + this.getDateTime() + " is " +
                this.getConditions() + "\n\tMaximum temperature: " + this.getTempMax() + 
                "\n\tMinimum temperature: " + this.getTempMin() + "\n\tAverage temperature: " + this.getTemp() + 
                "\n\tHumidity: " + this.getHumidity() + "\n\tPrecipitation probability: " + this.getPrecipProb() + 
                "\n\tAmount of snow: " + this.getSnow() + "\n\tWindspeed: " + this.getWindSpeed() + "\n\tSunrise time: " 
                + this.sunrise + "\n\tSunset time: " + this.getSunset(); 
    }

}
