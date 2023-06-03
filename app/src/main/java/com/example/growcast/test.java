//package com.example.growcast;
//
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.location.LocationServices;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class weather extends AppCompatActivity {
//
//    // API Key for OpenWeatherMap
//    private static final String API_KEY = "2469624757d5e8e00ee047890b315187";
//
//    // API Endpoint for weather data
//    private static final String API_ENDPOINT = "https://api.openweathermap.org/data/2.5/weather?q=city_name_here&appid=" + API_KEY;
//
//    private TextView temperatureValueTextView;
//    private TextView highTempValueTextView;
//    private TextView lowTempValueTextView;
//    private TextView uvIndexValueTextView;
//    private TextView airSpeedValueTextView;
//    private TextView humidityValueTextView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.weather);
//
//        // Initialize TextViews
//        temperatureValueTextView = findViewById(R.id.temperatureValueTextView);
//        highTempValueTextView = findViewById(R.id.highTempValueTextView);
//        lowTempValueTextView = findViewById(R.id.lowTempValueTextView);
//        uvIndexValueTextView = findViewById(R.id.uvIndexValueTextView);
//        airSpeedValueTextView = findViewById(R.id.airSpeedValueTextView);
//        humidityValueTextView = findViewById(R.id.humidityValueTextView);
//
//        // Fetch weather data
//        WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask();
//        weatherAsyncTask.execute();
//    }
//
//    private class WeatherAsyncTask extends AsyncTask<Void, Void, String> {
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            String result = "";
//
//            try {
//                // Create URL object with the API endpoint
//                URL url = new URL(API_ENDPOINT.replace("city_name_here", "your_city_name_here"));
//
//                // Create HttpURLConnection object and establish connection
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("GET");
//
//                // Read the response
//                InputStream inputStream = connection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    result += line;
//                }
//
//                // Close the streams and disconnect the connection
//                bufferedReader.close();
//                inputStream.close();
//                connection.disconnect();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//            try {
//                // Parse the JSON response
//                JSONObject jsonObject = new JSONObject(result);
//
//                // Extract the weather data
//                double temperature = jsonObject.getJSONObject("main").getDouble("temp");
//                double highTemp = jsonObject.getJSONObject("main").getDouble("temp_max");
//                double lowTemp = jsonObject.getJSONObject("main").getDouble("temp_min");
//                double uvIndex = jsonObject.getDouble("uv_index");
//                double airSpeed = jsonObject.getJSONObject("wind").getDouble("speed");
//                int humidity = jsonObject.getJSONObject("main").getInt("humidity");
//
//                // Update the TextViews with the fetched values
//                temperatureValueTextView.setText(String.valueOf(temperature));
//                highTempValueTextView.setText(String.valueOf(highTemp));
//                lowTempValueTextView.setText(String.valueOf(lowTemp));
//                uvIndexValueTextView.setText(String.valueOf(uvIndex));
//                airSpeedValueTextView.setText(String.valueOf(airSpeed));
//                humidityValueTextView.setText(String.valueOf(humidity));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
