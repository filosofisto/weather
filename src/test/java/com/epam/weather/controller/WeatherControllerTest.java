package com.epam.weather.controller;

import com.epam.weather.services.OpenWeatherMapService;
import com.epam.weather.to.WeatherResult;
import com.epam.weather.util.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import({Messages.class})
public class WeatherControllerTest {

    private static final String URL = "/weather";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123456";
    private static final String CITY_ID = "756135";
    private static final String APP_ID = "7ee0537507dc0758af5c72dfc17bdb58";
    public static final String PARAM_ID = "id";
    public static final String PARAM_APPID = "APPID";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OpenWeatherMapService service;

    @Test
    public void responsStatus401() throws Exception {
        this.mockMvc.perform(get(URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void responseStatus400() throws Exception {
        mockMvc.perform(
                get(URL)
                .with(httpBasic(USERNAME, PASSWORD))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void responseStatus200() throws Exception {
        mockMvc.perform(
                get(URL)
                .with(httpBasic(USERNAME, PASSWORD))
                .param(PARAM_ID, CITY_ID)
                .param(PARAM_APPID, APP_ID)
        ).andExpect(status().isOk());
    }



    @Test
    public void checkMockedResponse() throws Exception {
        WeatherResult weatherResult = new WeatherResult();
        weatherResult.setAtmosfericPressure(1);
        weatherResult.setMinTemperature(10.0);
        weatherResult.setMaxTemperature(10.0);
        weatherResult.setWindSpeed(3.0);
        weatherResult.setFeelsLike(1.5);

        when(service.weather(any(), any())).thenReturn(weatherResult);

        MvcResult mvcResult = mockMvc.perform(
                get(URL)
                .with(httpBasic(USERNAME, PASSWORD))
                .param(PARAM_ID, CITY_ID)
                .param(PARAM_APPID, APP_ID)
        )
        .andExpect(status().isOk())
        .andReturn();

        WeatherResult bodyAsWeatherResult = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), WeatherResult.class
        );

        assertEquals(weatherResult.getAtmosfericPressure(), bodyAsWeatherResult.getAtmosfericPressure());
        assertEquals(weatherResult.getMinTemperature(), bodyAsWeatherResult.getMinTemperature(), 0.001);
        assertEquals(weatherResult.getWindSpeed(), bodyAsWeatherResult.getWindSpeed(), 0.001);
        assertEquals(weatherResult.getFeelsLike(), bodyAsWeatherResult.getFeelsLike(), 0.001);
    }
}
