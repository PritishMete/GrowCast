package com.example.growcast;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class WeatherLoader extends AsyncTaskLoader<WeatherItem> {
    private final String CITY_NAME;

    public WeatherLoader(@NonNull Context context, final String CITY_NAME) {
        super(context);
        this.CITY_NAME = CITY_NAME;
    }

    @Nullable
    @Override
    public WeatherItem loadInBackground() {
        return NetworkUtility.createHttpRequest(CITY_NAME);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();

    }
}
