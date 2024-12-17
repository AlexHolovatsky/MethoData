package org.example.metho.services;

import org.example.metho.models.WeatherCondition;
import org.example.metho.repositories.WeatherConditionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherConditionsService {

    @Autowired
    private WeatherConditionsRepository weatherConditionsRepository;

    public List<WeatherCondition> getAllWeatherConditions() {
        return weatherConditionsRepository.findAll();
    }
}
