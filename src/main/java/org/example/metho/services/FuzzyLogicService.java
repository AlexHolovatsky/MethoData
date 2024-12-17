package org.example.metho.services;

public class FuzzyLogicService {

    // Температура: низька і висока
    public static double[] fuzzyTemperature(double temperature) {
        double low = Math.max(0, Math.min(1, (15 - temperature) / 15)); // Низька: < 15°C
        double high = Math.max(0, Math.min(1, (temperature - 5) / 15)); // Висока: > 5°C
        return new double[]{low, high};
    }

    // Опади: сухо і дощ/сніг
    public static double[] fuzzyPrecipitation(double precipitation) {
        double dry = Math.max(0, Math.min(1, (5 - precipitation) / 5)); // Сухо: < 5 mm
        double wet = Math.max(0, Math.min(1, precipitation / 10));     // Волога: > 10 mm
        return new double[]{dry, wet};
    }

    // Швидкість вітру: слабкий і сильний
    public static double[] fuzzyWindSpeed(double windSpeed) {
        double weak = Math.max(0, Math.min(1, (10 - windSpeed) / 10)); // Слабкий: < 10 km/h
        double strong = Math.max(0, Math.min(1, (windSpeed - 8) / 12)); // Сильний: > 8 km/h
        return new double[]{weak, strong};
    }

    // Оцінка стану території
    public static String evaluateDay(double temperature, double precipitation, double windSpeed) {
        double[] temp = fuzzyTemperature(temperature);
        double[] prec = fuzzyPrecipitation(precipitation);
        double[] wind = fuzzyWindSpeed(windSpeed);

        double stateGood = Math.min(temp[0], Math.min(prec[0], wind[0])); // Хороший стан
        double stateNormal = Math.min(temp[1], Math.min(prec[0], wind[0])); // Нормальний стан
        double stateCritical = Math.max(prec[1], wind[1]); // Критичний стан

        if (stateCritical > 0.5) return "Критичний";
        if (stateNormal > 0.5) return "Нормальний";
        return "Хороший";
    }
}
