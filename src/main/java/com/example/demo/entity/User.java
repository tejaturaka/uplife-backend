package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;
    private String name;
    private String password;
    private String role; // admin, agent, or user
    private String state;
    private String dist;
    private String mandal;
    private String createdBy; 
    
    private Integer claimStatus = 0; 
    private String statusMessage = "IN_PROGRESS"; 

    public User() {}

    // Manual Getters and Setters to avoid Eclipse/Lombok errors
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getDist() { return dist; }
    public void setDist(String dist) { this.dist = dist; }

    public String getMandal() { return mandal; }
    public void setMandal(String mandal) { this.mandal = mandal; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public Integer getClaimStatus() { return claimStatus; }
    public void setClaimStatus(Integer claimStatus) { this.claimStatus = claimStatus; }

    public String getStatusMessage() { return statusMessage; }
    public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }
}