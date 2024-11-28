package org.example.metho.models;
import javax.persistence.*;

@Entity
@Table(name = "Personnel")
public class Personnel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;

    private String role;

    private String contactInfo;

    @ManyToOne
    @JoinColumn(name = "assigned_area", nullable = false)
    private ProtectedArea assignedArea;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public ProtectedArea getAssignedArea() {
        return assignedArea;
    }

    public void setAssignedArea(ProtectedArea assignedArea) {
        this.assignedArea = assignedArea;
    }
}
