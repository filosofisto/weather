package com.epam.weather.to;

public class WeatherResult {

    private double minTemperature;

    private double maxTemperature;

    private double feelsLike;

    private double windSpeed;

    private int atmosfericPressure;

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getAtmosfericPressure() {
        return atmosfericPressure;
    }

    public void setAtmosfericPressure(int atmosfericPressure) {
        this.atmosfericPressure = atmosfericPressure;
    }

    @Override
    public String toString() {
        return "WeatherResult{" +
                "minTemperature=" + minTemperature +
                ", maxTemperature=" + maxTemperature +
                ", feelsLike=" + feelsLike +
                ", windSpeed=" + windSpeed +
                ", atmosfericPressure=" + atmosfericPressure +
                '}';
    }
}
