package org.example.metho.models;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Monitoring_Reports")
public class MonitoringReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "protected_area_id", nullable = false)
    private ProtectedArea protectedArea;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Column(columnDefinition = "TEXT")
    private String issues;

    @Column(columnDefinition = "TEXT")
    private String recommendations;

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

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getIssues() {
        return issues;
    }

    public void setIssues(String issues) {
        this.issues = issues;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }
}
