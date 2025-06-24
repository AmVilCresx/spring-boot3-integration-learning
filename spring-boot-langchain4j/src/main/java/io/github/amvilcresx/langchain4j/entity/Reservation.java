package io.github.amvilcresx.langchain4j.entity;

import java.time.LocalDateTime;

public class Reservation {

    private Long id;

    private String name;

    private String gender;

    private String phone;

    private LocalDateTime communicationTime;

    private String province;

    private Integer estimatedScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCommunicationTime() {
        return communicationTime;
    }

    public void setCommunicationTime(LocalDateTime communicationTime) {
        this.communicationTime = communicationTime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getEstimatedScore() {
        return estimatedScore;
    }

    public void setEstimatedScore(Integer estimatedScore) {
        this.estimatedScore = estimatedScore;
    }
}
