package org.example.metho.repositories;

import org.example.metho.models.WeatherCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherConditionsRepository extends JpaRepository<WeatherCondition, Integer> {
    List<WeatherCondition> findAll();
}
