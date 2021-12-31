package com.games.servicegames.repo.model;

import javax.persistence.*;

@Entity
@Table(name = "game")
public final class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private long id;

    @Column(name = "w_id")
    private long whiteId;

    @Column(name = "b_id")
    private long blackId;

    private String status;
    private String result;
    private long duration;
    private String variant;

    @Column(name = "time_control")
    private String timeControl;

    public Game() {}
    public Game(long whiteId, long blackId, String status, String result, long duration, String variant, String timeControl) {
        this.whiteId = whiteId;
        this.blackId = blackId;
        this.status = status;
        this.result = result;
        this.duration = duration;
        this.variant = variant;
        this.timeControl = timeControl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWhiteId() {
        return whiteId;
    }

    public void setWhiteId(long whiteId) {
        this.whiteId = whiteId;
    }

    public long getBlackId() {
        return blackId;
    }

    public void setBlackId(long blackId) {
        this.blackId = blackId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getTimeControl() {
        return timeControl;
    }

    public void setTimeControl(String timeControl) {
        this.timeControl = timeControl;
    }
}
