package org.example.metho.services;

public class FuzzyLogicService {

    // Температура: низька і висока
    public static double[] fuzzyTemperature(double temperature) {
        double low = Math.max(0, Math.min(1, (10 - temperature) / 10)); // Низька: < 10°C
        double high = Math.max(0, Math.min(1, (temperature - 15) / 10)); // Висока: > 15°C
        return new double[]{low, high};
    }

    // Опади: сухо і дощ/сніг
    public static double[] fuzzyPrecipitation(double precipitation) {
        double dry = Math.max(0, Math.min(1, (5 - precipitation) / 5)); // Сухо: < 5 mm
        double wet = Math.max(0, Math.min(1, (precipitation - 10) / 10)); // Волога: > 10 mm
        return new double[]{dry, wet};
    }

    // Швидкість вітру: слабкий і сильний
    public static double[] fuzzyWindSpeed(double windSpeed) {
        double weak = Math.max(0, Math.min(1, (10 - windSpeed) / 10)); // Слабкий: < 10 km/h
        double strong = Math.max(0, Math.min(1, (windSpeed - 15) / 10)); // Сильний: > 15 km/h
        return new double[]{weak, strong};
    }

    // Вологість: низька і висока
    public static double[] fuzzyHumidity(double humidity) {
        double low = Math.max(0, Math.min(1, (50 - humidity) / 50)); // Низька: < 50%
        double high = Math.max(0, Math.min(1, (humidity - 70) / 30)); // Висока: > 70%
        return new double[]{low, high};
    }

    // Сонячна радіація: низька і висока
    public static double[] fuzzySolarRadiation(double solarRadiation) {
        double low = Math.max(0, Math.min(1, (200 - solarRadiation) / 200)); // Низька: < 200 W/m²
        double high = Math.max(0, Math.min(1, (solarRadiation - 400) / 200)); // Висока: > 400 W/m²
        return new double[]{low, high};
    }

    // Тиск повітря: низький і високий
    public static double[] fuzzyAirPressure(double airPressure) {
        double low = Math.max(0, Math.min(1, (1010 - airPressure) / 10)); // Низький: < 1010 hPa
        double high = Math.max(0, Math.min(1, (airPressure - 1013) / 10)); // Високий: > 1013 hPa
        return new double[]{low, high};
    }

    // Індекс якості повітря: хороший і поганий
    public static double[] fuzzyAirQualityIndex(double airQualityIndex) {
        double good = Math.max(0, Math.min(1, (50 - airQualityIndex) / 50)); // Хороший: < 50
        double bad = Math.max(0, Math.min(1, (airQualityIndex - 30) / 70)); // Поганий: > 30
        return new double[]{good, bad};
    }

    // Покриття хмарами: мало і багато
    public static double[] fuzzyCloudCover(double cloudCover) {
        double low = Math.max(0, Math.min(1, (50 - cloudCover) / 50)); // Мало: < 50%
        double high = Math.max(0, Math.min(1, (cloudCover - 30) / 70)); // Багато: > 30%
        return new double[]{low, high};
    }

    // Інтенсивність опадів: слабка і сильна
    public static double[] fuzzyPrecipitationIntensity(double precipitationIntensity) {
        double weak = Math.max(0, Math.min(1, (2 - precipitationIntensity) / 2)); // Слабка: < 2 mm/h
        double strong = Math.max(0, Math.min(1, (precipitationIntensity - 1) / 3)); // Сильна: > 1 mm/h
        return new double[]{weak, strong};
    }

    // Оцінка стану території
    public static String evaluateDay(double temperature, double precipitation, double windSpeed,
                                     double humidity, double solarRadiation, double airPressure,
                                     double airQualityIndex, double cloudCover, double precipitationIntensity) {
        double[] temp = fuzzyTemperature(temperature);
        double[] prec = fuzzyPrecipitation(precipitation);
        double[] wind = fuzzyWindSpeed(windSpeed);
        double[] hum = fuzzyHumidity(humidity);
        double[] solar = fuzzySolarRadiation(solarRadiation);
        double[] pressure = fuzzyAirPressure(airPressure);
        double[] airQuality = fuzzyAirQualityIndex(airQualityIndex);
        double[] clouds = fuzzyCloudCover(cloudCover);
        double[] precIntensity = fuzzyPrecipitationIntensity(precipitationIntensity);

        // Логіка оцінки
        double ecologicalCatastrophe = Math.max(prec[1], Math.max(wind[1], Math.max(hum[0], airQuality[1])));
        double criticalThreat = Math.max(temp[1], Math.max(prec[1], Math.max(clouds[1], precIntensity[1])));
        double problematicState = Math.min(temp[1], Math.min(wind[0], Math.min(hum[1], solar[0])));
        double satisfactoryState = Math.min(temp[0], Math.min(prec[0], wind[0]));
        double healthyState = Math.min(hum[1], Math.min(solar[1], pressure[1]));

        if (ecologicalCatastrophe > 0.6) return "Екологічна катастрофа. Незворотні зміни, необхідна повна реконструкція середовища";
        if (criticalThreat > 0.6) return "Критична загроза. Порушення екосистеми, зниження чисельності видів";
        if (problematicState > 0.6) return "Проблемний стан. Виявлені загрози для флори/фауни";
        if (satisfactoryState > 0.6) return "Задовільний стан. Усі ключові параметри стабільні, але потрібно покращення";
        return "Здоровий стан. Оптимальні умови для існування видів";
    }
}
