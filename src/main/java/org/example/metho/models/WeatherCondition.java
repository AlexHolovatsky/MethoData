package org.example.metho.models;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Weather_Conditions")
public class WeatherCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "protected_area_id", nullable = false)
    private ProtectedArea protectedArea;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    private double temperature;

    private double precipitation;

    private double windSpeed;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private double humidity; // Вологість

    private double solarRadiation; // Сонячна радіація

    private double airPressure; // Тиск повітря

    private int airQualityIndex; // Індекс якості повітря

    private double cloudCover; // Покриття хмарами

    private double precipitationIntensity; // Інтенсивність опадів

    // Геттери та сеттери
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProtectedArea getProtectedArea() {
        return protectedArea;
    }

    public void setProtectedArea(ProtectedArea protectedArea) {
        this.protectedArea = protectedArea;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getSolarRadiation() {
        return solarRadiation;
    }

    public void setSolarRadiation(double solarRadiation) {
        this.solarRadiation = solarRadiation;
    }

    public double getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(double airPressure) {
        this.airPressure = airPressure;
    }

    public int getAirQualityIndex() {
        return airQualityIndex;
    }

    public void setAirQualityIndex(int airQualityIndex) {
        this.airQualityIndex = airQualityIndex;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public double getPrecipitationIntensity() {
        return precipitationIntensity;
    }

    public void setPrecipitationIntensity(double precipitationIntensity) {
        this.precipitationIntensity = precipitationIntensity;
    }
}
