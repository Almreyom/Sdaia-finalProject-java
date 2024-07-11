package org.example.dto;

import jakarta.ws.rs.QueryParam;

import java.time.LocalDateTime;

public class MedicalRecordFilterDTO {
    private @QueryParam("patientId") Integer patientId;
    private @QueryParam("fromDate") LocalDateTime fromDate;
    private @QueryParam("toDate") LocalDateTime toDate;

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }
}