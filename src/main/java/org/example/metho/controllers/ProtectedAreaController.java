package org.example.metho.controllers;

import java.util.*;
import org.example.metho.models.ProtectedArea;
import org.example.metho.models.WeatherCondition;
import org.example.metho.services.FuzzyLogicService;
import org.example.metho.services.ProtectedAreaService;
import org.example.metho.services.WeatherConditionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class ProtectedAreaController {
    @Autowired
    private ProtectedAreaService protectedAreaService;
    @Autowired
    private WeatherConditionsService weatherConditionsService;

    @GetMapping("/api/reserves")
    public List<ProtectedArea> getReserves() {
        return protectedAreaService.getAllReserves();
    }

    @GetMapping("/api/weather/reserves")
    public List<WeatherCondition> getAllWeatherConditions() {
        return weatherConditionsService.getAllWeatherConditions();
    }

    @PostMapping("/api/evaluate")
    public List<Map<String, String>> evaluateWeather(@RequestBody Map<String, Object> request) {
        List<Map<String, Object>> weatherData = (List<Map<String, Object>>) request.get("weatherData");
        List<Map<String, String>> result = new ArrayList<>();

        for (Map<String, Object> dayData : weatherData) {
            double temperature = ((Number) dayData.get("temperature")).doubleValue();
            double precipitation = ((Number) dayData.get("precipitation")).doubleValue();
            double windSpeed = ((Number) dayData.get("windSpeed")).doubleValue();

            String status = FuzzyLogicService.evaluateDay(temperature, precipitation, windSpeed);

            Map<String, String> dayResult = new HashMap<>();
            dayResult.put("date", (String) dayData.get("date"));
            dayResult.put("status", status);
            result.add(dayResult);
        }
        return result;
    }
}
