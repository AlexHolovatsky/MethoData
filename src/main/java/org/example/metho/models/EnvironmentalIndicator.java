package org.example.metho.models;
import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "Environmental_Indicators")
public class EnvironmentalIndicator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "protected_area_id", nullable = false)
    private ProtectedArea protectedArea;

    private String indicatorName;

    private double value;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date measurementDate;

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

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getMeasurementDate() {
        return measurementDate;
    }

    public void setMeasurementDate(Date measurementDate) {
        this.measurementDate = measurementDate;
    }

}