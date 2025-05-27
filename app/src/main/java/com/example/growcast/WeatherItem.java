package com.example.growcast;
import android.os.Build;
import android.widget.ImageView;
import java.time.LocalTime;

public class WeatherItem {
    private String description;

    private double temperature, feels, humidity;

    public WeatherItem(String description) {
        this.description = description;
        this.temperature = temperature;
        this.feels = feels;
        this.humidity = humidity;
        setValuesBasedOnDescription(description);
    }


    private void setValuesBasedOnDescription(String description) {
        if (description.equalsIgnoreCase("haze")) {
            temperature = 25.5;
            feels = 26.0;
            humidity = 60.0;
        } else if (description.equalsIgnoreCase("thunderstorm")) {
            temperature = 20.0;
            feels = 18.0;
            humidity = 75.0;
        } else if (description.equalsIgnoreCase("clear sky")) {
            temperature = 30.0;
            feels = 31.0;
            humidity = 50.0;
        } else if (description.equalsIgnoreCase("moderate rain")) {
            temperature = 22.5;
            feels = 24.0;
            humidity = 80.0;
        } else {
            // Default values if the description doesn't match any specific condition
            temperature = 0.0;
            feels = 0.0;
            humidity = 0.0;
        }
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

    public int getWeatherImageResourceId() {
         if (description.equalsIgnoreCase("haze")) {
            // Check the device's time to determine the appropriate image
            LocalTime currentTime = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                currentTime = LocalTime.now();
            }
            LocalTime daytimeStart = null; // Daytime starts at 5:00 AM
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                daytimeStart = LocalTime.of(5, 0);
            }
            LocalTime daytimeEnd = null; // Daytime ends at 6:00 PM
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                daytimeEnd = LocalTime.of(18, 0);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (currentTime.isAfter(daytimeStart) && currentTime.isBefore(daytimeEnd)) {
                    return R.drawable.hazeday; // Daytime image
                } else {
                    return R.drawable.hazenight; // Nighttime image
                }
            } else {
                return R.drawable.sun; // Return a default image resource if the description doesn't match any specific image
            }
        }

        else if (description.equalsIgnoreCase("thunderstorm")) {
            return R.drawable.thunderstorm;
        }

        else if (description.equalsIgnoreCase("broken clouds")) {
            return R.drawable.brokenclouds;
        }

        else if (description.equalsIgnoreCase("clear sky")) {
            // Check the device's time to determine the appropriate image
            LocalTime currentTime = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                currentTime = LocalTime.now();
            }
            LocalTime daytimeStart = null; // Daytime starts at 5:00 AM
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                daytimeStart = LocalTime.of(5, 0);
            }
            LocalTime daytimeEnd = null; // Daytime ends at 6:00 PM
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                daytimeEnd = LocalTime.of(18, 0);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (currentTime.isAfter(daytimeStart) && currentTime.isBefore(daytimeEnd)) {
                    return R.drawable.clear_sky_day; // Daytime image
                } else {
                    return R.drawable.clear_sky_night; // Nighttime image
                }
            } else {
                return R.drawable.sun; // Return a default image resource if the description doesn't match any specific image
            }
        }

        else if (description.equalsIgnoreCase("moderate rain")) {
            return R.drawable.moderaterain;
        }

        else {
            return 0; // Return a default image resource if the description doesn't match any specific image
        }
    }
    public void setWeatherImage(ImageView imageView) {
        int resourceId = getWeatherImageResourceId();
        if (resourceId != 0) {
            imageView.setImageResource(resourceId);
        } else {
            // Set a default image if the description doesn't match any specific image
            imageView.setImageResource(R.drawable.ashoka);
        }
    }
}
