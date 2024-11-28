package org.example.metho.models;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Visitors")
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date visitDate;

    @ManyToOne
    @JoinColumn(name = "protected_area_id", nullable = false)
    private ProtectedArea protectedArea;

    private String purpose;

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

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public ProtectedArea getProtectedArea() {
        return protectedArea;
    }

    public void setProtectedArea(ProtectedArea protectedArea) {
        this.protectedArea = protectedArea;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
