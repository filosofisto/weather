package com.epam.weather.services;

import com.epam.weather.to.WeatherResult;

public interface OpenWeatherMapService {

    WeatherResult weather(String cityId, String appId);
}
