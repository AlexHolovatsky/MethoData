package org.example.metho.controllers;

import java.io.ByteArrayOutputStream;
import java.util.*;
import org.example.metho.models.ProtectedArea;
import org.example.metho.models.WeatherCondition;
import org.example.metho.services.FuzzyLogicService;
import org.example.metho.services.ProtectedAreaService;
import org.example.metho.services.WeatherConditionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

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
    public ResponseEntity<?> evaluateWeather(@RequestBody Map<String, Object> request) {
        try {
            List<Map<String, Object>> weatherData = (List<Map<String, Object>>) request.get("weatherData");
            if (weatherData == null || weatherData.isEmpty()) {
                throw new IllegalArgumentException("Request must contain a non-empty 'weatherData' array.");
            }

            List<Map<String, String>> result = new ArrayList<>();

            for (int i = 0; i < weatherData.size(); i++) {
                Map<String, Object> dayData = weatherData.get(i);

                // Перевірка наявності всіх необхідних полів
                String[] requiredFields = {
                        "date", "temperature", "precipitation", "windSpeed",
                        "humidity", "solarRadiation", "airPressure",
                        "airQualityIndex", "cloudCover", "precipitationIntensity"
                };

                for (String field : requiredFields) {
                    if (!dayData.containsKey(field) || dayData.get(field) == null) {
                        throw new IllegalArgumentException("Missing required field: '" + field + "' in weatherData entry at index " + i);
                    }
                }

                // Отримання полів
                double temperature = ((Number) dayData.get("temperature")).doubleValue();
                double precipitation = ((Number) dayData.get("precipitation")).doubleValue();
                double windSpeed = ((Number) dayData.get("windSpeed")).doubleValue();
                double humidity = ((Number) dayData.get("humidity")).doubleValue();
                double solarRadiation = ((Number) dayData.get("solarRadiation")).doubleValue();
                double airPressure = ((Number) dayData.get("airPressure")).doubleValue();
                double airQualityIndex = ((Number) dayData.get("airQualityIndex")).doubleValue();
                double cloudCover = ((Number) dayData.get("cloudCover")).doubleValue();
                double precipitationIntensity = ((Number) dayData.get("precipitationIntensity")).doubleValue();

                // Виклик методу оцінки дня
                String status = FuzzyLogicService.evaluateDay(
                        temperature,
                        precipitation,
                        windSpeed,
                        humidity,
                        solarRadiation,
                        airPressure,
                        airQualityIndex,
                        cloudCover,
                        precipitationIntensity
                );

                // Формування результатів
                Map<String, String> dayResult = new HashMap<>();
                dayResult.put("date", (String) dayData.get("date"));
                dayResult.put("status", status);
                result.add(dayResult);
            }

            return ResponseEntity.ok(result);

        } catch (IllegalArgumentException e) {
            // Повернення повідомлення про помилку, якщо щось відсутнє або некоректне
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            // Загальна обробка помилок
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    @PostMapping("/api/reserve/report")
    public ResponseEntity<?> generateReport(@RequestBody Map<String, Object> request) {
        try {
            Integer reserveId = (Integer) request.get("reserveId");
            if (reserveId == null) {
                throw new IllegalArgumentException("Reserve ID is required.");
            }

            // Отримуємо дані про заповідник
            ProtectedArea reserve = protectedAreaService.getReserveById(reserveId);
            if (reserve == null) {
                return ResponseEntity.badRequest().body("Заповідник не знайдено.");
            }

            // Отримуємо погодні дані
            List<WeatherCondition> weatherConditions = weatherConditionsService.getConditionsByReserveId(reserveId);

            // Генеруємо PDF
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Заголовок
            document.add(new Paragraph("Звіт про заповідник: " + reserve.getName(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("\n"));

            // Основна інформація
            document.add(new Paragraph("Опис: " + reserve.getDescription()));
            document.add(new Paragraph("Розмір: " + reserve.getArea() + " га"));
            document.add(new Paragraph("Тип: " + reserve.getType()));
            document.add(new Paragraph("Статус: " + reserve.getStatus()));
            document.add(new Paragraph("Дата створення: " + reserve.getDateEstablished()));
            document.add(new Paragraph("\n"));

            // Погодні дані
            document.add(new Paragraph("Погодні дані:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            for (WeatherCondition condition : weatherConditions) {
                document.add(new Paragraph("Дата: " + condition.getDate()));
                document.add(new Paragraph("  Температура: " + condition.getTemperature() + "°C"));
                document.add(new Paragraph("  Опади: " + condition.getPrecipitation() + " мм"));
                document.add(new Paragraph("  Швидкість вітру: " + condition.getWindSpeed() + " км/год"));
                document.add(new Paragraph("  Вологість: " + condition.getHumidity() + "%"));
                document.add(new Paragraph("\n"));
            }

            // Рекомендації на основі оцінки
            document.add(new Paragraph("Рекомендації:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            String evaluation = FuzzyLogicService.evaluateDay(
                    weatherConditions.get(0).getTemperature(),
                    weatherConditions.get(0).getPrecipitation(),
                    weatherConditions.get(0).getWindSpeed(),
                    weatherConditions.get(0).getHumidity(),
                    weatherConditions.get(0).getSolarRadiation(),
                    weatherConditions.get(0).getAirPressure(),
                    weatherConditions.get(0).getAirQualityIndex(),
                    weatherConditions.get(0).getCloudCover(),
                    weatherConditions.get(0).getPrecipitationIntensity()
            );
            document.add(new Paragraph("Оцінка стану: " + evaluation));
            if (evaluation.equals("Екологічна катастрофа")) {
                document.add(new Paragraph("Рекомендації: Негайне втручання. Провести реновацію екосистеми."));
            } else if (evaluation.equals("Критична загроза")) {
                document.add(new Paragraph("Рекомендації: Моніторинг і впровадження заходів для стабілізації."));
            } else if (evaluation.equals("Проблемний стан")) {
                document.add(new Paragraph("Рекомендації: Регулярний контроль і обмеження людського впливу."));
            } else if (evaluation.equals("Задовільний стан")) {
                document.add(new Paragraph("Рекомендації: Підтримувати регулярний моніторинг."));
            } else {
                document.add(new Paragraph("Рекомендації: Підтримувати поточний стан території."));
            }

            document.close();

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=report.pdf")
                    .body(out.toByteArray());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не вдалося згенерувати звіт: " + e.getMessage());
        }
    }
}
