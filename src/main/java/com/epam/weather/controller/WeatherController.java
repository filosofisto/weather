package com.epam.weather.controller;

import com.epam.weather.services.OpenWeatherMapService;
import com.epam.weather.to.WeatherResult;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Weather EPAM API", description = "This API allow a user get information about weather of cities around the world.")
public class WeatherController {

    private final OpenWeatherMapService openWeatherMapService;

    public WeatherController(OpenWeatherMapService openWeatherMapService) {
        this.openWeatherMapService = openWeatherMapService;
    }

    @ApiOperation(value = "Get weather information from a City")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved data"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Something unexpected happened"),
    })
    @GetMapping(path = "weather")
    public ResponseEntity<WeatherResult> weather(
            @ApiParam(value = "City identifier", required = true) @RequestParam("id") String cityId,
            @ApiParam(value = "Application Identifier", required = true) @RequestParam("APPID") String appId) {
        return ResponseEntity.ok(openWeatherMapService.weather(cityId, appId));
    }
}
