package com.epam.weather.services;

import com.epam.weather.model.OpenWeatherData;
import com.epam.weather.to.WeatherResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class OpenWeatherMapServiceImpl implements OpenWeatherMapService {

    private static final Logger logger = LoggerFactory.getLogger(OpenWeatherMapServiceImpl.class);

    @Value("${endpoint}")
    private String endpoint;

    private final RestTemplate restTemplate;

    @Autowired
    public OpenWeatherMapServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    @Cacheable("weather")
    public WeatherResult weather(String cityId, String appId) {
        String url = applyToEndpoint(cityId, appId);

        logger.info("Calling {}...", url);

        WeatherResult weatherResult = adapt(
                Objects.requireNonNull(
                        restTemplate.getForObject(url, OpenWeatherData.class)
                )
        );

        logger.info("Result: {}", weatherResult);

        return weatherResult;
    }

    private WeatherResult adapt(OpenWeatherData openWeatherData) {
        WeatherResult weatherResult = new WeatherResult();

        weatherResult.setAtmosfericPressure(openWeatherData.getMain().getPressure());
        weatherResult.setFeelsLike(openWeatherData.getMain().getFeels_like());
        weatherResult.setMaxTemperature(openWeatherData.getMain().getTemp_max());
        weatherResult.setMinTemperature(openWeatherData.getMain().getTemp_min());
        weatherResult.setWindSpeed(openWeatherData.getWind().getSpeed());

        return weatherResult;
    }

    private String applyToEndpoint(String cityId, String appId) {
        return endpoint.replace("{CITY_ID}", cityId).replace("{APPLICATION_ID}", appId);
    }
}
