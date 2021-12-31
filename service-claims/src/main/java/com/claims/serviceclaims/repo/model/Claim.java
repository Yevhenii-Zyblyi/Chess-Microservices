package com.claims.serviceclaims.repo.model;

import javax.persistence.*;

@Entity
@Table(name = "report")
public final class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private long id;

    private String reason;
    private String description;

    @Column(name = "violator_id")
    private long violatorId;

    public Claim() {
    }

    public Claim(String reason, String description, long violatorId) {
        this.reason = reason;
        this.description = description;
        this.violatorId = violatorId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getViolator_id() {
        return violatorId;
    }

    public void setViolator_id(long violator_id) {
        this.violatorId = violator_id;
    }
}
