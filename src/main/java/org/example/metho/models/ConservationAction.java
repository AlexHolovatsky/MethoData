package org.example.metho.models;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Conservation_Actions")
public class ConservationAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "protected_area_id", nullable = false)
    private ProtectedArea protectedArea;

    private String actionType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Temporal(TemporalType.DATE)
    private Date dateImplemented;

    private String status;

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

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateImplemented() {
        return dateImplemented;
    }

    public void setDateImplemented(Date dateImplemented) {
        this.dateImplemented = dateImplemented;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
