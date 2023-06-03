package com.example.growcast;

public class WeatherItem {
    private String description;
    private double temperature,feels,humidity;

    public WeatherItem(String description, double temperature, double feels, double humidity) {
        this.description = description;
        this.temperature = temperature;
        this.feels = feels;
        this.humidity = humidity;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getFeels() {
        return feels;
    }

    public void setFeels(double feels) {
        this.feels = feels;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}

