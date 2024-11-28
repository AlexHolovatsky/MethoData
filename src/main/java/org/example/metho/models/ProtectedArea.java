package org.example.metho.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@TypeDef(name = "json", typeClass = JsonType.class)
@Entity
@Table(name = "Protected_Areas")
public class ProtectedArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Temporal(TemporalType.DATE)
    private Date dateEstablished;

    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "protectedArea", cascade = CascadeType.ALL)
    private List<FloraAndFauna> floraAndFauna;

    @JsonIgnore
    @OneToMany(mappedBy = "protectedArea", cascade = CascadeType.ALL)
    private List<MonitoringReport> monitoringReports;

    @Type(type = "json")
    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String location;

    private double area;

    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateEstablished() {
        return dateEstablished;
    }

    public void setDateEstablished(Date dateEstablished) {
        this.dateEstablished = dateEstablished;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FloraAndFauna> getFloraAndFauna() {
        return floraAndFauna;
    }

    public void setFloraAndFauna(List<FloraAndFauna> floraAndFauna) {
        this.floraAndFauna = floraAndFauna;
    }

    public List<MonitoringReport> getMonitoringReports() {
        return monitoringReports;
    }

    public void setMonitoringReports(List<MonitoringReport> monitoringReports) {
        this.monitoringReports = monitoringReports;
    }

}
